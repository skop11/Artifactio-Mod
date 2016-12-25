package com.skop11.artifactio.gui;

import com.skop11.artifactio.item.ItemToolCompendium;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class SlotToolCompendium extends Slot
{

    public SlotToolCompendium(IInventory inventoryIn, int index, int xPosition, int yPosition)
    {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(@Nullable ItemStack stack)
    {
        return !(stack.getItem() instanceof ItemToolCompendium);
    }
}
