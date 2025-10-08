package com.naumen;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicBoolean;

public class FileDownloaderTask implements Task, TaskRunnable {
    private final String fileUrl;
    private final String outputPath;
    private final AtomicBoolean isRunning;
    private final AtomicBoolean isCompleted;
    private final FileDownloader fileDownloader;
    private Thread downloadThread;

    public FileDownloaderTask(String fileUrl, String outputPath) {
        this(fileUrl, outputPath, new HttpClientFileDownloader());
    }

    public FileDownloaderTask(String fileUrl, String outputPath, FileDownloader fileDownloader) {
        this.fileUrl = fileUrl;
        this.outputPath = outputPath;
        this.fileDownloader = fileDownloader;
        this.isRunning = new AtomicBoolean(false);
        this.isCompleted = new AtomicBoolean(false);
    }

    @Override
    public void start() {
        if (isRunning.get()) {
            System.out.println("Download is already in progress");
            return;
        }
        isRunning.set(true);
        isCompleted.set(false);
        downloadThread = new Thread(this::downloadFile);
        downloadThread.start();
        System.out.println("Download started: " + fileUrl);
    }

    @Override
    public void stop() {
        if (!isRunning.getAndSet(false)) {
            System.out.println("Download is not running");
            return;
        }
        System.out.println("Stopping download...");
        if (downloadThread != null && downloadThread.isAlive()) {
            downloadThread.interrupt();
            try {
                downloadThread.join(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if (!isCompleted.get()) {
            try {
                boolean deleted = Files.deleteIfExists(Path.of(outputPath));
                System.out.println(deleted ? "Partially downloaded file removed"
                        : "File not found or already deleted");
            } catch (Exception e) {
                System.out.println("Could not remove partial file: " + e.getMessage());
            }
        } else {
            System.out.println("Download was completed successfully, file preserved: " + outputPath);
        }
    }

    private void downloadFile() {
        try {
            boolean success = fileDownloader.downloadFile(fileUrl, outputPath);
            if (success) {
                System.out.println("Download completed successfully!");
                System.out.println("File saved as: " + outputPath);
                isCompleted.set(true);
            } else {
                System.out.println("Download failed - HTTP error");
            }
        } catch (Exception e) {
            if (isRunning.get()) {
                System.out.println("Download error: " + e.getMessage());
            }
        } finally {
            isRunning.set(false);
        }
    }

    private void runFileDownload() {
        start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        stop();
    }

    @Override
    public void run() {
        runFileDownload();
    }
}