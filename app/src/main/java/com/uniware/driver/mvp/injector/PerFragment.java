package com.uniware.driver.mvp.injector;

import java.lang.annotation.Retention;
import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
/**
 * Created by jian on 15/12/08.
 */
@Scope
@Retention(RUNTIME)
public @interface PerFragment {}
