package com.skop11.portalstone.proxy;

import com.skop11.portalstone.PortalStoneMod;
import com.skop11.portalstone.item.PortalStoneItems;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit(FMLPreInitializationEvent event)
    {

    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        PortalStoneItems.registerRenders();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event)
    {

    }

    @Override
    public void registerModelBakeryVariants()
    {
        ModelBakery.registerItemVariants(PortalStoneItems.portalStone,
                new ResourceLocation(PortalStoneMod.MODID, "portalstone_0"),
                new ResourceLocation(PortalStoneMod.MODID, "portalstone_1")
                );
    }
}
