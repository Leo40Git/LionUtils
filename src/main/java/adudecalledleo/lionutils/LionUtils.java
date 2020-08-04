package adudecalledleo.lionutils;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Mod initializer. You shouldn't need to touch this, really.
 */
public class LionUtils implements ModInitializer {
    private static final Logger LOGGER = LogManager.getLogger("LionUtils");
    private static final LogFunction LOG_FUNCTION = LogFunction.create(LOGGER);

    @Override
    public void onInitialize() {
        LOG_FUNCTION.log(Level.INFO, "LionUtils initialized successfully.");
    }
}
