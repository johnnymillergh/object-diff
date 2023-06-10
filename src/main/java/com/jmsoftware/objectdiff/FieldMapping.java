package com.jmsoftware.objectdiff;

import java.lang.annotation.*;

/**
 * <h1>FieldMapping</h1>
 * <p>
 * Change description here.
 *
 * @author Johnny Miller (鍾俊), email: johnnysviva@outlook.com, 6/9/23 10:19 PM
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldMapping {
    /**
     * Source class.
     *
     * @return the class
     */
    Class<?> sourceClass() default Class.class;

    /**
     * Source field's name.
     *
     * @return the string
     */
    String source() default "";

    /**
     * Is the source nested?
     *
     * @return true - nested, false - not nested.
     */
    boolean isSourceNested() default false;

    /**
     * Need unwrapping.
     *
     * @return true - do need unwrapping, false - don't need unwrapping.
     */
    boolean needUnwrapping() default false;

    /**
     * Target method name.
     *
     * @return the string
     */
    String targetMethodName() default "";
}
