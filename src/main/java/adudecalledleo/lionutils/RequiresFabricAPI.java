package adudecalledleo.lionutils;

import java.lang.annotation.*;

/**
 * <p>Classes annotated with this annotation <em>require</em> Fabric API to function.</p>
 * If these classes are accessed without Fabric API being installed, they <em>will</em> crash!
 *
 * @since 3.0.0
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface RequiresFabricAPI {
}
