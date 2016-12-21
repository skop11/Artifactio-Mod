package com.skop11.portalstone.recipes;

import com.skop11.portalstone.item.PortalStoneItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

/**
 * Created by David on 20/12/2016.
 */
public class PortalStoneCraftingRecipes
{

    public static void register()
    {
        // Component items
        final ItemStack obsidian = new ItemStack(Blocks.OBSIDIAN);
        final ItemStack enderEye = new ItemStack(Items.ENDER_EYE);
        final ItemStack polishedAndesite = new ItemStack(Blocks.STONE);
        polishedAndesite.setItemDamage(6);

        // Result items
        final ItemStack portalStone = new ItemStack(PortalStoneItems.inactPortalStone);


        GameRegistry.addRecipe(new ShapedOreRecipe(portalStone,
                "oao",
                "aya",
                "oao",
                'o', obsidian, 'y', enderEye, 'a', polishedAndesite));


    }

}
