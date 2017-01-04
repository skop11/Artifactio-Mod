package com.skop11.artifactio.item;

import com.skop11.artifactio.ArtifactioMod;
import com.skop11.artifactio.gui.GUIHandler;
import com.skop11.artifactio.gui.InventoryItemBasic;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemToolCompendium extends Item
{

    public ItemToolCompendium(String name)
    {

        setUnlocalizedName(name);
        setHasSubtypes(false);
        setCreativeTab(ArtifactioMod.tabArtifactio);
        setMaxStackSize(1);


    }

    @Override
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack)
    {

        return super.onEntitySwing(entityLiving, stack);
    }



    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        if (!worldIn.isRemote)
        {
            if (itemStackIn.getTagCompound() == null)
                itemStackIn.setTagCompound(new NBTTagCompound());
            if (hand == EnumHand.MAIN_HAND)
            {
                if (playerIn.isSneaking())
                    itemStackIn.getTagCompound().setInteger("Slot", playerIn.inventory.currentItem);
                else
                    playerIn.openGui(ArtifactioMod.getMod(), GUIHandler.getGuiID(), worldIn, 0, 0, 0);
            }
            else
            {
                ItemStack held = playerIn.getHeldItemMainhand();
                if (!(held == null) && playerIn.inventory.currentItem == itemStackIn.getTagCompound().getInteger("Slot"))
                {
                    InventoryItemBasic inv = new InventoryItemBasic(itemStackIn);
                    for (int i = 0; i < inv.getSizeInventory(); i ++)
                    {
                        if (inv.getStackInSlot(i) == null)
                        {
                            inv.setInventorySlotContents(i, held);
                            playerIn.setHeldItem(EnumHand.MAIN_HAND, null);
                            break;
                        }
                    }

                }
            }
        }

        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }



    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if (!worldIn.isRemote)
        {
            if (!(entityIn instanceof EntityPlayer)) return;
            EntityPlayer player = (EntityPlayer) entityIn;
            NBTTagCompound tag = stack.getTagCompound();
            if (tag == null) return;

            if (player.getHeldItemOffhand() == stack && player.inventory.currentItem == stack.getTagCompound().getInteger("Slot"))
            {
                if (player.swingProgress > 0.15f && player.swingProgress < 0.4f)
                {
                    InventoryItemBasic inv = new InventoryItemBasic(stack);
                    RayTraceResult pos = player.rayTrace(4.5, 1f);

                    if (pos != null && pos.getBlockPos() != null)
                    {
                        IBlockState state = entityIn.getEntityWorld().getBlockState(pos.getBlockPos());
                        ItemStack held = player.getHeldItemMainhand();
                        ItemStack invItem;
                        float blockRes;
                        if (held == null) blockRes = 1f; // TODO : Change in 1.11
                        else blockRes = held.getStrVsBlock(state);

                        float str;
                        for (int i = 0; i < inv.getSizeInventory(); i++)
                        {
                            invItem = inv.getStackInSlot(i);
                            if (invItem != null)
                            {
                                str = invItem.getStrVsBlock(state);
                                if (str > blockRes)
                                {
                                    inv.setInventorySlotContents(i, held);
                                    player.setHeldItem(EnumHand.MAIN_HAND, invItem);
                                    blockRes = str;
                                    held = invItem;
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}
