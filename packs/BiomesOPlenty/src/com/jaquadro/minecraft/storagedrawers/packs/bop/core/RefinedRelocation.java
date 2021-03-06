package com.jaquadro.minecraft.storagedrawers.packs.bop.core;

import com.jaquadro.minecraft.storagedrawers.StorageDrawers;
import com.jaquadro.minecraft.storagedrawers.api.StorageDrawersApi;
import com.jaquadro.minecraft.storagedrawers.api.pack.BlockConfiguration;
import com.jaquadro.minecraft.storagedrawers.api.pack.IPackBlockFactory;
import com.jaquadro.minecraft.storagedrawers.api.pack.IPackDataResolver;
import com.jaquadro.minecraft.storagedrawers.config.ConfigManager;
import com.jaquadro.minecraft.storagedrawers.integration.notenoughitems.NEIStorageDrawersConfig;
import com.jaquadro.minecraft.storagedrawers.integration.refinedrelocation.*;
import com.jaquadro.minecraft.storagedrawers.packs.bop.StorageDrawersPack;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class RefinedRelocation
{
    public static Block fullDrawers1;
    public static Block fullDrawers2;
    public static Block fullDrawers4;
    public static Block halfDrawers2;
    public static Block halfDrawers4;

    public static void init () {
        ConfigManager config = StorageDrawers.config;
        if (!Loader.isModLoaded("RefinedRelocation") || !config.cache.enableRefinedRelocationIntegration)
            return;

        IPackBlockFactory factory = StorageDrawersApi.instance().packFactory();
        IPackDataResolver resolver = StorageDrawersPack.instance.resolver;

        fullDrawers1 = factory.createBlock(BlockConfiguration.SortingFull1, resolver);
        fullDrawers2 = factory.createBlock(BlockConfiguration.SortingFull2, resolver);
        fullDrawers4 = factory.createBlock(BlockConfiguration.SortingFull4, resolver);
        halfDrawers2 = factory.createBlock(BlockConfiguration.SortingHalf2, resolver);
        halfDrawers4 = factory.createBlock(BlockConfiguration.SortingHalf4, resolver);

        SortingBlockRegistry.register(ModBlocks.fullDrawers1, fullDrawers1);
        SortingBlockRegistry.register(ModBlocks.fullDrawers2, fullDrawers2);
        SortingBlockRegistry.register(ModBlocks.fullDrawers4, fullDrawers4);
        SortingBlockRegistry.register(ModBlocks.halfDrawers2, halfDrawers2);
        SortingBlockRegistry.register(ModBlocks.halfDrawers4, halfDrawers4);

        if (config.isBlockEnabled("fulldrawers1"))
            factory.registerBlock(fullDrawers1, "fullDrawersSort1");
        if (config.isBlockEnabled("fulldrawers2"))
            factory.registerBlock(fullDrawers2, "fullDrawersSort2");
        if (config.isBlockEnabled("fulldrawers4"))
            factory.registerBlock(fullDrawers4, "fullDrawersSort4");
        if (config.isBlockEnabled("halfdrawers2"))
            factory.registerBlock(halfDrawers2, "halfDrawersSort2");
        if (config.isBlockEnabled("halfdrawers4"))
            factory.registerBlock(halfDrawers4, "halfDrawersSort4");

        if (!config.cache.addonShowNEI) {
            NEIStorageDrawersConfig.hideBlock(ModBlocks.getQualifiedName(fullDrawers1));
            NEIStorageDrawersConfig.hideBlock(ModBlocks.getQualifiedName(fullDrawers2));
            NEIStorageDrawersConfig.hideBlock(ModBlocks.getQualifiedName(fullDrawers4));
            NEIStorageDrawersConfig.hideBlock(ModBlocks.getQualifiedName(halfDrawers2));
            NEIStorageDrawersConfig.hideBlock(ModBlocks.getQualifiedName(halfDrawers4));
        }
    }

    public static void postInit () {
        ConfigManager config = StorageDrawers.config;
        if (!Loader.isModLoaded("RefinedRelocation") || !config.cache.enableRefinedRelocationIntegration)
            return;

        IPackDataResolver resolver = StorageDrawersPack.instance.resolver;

        for (int i = 0; i < 16; i++) {
            if (!resolver.isValidMetaValue(i))
                continue;

            if (config.isBlockEnabled("fulldrawers1"))
                GameRegistry.addRecipe(new ItemStack(fullDrawers1, 1, i), "x x", " y ", "x x",
                    'x', Items.gold_nugget, 'y', new ItemStack(ModBlocks.fullDrawers1, 1, i));
            if (config.isBlockEnabled("fulldrawers2"))
                GameRegistry.addRecipe(new ItemStack(fullDrawers2, 1, i), "x x", " y ", "x x",
                    'x', Items.gold_nugget, 'y', new ItemStack(ModBlocks.fullDrawers2, 1, i));
            if (config.isBlockEnabled("halfdrawers2"))
                GameRegistry.addRecipe(new ItemStack(halfDrawers2, 1, i), "x x", " y ", "x x",
                    'x', Items.gold_nugget, 'y', new ItemStack(ModBlocks.halfDrawers2, 1, i));
            if (config.isBlockEnabled("fulldrawers4"))
                GameRegistry.addRecipe(new ItemStack(fullDrawers4, 1, i), "x x", " y ", "x x",
                    'x', Items.gold_nugget, 'y', new ItemStack(ModBlocks.fullDrawers4, 1, i));
            if (config.isBlockEnabled("halfdrawers4"))
                GameRegistry.addRecipe(new ItemStack(halfDrawers4, 1, i), "x x", " y ", "x x",
                    'x', Items.gold_nugget, 'y', new ItemStack(ModBlocks.halfDrawers4, 1, i));
        }
    }
}
