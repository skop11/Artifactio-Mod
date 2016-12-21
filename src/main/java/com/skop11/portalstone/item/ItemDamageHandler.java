package com.skop11.portalstone.item;

import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

class ItemDamageHandler
{

    public enum PortalStoneTypes implements IStringSerializable
    {
        ACTIVE("active", 0),
        USED("uncharged", 1),
        ;

        private String name;
        private int meta;

        PortalStoneTypes(String name, int meta)
        {
            this.name = name;
            this.meta = meta;
        }

        @Override @Nonnull
        public String getName()
        {
            return name;
        }

        /*public int getMeta()
        {
            return meta;
        }*/

        @Override
        public String toString()
        {
            return getName();
        }
    }

}
