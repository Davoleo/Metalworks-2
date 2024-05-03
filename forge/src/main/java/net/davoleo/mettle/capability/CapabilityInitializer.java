package net.davoleo.mettle.capability;

import net.davoleo.mettle.MettleForge;
import net.davoleo.mettle.api.capability.IMetalMixture;
import net.davoleo.mettle.item.MetalWorkpiece;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CapabilityInitializer {

    @SubscribeEvent
    public static void capabilityAttach(final AttachCapabilitiesEvent<ItemStack> event)
    {
        if (event.getObject().getItem() instanceof MetalWorkpiece) {
            IMetalMixture mixture = new MetalMixture();
            LazyOptional<IMetalMixture> mixtureOptional = LazyOptional.of(() -> mixture);

                ICapabilityProvider provider = new ICapabilitySerializable<CompoundTag>() {
                    @NotNull
                    @Override
                    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
                    {
                        if (cap == MetalMixture.MIXTURE) {
                            return mixtureOptional.cast();
                        }
                        return LazyOptional.empty();

                    }

                    @Override
                    public CompoundTag serializeNBT()
                    {
                        return mixture.serializeNBT();
                    }

                    @Override
                    public void deserializeNBT(CompoundTag nbt)
                    {
                        mixture.deserializeNBT(nbt);
                    }
                };

            event.addCapability(new ResourceLocation(MettleForge.MODID, "metal_mixture"), provider);
        }
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event)
    {
        event.register(IMetalMixture.class);
    }

}
