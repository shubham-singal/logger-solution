package logger;

import logger.sink.Sink;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Logger {
    private final Map<LogLevel, Sink> sinks;

    public Logger(Map<LogLevel, Sink> sinks) {
        if (sinks == null) {
            throw new IllegalArgumentException("Sinks cannot be null");
        }

        this.sinks =  new HashMap<>(sinks);
    }

    public void log(String content, LogLevel level, String namespace) {

        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Content cannot be empty");
        }

        if (level == null) {
            throw new IllegalArgumentException("Log level cannot be null");
        }

        if (namespace == null || namespace.isBlank()) {
            throw new IllegalArgumentException("Namespace cannot be empty");
        }

        Sink sink = sinks.get(level);

        if(sink == null) {
            return;
        }

        LogEvent event = new LogEvent(content, level, namespace, LocalDateTime.now());
        sink.write(event);
    }
}
