package com.skop11.portalstone.item;

import com.skop11.portalstone.PortalStoneMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by David on 15/12/2016.
 */
public class PortalStoneItems
{

    public static Item portalStone;

    public static void preInit()
    {

        portalStone = new ItemPortalStone("portalstone");

        registerItems();


    }

    public static void registerItems()
    {
        GameRegistry.register(portalStone, new ResourceLocation(PortalStoneMod.MODID, "portalstone"));
    }

    public static void registerRenders()
    {
        registerRender(portalStone);
    }

    public static void registerRender(Item item)
    {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0,
                new ModelResourceLocation(PortalStoneMod.MODID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
    }

}
