package com.skop11.portalstone.item;

import com.skop11.portalstone.PortalStoneMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by David on 15/12/2016.
 */
public class ItemPortalStone extends Item
{

    public ItemPortalStone(String name)
    {
        setUnlocalizedName(name);
        setCreativeTab(PortalStoneMod.tabPortalStone);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        if (!worldIn.isRemote)
        {

        }

        return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStackIn);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        tooltip.add("Item Tooltip");

        super.addInformation(stack, playerIn, tooltip, advanced);
    }

    /*@Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.UNCOMMON;
    }*/

    /*@Override
    public boolean hasEffect(ItemStack stack)
    {
        return true;
    }*/

}
