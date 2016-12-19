package com.skop11.portalstone.tab;

import com.skop11.portalstone.item.PortalStoneItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

/**
 * Created by David on 15/12/2016.
 */
public class CreativeTabPortalStone extends CreativeTabs
{
    public CreativeTabPortalStone(int index, String label)
    {
        super(index, label);
    }

    @Override
    public Item getTabIconItem()
    {
        return PortalStoneItems.portalStone;
    }
}
