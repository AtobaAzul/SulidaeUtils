package net.atobaazul.sulidae_utils.registries;

import net.atobaazul.sulidae_utils.common.blockentity.ElectrodeBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static net.atobaazul.sulidae_utils.SulidaeUtils2.MOD_ID;

public class SulidaeBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MOD_ID);

    public static final Supplier<BlockEntityType<ElectrodeBlockEntity>> MY_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("my_block_entity",
            // The block entity type.
            () -> new BlockEntityType<>(
                    // The supplier to use for constructing the block entity instances.
                    ElectrodeBlockEntity::new,
                    // An optional value that, when true, only allows players with OP permissions
                    // to load NBT data (e.g. placing a block item)
                    false,
                    // A vararg of blocks that can have this block entity.
                    // This assumes the existence of the referenced blocks as DeferredBlock<Block>s.
                    SulidaeBlocks.ELECTRODE.get()));


}
