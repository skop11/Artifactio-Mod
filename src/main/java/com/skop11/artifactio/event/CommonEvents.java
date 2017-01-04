package com.skop11.artifactio.event;

import com.skop11.artifactio.item.ItemCoating;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.swing.text.html.parser.Entity;

public class CommonEvents
{

    public CommonEvents(){
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void playerDestroyItemEvent(PlayerDestroyItemEvent event)
    {
        ItemStack stack = event.getOriginal().copy();
        NBTTagCompound tag = stack.getTagCompound();
        if (tag != null && tag.getBoolean(ItemCoating.COATING_NBTTAG))
        {
            stack.setItemDamage(0);
            stack.stackSize = 1;
        }
        event.getEntityPlayer().setHeldItem(event.getHand(), stack);
        event.getEntityPlayer().inventory.setInventorySlotContents(9, stack);
    }

}
