package open.seth.springnativediscordbot.config;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.internal.utils.cache.AbstractCacheView;
import open.seth.springnativediscordbot.commands.music.recommendations.MusicRecommendations;
import net.dv8tion.jda.internal.JDAImpl;
import net.dv8tion.jda.internal.entities.GuildImpl;
import open.seth.springnativediscordbot.commands.music.recommendations.helper.MusicHelper;
import org.springframework.aop.SpringProxy;
import org.springframework.aop.framework.Advised;
import org.springframework.aot.hint.*;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.core.DecoratingProxy;
import org.springframework.stereotype.Component;

@Component
@ImportRuntimeHints(AOTHintsConfig.ReflectionHintsRegistrar.class)
public class AOTHintsConfig {
    static class ReflectionHintsRegistrar implements RuntimeHintsRegistrar {

        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            hints.reflection().registerType(GuildImpl.class);
            hints.reflection().registerType(Guild.class);
            hints.reflection().registerType(AbstractCacheView.class);
            hints.reflection().registerType(JDABuilder.class);
            try {
//                hints.reflection().registerConstructor(GuildImpl.class.getConstructor(JDAImpl.class, int.class), ExecutableMode.INTROSPECT);
//                hints.reflection().registerMethod(GuildImpl.class.getMethod());
                hints.reflection().registerMethod(JDABuilder.class.getMethod("build"), ExecutableMode.INTROSPECT);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            hints.reflection().registerType(MusicRecommendations.class);
            hints.reflection().registerType(MusicHelper.class);

            hints.proxies().registerJdkProxy(SpringProxy.class);
            hints.proxies().registerJdkProxy(Advised.class);
            hints.proxies().registerJdkProxy(DecoratingProxy.class);
        }
    }

}
