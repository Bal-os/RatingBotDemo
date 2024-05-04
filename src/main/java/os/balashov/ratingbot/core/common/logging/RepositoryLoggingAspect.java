package os.balashov.ratingbot.core.common.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

@Slf4j
@Aspect
@Component
public class RepositoryLoggingAspect {
    private final ExecutorService executorService;

    public RepositoryLoggingAspect(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Before("@within(org.springframework.stereotype.Repository)")
    public void logMethodCall(JoinPoint joinPoint) {
        executorService.submit(() -> printLog(joinPoint));
    }

    private void printLog(JoinPoint joinPoint) {
        String loggingString = getLog(joinPoint);
        log.info(loggingString);
    }

    public String getLog(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        return createMessageFromMethodName(className, methodName, args);
    }

    private String createMessageFromMethodName(String className, String methodName, Object[] args) {
        StringBuilder stringBuilder = new StringBuilder("Try to ");
        for (int i = 0; i < methodName.length(); i++) {
            char c = methodName.charAt(i);
            if (Character.isUpperCase(c) && i > 0) {
                stringBuilder.append(' ').append(Character.toLowerCase(c));
            } else {
                stringBuilder.append(c);
            }
        }
        stringBuilder.append(" :");
        for (Object arg : args) {
            stringBuilder.append(" ").append(arg);
        }
        stringBuilder.append(" in ").append(className);
        return stringBuilder.toString();
    }
}