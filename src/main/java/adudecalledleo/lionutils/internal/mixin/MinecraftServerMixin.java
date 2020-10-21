package adudecalledleo.lionutils.internal.mixin;

import adudecalledleo.lionutils.network.GameProfileUtil;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.UserCache;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
    @Shadow @Final private MinecraftSessionService sessionService;
    @Shadow @Final private UserCache userCache;

    @Inject(method = "runServer",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/server/MinecraftServer;setFavicon(Lnet/minecraft/server/ServerMetadata;)V"))
    public void initGameProfileUtil(CallbackInfo ci) {
        GameProfileUtil.setSessionService(sessionService);
        GameProfileUtil.setUserCache(userCache);
    }
}
