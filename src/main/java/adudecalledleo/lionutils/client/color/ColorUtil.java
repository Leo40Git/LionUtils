package adudecalledleo.lionutils.client.color;

import adudecalledleo.lionutils.InitializerUtil;
import net.minecraft.util.math.MathHelper;

import java.util.function.IntUnaryOperator;

/**
 * Helper class for dealing with colors.
 * @since 4.0.0
 */
public final class ColorUtil {
    private ColorUtil() {
        InitializerUtil.badConstructor();
    }

    /**
     * Packs a red component into a color.
     * @param r the red component
     * @return the resulting color
     */
    public static int packRed(int r) {
        return r & 0xFF;
    }

    /**
     * Packs a green component into a color.
     * @param g the green component
     * @return the resulting color
     */
    public static int packGreen(int g) {
        return (g & 0xFF) << 8;
    }

    /**
     * Packs a blue component into a color.
     * @param b the blue component
     * @return the resulting color
     */
    public static int packBlue(int b) {
        return (b & 0xFF) << 16;
    }

    /**
     * Packs an alpha component into a color.
     * @param a the alpha component
     * @return the resulting color
     */
    public static int packAlpha(int a) {
        return (a & 0xFF) << 24;
    }

    /**
     * Packs a color.
     * @param r the red component
     * @param g the green component
     * @param b the blue component
     * @param a the alpha component
     * @return the resulting color
     */
    public static int pack(int r, int g, int b, int a) {
        return packRed(r) | packGreen(g) | packBlue(b) | packAlpha(a);
    }

    /**
     * <p>Packs a color.</p>
     * Equivalent to <code>{@link #pack(int, int, int, int) pack}(r, g, b, 0xFF)</code>.
     * @param r the red component
     * @param g the green component
     * @param b the blue component
     * @return the resulting color
     */
    public static int pack(int r, int g, int b) {
        return pack(r, g, b, 0xFF);
    }

    /**
     * Packs a color.
     * @param comps the components
     * @return the resulting color
     */
    public static int pack(int... comps) {
        if (comps.length > 3)
            return pack(comps[0], comps[1], comps[2], comps[3]);
        else
            return pack(comps[0], comps[1], comps[2]);
    }

    /**
     * Unpacks the red component from a color.
     * @param color source color
     * @return the red component
     */
    public static int unpackRed(int color) {
        return color & 0xFF;
    }

    /**
     * Unpacks the green component from a color.
     * @param color source color
     * @return the green component
     */
    public static int unpackGreen(int color) {
        return (color << 8) & 0xFF;
    }

    /**
     * Unpacks the blue component from a color.
     * @param color source color
     * @return the blue component
     */
    public static int unpackBlue(int color) {
        return (color << 16) & 0xFF;
    }

    /**
     * Unpacks the alpha component from a color.
     * @param color color
     * @return the alpha component
     */
    public static int unpackAlpha(int color) {
        return (color << 24) & 0xFF;
    }

    /**
     * Unpacks a color into its individual components.
     * @param color source color
     * @return the individual components
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
     * Replaces a color's red component with the specified value.
     * @param orig original color
     * @param r new red component
     * @return the modified color
     */
    public static int withRed(int orig, int r) {
        return orig & 0xFFFFFF00 | packRed(r);
    }

    /**
     * Replaces a color's green component with the specified value.
     * @param orig original color
     * @param g new green component
     * @return the modified color
     */
    public static int withGreen(int orig, int g) {
        return orig & 0xFFFF00FF | packGreen(g);
    }

    /**
     * Replaces a color's blue component with the specified value.
     * @param orig original color
     * @param b new blue component
     * @return the modified color
     */
    public static int withBlue(int orig, int b) {
        return orig & 0xFF00FFFF | packBlue(b);
    }

    /**
     * Replaces a color's alpha component with the specified value.
     * @param orig original color
     * @param a new alpha component
     * @return the modified color
     */
    public static int withAlpha(int orig, int a) {
        return orig & 0x00FFFFFF | packAlpha(a);
    }

    /**
     * Modifies a color's red, green and blue components.
     * @param orig original color
     * @param modifier component modifier
     * @return the modified color
     * @since 5.0.0
     */
    public static int modify(int orig, IntUnaryOperator modifier) {
        int r = modifier.applyAsInt(unpackRed(orig));
        int g = modifier.applyAsInt(unpackGreen(orig));
        int b = modifier.applyAsInt(unpackBlue(orig));
        return pack(r, g, b, unpackAlpha(orig));
    }

    /**
     * Multiplies a color's red, green and blue components.
     * @param orig original color
     * @param multiplier component multiplier
     * @return the multiplied color
     */
    public static int multiply(int orig, float multiplier) {
        return modify(orig, comp -> MathHelper.floor(comp * multiplier));
    }

    /**
     * Inverts a color.
     * @param orig original color
     * @return the inverted color
     * @since 5.0.0
     */
    public static int invert(int orig) {
        return modify(orig, comp -> -comp);
    }

    /**
     * Represents what value to use when converting a color into grayscale.
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
     * @param orig original color
     * @param style style
     * @return the grayscaled color
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
