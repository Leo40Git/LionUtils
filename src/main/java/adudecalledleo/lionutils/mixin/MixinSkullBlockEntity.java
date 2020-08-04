package adudecalledleo.lionutils.mixin;

import adudecalledleo.lionutils.GameProfileUtil;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.util.UserCache;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin to give {@link GameProfileUtil} its required {@link MinecraftSessionService} and {@link UserCache}.
 * @see GameProfileUtil#setSessionService(MinecraftSessionService)
 * @see GameProfileUtil#setUserCache(UserCache)
 */
@Mixin(SkullBlockEntity.class)
public abstract class MixinSkullBlockEntity {
    @Inject(method = "setSessionService", at = @At("TAIL"))
    private static void parcels$setSessionService(MinecraftSessionService value, CallbackInfo ci) {
        GameProfileUtil.setSessionService(value);
    }

    @Inject(method = "setUserCache", at = @At("TAIL"))
    private static void parcels$setUserCache(UserCache value, CallbackInfo ci) {
        GameProfileUtil.setUserCache(value);
    }
}
