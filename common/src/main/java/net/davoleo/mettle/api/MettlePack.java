package net.davoleo.mettle.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface MettlePack {

    String modid();

}
