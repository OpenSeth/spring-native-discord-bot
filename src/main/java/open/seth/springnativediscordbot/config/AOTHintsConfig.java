package open.seth.springnativediscordbot.config;

import net.dv8tion.jda.api.entities.Guild;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.context.annotation.ImportRuntimeHints;

@ImportRuntimeHints(AOTHintsConfig.GraphQlResourcesRegistrar.class)
public class AOTHintsConfig {
    static class GraphQlResourcesRegistrar implements RuntimeHintsRegistrar {

        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            hints.reflection().registerType(Guild.class);
        }
    }

}
