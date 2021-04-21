package ru.tlgrmbot.myhelperbot.appconfig;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.tlgrmbot.myhelperbot.MyHelperTelegramBot;
import ru.tlgrmbot.myhelperbot.botapi.TelegramFacade;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "telegrambot")
public class BotConfig {
    private String webHookPath;
    private String botUserName;
    private String botToken;

    @Bean
    public MyHelperTelegramBot MyTelegramBot(TelegramFacade telegramFacade) {
        MyHelperTelegramBot myHelperTelegramBot = new MyHelperTelegramBot(telegramFacade);
        myHelperTelegramBot.setBotUserName(botUserName);
        myHelperTelegramBot.setBotToken(botToken);
        myHelperTelegramBot.setWebHookPath(webHookPath);

        return myHelperTelegramBot;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageResource
                = new ReloadableResourceBundleMessageSource();

        messageResource.setBasename("classpath:messages");
        messageResource.setDefaultEncoding("UTF-8");
        return messageResource;
    }
}
