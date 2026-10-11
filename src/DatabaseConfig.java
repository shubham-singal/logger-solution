import javax.sql.DataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseConfig {

    public static DataSource createDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("");
        config.setUsername();
        config.setPassword();
        config.setMaximumPoolSize();

        return new HikariDataSource(config);
    }
}
