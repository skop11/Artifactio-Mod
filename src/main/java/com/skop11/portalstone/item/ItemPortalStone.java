package com.skop11.portalstone.item;

import com.skop11.portalstone.PortalStoneMod;
import com.skop11.portalstone.item.ItemDamageHandler.PortalStoneTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleSmokeNormal;
import net.minecraft.creativetab.CreativeTabs;
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
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class ItemPortalStone extends Item
{

    private static final int MAX_ITEM_USE = 100;

    private static final long DEFAULT_COOLDOWN = 1800000;
    private static final long INTERDIM_COOLDOWN = 2 * DEFAULT_COOLDOWN;

    @SuppressWarnings("all")
    public ItemPortalStone(String name)
    {
        setUnlocalizedName(name);
        setHasSubtypes(true);
        setCreativeTab(PortalStoneMod.tabPortalStone);
        setMaxStackSize(1);
    }

    @Override @Nonnull
    public String getUnlocalizedName(ItemStack stack)
    {
        return this.getUnlocalizedName() + "." + PortalStoneTypes.values()[stack.getItemDamage()].getName();
    }

    @Override
    public int getMetadata(int damage)
    {
        return damage;
    }

    /*@SideOnly(Side.CLIENT)*/ @Override
    public void getSubItems(@Nonnull Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
    {
        subItems.add(new ItemStack(itemIn, 1, 0));
    }

    @Override @Nonnull
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BOW;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return MAX_ITEM_USE;
    }

    @Override @Nonnull
    public ActionResult<ItemStack> onItemRightClick(@Nonnull ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        if (itemStackIn.getItemDamage() != 0) return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStackIn);
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
        }
        else
        {
            // TODO: MAKE SOME KIND OF SOUND HERE
        }

        playerIn.setActiveHand(hand);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
    }


    @Override @Nonnull
    public ItemStack onItemUseFinish(@Nonnull ItemStack itemStackIn, World worldIn, EntityLivingBase entityLiving)
    {
        if (itemStackIn.getItemDamage() == 0)
        {
            NBTTagCompound nbtTagCompound = itemStackIn.getTagCompound();
            if (!worldIn.isRemote && nbtTagCompound != null)
            {
                if (entityLiving instanceof EntityPlayerMP)
                {
                    EntityPlayerMP entityPlayerMP = (EntityPlayerMP) entityLiving;
                    int dim = nbtTagCompound.getInteger("Dim");
                    int x = nbtTagCompound.getInteger("XCoord");
                    int y = nbtTagCompound.getInteger("YCoord");
                    int z = nbtTagCompound.getInteger("ZCoord");
                    if (dim != ((EntityPlayerMP) entityLiving).dimension)
                    {
                        nbtTagCompound.setLong("Cooldown", Minecraft.getSystemTime() + INTERDIM_COOLDOWN);
                        entityLiving.changeDimension(dim);
                    }
                    else nbtTagCompound.setLong("Cooldown", Minecraft.getSystemTime() + DEFAULT_COOLDOWN);
                    entityPlayerMP.connection.setPlayerLocation(x, y, z, entityPlayerMP.rotationYaw,
                            entityPlayerMP.rotationPitch);
                    itemStackIn.setItemDamage(1);
                }
            }
        }
        return itemStackIn;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        super.addInformation(stack, playerIn, tooltip, advanced);
        NBTTagCompound nbtTagCompound = stack.getTagCompound();
        if (nbtTagCompound != null)
        {
            int time = timeRemaining(stack);
            if (time > 0)
            {
                int secs = (int) (time * 0.001f) + 1;
                int mins = secs / 60;
                secs %= 60;
                tooltip.add("Time remaining: " + String.format("%02d", mins) + ":" + String.format("%02d", secs));
            }
            else tooltip.add("Ready for use");
        }
        else tooltip.add("Not bound to any position");

    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        if (stack.getItemDamage() == 1 && stack.getTagCompound() != null)
        {
            if (timeRemaining(stack) == 0) stack.setItemDamage(0);

        }

    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count)
    {
        if (player.worldObj.isRemote && stack.getTagCompound() != null)
        {
            if (timeRemaining(stack) == 0)
            {
                int npart = (int) ((((float) (MAX_ITEM_USE - count)) / (float)MAX_ITEM_USE) * 20);
                int clamp = (int) (npart * 0.5);
                float range = 3f, offset = range * 0.5f;
                Particle particle;
                //System.out.println(npart + " " + (((float) (MAX_ITEM_USE - count)) / (float)MAX_ITEM_USE));
                ParticleSmokeNormal.Factory factory = new ParticleSmokeNormal.Factory();

                for (int i = 0; i < Math.min(npart, clamp); i++)
                {
                    particle = factory.createParticle(0, player.worldObj,
                            player.posX + Math.random() * range - offset,
                            player.posY + Math.random() * range,
                            player.posZ + Math.random() * range - offset,
                            0, 0.05f, 0);
                    particle.setRBGColorF(0.47f + (((float) Math.random() - 0.5f) * 0.25f), 0.1961f, 0.62745f);
                    Minecraft.getMinecraft().effectRenderer.addEffect(particle);
                }
            }

        }
    }

    private static int timeRemaining(ItemStack itemStackIn)
    {
        @SuppressWarnings("ConstantConditions")
        long cooldown = itemStackIn.getTagCompound().getLong("Cooldown");

        long time = Minecraft.getSystemTime();

        int r = (int) (cooldown - time);

        return r > 0 ? r : 0;
    }

}
