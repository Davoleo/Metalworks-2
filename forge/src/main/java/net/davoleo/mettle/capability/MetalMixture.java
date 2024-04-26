package net.davoleo.mettle.capability;

import net.davoleo.mettle.api.capability.IMetalMixture;
import net.davoleo.mettle.api.metal.IMetal;
import net.davoleo.mettle.api.metal.MetalProperties;
import net.davoleo.mettle.api.metal.MixtureRatio;
import net.davoleo.mettle.register.CoreMetals;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.registries.RegistryObject;

public class MetalMixture implements IMetalMixture {

    public static final Capability<IMetalMixture> MIXTURE = CapabilityManager.get(new CapabilityToken<>() {
        @Override
        public String toString()
        {
            return super.toString();
        }
    });



    MetalProperties properties;
    int color;

    public static IMetal getMetal(ItemStack stack)
    {
        for (RegistryObject<IMetal> entry : CoreMetals.BASE_REGISTER.getEntries())
        {
            if(stack.is(CoreMetals.METAL_TAG.get(entry.getId())))
            {

            }
        }

        return null;
    }

    public String banane;

    @Override
    public CompoundTag serializeNBT()
    {
        CompoundTag tag = new CompoundTag();
        tag.putString("BANANE", "baaa");
        return tag;
    }

    //lm -> ls

    //100% | 90% COPPER | 10% TIN
    //100% | 75% C | 25% T
    @Override
    public void deserializeNBT(CompoundTag nbt)
    {
        banane = nbt.getString("BANANE");
        //nbt.remove("BANANE");
    }

    @Override
    public MixtureRatio getRatio()
    {
        return null;
    }
}
