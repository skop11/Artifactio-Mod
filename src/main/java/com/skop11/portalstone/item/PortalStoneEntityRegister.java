package com.skop11.portalstone.item;

import com.skop11.portalstone.PortalStoneMod;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class PortalStoneEntityRegister
{

    public static void preInit()
    {
        EntityRegistry.registerModEntity(EntityItemPortalStone.class, "ItemPortalStone", 1, PortalStoneMod.getMod(), 64, 20,true);

    }

}
