package os.balashov.ratingbot.infrastructure.sql.mssql.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "spring.datasource.driver-class-name", havingValue = "com.microsoft.sqlserver.jdbc.SQLServerDriver")
public class RepositoryConfig {

}
