package os.balashov.ratingbot.core.common.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.function.Function;

@Slf4j
@Getter
@EnableAsync
@Configuration
@RequiredArgsConstructor
public class MultiThreadingConfig {
    @Value("${spring.threads.virtual.enabled}")
    private boolean useVirtualMultithreading;
    @Value("${spring.threads.virtual.core-pool-size}")
    private int virtualPoolSize;

    @PostConstruct
    public void init() {
        log.info("Virtual multithreading is {}", useVirtualMultithreading ? "enabled" : "disabled");
    }

    @Bean
    public ThreadFactory threadFactory() {
        var builder = useVirtualMultithreading ? Thread.ofVirtual() : Thread.ofPlatform();
        return builder.factory();
    }

    @Bean
    public Function<ThreadFactory, ExecutorService> executorServiceHandler() {
        return useVirtualMultithreading ? Executors::newThreadPerTaskExecutor : Executors::newCachedThreadPool;
    }

    @Bean
    public ExecutorService executorService(ThreadFactory threadFactory,
                                           Function<ThreadFactory, ExecutorService> executorServiceHandler) {
        return executorServiceHandler.apply(threadFactory);
    }

    @Bean
    public ScheduledExecutorService scheduledExecutorService(ThreadFactory threadFactory) {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        int poolSize = useVirtualMultithreading ? availableProcessors : Math.max(virtualPoolSize, availableProcessors);
        return Executors.newScheduledThreadPool(poolSize, threadFactory);
    }
}
