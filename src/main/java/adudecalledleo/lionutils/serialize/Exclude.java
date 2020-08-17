package adudecalledleo.lionutils.serialize;

import java.lang.annotation.*;

/**
 * Fields annotated with this will be excluded from serialization and deserialization via {@link GsonHolder#GSON}.
 * @since 1.0.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Exclude {
}
