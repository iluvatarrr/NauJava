package com.naumen;

import java.util.List;
import java.util.Map;

import static com.naumen.Commands.*;

public class Main {
    private final static Map<Commands ,TaskRunnable> tasks = Map.of(
            HTTP, new HttpTask(),
            SALARY, new AverageSalaryTask(List.of()),
            DOWNLOAD, new FileDownloaderTask(
                    "https://arppsoft.ru/upload/iblock/11e/naumen_new.png",
                    "downloaded_file.png"),
            SORT, new QuickSortTask(),
            MINABS, new MinAbsValueTask());

    public static void main(String[] args) {
        if (args.length == 0) {
            tasks.values().forEach(TaskRunnable::run);
            return;
        }
        switch (args[0].toLowerCase()) {
            case "http" -> tasks.get(HTTP).run();
            case "salary" -> tasks.get(SALARY).run();
            case "download" -> tasks.get(DOWNLOAD).run();
            case "sort" -> tasks.get(SORT).run();
            case "minabs" -> tasks.get(MINABS).run();
            default -> System.out.println("Unknown command: " + args[0]);
        }
    }
}