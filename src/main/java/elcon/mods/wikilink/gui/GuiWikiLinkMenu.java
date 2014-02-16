package elcon.mods.wikilink.gui;

import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import org.lwjgl.opengl.GL11;

import elcon.mods.wikilink.WLResources;

public class GuiWikiLinkMenu extends GuiScreen {

	public int xSize;
	public int ySize;
	public int guiLeft;
	public int guiTop;

	public Object topic;

	public GuiWikiLinkMenu(Object topic) {
		this.topic = topic;

		xSize = 176;
		ySize = 156;
	}

	@Override
	public void initGui() {
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialRenderTime) {
		super.drawScreen(mouseX, mouseY, partialRenderTime);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(WLResources.guiMenu);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		if(topic instanceof ItemStack) {
			RenderHelper.enableGUIStandardItemLighting();
			renderItemStack((ItemStack) topic, guiLeft + 152, guiTop + 111);
			RenderHelper.disableStandardItemLighting();
			if(isMouseOverSlot(mouseX, mouseY)) {
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				GL11.glColorMask(true, true, true, false);
				drawGradientRect(guiLeft + 152, guiTop + 111, guiLeft + 152 + 16, guiTop + 111 + 16, -0x7F000001, -0x7F000001);
				GL11.glColorMask(true, true, true, true);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_DEPTH_TEST);
				
				renderTooltip((ItemStack) topic, mouseX, mouseY);
			}
		}
	}

	private boolean isMouseOverSlot(int mouseX, int mouseY) {
		return mouseX >= guiLeft + 152 && mouseX < guiLeft + 152 + 16 && mouseY >= guiTop + 111 && mouseY < guiTop + 111 + 16;
	}

	private void renderItemStack(ItemStack stack, int x, int y) {
		GL11.glTranslatef(0.0F, 0.0F, 32.0F);
		zLevel = 200.0F;
		itemRender.zLevel = 200.0F;
		FontRenderer font = null;
		if(stack != null) {
			font = stack.getItem().getFontRenderer(stack);
		}
		if(font == null) {
			font = fontRendererObj;
		}
		itemRender.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), stack, x, y);
		itemRender.renderItemOverlayIntoGUI(font, this.mc.getTextureManager(), stack, x, y, null);
		zLevel = 0.0F;
		itemRender.zLevel = 0.0F;
	}

	private void renderTooltip(ItemStack stack, int x, int y) {
		List list = stack.getTooltip(mc.thePlayer, mc.gameSettings.advancedItemTooltips);
		for(int i = 0; i < list.size(); ++i) {
			if(i == 0) {
				list.set(i, stack.getRarity().rarityColor + (String) list.get(i));
			} else {
				list.set(i, EnumChatFormatting.GRAY + (String) list.get(i));
			}
		}
		FontRenderer font = stack.getItem().getFontRenderer(stack);
		func_146283_a(list, x, y);
		drawHoveringText(list, x, y, (font == null ? fontRendererObj : font));
	}
}
