package com.skop11.portalstone;

import com.skop11.portalstone.item.PortalStoneItems;
import com.skop11.portalstone.proxy.CommonProxy;
import com.skop11.portalstone.tab.CreativeTabPortalStone;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = PortalStoneMod.MODID, version = PortalStoneMod.VERSION, name = PortalStoneMod.NAME)
public class PortalStoneMod
{
    public static final String MODID = "portalstone";
    public static final String VERSION = "0.1";
    public static final String NAME = "PortalStone Mod";

    @SidedProxy(clientSide = "com.skop11.portalstone.proxy.ClientProxy", serverSide = "com.skop11.portalstone.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static PortalStoneMod instace;

    public static CreativeTabPortalStone tabPortalStone;


    @EventHandler @SuppressWarnings("unused")
    public void preInit(FMLPreInitializationEvent event)
    {
        tabPortalStone = new CreativeTabPortalStone(CreativeTabs.getNextID(), "tab_portalstone");
        PortalStoneItems.preInit();
        proxy.preInit(event);
    }

    @EventHandler @SuppressWarnings("unused")
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

    @EventHandler @SuppressWarnings("unused")
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit(event);
    }

}
