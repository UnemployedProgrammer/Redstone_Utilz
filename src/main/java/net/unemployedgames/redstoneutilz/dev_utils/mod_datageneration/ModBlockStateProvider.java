package net.unemployedgames.redstoneutilz.dev_utils.mod_datageneration;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.unemployedgames.redstoneutilz.RedstoneMod;
import net.unemployedgames.redstoneutilz.content.block.ModBlocks;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, RedstoneMod.Mod_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.COPYCAT_BLOCK);
        blockWithItem(ModBlocks.ZINC_BLOCK);
        simpleBlockWithItem(ModBlocks.ZINK_ORE.get(), cubeAll(ModBlocks.ZINK_ORE.get()));
        simpleBlockWithItem(ModBlocks.DEEPSLATE_ZINK_ORE.get(), cubeAll(ModBlocks.DEEPSLATE_ZINK_ORE.get()));

        //directionalBlock(ModBlocks.PLACER_BLOCK.get(), new ModelFile.UncheckedModelFile(modLoc("block/placer_block")));
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
