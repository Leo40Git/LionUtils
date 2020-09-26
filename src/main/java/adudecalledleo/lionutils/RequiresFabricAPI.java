package adudecalledleo.lionutils;

import java.lang.annotation.*;

/**
 * Classes annotated with this annotation <em>require</em> Fabric API to function.<p>
 * If these classes are accessed without Fabric API being installed, they <em>will</em> crash!<p>
 * If a package is annotated with this annotation, assume all classes under this package are annotated with this
 * annotation as well.
 *
 * @since 3.0.0
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.PACKAGE, ElementType.TYPE})
public @interface RequiresFabricAPI {
}
