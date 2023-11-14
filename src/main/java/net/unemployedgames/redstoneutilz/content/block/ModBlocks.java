package net.unemployedgames.redstoneutilz.content.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.unemployedgames.redstoneutilz.RedstoneMod;
import net.unemployedgames.redstoneutilz.content.block.custom.CopycatBlock;
import net.unemployedgames.redstoneutilz.content.block.entities.ConfigurableCustomButton;
import net.unemployedgames.redstoneutilz.content.item.ModItems;

import java.util.function.Supplier;

import static net.minecraft.world.item.Items.registerBlock;

public class ModBlocks {
    public static DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, RedstoneMod.Mod_ID);

    //public static final RegistryObject<Block> HOWL_BLOCK = registerBlock("block_of_howl",
    //() -> new Block(BlockBehaviour.Properties.of()
    //.destroyTime(2f)
    //.instrument(NoteBlockInstrument.SNARE)
    //.strength(2f, 3f)
    //.pushReaction(PushReaction.PUSH_ONLY)
    //.sound(SoundType.STONE)
    //.mapColor(DyeColor.BLUE)
    //.requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> COPYCAT_BLOCK = registerBlock("copycat_block",
    () -> new CopycatBlock(BlockBehaviour.Properties.of()
            .instrument(NoteBlockInstrument.BASS)
            .strength(0.5f, 1f)
            .pushReaction(PushReaction.BLOCK)
            .sound(SoundType.STONE)
            .mapColor(DyeColor.GRAY)
            .noOcclusion()
    ));

    public static final RegistryObject<Block> COPYCAT_BUTTON = registerBlock("copycat_button",
            () -> new ConfigurableCustomButton(
                    BlockBehaviour.Properties.copy(ModBlocks.COPYCAT_BLOCK.get()),
                    BlockSetType.STONE,
                    10,
                    false
            ));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }
    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
