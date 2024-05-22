package net.davoleo.mettle.tag;

import net.davoleo.mettle.registry.IRegistryName;
import net.minecraft.tags.TagKey;

import java.util.List;

public interface ITagMember<T> extends IRegistryName {

    List<TagKey<T>> getTags();

}
