package adudecalledleo.lionutils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import net.minecraft.util.UserCache;

import java.util.UUID;

/**
 * Helper class for obtaining {@link GameProfile}s smartly.
 */
public final class GameProfileUtil {
    private GameProfileUtil() {
        InitializerUtil.badConstructor();
    }

    private static MinecraftSessionService sessionService;
    private static UserCache userCache;

    /**
     * Sets the session service that we should use.<br>
     * This is set automatically by {@linkplain adudecalledleo.lionutils.mixin.MixinSkullBlockEntity a mixin},
     * so you probably shouldn't call this.
     * @param sessionService session service to use
     */
    public static void setSessionService(MinecraftSessionService sessionService) {
        GameProfileUtil.sessionService = sessionService;
    }

    /**
     * Sets the user cache we should use.<br>
     * This is set automatically by {@linkplain adudecalledleo.lionutils.mixin.MixinSkullBlockEntity a mixin},
     * so you probably shouldn't call this.
     * @param userCache user cache to use
     */
    public static void setUserCache(UserCache userCache) {
        GameProfileUtil.userCache = userCache;
    }

    /**
     * Gets a {@link GameProfile}. Accepts either a {@link UUID}, a name, or both - but not neither!
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
            profile = new GameProfile(id, name);
            sessionService.fillProfileProperties(profile, true);
            userCache.add(profile);
        }
        return profile;
    }

    /**
     * Default return value of {@link #getPlayerName(UUID)} if something goes wrong.<br>
     * This name is illegal - {@code '<'} and {@code '>'} are not allowed.
     */
    public static final String PLAYER_NAME_UNKNOWN = "<???>";

    /**
     * Gets a player's name from their UUID.
     * @param playerUUID player UUID
     * @return player name, or {@link #PLAYER_NAME_UNKNOWN} if something went wrong (like the player not existing)
     */
    public static String getPlayerName(UUID playerUUID) {
        GameProfile profile = getGameProfile(playerUUID, null);
        String name = profile.getName();
        if (name == null)
            name = PLAYER_NAME_UNKNOWN;
        return name;
    }
}
