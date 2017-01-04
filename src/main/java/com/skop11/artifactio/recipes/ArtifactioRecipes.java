package com.skop11.artifactio.recipes;

import com.skop11.artifactio.item.ArtifactioItems;
import com.skop11.artifactio.item.ItemCoating;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ArtifactioRecipes
{

    public static void register()
    {
        registerCrafting();
        registerCoating();
    }

    private static void registerCrafting()
    {
        // Component items
        final ItemStack obsidian = new ItemStack(Blocks.OBSIDIAN);
        final ItemStack enderPearl = new ItemStack(Items.ENDER_PEARL);
        final ItemStack diamond = new ItemStack(Items.DIAMOND);

        final ItemStack blackDye = new ItemStack(Items.DYE);
        final ItemStack enchBook = new ItemStack(Items.ENCHANTED_BOOK);
        final ItemStack comparator = new ItemStack(Items.COMPARATOR);
        final ItemStack diamPick = new ItemStack(Items.DIAMOND_PICKAXE);
        final ItemStack diamAxe = new ItemStack(Items.DIAMOND_AXE);
        final ItemStack diamShovel = new ItemStack(Items.DIAMOND_SHOVEL);


        // Result items
        final ItemStack portalStone = new ItemStack(ArtifactioItems.inactPortalStone);
        final ItemStack toolComp = new ItemStack(ArtifactioItems.toolCompendium);

        GameRegistry.addRecipe(new ShapedOreRecipe(portalStone,
                "oao",
                "aya",
                "oao",
                'o', obsidian, 'y', diamond, 'a', enderPearl
        ));
        GameRegistry.addRecipe(new ShapedOreRecipe(toolComp,
                "aba",
                "cde",
                "afa",
                'a', blackDye, 'b', diamPick, 'c', diamAxe, 'd', enchBook, 'e', diamShovel, 'f', comparator
        ));
    }

    private static void registerCoating()
    {
        ItemCoatingRecipe coating = new ItemCoatingRecipe();
        coating.addCoatingItem(Items.DIAMOND_SHOVEL.getRegistryName().toString());
        coating.addCoatingItem(Items.GOLDEN_SHOVEL.getRegistryName().toString());
        coating.addCoatingItem(Items.IRON_SHOVEL.getRegistryName().toString());
        coating.addCoatingItem(Items.DIAMOND_AXE.getRegistryName().toString());
        coating.addCoatingItem(Items.GOLDEN_AXE.getRegistryName().toString());
        coating.addCoatingItem(Items.IRON_AXE.getRegistryName().toString());
        coating.addCoatingItem(Items.DIAMOND_PICKAXE.getRegistryName().toString());
        coating.addCoatingItem(Items.GOLDEN_PICKAXE.getRegistryName().toString());
        coating.addCoatingItem(Items.IRON_PICKAXE.getRegistryName().toString());
        coating.addCoatingItem(Items.DIAMOND_HOE.getRegistryName().toString());
        coating.addCoatingItem(Items.GOLDEN_HOE.getRegistryName().toString());
        coating.addCoatingItem(Items.IRON_HOE.getRegistryName().toString());
        coating.addCoatingItem(Items.DIAMOND_SWORD.getRegistryName().toString());
        coating.addCoatingItem(Items.GOLDEN_SWORD.getRegistryName().toString());
        coating.addCoatingItem(Items.IRON_SWORD.getRegistryName().toString());
        coating.addCoatingItem(Items.SHEARS.getRegistryName().toString());
        coating.addCoatingItem(Items.FLINT_AND_STEEL.getRegistryName().toString());

        GameRegistry.addRecipe(coating);
    }

}
