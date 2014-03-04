package elcon.mods.wikilink.gui;

import java.util.List;

import javax.swing.text.html.HTMLDocument;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import org.apache.commons.lang3.text.WordUtils;
import org.lwjgl.opengl.GL11;

import elcon.mods.wikilink.WLResources;
import elcon.mods.wikilink.WikiLink;
import elcon.mods.wikilink.api.ILink;
import elcon.mods.wikilink.web.BrowserHelper;
import elcon.mods.wikilink.web.WebHelper;

public class GuiWikiLinkSummary extends GuiScreen {

	public int xSize;
	public int ySize;
	public int guiLeft;
	public int guiTop;
	
	public Object topic;
	public ILink link;
	public ItemStack stack;
	
	public HTMLDocument html;
	
	public String title;
	public String description;
	public String[] descriptionSplit;
	
	public GuiButton browserButton;
	public GuiButton clipboardButton;
	public GuiButton backButton;
	
	public GuiWikiLinkSummary(Object topic, ILink link, ItemStack stack) {
		this.topic = topic;
		this.link = link;
		this.stack = stack;
		
		xSize = 176;
		ySize = 174;
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	@Override
	public void initGui() {
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
		
		browserButton = new GuiButton(0, guiLeft + 7, guiTop + 149, 68, 20, StatCollector.translateToLocal("wikilink.gui.browser"));
		clipboardButton = new GuiButton(1, guiLeft + 79, guiTop + 149, 68, 20, StatCollector.translateToLocal("wikilink.gui.clipboard"));
		backButton = new GuiButton(2, guiLeft + 152, guiTop + 149, 16, 20, StatCollector.translateToLocal("wikilink.gui.back"));

		buttonList.add(browserButton);
		buttonList.add(clipboardButton);
		buttonList.add(backButton);
		
		html = WebHelper.getHTMLDocument(link.getURL());
		if(html != null && link.getLinkType().hasPreview()) {
			title = link.getLinkType().getPreviewTitle(link, html);
			description = link.getLinkType().getPreview(link, html);
			if(description == null || description.isEmpty()) {
				description = EnumChatFormatting.DARK_RED + StatCollector.translateToLocal("wikilink.gui.fail");
			}
		} else {
			title = link.getName();
			description = EnumChatFormatting.DARK_RED + StatCollector.translateToLocal("wikilink.gui.fail");
		}
		if(title.length() > 24) {
			title = title.substring(0, Math.min(24, title.length()));
			title += "...";
		}	
		description = description.replaceAll("\\r\\n|\\r|\\n", " ");
		description = WordUtils.wrap(description, 29);
		descriptionSplit = description.split("\\r?\\n");
		if(descriptionSplit.length > 13) {
			String[] lines = new String[13];
			System.arraycopy(descriptionSplit, 0, lines, 0, 13);
			lines[12] = lines[12].substring(0, 24);
			lines[12] += "...";
			descriptionSplit = lines;
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		if(button.id == 0) {
			WikiLink.log.info("Opening in browser: " + link.getURL());
			BrowserHelper.openLink(link.getURL());
		} else if(button.id == 1) {
			WikiLink.log.info("Copying to clipboard: " + link.getURL());
			setClipboardString(link.getURL());
		} else if(button.id == 2) {
			mc.displayGuiScreen(new GuiWikiLinkMenu(topic));
		}
	}
	
	@Override
	protected void keyTyped(char c, int i) {
		if(i == 1 || i == mc.gameSettings.keyBindInventory.getKeyCode()) {
			mc.displayGuiScreen(new GuiWikiLinkMenu(topic));
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialRenderTime) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(WLResources.guiSummary);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		mc.fontRenderer.drawString(title, guiLeft + 7, guiTop + 10, 0x404040, false);
		
		for(int i = 0; i < descriptionSplit.length; i++) {
			mc.fontRenderer.drawString(descriptionSplit[i], guiLeft + 11, guiTop + 29 + (i * mc.fontRenderer.FONT_HEIGHT), 0xE0E0E0, false);
		}
		
		if(stack != null) {
			RenderHelper.enableGUIStandardItemLighting();
			renderItemStack(stack, guiLeft + 152, guiTop + 6);
			if(isMouseOverSlot(mouseX, mouseY)) {
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				GL11.glColorMask(true, true, true, false);
				drawGradientRect(guiLeft + 152, guiTop + 6, guiLeft + 152 + 16, guiTop + 6 + 16, -0x7F000001, -0x7F000001);
				GL11.glColorMask(true, true, true, true);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_DEPTH_TEST);

				renderTooltip(stack, mouseX, mouseY);
			}
			RenderHelper.disableStandardItemLighting();
		}
		super.drawScreen(mouseX, mouseY, partialRenderTime);
	}
	
	private boolean isMouseOverSlot(int mouseX, int mouseY) {
		return mouseX >= guiLeft + 152 && mouseX < guiLeft + 152 + 16 && mouseY >= guiTop + 6 && mouseY < guiTop + 6 + 16;
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
