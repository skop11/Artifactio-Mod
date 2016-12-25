package com.skop11.artifactio.entity;

import com.skop11.artifactio.item.ItemPortalStone;
import com.skop11.artifactio.item.ArtifactioItems;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityItemPortalStone extends EntityItem
{

    public EntityItemPortalStone(World worldIn)
    {
        super(worldIn);
    }

    public EntityItemPortalStone(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    public EntityItemPortalStone(World worldIn, double x, double y, double z, ItemStack stack)
    {
        super(worldIn, x, y, z, stack);
    }

    {
        isImmuneToFire = true;
        setNoDespawn();
    }

    @Override
    public void setFire(int seconds)
    {
        ItemStack itemStack = getEntityItem();
        if (itemStack.getItem() == ArtifactioItems.inactPortalStone && worldObj.isMaterialInBB(getEntityBoundingBox(), Material.FIRE))
        {
            itemStack.setItem(ArtifactioItems.portalStone);
            ItemPortalStone.init(itemStack);
            playSound(SoundEvents.ITEM_FLINTANDSTEEL_USE, 0.5f, 1.0f);
        }
    }

    @Override
    protected void dealFireDamage(int amount)
    {
    }

    @Override
    protected void setOnFireFromLava()
    {
        this.kill();
    }

}
