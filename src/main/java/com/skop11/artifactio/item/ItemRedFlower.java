package com.skop11.artifactio.item;

import com.skop11.artifactio.ArtifactioMod;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.List;

public class ItemRedFlower extends Item
{

    public ItemRedFlower(String name)
    {
        setUnlocalizedName(name);
        setHasSubtypes(false);
        setCreativeTab(ArtifactioMod.tabArtifactio);
        setMaxStackSize(1);
    }

    @Override
    public void onUpdate(ItemStack stackIn, World worldIn, Entity entityIn, int itemSlotIn, boolean isSelected)
    {
        if (entityIn instanceof EntityPlayer && !worldIn.isRemote)
        {
            long time = worldIn.getWorldTime();
            if (time % 16 == 0)
            {
                NBTTagCompound tagIn = stackIn.getTagCompound();
                int energy = 0;
                if (tagIn == null) tagIn = new NBTTagCompound();
                else energy = tagIn.getInteger("energy");
                EntityPlayer player = (EntityPlayer) entityIn;
                int slot = (int) ((time / 8) % 64);
                if (slot % 16 == 0 && worldIn.canSeeSky(player.getPosition()))
                    energy++;
                ItemStack stack = player.inventory.getStackInSlot(slot);
                ItemStack selected = player.getHeldItem(EnumHand.MAIN_HAND);
                if (isStackShielded(stack))
                    energy = powerShield(stack, energy);
                else if (isStackShielded(selected))
                    energy = powerShield(selected, energy);

                tagIn.setInteger("energy", (energy >= 100 ? 100 : energy));
                stackIn.setTagCompound(tagIn);
            }
        }
        super.onUpdate(stackIn, worldIn, entityIn, itemSlotIn, isSelected);
    }

    private boolean isStackShielded(ItemStack stackIn)
    {
        if (stackIn == null) return false;  // TODO : Change in 1.11
        NBTTagCompound tag = stackIn.getTagCompound();
        return (tag != null && tag.getBoolean(ItemCoating.COATING_NBTTAG));
    }

    private int powerShield(ItemStack stackIn , int energy)
    {
        int damage = stackIn.getItemDamage();
        if (damage > 0)
        {
            if (damage - energy > 0)
            {
                damage -= energy;
                energy = 0;
            } else
            {
                energy -= damage;
                damage = 0;
            }
            stackIn.setItemDamage(damage - energy > 0 ? damage - energy : 0);
        }
        return energy;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        super.addInformation(stack, playerIn, tooltip, advanced);
        NBTTagCompound tag = stack.getTagCompound();
        if (tag != null && tag.hasKey("energy"))
            tooltip.add(I18n.format("item.redflower_solar.tooltip.energy") + " " + tag.getInteger("energy"));
    }
}
