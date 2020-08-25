package adudecalledleo.lionutils;

import java.util.List;

/**
 * Helper class for dealing with {@link List}s.
 *
 * @since 3.0.0
 */
public final class ListUtil {
    private ListUtil() {
        InitializerUtil.badConstructor();
    }

    /**
     * Updates a list using an "elements to add" list and an "elements to remove" list.<br> Useful for implementing
     * listener/observer patterns.
     *
     * @param list
     *         list to update
     * @param toAdd
     *         list of elements to add
     * @param toRemove
     *         list of elements to remove
     * @param <T>
     *         element type
     */
    public static <T> void addAndRemove(List<T> list, List<T> toAdd, List<T> toRemove) {
        list.addAll(toAdd);
        toAdd.clear();
        list.removeAll(toRemove);
        toRemove.clear();
    }
}
