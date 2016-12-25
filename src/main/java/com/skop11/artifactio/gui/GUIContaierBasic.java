package com.skop11.artifactio.gui;

import com.skop11.artifactio.ArtifactioMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;

@SideOnly(Side.CLIENT)
public class GUIContaierBasic extends GuiContainer
{

    private static final ResourceLocation texture = new ResourceLocation(ArtifactioMod.MODID, "textures/gui/tool_compendium_gui.png");

    private final InventoryItemBasic inventory;

    public GUIContaierBasic(ContainerBasic inventorySlotsIn)
    {
        super(inventorySlotsIn);
        inventory = inventorySlotsIn.getItemInventory();
    }

    /*@Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);
        xSize = mouseX;
        ySize = mouseY;
    }*/

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

        GlStateManager.color(1f, 1f, 1f, 1f);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        final int LABEL_XPOS = 5;
        final int LABEL_YPOS = 5;
        fontRendererObj.drawString(inventory.getDisplayName().getUnformattedText(), LABEL_XPOS, LABEL_YPOS, Color.darkGray.getRGB());
    }
}
