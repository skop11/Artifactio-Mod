package com.skop11.portalstone.item;

import com.skop11.portalstone.PortalStoneMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class PortalStoneItems
{

    public static Item inactPortalStone;
    public static Item portalStone;

    public static void preInit()
    {
        inactPortalStone = new ItemInactivePortalStone("inactpstone");
        portalStone = new ItemPortalStone("portalstone");
        registerItems();
    }

    public static void registerItems()
    {
        GameRegistry.register(inactPortalStone, new ResourceLocation(PortalStoneMod.MODID, "inactpstone"));
        GameRegistry.register(portalStone, new ResourceLocation(PortalStoneMod.MODID, "portalstone"));
    }

    public static void registerRenders()
    {
        registerRender(inactPortalStone, 0, "inactpstone");
        for (int i = 0; i < ItemDamageHandler.PortalStoneTypes.values().length; i ++)
        {
            registerRender(portalStone, i, "portalstone_" + i);
        }
    }

    public static void registerRender(Item item)
    {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0,
                new ModelResourceLocation(new ResourceLocation(PortalStoneMod.MODID, item.getUnlocalizedName()), "inventory"));
    }


    public static void registerRender(Item item, int meta, String fileName)
    {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta,
                new ModelResourceLocation(new ResourceLocation(PortalStoneMod.MODID, fileName), "inventory"));
    }

}
