package adudecalledleo.lionutils.internal.mixin;

import adudecalledleo.lionutils.GameProfileUtil;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.util.UserCache;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SkullBlockEntity.class)
abstract class MixinSkullBlockEntity {
    @Inject(method = "setSessionService", at = @At("TAIL"))
    private static void lionutils$setSessionService(MinecraftSessionService value, CallbackInfo ci) {
        GameProfileUtil.setSessionService(value);
    }

    @Inject(method = "setUserCache", at = @At("TAIL"))
    private static void lionutils$setUserCache(UserCache value, CallbackInfo ci) {
        GameProfileUtil.setUserCache(value);
    }
}
