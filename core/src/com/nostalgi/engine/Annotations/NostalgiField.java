package com.nostalgi.engine.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Krille on 19/10/2016.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD}) //on field and method level
public @interface NostalgiField {
    boolean fromEditor() default true;
    String fieldName() default "";
}
