package com.black.multi.libnavannotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created by wei.
 * Date: 2020/5/18 下午10:13
 * Description:
 */
@Target(ElementType.TYPE)
public @interface FragmentDestination {
    String pageUrl();
    boolean needLogin() default false;
    boolean asStartPage() default false;
}
