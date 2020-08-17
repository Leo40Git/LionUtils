package adudecalledleo.lionutils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import net.minecraft.util.UserCache;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

/**
 * Helper class for obtaining {@link GameProfile}s smartly.
 * @since 1.0.0
 */
public final class GameProfileUtil {
    private GameProfileUtil() {
        InitializerUtil.badConstructor();
    }

    private static final Logger LOGGER = LoggerUtil.getLogger("LionUtils|GameProfileUtil");

    private static MinecraftSessionService sessionService;
    private static UserCache userCache;
    private static boolean warnedAboutIncompleteProfile = false;

    /**
     * <p>Sets the session service that we should use.</p>
     * This is set automatically, so you shouldn't need to call this.
     * @param sessionService session service to use
     */
    public static void setSessionService(MinecraftSessionService sessionService) {
        GameProfileUtil.sessionService = sessionService;
    }

    /**
     * <p>Sets the user cache we should use.</p>
     * This is set automatically, so you shouldn't need to call this.
     * @param userCache user cache to use
     */
    public static void setUserCache(UserCache userCache) {
        GameProfileUtil.userCache = userCache;
    }

    /**
     * Gets a {@link GameProfile}. Accepts either a UUID, a name, or both - but not neither!
     * @param id player UUID
     * @param name player name
     * @return profile associated with the specified player
     */
    public static GameProfile getGameProfile(UUID id, String name) {
        if (id == null && name == null)
            throw new IllegalArgumentException("Either ID or name need to be non-null!");
        GameProfile profile = null;
        if (id != null)
            profile = userCache.getByUuid(id);
        if (profile == null && name != null)
            profile = userCache.findByName(name);
        if (profile == null) {
            // cache miss, get from session service and add to cache
            profile = sessionService.fillProfileProperties(new GameProfile(id, name), true);
            // warn about incomplete profile
            // the reason for this "warned" boolean is so we don't spam the log if the player has no internet (SP)
            if (!warnedAboutIncompleteProfile && !profile.isComplete()) {
                warnedAboutIncompleteProfile = true;
                LOGGER.warn("getGameProfile: sessionService.fillProfileProperties failed, profile is incomplete. " +
                        "id = {}, name = \"{}\"", "{" + id + "}", name);
            } else if (profile.isComplete()) {
                warnedAboutIncompleteProfile = false;
                userCache.add(profile);
            }
        }
        return profile;
    }

    /**
     * <p>Default return value of {@link #getPlayerName(UUID)} if something goes wrong.</p>
     * This name is illegal, as it contains characters which are not normally allowed in names.
     */
    public static final String PLAYER_NAME_UNKNOWN = "<???>";

    /**
     * Gets a player's name from their UUID.
     * @param playerID player UUID
     * @return the player's name, or {@link #PLAYER_NAME_UNKNOWN} if something went wrong (like the player not existing)
     */
    public static String getPlayerName(UUID playerID) {
        GameProfile profile = getGameProfile(playerID, null);
        if (!profile.isComplete())
            return PLAYER_NAME_UNKNOWN;
        return profile.getName();
    }

    /**
     * Gets a player's UUID from their name.
     * @param playerName player name
     * @return the player's UUID, or {@code null} if something went wrong (like the player not existing)
     * @since 3.0.0
     */
    public static UUID findPlayerID(String playerName) {
        GameProfile profile = getGameProfile(null, playerName);
        if (!profile.isComplete())
            return null;
        return profile.getId();
    }
}
