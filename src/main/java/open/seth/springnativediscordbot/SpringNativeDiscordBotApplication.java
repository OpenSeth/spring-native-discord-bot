package open.seth.springnativediscordbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@NativeHint(
//        trigger = Guild.class,
//        types = {
//                @TypeHint(types = { Instant[].class, ZonedDateTime[].class, URI[].class }, access = {}),
//        }
//)
public class SpringNativeDiscordBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringNativeDiscordBotApplication.class, args);
    }

}
