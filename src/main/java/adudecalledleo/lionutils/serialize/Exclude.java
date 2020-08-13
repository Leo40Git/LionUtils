package adudecalledleo.lionutils.serialize;

import java.lang.annotation.*;

/**
 * Fields annotated with this will be excluded from serialization and deserialization via {@link GsonHolder#GSON}.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Exclude {
}
