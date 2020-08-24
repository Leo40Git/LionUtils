package adudecalledleo.lionutils.client.render;

import adudecalledleo.lionutils.InitializerUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import org.lwjgl.opengl.GL11;

import java.util.ArrayDeque;

/**
 * Helper class for managing OpenGL's "scissor test" feature using a stack-based approach.
 * @since 5.0.0
 * @author Juuxel
 */
public final class ScissorStack {
    private ScissorStack() {
        InitializerUtil.badConstructor();
    }

    private static final ArrayDeque<Frame> FRAMES = new ArrayDeque<>();

    private static final class Frame {
        public final int x;
        public final int y;
        public final int width;
        public final int height;

        private Frame(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }

    /**
     * Pushes a new scissoring rect onto the stack.
     * @param x X position
     * @param y Y position
     * @param width width
     * @param height height
     */
    public static void push(int x, int y, int width, int height) {
        if (width < 0)
            throw new IllegalArgumentException("width < 0!");
        if (height < 0)
            throw new IllegalArgumentException("height < 0!");
        FRAMES.push(new Frame(x, y, width, height));
        refreshScissorRect();
    }

    /**
     * Pops the last scissoring rect from the stack.
     */
    public static void pop() {
        if (isEmpty())
            throw new IllegalStateException("Tried to pop when frame stack was empty!");
        FRAMES.pop();
        refreshScissorRect();
    }

    /**
     * Checks if the scissoring rect stack is empty.
     * @return {@code true} if stack is empty, {@code false} otherwise
     */
    public static boolean isEmpty() {
        return FRAMES.isEmpty();
    }

    /**
     * <p>Refreshes the current scissoring rect.</p>
     * This is called automatically, so you shouldn't need to call this yourself.
     * @author Juuxel, vini2003
     */
    public static void refreshScissorRect() {
        if (FRAMES.isEmpty()) {
            // disable scissor testing since we don't need it anymore
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
            return;
        }

        GL11.glEnable(GL11.GL_SCISSOR_TEST);

        int x = Integer.MIN_VALUE;
        int y = Integer.MIN_VALUE;
        int width = -1;
        int height = -1;

        // calculate the largest rect that covers *all* the rects on the stack
        for (Frame frame : FRAMES) {
            if (x < frame.x)
                x = frame.x;
            if (y < frame.y)
                y = frame.y;
            if (width == -1 || x + width > frame.x + frame.width)
                width = frame.width - (x - frame.x);
            if (height == -1 || y + height > frame.y + frame.height)
                height = frame.height - (y - frame.y);
        }

        Window window = MinecraftClient.getInstance().getWindow();
        int windowHeight = window.getHeight();
        double scale = window.getScaleFactor();
        int scaledWidth = (int) (width * scale);
        int scaledHeight = (int) (height * scale);

        // expression for Y coordinate adapted from vini2003's Spinnery (code snippet released under WTFPL)
        GL11.glScissor((int) (x * scale), (int) (windowHeight - (y * scale) - scaledHeight), scaledWidth, scaledHeight);
    }
}
