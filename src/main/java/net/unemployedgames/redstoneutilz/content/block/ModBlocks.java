package net.unemployedgames.redstoneutilz.content.block;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DebugStickItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
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
import net.unemployedgames.redstoneutilz.content.block.entities.configurable_button.ConfigurableCustomButton;
import net.unemployedgames.redstoneutilz.content.block.entities.destroyer.DestroyerBlock;
import net.unemployedgames.redstoneutilz.content.block.entities.display.DisplayBlock;
import net.unemployedgames.redstoneutilz.content.block.entities.placer.PlacerBlock;
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

    public static final RegistryObject<Block> ZINC_BLOCK = registerBlock("zinc_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(0.5f, 1f)
                    .pushReaction(PushReaction.NORMAL)
                    .sound(SoundType.STONE)
                    .mapColor(DyeColor.GRAY)
                    .noOcclusion()
            ));

    public static final RegistryObject<DropExperienceBlock> ZINK_ORE = registerBlock("zinc_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE), UniformInt.of(3, 13)));

    public static final RegistryObject<DropExperienceBlock> DEEPSLATE_ZINK_ORE = registerBlock("deepslate_zinc_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE), UniformInt.of(3, 13)));
    public static final RegistryObject<Block> PLACER_BLOCK = registerBlock("placer_block",
            () -> new PlacerBlock(BlockBehaviour.Properties.of()
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(0.5f, 1f)
                    .pushReaction(PushReaction.BLOCK)
                    .sound(SoundType.STONE)
                    .mapColor(DyeColor.GRAY)
                    .noOcclusion()
            ));

    public static final RegistryObject<Block> DISPLAY_BLOCK = registerBlock("display_block",
            () -> new DisplayBlock(BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK).noOcclusion().pushReaction(PushReaction.BLOCK)));

    public static final RegistryObject<Block> DESTROYER_BLOCK = registerBlock("destroyer_block",
            () -> new DestroyerBlock(BlockBehaviour.Properties.of()
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2f, 1f)
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

    //public static final RegistryObject<Block> INFINITE_LENGTH_RREDSTONE = registerBlock("infinite_length_redstone",
    //        () -> new InfiniteLenghRedstone(BlockBehaviour.Properties.copy(Blocks.REDSTONE_WIRE)));

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