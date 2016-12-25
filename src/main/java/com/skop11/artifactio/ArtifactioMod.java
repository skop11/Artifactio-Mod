package com.skop11.artifactio;

import com.skop11.artifactio.entity.ArtifactioEntityRegister;
import com.skop11.artifactio.gui.GUIHandler;
import com.skop11.artifactio.item.ArtifactioItems;
import com.skop11.artifactio.proxy.CommonProxy;
import com.skop11.artifactio.recipes.ArtifactioCraftingRecipes;
import com.skop11.artifactio.tab.CreativeTabArtifactio;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = ArtifactioMod.MODID, version = ArtifactioMod.VERSION, name = ArtifactioMod.NAME)
public class ArtifactioMod
{
    public static final String MODID = "artifactio";
    public static final String VERSION = "1.1.0";
    public static final String NAME = "Artifactio!";

    @SidedProxy(clientSide = "com.skop11.artifactio.proxy.ClientProxy", serverSide = "com.skop11.artifactio.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    private static ArtifactioMod instance;

    public static CreativeTabArtifactio tabArtifactio;


    @EventHandler @SuppressWarnings("unused")
    public void preInit(FMLPreInitializationEvent event)
    {
        tabArtifactio = new CreativeTabArtifactio(CreativeTabs.getNextID(), "tab_artifactio");
        ArtifactioItems.preInit();
        ArtifactioEntityRegister.preInit();
        proxy.preInit(event);
        proxy.registerModelBakeryVariants();

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GUIHandler());

    }

    @EventHandler @SuppressWarnings("unused")
    public void init(FMLInitializationEvent event)
    {
        ArtifactioCraftingRecipes.register();
        proxy.init(event);

    }

    @EventHandler @SuppressWarnings("unused")
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit(event);
    }

    public static ArtifactioMod getMod()
    {
        return instance;
    }

}
