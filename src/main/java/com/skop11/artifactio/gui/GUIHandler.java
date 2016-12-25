package com.skop11.artifactio.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler
{

    private static final int GUIID_TOOLCOM = 1;

    public static int getGuiID() { return GUIID_TOOLCOM; }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {

        if (ID == getGuiID())
        {
            return new ContainerBasic(player, player.inventory, new InventoryItemBasic(player.getHeldItemMainhand()));
        }
        else
            System.err.println("Invalid ID: expected " + getGuiID() + ", received " + ID);

        return null;

    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {

        if (ID == getGuiID())
        {
            return new GUIContaierBasic(new ContainerBasic(player, player.inventory, new InventoryItemBasic(player.getHeldItemMainhand())));
        }
        else
            System.err.println("Invalid ID: expected " + getGuiID() + ", received " + ID);

        return null;

    }
}
