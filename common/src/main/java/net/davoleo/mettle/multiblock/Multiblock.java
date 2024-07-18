package net.davoleo.mettle.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Predicate;

public class Multiblock {

    private StructureTemplate template;

    private Multiblock(StructureTemplate template)
    {
        this.template = template;
    }

    private List<StructureTemplate.StructureBlockInfo> getStructureBlocks() {
        return getStructureBlocks(structureBlockInfo -> true);
    }

    private List<StructureTemplate.StructureBlockInfo> getStructureBlocks(Predicate<StructureTemplate.StructureBlockInfo> condition) {
        List<StructureTemplate.StructureBlockInfo> list = new ArrayList<>();

        //access widener
        StructureTemplate.Palette palette = template.palettes.get(0);
        for (var blockInfo : palette.blocks()) {
            if (condition.test(blockInfo)) {
                list.add(blockInfo);
            }
        }
        return list;
    }

    public boolean checkStructure(Level level, BlockPos pos)
    {

        var pivotList = getStructureBlocks(structureBlockInfo -> structureBlockInfo.state.is(level.getBlockState(pos).getBlock()));
        if (pivotList.isEmpty()) {
            throw new IllegalStateException("no pivot in structure, oh man...");
        }

        StructurePlaceSettings settings = new StructurePlaceSettings().setRotationPivot(pivotList.get(0).pos);

        for (StructureTemplate.StructureBlockInfo structureBlockInfo : this.getStructureBlocks())
        {
            BlockPos worldPos = StructureTemplate.calculateRelativePosition(settings, structureBlockInfo.pos).offset(pos);

            // pos is the base multiblock block (controller), we can safely assume it's the controller TE block
            if (worldPos == pos) {
                continue;
            }

            if(!structureBlockInfo.state.is(level.getBlockState(worldPos).getBlock()))
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

    public Multiblock getMultiblock(@NotNull ServerLevel server, ResourceLocation resourceLocation)
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