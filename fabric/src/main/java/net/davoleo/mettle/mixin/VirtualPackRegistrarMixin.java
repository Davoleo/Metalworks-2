package net.davoleo.mettle.mixin;

import com.google.common.collect.ImmutableSet;
import net.davoleo.mettle.data.VirtualRepositorySource;
import net.minecraft.server.packs.repository.PackRepository;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PackRepository.class)
public class VirtualPackRegistrarMixin {

    @Redirect(
            method = "<init>(Lnet/minecraft/server/packs/repository/Pack$PackConstructor;[Lnet/minecraft/server/packs/repository/RepositorySource;)V",
            at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableSet;copyOf([Ljava/lang/Object;)Lcom/google/common/collect/ImmutableSet;")
    )
    private ImmutableSet<Object> onAssignSources(Object[] elements) {
        return ImmutableSet.builder().add(elements).add(VirtualRepositorySource.get()).build();
    }
}
