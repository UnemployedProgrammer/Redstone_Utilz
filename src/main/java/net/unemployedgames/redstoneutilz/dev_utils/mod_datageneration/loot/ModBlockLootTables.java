package net.unemployedgames.redstoneutilz.dev_utils.mod_datageneration.loot;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import net.unemployedgames.redstoneutilz.content.block.ModBlocks;
import net.unemployedgames.redstoneutilz.content.item.ModItems;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {

    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.COPYCAT_BLOCK.get());
        this.dropSelf(ModBlocks.ZINC_BLOCK.get());
        this.dropOther(ModBlocks.ZINK_ORE.get(), ModItems.ZINC_INGOT.get());  //CHANGE TO ZINC ITEM
        this.dropOther(ModBlocks.DEEPSLATE_ZINK_ORE.get(), ModItems.ZINC_INGOT.get());  //CHANGE TO ZINC ITEM
        this.dropSelf(ModBlocks.PLACER_BLOCK.get());
        this.dropSelf(ModBlocks.COPYCAT_BUTTON.get());
        this.dropSelf(ModBlocks.DISPLAY_BLOCK.get());
        this.dropSelf(ModBlocks.DESTROYER_BLOCK.get());
    }

    protected LootTable.Builder createCopperLikeOreDrops(Block pBlock, Item item) {
        return createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock,
                        LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
