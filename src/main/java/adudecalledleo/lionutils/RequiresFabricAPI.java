package adudecalledleo.lionutils;

import java.lang.annotation.*;

/**
 * Classes annotated with this annotation <em>require</em> Fabric API to function.
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface RequiresFabricAPI {
}
