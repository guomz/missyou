package com.guomz.missyou.annotations;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ScopeLevel {

    /**
     * 等级，默认游客等级
     * @return
     */
    int value() default 0;
}
