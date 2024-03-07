package open.seth.springnativediscordbot.config;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.internal.JDAImpl;
import net.dv8tion.jda.internal.entities.GuildImpl;
import org.springframework.aot.hint.*;
import org.springframework.context.annotation.ImportRuntimeHints;

@ImportRuntimeHints(AOTHintsConfig.ReflectionHintsRegistrar.class)
public class AOTHintsConfig {
    static class ReflectionHintsRegistrar implements RuntimeHintsRegistrar {

        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            hints.reflection().registerType(GuildImpl.class);
            hints.reflection().registerType(Guild.class, MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS);
            try {
                hints.reflection().registerConstructor(GuildImpl.class.getConstructor(JDAImpl.class, int.class), ExecutableMode.INTROSPECT);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
