package com.skop11.artifactio.gui;

import com.skop11.artifactio.item.ItemToolCompendium;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.Arrays;

public class InventoryItemBasic implements IInventory
{

    private final ItemStack invItem;

    public static final int INV_SIZE = 7;
    private ItemStack[] inventory = new ItemStack[INV_SIZE];


    public InventoryItemBasic(ItemStack stack)
    {
        this.invItem = stack;

        if (!stack.hasTagCompound())
        {
            stack.setTagCompound(new NBTTagCompound());
        }

        readFromNBT(stack.getTagCompound());

    }

    @Override
    public int getSizeInventory() { return inventory.length; }

    @Nullable
    @Override
    public ItemStack getStackInSlot(int index) { return inventory[index]; }

    @Nullable
    @Override
    public ItemStack decrStackSize(int index, int count)
    {
        ItemStack stack = getStackInSlot(index);

        if (stack != null)
        {
            if (stack.stackSize > count)
            {
                stack = stack.splitStack(count);
            }
            else
            {
                setInventorySlotContents(index, null);
            }
        }

        return stack;
    }

    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index)
    {
        ItemStack stack = getStackInSlot(index);
        setInventorySlotContents(index, null);

        return stack;
    }

    @Override
    public void setInventorySlotContents(int index, @Nullable ItemStack stack)
    {
        inventory[index] = stack;

        if (stack != null && stack.stackSize > getInventoryStackLimit())
        {
            stack.stackSize = getInventoryStackLimit();
        }

        markDirty();

    }

    @Override
    public boolean hasCustomName() { return false; }

    @Override
    public ITextComponent getDisplayName()
    {
        return hasCustomName() ? new TextComponentString(getName()) : new TextComponentTranslation(getName());
    }

    @Override
    public String getName() {return "container.tool_compendium.name";}

    @Override
    public int getInventoryStackLimit() { return 64; }

    @Override
    public void markDirty()
    {
        for (int i = 0; i < getSizeInventory(); i ++)
        {
            if (getStackInSlot(i) != null && getStackInSlot(i).stackSize == 0)
            {
                inventory[i] = null;
            }
        }

        writeToNBT(invItem.getTagCompound());

    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {return true;}

    @Override
    public void openInventory(EntityPlayer player) {}

    @Override
    public void closeInventory(EntityPlayer player) {}

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        return !(stack.getItem() instanceof ItemToolCompendium);
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        NBTTagList items = compound.getTagList("ItemInventory", Constants.NBT.TAG_COMPOUND);

        for (int i = 0; i < items.tagCount(); i ++)
        {
            NBTTagCompound item = items.getCompoundTagAt(i);
            int slot = item.getInteger("Slot");

            if (slot >= 0 && slot < getSizeInventory())
            {
                inventory[slot] = ItemStack.loadItemStackFromNBT(item);
            }
        }
    }

    public void writeToNBT(NBTTagCompound tagCompound)
    {
        NBTTagList items = new NBTTagList();

        for (int i = 0; i < getSizeInventory(); i ++)
        {

            if (getStackInSlot(i) != null)
            {
                NBTTagCompound item = new NBTTagCompound();
                item.setInteger("Slot", i);

                getStackInSlot(i).writeToNBT(item);
                items.appendTag(item);
            }
        }

        tagCompound.setTag("ItemInventory", items);
    }

    @Override
    public void clear() { Arrays.fill(inventory, null); }

    @Override
    public int getFieldCount() { return 0; }

    @Override
    public int getField(int id) { return 0; }

    @Override
    public void setField(int id, int value) {}

}
