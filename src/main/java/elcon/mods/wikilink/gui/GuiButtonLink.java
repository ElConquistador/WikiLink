package elcon.mods.wikilink.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;

import org.lwjgl.opengl.GL11;

import elcon.mods.wikilink.WLResources;
import elcon.mods.wikilink.api.ILink;

public class GuiButtonLink extends GuiButton {

	public ILink link;
	public boolean selected;

	public GuiButtonLink(int id, int x, int y, int width, int height, ILink link) {
		super(id, x, y, width, height, link.getLinkType().getDisplayName());
		this.link = link;
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if(visible) {
			mc.getTextureManager().bindTexture(WLResources.guiMenu);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			field_146123_n = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
			int hoverState = getHoverState(field_146123_n);
			GL11.glEnable(GL11.GL_BLEND);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			if(selected) {
				drawTexturedModalRect(xPosition, yPosition, 0, 184, width, height);
			} else {
				drawTexturedModalRect(xPosition, yPosition, 0, 184 + hoverState * 24, width, height);
			}
			mouseDragged(mc, mouseX, mouseY);
			int color = 0xE0E0E0;
			if(packedFGColour != 0) {
				color = packedFGColour;
			} else if(!enabled) {
				color = 0xA0A0A0;
			} else if(field_146123_n || selected) {
				color = 0xFFFFA0;
			}
			mc.fontRenderer.drawStringWithShadow(displayString, xPosition + 4, yPosition + (height - 8) / 2, color);
		}
	}
}
