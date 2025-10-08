package com.naumen;

import java.io.IOException;

public interface FileDownloader {
    boolean downloadFile(String fileUrl, String outputPath)
            throws IOException, InterruptedException;
}