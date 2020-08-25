package adudecalledleo.lionutils.serialize;

import java.lang.annotation.*;

/**
 * Fields annotated with this will be excluded from serialization and deserialization via {@link GsonHolder#GSON}.
 *
 * @since 1.0.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Exclude {
    /**
     * Represents the exclusion policy to use.
     *
     * @since 5.0.0
     */
    enum Type {
        /**
         * Exclude the field from both serialization and deserialization.
         */
        BOTH,
        /**
         * Exclude the field from serialization only.
         */
        FROM_SERIALIZATION,
        /**
         * Exclude the field from deserialization only.
         */
        FROM_DESERIALIZATION,
    }

    /**
     * The exclusion policy to use. Defaults to {@link Type#BOTH}.
     *
     * @return exclusion policy to use
     *
     * @since 5.0.0
     */
    Type value() default Type.BOTH;
}
