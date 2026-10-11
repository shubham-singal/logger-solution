package logger.sink;

import logger.LogEvent;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class DBSink implements Sink{
    private final DataSource dataSource;
    private final DateTimeFormatter dateTimeFormatter;

    public DBSink(DataSource dataSource, DateTimeFormatter dateTimeFormatter) {
        if(dataSource == null) {
            throw new IllegalArgumentException("Data source cannot be null");
        }

        this.dataSource = dataSource;
        this.dateTimeFormatter = dateTimeFormatter;

    }

    @Override
    public void write(LogEvent event) {
        String sql = "INSERT INTO logs (level, namespace, timestamp, content) VALUES (?, ?, ?, ?)";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql)) {


            stmt.setString(1, event.getLevel().toString());
            stmt.setString(2, event.getNamespace());
            stmt.setString(3, event.getTimestamp().format(dateTimeFormatter));
            stmt.setString(4, event.getContent());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
