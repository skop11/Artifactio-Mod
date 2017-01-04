package com.skop11.artifactio.item;

import com.skop11.artifactio.ArtifactioMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ArtifactioItems
{

    public static Item inactPortalStone;
    public static Item portalStone;
    public static Item toolCompendium;
    public static Item coating;
    public static Item redFlower;

    public static void preInit()
    {
        inactPortalStone = new ItemInactivePortalStone("inactpstone");
        portalStone = new ItemPortalStone("portalstone");
        toolCompendium = new ItemToolCompendium("tool_compendium");
        coating = new ItemCoating("coating");
        redFlower = new ItemRedFlower("redflower_solar");
        registerItems();
    }

    public static void registerItems()
    {
        GameRegistry.register(inactPortalStone, new ResourceLocation(ArtifactioMod.MODID, "inactpstone"));
        GameRegistry.register(portalStone, new ResourceLocation(ArtifactioMod.MODID, "portalstone"));
        GameRegistry.register(toolCompendium, new ResourceLocation(ArtifactioMod.MODID, "tool_compendium"));
        GameRegistry.register(coating, new ResourceLocation(ArtifactioMod.MODID, "coating"));
        GameRegistry.register(redFlower, new ResourceLocation(ArtifactioMod.MODID, "redflower_solar"));
    }

    public static void registerRenders()
    {
        registerRender(inactPortalStone, 0, "inactpstone");
        for (int i = 0; i < ItemDamageHandler.PortalStoneTypes.values().length; i ++)
        {
            registerRender(portalStone, i, "portalstone_" + i);
        }
        registerRender(toolCompendium, 0, "tool_compendium");
        registerRender(coating, 0, "coating");
        registerRender(redFlower, 0, "redflower_solar");
    }


    public static void registerRender(Item item, int meta, String fileName)
    {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta,
                new ModelResourceLocation(new ResourceLocation(ArtifactioMod.MODID, fileName), "inventory"));
    }

}
