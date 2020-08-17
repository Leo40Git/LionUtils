package adudecalledleo.lionutils.client.render;

import adudecalledleo.lionutils.InitializerUtil;
import org.lwjgl.opengl.GL11;

/**
 * Helper class for managing OpenGL's "scissor test" feature.
 * @since 4.0.0
 */
public final class ScissorManager {
    private ScissorManager() {
        InitializerUtil.badConstructor();
    }

    /**
     * Enables scissor testing.
     */
    public static void enable() {
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
    }

    /**
     * Disables scissor testing.
     */
    public static void disable() {
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    /**
     * Sets the scissoring rect.
     * @param x left corner
     * @param y top corner
     * @param width width
     * @param height height
     */
    public static void setRect(int x, int y, int width, int height) {
        // flip Y, since glScissor expects left-bottom
        y = WindowUtil.getHeight() - y;
        // scale the coordinates
        x = WindowUtil.scale(x);
        y = WindowUtil.scale(y);
        width = WindowUtil.scale(width);
        height = WindowUtil.scale(height);
        // finally, send 'em to glScissor
        GL11.glScissor(x, y, width, height);
    }
}
