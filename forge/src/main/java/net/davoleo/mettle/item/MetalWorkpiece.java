package net.davoleo.mettle.item;

import net.davoleo.mettle.Mettle;
import net.davoleo.mettle.capability.MetalMixture;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class MetalWorkpiece extends Item {

    public MetalWorkpiece()
    {
        super(new Properties().tab(Mettle.CREATIVE_TAB));
        //setRegistryName("mettle:metal_workpiece");
    }


    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level pLevel, @Nonnull Player pPlayer, @Nonnull InteractionHand pUsedHand)
    {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);

        stack.getCapability(MetalMixture.MIXTURE).ifPresent(iMixture -> {
            if (iMixture instanceof MetalMixture mixture) {
                System.out.println(mixture.banane);
            }
        });

        return InteractionResultHolder.pass(stack);
    }
}
