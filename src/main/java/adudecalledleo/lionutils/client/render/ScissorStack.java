package adudecalledleo.lionutils.client.render;

import adudecalledleo.lionutils.InitializerUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import org.lwjgl.opengl.GL11;

import java.util.ArrayDeque;
import java.util.NoSuchElementException;

/**
 * <p>Helper class for managing OpenGL's "scissor test" feature using a stack-based approach.</p>
 * {@linkplain #push(int, int, int, int) Adding a frame to the stack} will expand the current scissoring rect to
 * accommodate all the frames on the stack.<br> {@link #set(int, int, int, int)} can be used to "directly" set the
 * scissoring rect to the specified position and size.
 *
 * @author Juuxel
 * @since 5.0.0
 */
public class ScissorStack {
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
     * Represents the stack mode.
     */
    public enum Mode {
        /**
         * <p>Only the last frame in the stack will be used for determining the final scissoring rect.</p>
         * This is the default mode.
         */
        ABSOLUTE,
        /**
         * <p>All frames will be accommodated from when determining the final scissoring rect.</p>
         * This will make the final scissoring rect expand to accommodate all frames on the stack.
         */
        ADDITIVE
    }

    private static Mode mode = Mode.ABSOLUTE;

    /**
     * Gets the current stack mode.
     *
     * @return the stack mode
     */
    public static Mode getMode() {
        return mode;
    }

    /**
     * Sets the current stack mode.
     *
     * @param mode
     *         new stack mode
     */
    public static void setMode(Mode mode) {
        ScissorStack.mode = mode;
    }

    /**
     * Pushes a new frame onto the stack.
     *
     * @param x
     *         X position
     * @param y
     *         Y position
     * @param width
     *         width
     * @param height
     *         height
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
     * Pops the last frame from the stack.
     *
     * @throws NoSuchElementException
     *         if the frame stack is empty.
     */
    public static void pop() {
        if (isEmpty())
            throw new NoSuchElementException("Tried to pop when frame stack was empty!");
        FRAMES.pop();
        refreshScissorRect();
    }

    /**
     * Clears the frame stack.
     */
    public static void clear() {
        FRAMES.clear();
        refreshScissorRect();
    }

    /**
     * <p>Forcibly sets the scissoring state to the specified frame.</p>
     * <p>Note that {@link #pop()} still must be called afterwards.</p>
     * Equivalent to:<pre>
     * {@link #clear() ScissorStack.clear()};
     * {@link #push(int, int, int, int) ScissorStack.push}(x, y, width, height);
     * </pre>
     *
     * @param x
     *         X position
     * @param y
     *         Y position
     * @param width
     *         width
     * @param height
     *         height
     */
    public static void set(int x, int y, int width, int height) {
        if (width < 0)
            throw new IllegalArgumentException("width < 0!");
        if (height < 0)
            throw new IllegalArgumentException("height < 0!");
        FRAMES.clear();
        FRAMES.push(new Frame(x, y, width, height));
        refreshScissorRect();
    }

    /**
     * Checks if the scissoring rect stack is empty.
     *
     * @return {@code true} if stack is empty, {@code false} otherwise
     */
    public static boolean isEmpty() {
        return FRAMES.isEmpty();
    }

    /**
     * <p>Refreshes the current scissoring rect.</p>
     * This is called automatically, so you shouldn't need to call this yourself.
     */
    public static void refreshScissorRect() {
        if (FRAMES.isEmpty()) {
            // disable scissor testing, since we don't need it anymore
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
            return;
        }

        // (re)enable scissor testing
        GL11.glEnable(GL11.GL_SCISSOR_TEST);

        // calculate the largest rect that covers *all* the rects on the stack
        int x = Integer.MIN_VALUE;
        int y = Integer.MIN_VALUE;
        int width = -1;
        int height = -1;

        for (Frame frame : FRAMES) {
            if (x < frame.x)
                x = frame.x;
            if (y < frame.y)
                y = frame.y;
            if (width == -1 || x + width > frame.x + frame.width)
                width = frame.width - (x - frame.x);
            if (height == -1 || y + height > frame.y + frame.height)
                height = frame.height - (y - frame.y);
            // if we're in ABSOLUTE mode, stop after the first frame in the stack
            if (mode == Mode.ABSOLUTE)
                break;
        }

        // grab some values for scaling
        Window window = MinecraftClient.getInstance().getWindow();
        int windowHeight = window.getHeight();
        double scale = window.getScaleFactor();
        int scaledWidth = (int) (width * scale);
        int scaledHeight = (int) (height * scale);

        // send our rect to GL! this scales the coordinates and corrects the Y value,
        // since GL expects the bottom-left point of the rect and *also* the bottom of the screen to be 0 here
        // expression for Y coordinate adapted from vini2003's Spinnery (code snippet released under WTFPL)
        GL11.glScissor((int) (x * scale), (int) (windowHeight - (y * scale) - scaledHeight), scaledWidth, scaledHeight);
    }
}
