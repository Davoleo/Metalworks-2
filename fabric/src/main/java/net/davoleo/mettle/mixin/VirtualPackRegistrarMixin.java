package net.davoleo.mettle.mixin;

import com.google.common.collect.ImmutableSet;
import net.davoleo.mettle.data.VirtualRepositorySource;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.RepositorySource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(PackRepository.class)
public class VirtualPackRegistrarMixin {

    @Final @Shadow @Mutable
    private Set<RepositorySource> sources;

    @Inject(
            method = "<init>(Lnet/minecraft/server/packs/repository/Pack$PackConstructor;[Lnet/minecraft/server/packs/repository/RepositorySource;)V",
            at = @At("TAIL")
    )
    private void onConstruction(CallbackInfo ci) {
        this.sources = ImmutableSet.<RepositorySource>builder()
                .addAll(this.sources)
                .add(VirtualRepositorySource.get())
                .build();
    }
}
