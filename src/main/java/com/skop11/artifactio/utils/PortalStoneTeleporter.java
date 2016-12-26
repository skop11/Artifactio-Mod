package com.skop11.artifactio.utils;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class PortalStoneTeleporter extends Teleporter
{

    private final WorldServer worldServer;

    private double x, y, z;

    public PortalStoneTeleporter(WorldServer worldServer, double x, double y, double z)
    {
        super(worldServer);
        this.worldServer = worldServer;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void placeInPortal(Entity entityIn, float rotationYaw)
    {
        worldServer.getBlockState(new BlockPos((int) x, (int) y, (int) z));

        entityIn.setPosition(x, y, z);
        entityIn.motionX = 0f;
        entityIn.motionY = 0f;
        entityIn.motionZ = 0f;

    }
}
