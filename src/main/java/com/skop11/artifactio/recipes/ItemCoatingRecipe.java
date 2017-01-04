package com.skop11.artifactio.recipes;

import com.skop11.artifactio.item.ArtifactioItems;
import com.skop11.artifactio.item.ItemCoating;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.HashSet;

public class ItemCoatingRecipe implements IRecipe
{

    private HashSet<String> itemsAllowed = new HashSet<String>();

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn)
    {
        boolean coating = false, target = false;
        ItemStack stack;

        for (int i = 0; i < inv.getSizeInventory(); i ++)
        {
            stack = inv.getStackInSlot(i);
            if (stack != null)  // TODO : Change in 1.11
            {
                if (stack.getItem() == ArtifactioItems.coating)
                {
                    if (coating) return false;
                    coating = true;
                }
                else if (isAllowed(stack))
                {
                    if (target) return false;
                    target = true;
                }
                else return true;
            }
        }
        return (coating && target);
    }

    @Nullable
    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv)
    {
        ItemStack target = null;
        ItemStack stack;
        for (int i = 0; i < inv.getSizeInventory(); i ++)
        {
            stack = inv.getStackInSlot(i);
            if (stack != null)
            {
                if (isAllowed(stack))
                {
                    target = stack.copy();
                    break;
                }
            }
        }

        NBTTagCompound tag;
        if (target != null)
        {
            tag = target.getTagCompound();
            if (tag == null) tag = new NBTTagCompound();
            tag.setBoolean(ItemCoating.COATING_NBTTAG, true);
            target.setTagCompound(tag);
            target.setStackDisplayName(target.getDisplayName() +
                    " (" + I18n.format("text.coating.shielded.display") + ")");
        }

        return target;
    }

    @Override
    public int getRecipeSize()
    {
        return 10;
    }

    @Nullable
    @Override
    public ItemStack getRecipeOutput()
    {
        return null; // TODO : Change in 1.11
    }

    @Override
    public ItemStack[] getRemainingItems(InventoryCrafting inv)
    {
        return new ItemStack[inv.getSizeInventory()];
    }

    public void addCoatingItem(String itemRegistryName)
    {
        itemsAllowed.add(itemRegistryName);
    }

    private boolean isAllowed(ItemStack stack)
    {

        boolean shielded = (stack.getTagCompound() != null &&
                stack.getTagCompound().getBoolean(ItemCoating.COATING_NBTTAG));
        return (
                        stack.isItemStackDamageable() &&
                        stack.getItemDamage() == 0 &&
                        !shielded &&
                        itemsAllowed.contains(stack.getItem().getRegistryName().toString())
                );
    }

}
