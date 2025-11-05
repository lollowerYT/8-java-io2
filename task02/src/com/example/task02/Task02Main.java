package com.example.task02;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Task02Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println(listFiles(Paths.get("task02/src/main/resources/")));
    }

    public static List<Path> listFiles(Path rootDir) throws IOException, InterruptedException {
        try (Stream<Path> stream = Files.walk(rootDir)) { // рекурсивный обход
            return stream
                    .filter(Files::isRegularFile) // оставляем только файлы
                    .collect(Collectors.toList());
        }
    }
}
