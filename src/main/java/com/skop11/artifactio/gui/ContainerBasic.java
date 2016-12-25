package com.skop11.artifactio.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class ContainerBasic extends Container
{

    private final InventoryItemBasic inventory;

    private final int HOTBAR_SLOT_COUNT = 9;
    private final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;

    private final int VANILLA_FIRST_SLOT_INDEX = 0;
    private final int TC_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    private final int TC_INVENTORY_SLOT_COUNT = 7;
    //private final int TC_INVENTORY_FIRST_RIGHT_SLOT_INDEX = TC_INVENTORY_FIRST_SLOT_INDEX + TC_INVENTORY_SLOT_COUNT_LEFT;
    //private final int TC_INVENTORY_SLOT_COUNT_RIGHT = 2;

    public ContainerBasic(EntityPlayer player, InventoryPlayer inventoryPlayer, InventoryItemBasic inventoryItemBasic)
    {
        inventory = inventoryItemBasic;

        final int SLOT_X_SPACING = 18;
        final int SLOT_Y_SPACING = 18;
        final int HOTBAR_XPOS = 8;
        final int HOTBAR_YPOS = 109;
        // Player Hotbar
        for (int x = 0; x < HOTBAR_SLOT_COUNT; x ++) {
            int slotNumber = x;
            addSlotToContainer(new Slot(inventoryPlayer, slotNumber, HOTBAR_XPOS + SLOT_X_SPACING * x, HOTBAR_YPOS));
        }

        final int PLAYER_INVENTORY_XPOS = 8;
        final int PLAYER_INVENTORY_YPOS = 51;
        // Player inventory
        for (int y = 0; y < PLAYER_INVENTORY_ROW_COUNT; y++) {
            for (int x = 0; x < PLAYER_INVENTORY_COLUMN_COUNT; x++) {
                int slotNumber = HOTBAR_SLOT_COUNT + y * PLAYER_INVENTORY_COLUMN_COUNT + x;
                int xpos = PLAYER_INVENTORY_XPOS + x * SLOT_X_SPACING;
                int ypos = PLAYER_INVENTORY_YPOS + y * SLOT_Y_SPACING;
                addSlotToContainer(new Slot(inventoryPlayer, slotNumber,  xpos, ypos));
            }
        }

        if (TC_INVENTORY_SLOT_COUNT != inventoryItemBasic.getSizeInventory())
        {
            System.err.println("Mismatched slot count in ContainerBasic("
                    + (TC_INVENTORY_SLOT_COUNT)
                    + ") and TileInventory (" + inventoryItemBasic.getSizeInventory()+")");
        }
        final int TC_LEFT_INVENTORY_XPOS = 26;
        final int TC_INVENTORY_YPOS = 21;
        // Tool Compendium left inventory
        for (int x = 0; x < TC_INVENTORY_SLOT_COUNT; x++) {
            int slotNumber = x;
            addSlotToContainer(new SlotToolCompendium(inventoryItemBasic, slotNumber, TC_LEFT_INVENTORY_XPOS + SLOT_X_SPACING * x, TC_INVENTORY_YPOS));
        }

    }

    public InventoryItemBasic getItemInventory()
    {
        return inventory;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return inventory.isUseableByPlayer(playerIn);
    }

    @Nullable
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemStack = null;
        Slot slot = inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemStack1 = slot.getStack();
            itemStack = itemStack1.copy();

            if (index >= TC_INVENTORY_FIRST_SLOT_INDEX)
            {
                if (!this.mergeItemStack(itemStack1, VANILLA_FIRST_SLOT_INDEX,
                        TC_INVENTORY_FIRST_SLOT_INDEX,
                        false))
                {
                    return null;
                }

                slot.onSlotChange(itemStack1, itemStack);
            }
            else
            {
                if (!this.mergeItemStack(itemStack1, TC_INVENTORY_FIRST_SLOT_INDEX ,
                        TC_INVENTORY_FIRST_SLOT_INDEX  + TC_INVENTORY_SLOT_COUNT,
                        false))
                {
                    return null;
                }

            }

            if (itemStack1.stackSize == 0)
            {
                slot.putStack(null);
            }
            else slot.onSlotChanged();

            if (itemStack1.stackSize == itemStack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(playerIn, itemStack1);

        }

        return itemStack;
    }

    @Nullable
    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player)
    {
        if (slotId >= 0 && getSlot(slotId) != null && getSlot(slotId).getStack() == player.getHeldItemMainhand())
        {
            return null;
        }

        return super.slotClick(slotId, dragType, clickTypeIn, player);
    }

}
