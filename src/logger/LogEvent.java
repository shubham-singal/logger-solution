package logger;

import java.time.LocalDateTime;

public class LogEvent {

    private final String content;
    private final LogLevel level;
    private final String namespace;
    private final LocalDateTime timestamp;
    private final String trackingId;
    private final String instanceId;

    public LogEvent(String content, LogLevel level,
                    String namespace, LocalDateTime timestamp, String trackingId, String instanceId) {
        this.content = content;
        this.level = level;
        this.namespace = namespace;
        this.timestamp = timestamp;
        this.trackingId = trackingId;
        this.instanceId = instanceId;

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

    public String getTrackingId() {
        return trackingId;
    }

    public String getInstanceId() {
        return instanceId;
    }
}