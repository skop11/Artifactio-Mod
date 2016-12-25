package com.skop11.artifactio.entity;

import com.skop11.artifactio.ArtifactioMod;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ArtifactioEntityRegister
{

    public static void preInit()
    {
        EntityRegistry.registerModEntity(EntityItemPortalStone.class, "ItemPortalStone", 1, ArtifactioMod.getMod(), 64, 20,true);

    }

}
