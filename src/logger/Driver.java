package logger;

import logger.logformatter.LogFormatter;
import logger.logformatter.DefaultLogFormatter;
import logger.sink.FileSink;
import logger.sink.Sink;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Driver {

    public static void main(String[] args) {

        LogFormatter formatter = new DefaultLogFormatter("dd:mm:yyyy hh:mm:ss");

        Sink fileSink = new FileSink(Path.of("application.log"), formatter, 1024 * 1024);

        Map<LogLevel, Sink> sinks = new HashMap<>();

        sinks.put(LogLevel.INFO, fileSink);
        sinks.put(LogLevel.WARN, fileSink);
        sinks.put(LogLevel.ERROR, fileSink);

        Logger logger = new Logger(sinks);
        logger.log("Transaction completed", LogLevel.INFO, "transaction-service");
        logger.log("User not found", LogLevel.WARN, "user-service");
        logger.log("Payment failed", LogLevel.ERROR, "payment-service");
    }
}