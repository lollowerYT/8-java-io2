package com.example.task01;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Task01Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println(extractSoundName(new File("task01/src/main/resources/3727.mp3")));
    }

    public static String extractSoundName(File file) throws IOException, InterruptedException {
        // Формируем команду ffprobe
        ProcessBuilder processBuilder = new ProcessBuilder(
                "ffprobe",
                "-v", "error",
                "-of", "flat",
                "-show_format",
                file.getAbsolutePath()
        );

        processBuilder.redirectErrorStream(true); // объединяем stdout и stderr
        Process process = processBuilder.start();

        // Читаем вывод ffprobe
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {

            String line;
            String title = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("format.tags.title=")) {
                    // пример строки: format.tags.title="Canon trias harmonica a 8"
                    int start = line.indexOf('"');
                    int end = line.lastIndexOf('"');
                    if (start != -1 && end != -1 && end > start) {
                        title = line.substring(start + 1, end);
                    }
                    break;
                }
            }

            // Дожидаемся завершения процесса
            process.waitFor();

            return title;
        }
    }
}
