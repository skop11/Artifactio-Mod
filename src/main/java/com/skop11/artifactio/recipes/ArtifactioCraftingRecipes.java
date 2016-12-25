package com.skop11.artifactio.recipes;

import com.skop11.artifactio.item.ArtifactioItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

/**
 * Created by David on 20/12/2016.
 */
public class ArtifactioCraftingRecipes
{

    public static void register()
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

}
