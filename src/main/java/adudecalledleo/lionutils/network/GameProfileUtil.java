package adudecalledleo.lionutils.network;

import adudecalledleo.lionutils.InitializerUtil;
import adudecalledleo.lionutils.LoggerUtil;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.util.UserCache;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

/**
 * Helper class for obtaining {@link GameProfile}s smartly.
 *
 * @since 1.0.0
 */
public final class GameProfileUtil {
    private GameProfileUtil() {
        InitializerUtil.utilCtor();
    }

    private static final Logger LOGGER = LoggerUtil.getLogger("LionUtils|GameProfileUtil");

    private static MinecraftSessionService sessionService;
    private static UserCache userCache;
    private static boolean warnedAboutIncompleteProfile = false;

    /**
     * Sets the session service that we should use.<p>
     * This is set automatically, so you shouldn't need to call this.
     *
     * @param sessionService
     *         session service to use
     */
    public static void setSessionService(MinecraftSessionService sessionService) {
        GameProfileUtil.sessionService = sessionService;
    }

    /**
     * Sets the user cache we should use.<p>
     * This is set automatically, so you shouldn't need to call this.
     *
     * @param userCache
     *         user cache to use
     */
    public static void setUserCache(UserCache userCache) {
        GameProfileUtil.userCache = userCache;
    }

    /**
     * Gets a {@link GameProfile}. Accepts either a UUID, a name, or both - but not neither!
     *
     * @param id
     *         player UUID
     * @param name
     *         player name
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
     * Default return value of {@link #getPlayerName(UUID)} if something goes wrong.<p>
     * This name is illegal, as it contains characters which are not normally allowed in names.
     */
    public static final String PLAYER_NAME_UNKNOWN = "<???>";

    /**
     * Gets a player's name from their UUID.
     *
     * @param playerID
     *         player UUID
     * @return the player's name, or {@link #PLAYER_NAME_UNKNOWN} if something went wrong (like the player not
     *         existing)
     */
    public static String getPlayerName(UUID playerID) {
        GameProfile profile = getGameProfile(playerID, null);
        if (!profile.isComplete())
            return PLAYER_NAME_UNKNOWN;
        return profile.getName();
    }

    /**
     * Default return value of {@link #findPlayerID(String)} if something goes wrong.<p>
     * This is a <a href="http://tools.ietf.org/html/rfc4122#section-4.1.7">nil UUID</a>.
     *
     * @since 5.0.0
     */
    public static final UUID PLAYER_ID_UNKNOWN = new UUID(0, 0);

    /**
     * Gets a player's UUID from their name.
     *
     * @param playerName
     *         player name
     * @return the player's UUID, or {@link #PLAYER_ID_UNKNOWN} if something went wrong (like the player not existing)
     *
     * @since 3.0.0
     */
    public static UUID findPlayerID(String playerName) {
        GameProfile profile = getGameProfile(null, playerName);
        if (!profile.isComplete())
            return PLAYER_ID_UNKNOWN;
        return profile.getId();
    }

    /**
     * Creates a {@code CompoundTag} that can be set on a "player head" stack to get the associated player's head.
     * @param profile profile to create head tag from
     * @return the tag of the head of the player that is associated with that profile
     * @since 6.0.0
     */
    public static CompoundTag createPlayerHeadTag(GameProfile profile) {
        CompoundTag baseTag = new CompoundTag();
        baseTag.put("SkullOwner", NbtHelper.fromGameProfile(new CompoundTag(), profile));
        return baseTag;
    }

    /**
     * Creates a {@code CompoundTag} that can be set on a "player head" stack to get the associated player's head.<br>
     * Accepts either a UUID, a name, or both - but not neither!<p>
     * Equivalent to:<pre>
     * {@link #createPlayerHeadTag(GameProfile) createPlayerHeadTag}({@link #getGameProfile(UUID, String) getGameProfile}(id, name));
     * </pre>
     *
     * @param id
     *         player UUID
     * @param name
     *         player name
     * @return the tag head of the player that is associated with that UUID/name
     *
     * @since 6.0.0
     */
    public static CompoundTag createPlayerHeadTag(UUID id, String name) {
        return createPlayerHeadTag(getGameProfile(id, name));
    }

    /**
     * Creates a stack of a player's head from their {@link GameProfile}.
     *
     * @param profile
     *         profile to create head from
     * @return the head of the player that is associated with that profile
     *
     * @since 5.1.0
     */
    public static ItemStack createPlayerHead(GameProfile profile) {
        ItemStack headStack = new ItemStack(Items.PLAYER_HEAD);
        headStack.getOrCreateTag().copyFrom(createPlayerHeadTag(profile));
        return headStack;
    }

    /**
     * Creates a stack of a player's head from their UUID or name.<br>
     * Accepts either a UUID, a name, or both - but not neither!<p>
     * Equivalent to:<pre>
     * {@link #createPlayerHead(GameProfile) createPlayerHead}({@link #getGameProfile(UUID, String) getGameProfile}(id, name));
     * </pre>
     *
     * @param id
     *         player UUID
     * @param name
     *         player name
     * @return the head of the player that is associated with that UUID/name
     *
     * @since 5.1.0
     */
    public static ItemStack createPlayerHead(UUID id, String name) {
        return createPlayerHead(getGameProfile(id, name));
    }
}
