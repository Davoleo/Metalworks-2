package net.davoleo.mettle.api.capability;

import net.davoleo.mettle.api.metal.MixtureRatio;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface IMetalMixture extends INBTSerializable<CompoundTag> {

    MixtureRatio getRatio();

}
