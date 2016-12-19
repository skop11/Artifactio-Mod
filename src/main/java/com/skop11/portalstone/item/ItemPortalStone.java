package com.skop11.portalstone.item;

import com.skop11.portalstone.PortalStoneMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleSmokeNormal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by David on 15/12/2016.
 */
public class ItemPortalStone extends Item
{

    private static final int MAX_ITEM_USE = 100;

    public ItemPortalStone(String name)
    {
        setUnlocalizedName(name);
        setHasSubtypes(true);
        setMaxDamage(1);
        setCreativeTab(PortalStoneMod.tabPortalStone);

    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BOW;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return MAX_ITEM_USE;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        NBTTagCompound nbtTagCompound = itemStackIn.getTagCompound();
        boolean sneaking = playerIn.isSneaking();
        if (!worldIn.isRemote)
        {
            if (sneaking)
            {
                if (nbtTagCompound == null)
                {
                    nbtTagCompound = new NBTTagCompound();
                    itemStackIn.setTagCompound(nbtTagCompound);
                    nbtTagCompound.setLong("Cooldown", 0);
                }
                nbtTagCompound.setInteger("Dim", playerIn.dimension);
                nbtTagCompound.setInteger("XCoord", playerIn.getPosition().getX());
                nbtTagCompound.setInteger("YCoord", playerIn.getPosition().getY());
                nbtTagCompound.setInteger("ZCoord", playerIn.getPosition().getZ());


                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);

            }
            else
            {
            }
        }
        else
        {
            //if (!sneaking) playerIn.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F,1.0F);
        }

        playerIn.setActiveHand(hand);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
    }

    @Nullable
    @Override
    public ItemStack onItemUseFinish(ItemStack itemStackIn, World worldIn, EntityLivingBase entityLiving)
    {
        NBTTagCompound nbtTagCompound = itemStackIn.getTagCompound();
        if (!worldIn.isRemote && nbtTagCompound != null)
        {
            long time = worldIn.getWorldTime();
            long cooldown = nbtTagCompound.getLong("Cooldown");
            if (time  >= cooldown)
            {
                if (entityLiving instanceof EntityPlayerMP)
                {
                    EntityPlayerMP entityPlayerMP = (EntityPlayerMP) entityLiving;
                    int dim = nbtTagCompound.getInteger("Dim");
                    int x = nbtTagCompound.getInteger("XCoord");
                    int y = nbtTagCompound.getInteger("YCoord");
                    int z = nbtTagCompound.getInteger("ZCoord");
                    if (dim != ((EntityPlayerMP) entityLiving).dimension)
                        entityLiving.changeDimension(dim);
                    entityPlayerMP.connection.setPlayerLocation(x, y, z, entityPlayerMP.rotationYaw,
                            entityPlayerMP.rotationPitch);
                    entityPlayerMP.addChatComponentMessage(new TextComponentString("Time set to 30 min"));
                }
                nbtTagCompound.setLong("Cooldown", worldIn.getWorldTime() + 36000);


            }
                //((EntityPlayerMP) entityLiving).addChatComponentMessage(new TextComponentString((cooldown - time) / 20 + " seconds"));
        }
        return itemStackIn;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        tooltip.add("Item Tooltip");

        super.addInformation(stack, playerIn, tooltip, advanced);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count)
    {
        if (player.worldObj.isRemote)
        {
            int clamp = 10;
            int npart = (MAX_ITEM_USE - count) / (clamp >> 1);
            Particle particle;

            ParticleSmokeNormal.Factory factory = new ParticleSmokeNormal.Factory();

            for (int i = 0; i < Math.min(npart, clamp); i++)
            {
                particle = factory.createParticle(0, player.worldObj, player.posX + Math.random() * 1.8 - 0.9, player.posY + Math.random() * 1.8f, player.posZ + Math.random() * 1.8 - 0.9, 0, 0, 0);
                particle.setRBGColorF(0.47f + (((float)Math.random() - 0.5f) * 0.25f), 0.1961f, 0.62745f);
                Minecraft.getMinecraft().effectRenderer.addEffect(particle);
            }

        }

        //super.onUsingTick(stack, player, count);
    }

    /*@Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.UNCOMMON;
    }*/

    public boolean hasEffect(ItemStack stack)
    {
        return false;
        //return !(stack.getTagCompound() == null);
    }

}
