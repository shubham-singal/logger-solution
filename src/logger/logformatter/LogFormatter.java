package logger.logformatter;

import logger.LogEvent;

public interface LogFormatter {


    String format(LogEvent event);
}
