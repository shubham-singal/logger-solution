package logger.sink;

import logger.LogEvent;
import logger.logformatter.LogFormatter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.zip.GZIPOutputStream;

public class FileSink implements Sink {

    private final Path filePath;
    private final LogFormatter formatter;
    private final long maxFileSize;

    public FileSink(Path filePath, LogFormatter formatter, long maxFileSize) {

        if (filePath == null) {
            throw new IllegalArgumentException("File path cannot be null");
        }

        if (formatter == null) {
            throw new IllegalArgumentException("Formatter cannot be null");
        }

        if (maxFileSize <= 0) {
            throw new IllegalArgumentException("Max file size must be greater than 0");
        }

        this.filePath = filePath;
        this.formatter = formatter;
        this.maxFileSize = maxFileSize;
    }

    @Override
    public synchronized void write(LogEvent event) {

        String log = formatter.format(event) + System.lineSeparator();
        byte[] data = log.getBytes(StandardCharsets.UTF_8);

        try {
            if (Files.exists(filePath)) {
                long currentSize = Files.size(filePath);

                if (currentSize + data.length > maxFileSize) {
                    rotate();
                }
            }
            Files.write(filePath, data, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write log", e);
        }
    }

    private void rotate() throws IOException {

        Path backupPath = getNextBackupPath();
        compressFile(backupPath);
        Files.delete(filePath);
    }

    private void compressFile(Path backupPath) throws IOException {

        FileInputStream input = null;
        GZIPOutputStream output = null;
        try {
            input = new FileInputStream(filePath.toFile());
            output = new GZIPOutputStream(new FileOutputStream(backupPath.toFile()));

            byte[] buffer = new byte[1024];
            int count = input.read(buffer);

            while (count != -1) {
                output.write(buffer, 0, count);
                count = input.read(buffer);
            }
        }  finally {
            if(output != null) {
                output.close();
            }

            if(input != null) {
                input.close();
            }
        }
    }

    private Path getNextBackupPath() {

        int backupNumber = 1;
        Path backupPath = Path.of(filePath.toString() + "." + backupNumber + ".gz");
        while (Files.exists(backupPath)) {
            backupNumber++;
            backupPath = Path.of(filePath.toString() + "." + backupNumber + ".gz");
        }

        return backupPath;
    }
}