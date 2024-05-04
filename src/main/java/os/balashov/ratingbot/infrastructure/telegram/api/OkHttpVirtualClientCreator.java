package os.balashov.ratingbot.infrastructure.telegram.api;

import lombok.AllArgsConstructor;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.util.TelegramOkHttpClientFactory;

@Component
@AllArgsConstructor
public class OkHttpVirtualClientCreator extends TelegramOkHttpClientFactory.DefaultOkHttpClientCreator {
    private final Dispatcher dispatcher;

    @Override
    public OkHttpClient get() {
        return getBaseClient().dispatcher(dispatcher).build();
    }
}
