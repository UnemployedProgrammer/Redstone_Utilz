package net.unemployedgames.redstoneutilz.dev_utils.mod_datageneration;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.unemployedgames.redstoneutilz.RedstoneMod;
import net.unemployedgames.redstoneutilz.content.block.ModBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {

    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, RedstoneMod.Mod_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(Tags.Blocks.NEEDS_NETHERITE_TOOL)
                .add(ModBlocks.ZINK_ORE.get())
                .add(ModBlocks.DEEPSLATE_ZINK_ORE.get());

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(
                        ModBlocks.ZINK_ORE.get(),
                        ModBlocks.DEEPSLATE_ZINK_ORE.get(),
                        ModBlocks.ZINC_BLOCK.get());
    }
}
