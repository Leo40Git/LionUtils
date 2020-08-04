package adudecalledleo.lionutils;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.Logger;

/**
 * Mod initializer. You shouldn't need to touch this, really.
 */
public class LionUtils implements ModInitializer {
    private static final Logger LOGGER = LoggerUtil.getLogger("LionUtils");

    @Override
    public void onInitialize() {
        LOGGER.info("LionUtils initialized successfully.");
    }
}
