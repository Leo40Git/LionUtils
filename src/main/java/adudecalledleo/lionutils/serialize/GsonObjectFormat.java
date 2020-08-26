package adudecalledleo.lionutils.serialize;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.io.Reader;

/**
 * {@link ObjectFormat} that delegates reading and writing to a {@link Gson} instance.
 *
 * @since 4.0.0
 */
public final class GsonObjectFormat implements ObjectFormat {
    private final Gson delegate;

    /**
     * Constructs a {@code GsonObjectFormat}.
     *
     * @param delegate
     *         {@link Gson} instance to delegate to
     */
    public GsonObjectFormat(Gson delegate) {
        this.delegate = delegate;
    }

    /**
     * {@inheritDoc}
     * <p>Delegates to {@link Gson#fromJson(Reader, Class)}.</p>
     */
    @Override
    public <T> T read(Reader reader, Class<T> typeOfT) throws IOException {
        try {
            return delegate.fromJson(reader, typeOfT);
        } catch (JsonSyntaxException | JsonIOException e) {
            throw new IOException("JSON deserialization failed", e);
        }
    }

    /**
     * {@inheritDoc}
     * <p>Delegates to {@link Gson#toJson(Object, Appendable)}.</p>
     */
    @Override
    public <T> void write(T src, Appendable writer) throws IOException {
        try {
            delegate.toJson(src, writer);
        } catch (JsonIOException e) {
            throw new IOException("JSON serialization failed", e);
        }
    }
}
