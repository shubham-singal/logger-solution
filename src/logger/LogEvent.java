package logger;

import java.time.LocalDateTime;

public class LogEvent {

    private final String content;
    private final LogLevel level;
    private final String namespace;
    private final LocalDateTime timestamp;

    public LogEvent(String content, LogLevel level,
                    String namespace, LocalDateTime timestamp) {
        this.content = content;
        this.level = level;
        this.namespace = namespace;
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public LogLevel getLevel() {
        return level;
    }

    public String getNamespace() {
        return namespace;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}