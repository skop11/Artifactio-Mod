package com.skop11.artifactio.item;

import com.skop11.artifactio.ArtifactioMod;
import com.skop11.artifactio.item.ItemDamageHandler.PortalStoneTypes;
import com.skop11.artifactio.utils.PlayerTelport;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleSmokeNormal;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
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
        setCreativeTab(ArtifactioMod.tabArtifactio);
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
        ItemStack stack = new ItemStack(itemIn, 1, 0);
        init(stack);
        subItems.add(stack);
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

        if (playerIn.isSneaking())
            return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
        if (playerIn.dimension == 1 && !nbtTagCompound.getBoolean("EndTravel"))
            return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStackIn);
        if (!nbtTagCompound.getBoolean("Bound"))
            return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStackIn);

        playerIn.setActiveHand(hand);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {

        Block block = worldIn.getBlockState(pos).getBlock();
        if (playerIn.isSneaking())
        {
            if (block == Blocks.DRAGON_EGG)
            {
                stack.getTagCompound().setBoolean("EndTravel", true);
                worldIn.playSound(playerIn, pos, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.AMBIENT, 1f, 1f);
            } else
            {
                if (playerIn.dimension != 1 || stack.getTagCompound().getBoolean("EndTravel"))
                {
                    NBTTagCompound nbtTagCompound = stack.getTagCompound();
                    nbtTagCompound.setInteger("Dim", playerIn.dimension);
                    nbtTagCompound.setInteger("XCoord", pos.getX());
                    nbtTagCompound.setInteger("YCoord", pos.getY() + 1);
                    nbtTagCompound.setInteger("ZCoord", pos.getZ());
                    nbtTagCompound.setBoolean("Bound", true);
                    worldIn.playSound(playerIn, pos, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.AMBIENT, 1f, 1f);
                }
            }
        }

        return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override @Nonnull
    public ItemStack onItemUseFinish(@Nonnull ItemStack itemStackIn, World worldIn, EntityLivingBase entityLiving)
    {
        if (itemStackIn.getItemDamage() == 0)
        {
            NBTTagCompound nbtTagCompound = itemStackIn.getTagCompound();
            if (!worldIn.isRemote)
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
                        PlayerTelport.teleportToDim(entityPlayerMP, dim, x, y, z);

                    }
                    else
                    {
                        nbtTagCompound.setLong("Cooldown", Minecraft.getSystemTime() + DEFAULT_COOLDOWN);
                        entityPlayerMP.connection.setPlayerLocation(x, y, z, entityPlayerMP.rotationYaw,
                                entityPlayerMP.rotationPitch);
                    }
                    itemStackIn.setItemDamage(1);
                }
            }
            else
            {
                // TODO: MAKE SOUND HERE
            }
        }
        return itemStackIn;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        super.addInformation(stack, playerIn, tooltip, advanced);
        NBTTagCompound nbtTagCompound = stack.getTagCompound();
        if (nbtTagCompound.getBoolean("Bound"))
        {
            int time = timeRemaining(stack);
            if (time > 0)
            {
                int secs = (int) (time * 0.001f) + 1;
                int mins = secs / 60;
                secs %= 60;
                tooltip.add("Time remaining: " + String.format("%02d", mins) + ":" + String.format("%02d", secs));
            }
            else
            {
                if (playerIn.dimension == 1 && !nbtTagCompound.getBoolean("EndTravel"))
                    tooltip.add("Cannot teleport from The End");
                else tooltip.add("Ready to teleport");
            }
        }
        else
        {
            tooltip.add("Not bound to any position");
            tooltip.add("(Shift + Right click on a Bed)");
        }

    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        if (stack.getItemDamage() == 1)
        {
            if (timeRemaining(stack) == 0) stack.setItemDamage(0);

        }

    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count)
    {
        if (player.worldObj.isRemote)
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

    /*@Override
    public boolean hasEffect(ItemStack stack)
    {
        return stack.getTagCompound().getBoolean("EndTravel");
    }*/

    public static ItemStack init(ItemStack itemStackIn)
    {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setBoolean("Bound", false);
        tag.setBoolean("EndTravel", false);
        tag.setLong("Cooldown", 0);
        itemStackIn.setTagCompound(tag);

        return itemStackIn;
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
