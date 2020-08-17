package adudecalledleo.lionutils.client.render;

import adudecalledleo.lionutils.InitializerUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import net.minecraft.util.math.MathHelper;

/**
 * Helper class for getting window attributes.
 * @since 4.0.0
 */
public final class WindowUtil {
    private WindowUtil() {
        InitializerUtil.badConstructor();
    }

    /**
     * Gets Minecraft's {@link Window} instance.
     * @return window instance
     */
    public static Window getWindow() {
        return MinecraftClient.getInstance().getWindow();
    }

    /**
     * Gets the window's width.
     * @return window width
     */
    public static int getWidth() {
        return getWindow().getFramebufferWidth();
    }

    /**
     * Gets the window's height.
     * @return window height
     */
    public static int getHeight() {
        return getWindow().getFramebufferHeight();
    }

    /**
     * Gets the Minecraft window's current scale factor.
     * @return the scale factor
     */
    public static double getScaleFactor() {
        return getWindow().getScaleFactor();
    }

    /**
     * Scales a screen-space coordinate.
     * @param coord original coordinate
     * @return scaled coordinate
     */
    public static int scale(int coord) {
        return MathHelper.ceil(coord / getScaleFactor());
    }
}
