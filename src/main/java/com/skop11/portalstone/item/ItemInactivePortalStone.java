package com.skop11.portalstone.item;

import com.skop11.portalstone.PortalStoneMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemInactivePortalStone extends Item
{

    public ItemInactivePortalStone(String name)
    {
        setUnlocalizedName(name);
        setHasSubtypes(false);
        setCreativeTab(PortalStoneMod.tabPortalStone);
        setMaxStackSize(1);
    }


    @Override
    public boolean hasCustomEntity(ItemStack stack)
    {
        return true;
    }

    @Override
    public Entity createEntity(World world, Entity location, ItemStack itemstack)
    {
        EntityItemPortalStone itemEntity = new EntityItemPortalStone(world, location.posX, location.posY, location.posZ, itemstack);
        itemEntity.motionX = location.motionX;
        itemEntity.motionY = location.motionY;
        itemEntity.motionZ = location.motionZ;
        itemEntity.setDefaultPickupDelay();

        return itemEntity;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }
}
