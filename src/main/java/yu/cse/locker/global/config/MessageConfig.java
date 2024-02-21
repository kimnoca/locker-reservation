package yu.cse.locker.global.config;

import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
@RequiredArgsConstructor
public class MessageConfig {

    private final String MESSAGE_API_KEY = "NCS0MHP6VULVVU7Q";
    private final String MESSAGE_API_SECRET_KEY = "OV1NM0NPZH2LOSTHFSNHAVB70ZVMBAE4";
    private final String MESSAGE_API_DOMAIN = "https://api.coolsms.co.kr";


    @Bean
    public DefaultMessageService messageService() {
        return NurigoApp.INSTANCE.initialize(MESSAGE_API_KEY, MESSAGE_API_SECRET_KEY, MESSAGE_API_DOMAIN);
    }

}
