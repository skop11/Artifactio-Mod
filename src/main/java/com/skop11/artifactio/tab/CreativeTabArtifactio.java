package com.skop11.artifactio.tab;

import com.skop11.artifactio.item.ArtifactioItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * Created by David on 15/12/2016.
 */
public class CreativeTabArtifactio extends CreativeTabs
{
    public CreativeTabArtifactio(int index, String label)
    {
        super(index, label);
    }

    @Override
    public Item getTabIconItem()
    {
        return ArtifactioItems.portalStone;
    }
}
