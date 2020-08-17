package adudecalledleo.lionutils.client.color;

import adudecalledleo.lionutils.InitializerUtil;

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
        return orig & ~0xFF | packRed(r);
    }

    /**
     * Replaces a color's green component with the specified value.
     * @param orig original color
     * @param g new green component
     * @return the modified color
     */
    public static int withGreen(int orig, int g) {
        return orig & ~0xFF00 | packGreen(g);
    }

    /**
     * Replaces a color's blue component with the specified value.
     * @param orig original color
     * @param b new blue component
     * @return the modified color
     */
    public static int withBlue(int orig, int b) {
        return orig & ~0xFF0000 | packBlue(b);
    }

    /**
     * Replaces a color's alpha component with the specified value.
     * @param orig original color
     * @param a new alpha component
     * @return the modified color
     */
    public static int withAlpha(int orig, int a) {
        return orig & ~0xFF000000 | packAlpha(a);
    }

    /**
     * Multiplies a color's red, green and blue components.
     * @param orig original color
     * @param scalar component scalar
     * @return the modified color
     */
    public static int multiply(int orig, float scalar) {
        int r = (int)(unpackRed(orig) * scalar);
        int b = (int)(unpackGreen(orig) * scalar);
        int g = (int)(unpackBlue(orig) * scalar);
        return pack(r, g, b, unpackAlpha(orig));
    }
}
