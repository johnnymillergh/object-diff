package com.jmsoftware.objectdiff;

import java.lang.annotation.*;

/**
 * <h1>ClassMapping</h1>
 * <p>
 * Change description here.
 *
 * @author Johnny Miller (鍾俊), email: johnnysviva@outlook.com, 6/9/23 10:20 PM
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ClassMapping {
    Class<?> source();
}
