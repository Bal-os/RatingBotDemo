package os.balashov.ratingbot.core.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ThreadFactory;

@EnableAsync
@Configuration
@EnableAspectJAutoProxy
public class SpringConfig {
    @Bean
    public ApplicationEventMulticaster applicationEventMulticaster(ThreadFactory threadFactory) {
        var executor = new SimpleAsyncTaskExecutor();
        executor.setThreadFactory(threadFactory);

        var eventMulticaster = new SimpleApplicationEventMulticaster();
        eventMulticaster.setTaskExecutor(executor);
        return eventMulticaster;
    }
}
