package com.skop11.artifactio.utils;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

public class PlayerTelport
{

    public static void teleportToDim(EntityPlayerMP player, int dim, int x, int y, int z)
    {
        int curDim = player.worldObj.provider.getDimension();
        MinecraftServer server = player.worldObj.getMinecraftServer();
        WorldServer worldServer = server.worldServerForDimension(dim);
        player.addExperienceLevel(0);

        worldServer.getMinecraftServer().getPlayerList().transferPlayerToDimension(
                player, dim, new PortalStoneTeleporter(worldServer, x + 0.5f, y, z + 0.5f));
        player.setPositionAndUpdate(x + 0.5f, y, z + 0.5f);
        if (curDim == 1)
        {
            worldServer.spawnEntityInWorld(player);
            worldServer.updateEntityWithOptionalForce(player, false);
        }

    }

}
