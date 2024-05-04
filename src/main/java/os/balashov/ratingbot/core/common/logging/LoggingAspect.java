package os.balashov.ratingbot.core.common.logging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {
    private final ExecutorService executorService;

    @Before("@annotation(loggable)")
    public void logMethodCall(JoinPoint joinPoint, Loggable loggable) {
        executorService.submit(() -> printLog(joinPoint, loggable));
    }

    private void printLog(JoinPoint joinPoint, Loggable loggable) {
        String message = loggable.message();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        if (message.isEmpty()) {
            message = createMessageFromClassName(signature.getDeclaringType().getSimpleName());
            log.info(message + " " + Arrays.toString(args));
            return;
        }
        message = parseMessage(message, args);
        log.info(message);
    }

    private String createMessageFromClassName(String className) {
        StringBuilder stringBuilder = new StringBuilder("Try to ");
        for (int i = 0; i < className.length(); i++) {
            char c = className.charAt(i);
            if (Character.isUpperCase(c) && i > 0) {
                stringBuilder.append(' ').append(Character.toLowerCase(c));
            } else {
                stringBuilder.append(c);
            }
        }
        stringBuilder.append(" with arguments:");
        return stringBuilder.toString();
    }

    private String parseMessage(String message, Object[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            if (c == '{') {
                int j = i + 1;
                while (message.charAt(j) != '}') {
                    j++;
                }
                String argIndex = message.substring(i + 1, j);
                stringBuilder.append(args[Integer.parseInt(argIndex) - 1]);
                i = j;
            } else {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }
}
