package logger.logformatter;

import logger.LogEvent;

import java.time.format.DateTimeFormatter;

public class DefaultLogFormatter implements LogFormatter {
    private final DateTimeFormatter formatter;

    public DefaultLogFormatter(String timestampFormat) {
        this.formatter = DateTimeFormatter.ofPattern(timestampFormat);
    }

    @Override
    public String format(LogEvent event) {
        String trackingId;
        if(event.getTrackingId() == null || event.getTrackingId().isBlank()) {
            trackingId = "[]";
        } else {
            trackingId = "[ " + event.getTrackingId() + " ]";
        }
        return new StringBuilder().append(event.getInstanceId()).append(trackingId).append(event.getLevel()).append(" [").append(event.getTimestamp().format(formatter)).append("] [").append(event.getNamespace())
                .append("] ").append(event.getContent()).toString();
    }
}
