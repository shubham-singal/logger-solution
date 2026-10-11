package logger;

import logger.sink.Sink;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Logger {
    private final Map<LogLevel, Sink> sinks;
    private final String instanceId;

    public Logger(Map<LogLevel, Sink> sinks, String instanceId) {
        if (sinks == null) {
            throw new IllegalArgumentException("Sinks cannot be null");
        }

        this.sinks =  new HashMap<>(sinks);

        if(instanceId == null || instanceId.isBlank()) {
            throw new IllegalArgumentException("Please provide instanceId");
        }

        this.instanceId = instanceId;
    }



    public void log(String content, LogLevel level, String namespace, String txnId) {

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

        LogEvent event = new LogEvent(content, level, namespace, LocalDateTime.now(), txnId, instanceId);
        try {
            sink.write(event);
        } catch(RuntimeException e) {
            System.err.println("Logging failed for level " + level + e.getMessage());
        }

    }
}
