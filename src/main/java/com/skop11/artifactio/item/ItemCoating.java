package com.skop11.artifactio.item;

import com.skop11.artifactio.ArtifactioMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemCoating extends Item
{

    public static final String COATING_NBTTAG = "artifatio:coating_enabled";

    public ItemCoating(String name)
    {
        setUnlocalizedName(name);
        setHasSubtypes(false);
        setCreativeTab(ArtifactioMod.tabArtifactio);
        setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {

        ItemStack tool = playerIn.inventory.getStackInSlot(0);
        if (tool != null)
        {
            if (!playerIn.isSneaking())
                tool.damageItem(100, playerIn);
            else tool.damageItem(60, playerIn);
        }

        //playerIn.inventory.setInventorySlotContents(0, new ItemStack(Items.DIAMOND_SHOVEL.setContainerItem(Items.DIAMOND_SHOVEL)));
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }
}
