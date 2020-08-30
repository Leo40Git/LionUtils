package adudecalledleo.lionutils.client.color;

import adudecalledleo.lionutils.InitializerUtil;
import net.minecraft.util.math.MathHelper;

import java.util.function.IntUnaryOperator;

/**
 * Helper class for dealing with colors.
 *
 * @since 4.0.0
 */
public final class ColorUtil {
    private ColorUtil() {
        InitializerUtil.badConstructor();
    }

    /**
     * Packs a red component into a color.
     *
     * @param r
     *         the red component
     * @return the resulting color
     */
    public static int packRed(int r) {
        return (r & 0xFF) << 16;
    }

    /**
     * Packs a green component into a color.
     *
     * @param g
     *         the green component
     * @return the resulting color
     */
    public static int packGreen(int g) {
        return (g & 0xFF) << 8;
    }

    /**
     * Packs a blue component into a color.
     *
     * @param b
     *         the blue component
     * @return the resulting color
     */
    public static int packBlue(int b) {
        return b & 0xFF;
    }

    /**
     * Packs an alpha component into a color.
     *
     * @param a
     *         the alpha component
     * @return the resulting color
     */
    public static int packAlpha(int a) {
        return (a & 0xFF) << 24;
    }

    /**
     * Packs a color.
     *
     * @param r
     *         the red component
     * @param g
     *         the green component
     * @param b
     *         the blue component
     * @param a
     *         the alpha component
     * @return the resulting color
     */
    public static int pack(int r, int g, int b, int a) {
        return packRed(r) | packGreen(g) | packBlue(b) | packAlpha(a);
    }

    /**
     * <p>Packs a color.</p>
     * Equivalent to <code>{@link #pack(int, int, int, int) pack}(r, g, b, 0xFF)</code>.
     *
     * @param r
     *         the red component
     * @param g
     *         the green component
     * @param b
     *         the blue component
     * @return the resulting color
     */
    public static int pack(int r, int g, int b) {
        return pack(r, g, b, 0xFF);
    }

    /**
     * Packs a color.
     *
     * @param comps
     *         the components in RGB(A) order
     * @return the resulting color
     */
    public static int pack(int[] comps) {
        if (comps.length > 3)
            return pack(comps[0], comps[1], comps[2], comps[3]);
        else
            return pack(comps[0], comps[1], comps[2]);
    }

    /**
     * Unpacks the red component from a color.
     *
     * @param color
     *         source color
     * @return the red component
     */
    public static int unpackRed(int color) {
        return (color << 16) & 0xFF;
    }

    /**
     * Unpacks the green component from a color.
     *
     * @param color
     *         source color
     * @return the green component
     */
    public static int unpackGreen(int color) {
        return (color << 8) & 0xFF;
    }

    /**
     * Unpacks the blue component from a color.
     *
     * @param color
     *         source color
     * @return the blue component
     */
    public static int unpackBlue(int color) {
        return color & 0xFF;
    }

    /**
     * Unpacks the alpha component from a color.
     *
     * @param color
     *         color
     * @return the alpha component
     */
    public static int unpackAlpha(int color) {
        return (color << 24) & 0xFF;
    }

    /**
     * Unpacks a color into its individual components.
     *
     * @param color
     *         source color
     * @return the individual components in RGBA order
     */
    public static int[] unpack(int color) {
        return new int[] {
                unpackRed(color),
                unpackGreen(color),
                unpackBlue(color),
                unpackAlpha(color)
        };
    }

    /**
     * Represents a color component.
     *
     * @since 5.0.0
     */
    public enum Component {
        /**
         * Represents the red component.
         */
        RED(ColorUtil::packRed, ColorUtil::unpackRed),
        /**
         * Represents the green component.
         */
        GREEN(ColorUtil::packGreen, ColorUtil::unpackGreen),
        /**
         * Represents the blue component.
         */
        BLUE(ColorUtil::packBlue, ColorUtil::unpackBlue),
        /**
         * Represents the alpha component.
         */
        ALPHA(ColorUtil::packAlpha, ColorUtil::unpackAlpha);

        private final IntUnaryOperator packOp;
        private final IntUnaryOperator unpackOp;

        Component(IntUnaryOperator packOp, IntUnaryOperator unpackOp) {
            this.packOp = packOp;
            this.unpackOp = unpackOp;
        }

        /**
         * Packs the value as if it were this component into a color.
         *
         * @param value
         *         component value
         * @return the resulting color
         */
        public int pack(int value) {
            return packOp.applyAsInt(value);
        }

        /**
         * Unpacks this component's value from a color.
         *
         * @param color
         *         color
         * @return the component's value
         */
        public int unpack(int color) {
            return unpackOp.applyAsInt(color);
        }
    }

    /**
     * Replaces a color's red component with the specified value.
     *
     * @param orig
     *         original color
     * @param r
     *         new red component
     * @return the modified color
     */
    public static int withRed(int orig, int r) {
        return orig & 0xFF00FFFF | packRed(r);
    }

    /**
     * Replaces a color's green component with the specified value.
     *
     * @param orig
     *         original color
     * @param g
     *         new green component
     * @return the modified color
     */
    public static int withGreen(int orig, int g) {
        return orig & 0xFFFF00FF | packGreen(g);
    }

    /**
     * Replaces a color's blue component with the specified value.
     *
     * @param orig
     *         original color
     * @param b
     *         new blue component
     * @return the modified color
     */
    public static int withBlue(int orig, int b) {
        return orig & 0xFFFFFF00 | packBlue(b);
    }

    /**
     * Replaces a color's alpha component with the specified value.
     *
     * @param orig
     *         original color
     * @param a
     *         new alpha component
     * @return the modified color
     */
    public static int withAlpha(int orig, int a) {
        return orig & 0x00FFFFFF | packAlpha(a);
    }

    /**
     * Modifies a color's red, green, blue and alpha components.
     *
     * @param orig
     *         original color
     * @param modifier
     *         component modifier
     * @param modAlpha
     *         whether to modify the alpha component or not
     * @return the modified color
     *
     * @since 5.0.0
     */
    public static int modify(int orig, IntUnaryOperator modifier, boolean modAlpha) {
        int r = modifier.applyAsInt(unpackRed(orig));
        int g = modifier.applyAsInt(unpackGreen(orig));
        int b = modifier.applyAsInt(unpackBlue(orig));
        int a = unpackAlpha(orig);
        if (modAlpha)
            a = modifier.applyAsInt(a);
        return pack(r, g, b, a);
    }

    /**
     * Modifies a color's red, green and blue components.
     *
     * @param orig
     *         original color
     * @param modifier
     *         component modifier
     * @return the modified color
     *
     * @since 5.0.0
     */
    public static int modify(int orig, IntUnaryOperator modifier) {
        return modify(orig, modifier, false);
    }

    /**
     * Represents a color component modifier.
     *
     * @since 5.0.0
     */
    @FunctionalInterface
    public interface ComponentModifier {
        /**
         * Modifies a color component.
         *
         * @param comp
         *         component type
         * @param orig
         *         component value
         * @return the new component value
         */
        int modify(Component comp, int orig);
    }

    /**
     * Modifies a color's red, green, blue and alpha components.
     *
     * @param orig
     *         original color
     * @param modifier
     *         component modifier
     * @return the modified color
     *
     * @since 5.0.0
     */
    public static int modify(int orig, ComponentModifier modifier) {
        int r = modifier.modify(Component.RED, unpackRed(orig));
        int g = modifier.modify(Component.GREEN, unpackGreen(orig));
        int b = modifier.modify(Component.BLUE, unpackBlue(orig));
        int a = modifier.modify(Component.ALPHA, unpackAlpha(orig));
        return pack(r, g, b, a);
    }

    /**
     * Multiplies a color's red, green and blue components.
     *
     * @param orig
     *         original color
     * @param multiplier
     *         component multiplier
     * @return the multiplied color
     */
    public static int multiply(int orig, float multiplier) {
        return modify(orig, comp -> MathHelper.floor(comp * multiplier));
    }

    /**
     * Inverts a color.
     *
     * @param orig
     *         original color
     * @return the inverted color
     *
     * @since 5.0.0
     */
    public static int invert(int orig) {
        return modify(orig, comp -> -comp);
    }

    /**
     * Represents what value to use when converting a color into grayscale.
     *
     * @since 5.0.0
     */
    public enum GrayscaleStyle {
        /**
         * Use the red component's value.
         */
        RED,
        /**
         * Use the green component's value.
         */
        GREEN,
        /**
         * Use the blue component's value.
         */
        BLUE,
        /**
         * Use the average of the red, green and blue components' values.
         */
        AVERAGE
    }

    /**
     * Converts a color into grayscale.
     *
     * @param orig
     *         original color
     * @param style
     *         style
     * @return the grayscale color
     *
     * @since 5.0.0
     */
    public static int grayscale(int orig, GrayscaleStyle style) {
        int v = 0;
        switch (style) {
        case RED:
            v = unpackRed(orig);
            break;
        case GREEN:
            v = unpackGreen(orig);
            break;
        case BLUE:
            v = unpackBlue(orig);
            break;
        case AVERAGE:
            v = unpackRed(orig);
            v += unpackGreen(orig);
            v += unpackBlue(orig);
            v = MathHelper.floor(v / 3f);
            break;
        }
        return pack(v, v, v, unpackAlpha(orig));
    }
}
