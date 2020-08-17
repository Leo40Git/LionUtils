package adudecalledleo.lionutils;

import java.lang.annotation.*;

/**
 * Classes annotated with this annotation <em>require</em> Fabric API to function.
 * @since 3.0.0
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface RequiresFabricAPI {
}
