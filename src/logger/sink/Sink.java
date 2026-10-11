package logger.sink;

import logger.LogEvent;

public interface Sink {
    void write(LogEvent event);
}
