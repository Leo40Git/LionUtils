package adudecalledleo.lionutils.serialize;

import adudecalledleo.lionutils.InitializerUtil;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.lang.annotation.Annotation;

/**
 * Holds a {@link Gson} instance that has some extra goodies.
 */
public final class GsonHolder {
    private GsonHolder() {
        InitializerUtil.badConstructor();
    }

    private static GsonBuilder baseGsonBuilder() {
        return new GsonBuilder()
                .setExclusionStrategies(new AnnotationExclusionStrategy<>(Exclude.class))
                .registerTypeAdapter(Identifier.class, new IdentifierTypeAdapter())
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
     */
    public static final Gson GSON_COMPRESSED = baseGsonBuilder().create();

    private static class AnnotationExclusionStrategy<T extends Annotation> implements ExclusionStrategy {
        private final Class<T> annoClass;

        private AnnotationExclusionStrategy(Class<T> annoClass) {
            this.annoClass = annoClass;
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }

        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            return f.getAnnotation(annoClass) != null;
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
