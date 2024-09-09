package com.library.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FileDesc {
    String schema() default "public";

    String database() default "database";

    String filename() default "";

    boolean autocrement() default false;
}
