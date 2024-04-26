package net.davoleo.mettle.api.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Multiblock {

    private StructureTemplate template;

    private Vec3i[] itemPorts;
    private Vec3i[] fluidPorts;
    private Vec3i[] simpleSlaves;

    private Multiblock(StructureTemplate template)
    {
        this.template = template;
        //OFFUSCAMENTO
    }

    public boolean checkStructure(Level level, BlockPos pos)
    {
        for (StructureTemplate.StructureBlockInfo block : template.palettes.get(0).blocks())
        {
            if(!block.state.is(level.getBlockState(pos).getBlock()))
                return false;
        }
        return true;
    }

    /*
    private static Optional<InputStream> getModResource(PackType type, ResourceLocation name)
    {
        if (EXISTING_HELPER != null)
        {
            try
            {
                int slash = name.getPath().indexOf('/');
                String prefix = name.getPath().substring(0, slash);
                ResourceLocation shortLoc = new ResourceLocation(
                        name.getNamespace(),
                        name.getPath().substring(slash + 1)
                );
                return Optional.of(
                        EXISTING_HELPER.getResource(shortLoc, type, "", prefix)
                                .getInputStream()
                );
            }
            catch (Exception x)
            {
                throw new RuntimeException(x);
            }
        }
    }
    */

    public void init(ServerLevel serverLevel, StructureTemplate template) {
        //OFFUSCAMENTO
        //int x = i * cos(a) + k * -sin(a)
        //int z = i * sin(a) + k * cos(a)
    }

    private static Map<ResourceLocation, Multiblock> map = new HashMap<>();

    public Multiblock getMultiblock(@Nonnull ServerLevel server, ResourceLocation resourceLocation)
    {
        Multiblock multiblock = map.get(resourceLocation);
        if(multiblock == null)
        {
            Optional<StructureTemplate> optional = server.getStructureManager().get(resourceLocation);
            if(optional.isPresent())
            {
                multiblock = new Multiblock(optional.get());
                map.put(resourceLocation, multiblock);
            }
        }
        return multiblock;
    }



    enum BlockType {
        NONE((state, level, pos) -> true),
        CONTROLLER((state, level, pos) -> state.is(Blocks.STONE)),
        SIMPLE_SLAVE((state, level, pos) -> true),
        ITEM_PORT((state, level, pos) -> state.is(Blocks.IRON_BLOCK)),
        FLUID_PORT((state, level, pos) -> state.is(Blocks.GOLD_BLOCK));

        private final BlockBehaviour.StatePredicate predicate;

        BlockType(BlockBehaviour.StatePredicate predicate)
        {
            this.predicate = predicate;
        }

        public BlockBehaviour.StatePredicate getPredicate()
        {
            return predicate;
        }
    }

}