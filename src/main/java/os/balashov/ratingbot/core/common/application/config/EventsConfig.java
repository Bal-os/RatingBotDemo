package os.balashov.ratingbot.core.common.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import java.util.concurrent.ThreadFactory;


@Configuration

public class EventsConfig {
    @Bean
    public ApplicationEventMulticaster applicationEventMulticaster(ThreadFactory threadFactory) {
        var executor = new SimpleAsyncTaskExecutor();
        executor.setThreadFactory(threadFactory);

        var eventMulticaster = new SimpleApplicationEventMulticaster();
        eventMulticaster.setTaskExecutor(executor);
        return eventMulticaster;
    }
}
