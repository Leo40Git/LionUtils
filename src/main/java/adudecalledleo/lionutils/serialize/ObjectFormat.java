package adudecalledleo.lionutils.serialize;

import java.io.IOException;
import java.io.Reader;

/**
 * Represents a format used to read and write objects using files.
 *
 * @since 4.0.0
 */
public interface ObjectFormat {
    /**
     * Reads an object from a {@link Reader}.
     *
     * @param reader
     *         source reader
     * @param typeOfT
     *         type of object to read
     * @param <T>
     *         type of object to read
     * @return the object that was read
     *
     * @throws IOException
     *         if an I/O exception occurs.
     */
    <T> T read(Reader reader, Class<T> typeOfT) throws IOException;

    /**
     * Writes an object to an {@link Appendable}.
     *
     * @param src
     *         object to write
     * @param writer
     *         destination {@link Appendable}
     * @param <T>
     *         type of object to write
     * @throws IOException
     *         if an I/O exception occurs.
     */
    <T> void write(T src, Appendable writer) throws IOException;
}
