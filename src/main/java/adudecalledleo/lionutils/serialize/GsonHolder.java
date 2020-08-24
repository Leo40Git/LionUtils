package adudecalledleo.lionutils.serialize;

import adudecalledleo.lionutils.InitializerUtil;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.minecraft.util.Identifier;

import java.io.IOException;

/**
 * Holds a {@link Gson} instance that has some extra goodies.
 * @since 1.0.0
 */
public final class GsonHolder {
    private GsonHolder() {
        InitializerUtil.badConstructor();
    }

    private static final ExcludeStrategy EXCLUDE_STRATEGY_SERIALIZE = new ExcludeStrategy(Exclude.Type.FROM_SERIALIZATION);
    private static final ExcludeStrategy EXCLUDE_STRATEGY_DESERIALIZE = new ExcludeStrategy(Exclude.Type.FROM_DESERIALIZATION);
    private static final IdentifierTypeAdapter IDENTIFIER_TYPE_ADAPTER = new IdentifierTypeAdapter();

    private static GsonBuilder baseGsonBuilder() {
        return new GsonBuilder()
                .addSerializationExclusionStrategy(EXCLUDE_STRATEGY_SERIALIZE)
                .addDeserializationExclusionStrategy(EXCLUDE_STRATEGY_DESERIALIZE)
                .registerTypeAdapter(Identifier.class, IDENTIFIER_TYPE_ADAPTER)
                .enableComplexMapKeySerialization()
                .serializeSpecialFloatingPointValues()
                .setLenient()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
    }

    /**
     * <p>{@link Gson} instance. Do you even need an explanation?</p>
     * Includes the following goodies: <ul>
     *     <li>Exclusion strategy for excluding fields annotated with the {@link Exclude} annotation.</li>
     *     <li>Type adapter for {@link Identifier}s, to serialize and deserialize them via their string representation.</li>
     *     <li>{@linkplain GsonBuilder#enableComplexMapKeySerialization() Complex map key serialization}.</li>
     *     <li>{@linkplain GsonBuilder#serializeSpecialFloatingPointValues() Support for serializing special floating point values.}</li>
     *     <li>{@linkplain JsonReader#setLenient(boolean) Support for non-standard JSON features when deserializing}.</li>
     *     <li>{@code snake_case} field naming policy.</li>
     *     <li>Pretty printing!</li>
     * </ul>
     */
    public static final Gson GSON = baseGsonBuilder().setPrettyPrinting().create();

    /**
     * <p>{@link Gson} instance for "compressed" JSON.</p>
     * Has the same features as {@link #GSON}, but without pretty printing.
     * @since 3.0.0
     */
    public static final Gson GSON_COMPRESSED = baseGsonBuilder().create();

    /**
     * {@link ObjectFormat} instance that wraps {@link #GSON}.
     * @since 4.0.0
     */
    public static final ObjectFormat FORMAT = new GsonObjectFormat(GSON);

    /**
     * {@link ObjectFormat} instance that wraps {@link #GSON_COMPRESSED}.
     * @since 4.0.0
     */
    public static final ObjectFormat FORMAT_COMPRESSED = new GsonObjectFormat(GSON_COMPRESSED);

    private static class ExcludeStrategy implements ExclusionStrategy {
        private final Exclude.Type type;

        private ExcludeStrategy(Exclude.Type type) {
            this.type = type;
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }

        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            Exclude exclude = f.getAnnotation(Exclude.class);
            if (exclude == null)
                return true;
            Exclude.Type type = exclude.value();
            return type == Exclude.Type.BOTH || this.type == type;
        }
    }

    // serializes Identifiers via their toString method and deserializes them via the Identifier(String) constructor
    private static class IdentifierTypeAdapter extends TypeAdapter<Identifier> {
        @Override
        public Identifier read(JsonReader in) throws IOException {
            return Identifier.tryParse(in.nextString());
        }

        @Override
        public void write(JsonWriter out, Identifier value) throws IOException {
            if (value == null) {
                out.nullValue();
                return;
            }
            if ("minecraft".equals(value.getNamespace()))
                // only write the path, since no namespace == namespace "minecraft"
                out.value(value.getPath());
            else
                out.value(value.toString());
        }
    }
}
