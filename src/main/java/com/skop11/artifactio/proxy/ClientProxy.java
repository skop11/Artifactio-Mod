package com.skop11.artifactio.proxy;

import com.skop11.artifactio.ArtifactioMod;
import com.skop11.artifactio.item.ArtifactioItems;
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
        ArtifactioItems.registerRenders();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event)
    {

    }

    @Override
    public void registerModelBakeryVariants()
    {
        ModelBakery.registerItemVariants(ArtifactioItems.portalStone,
                new ResourceLocation(ArtifactioMod.MODID, "portalstone_0"),
                new ResourceLocation(ArtifactioMod.MODID, "portalstone_1")
                );
    }
}
