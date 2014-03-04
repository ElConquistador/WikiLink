package elcon.mods.wikilink.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import elcon.mods.wikilink.WLResources;
import elcon.mods.wikilink.WikiLink;
import elcon.mods.wikilink.api.ILink;
import elcon.mods.wikilink.api.ILinkCollection;
import elcon.mods.wikilink.api.WikiLinkAPI;
import elcon.mods.wikilink.web.BrowserHelper;

public class GuiWikiLinkMenu extends GuiScreen {

	public int xSize;
	public int ySize;
	public int guiLeft;
	public int guiTop;

	public Object topic;
	public ILinkCollection linkCollection;
	public String topicName;
	public ItemStack stack;

	public GuiButton browserButton;
	public GuiButton clipboardButton;
	public GuiButton summarizeButton;
	public GuiButtonLink selectedButton;

	public GuiButtonLink[] linkButtons;

	public float currentScroll;
	public boolean isScrolling;
	public boolean wasClicking;

	public GuiWikiLinkMenu(Object topic) {
		this.topic = topic;

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

		linkCollection = WikiLinkAPI.linkRegistry.getLink(topic);
		topicName = linkCollection.getTopicName();
		if(topic instanceof ItemStack) {
			stack = (ItemStack) topic;
		} else if(topic instanceof Entity) {
			EntityEggInfo egg = (EntityEggInfo) EntityList.entityEggs.get(Integer.valueOf(EntityList.getEntityID((Entity) topic)));
			if(egg != null) {
				stack = new ItemStack(Items.spawn_egg, 1, egg.spawnedID);
			}
		}

		browserButton = new GuiButton(0, guiLeft + 7, guiTop + 149, 68, 20, StatCollector.translateToLocal("wikilink.gui.browser"));
		clipboardButton = new GuiButton(1, guiLeft + 79, guiTop + 149, 68, 20, StatCollector.translateToLocal("wikilink.gui.clipboard"));
		summarizeButton = new GuiButton(2, guiLeft + 152, guiTop + 149, 16, 20, StatCollector.translateToLocal("wikilink.gui.summarize"));
		
		browserButton.enabled = false;
		clipboardButton.enabled = false;
		summarizeButton.enabled = false;
		
		buttonList.add(browserButton);
		buttonList.add(clipboardButton);
		buttonList.add(summarizeButton);

		int index = 0;
		linkButtons = new GuiButtonLink[linkCollection.getAllLinks().size()];
		ArrayList<ILink> links = new ArrayList<ILink>();
		links.addAll(linkCollection.getAllLinks());
		Collections.sort(links, WikiLinkAPI.linkRegistry.getLinkComparator());
		Collections.sort(links, WikiLinkAPI.linkRegistry.getLinkComparator());
		for(ILink link : links) {
			linkButtons[index] = new GuiButtonLink(3 + index, guiLeft + 8, guiTop + 26 + index * 24, 138, 24, link);
			linkButtons[index].enabled = false;
			linkButtons[index].visible = false;
			buttonList.add(linkButtons[index]);
			index++;
		}

		scrollTo(0.0F);
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if(selectedButton != null && selectedButton.link != null) {
			if(button.id == 0) {
				WikiLink.log.info("Opening in browser: " + selectedButton.link.getURL());
				BrowserHelper.openLink(selectedButton.link.getURL());
			} else if(button.id == 1) {
				WikiLink.log.info("Copying to clipboard: " + selectedButton.link.getURL());
				setClipboardString(selectedButton.link.getURL());
			} else if(button.id == 2) {
				mc.displayGuiScreen(new GuiWikiLinkSummary(topic, selectedButton.link, stack));
			}
		}
		if(button.id >= 3) {
			if(button instanceof GuiButtonLink) {
				selectedButton = (GuiButtonLink) button;
				for(GuiButton b : (List<GuiButton>) buttonList) {
					if(b instanceof GuiButtonLink) {
						((GuiButtonLink) b).selected = false;
					}
				}
				selectedButton.selected = true;
				browserButton.enabled = true;
				clipboardButton.enabled = true;
				summarizeButton.enabled = selectedButton.link.getLinkType().hasPreview();
			}
		}
	}

	@Override
	protected void keyTyped(char c, int i) {
		if(i == 1 || i == mc.gameSettings.keyBindInventory.getKeyCode()) {
			mc.displayGuiScreen((GuiScreen) null);
			mc.setIngameFocus();
		}
	}

	@Override
	public void handleMouseInput() {
		super.handleMouseInput();
		int delta = Mouse.getEventDWheel();
		if(delta != 0 && linkButtons.length > 5) {
			int deltaPerItem = linkButtons.length - 5;
			if(delta > 0) {
				delta = 1;
			}
			if(delta < 0) {
				delta = -1;
			}
			currentScroll = (float) ((double) currentScroll - (double) delta / (double) deltaPerItem);
			if(currentScroll < 0.0F) {
				currentScroll = 0.0F;
			}
			if(currentScroll > 1.0F) {
				currentScroll = 1.0F;
			}
			scrollTo(currentScroll);
		}
	}

	public void scrollTo(float currentScroll) {
		int startIndex = (int) (currentScroll * (linkButtons.length - 5));
		for(int i = 0; i < linkButtons.length; i++) {
			if(i >= startIndex && i < startIndex + 5) {
				linkButtons[i].enabled = true;
				linkButtons[i].visible = true;
				linkButtons[i].yPosition = guiTop + 26 + ((i - startIndex) * 24);
			} else {
				linkButtons[i].enabled = false;
				linkButtons[i].visible = false;
			}
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialRenderTime) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(WLResources.guiMenu);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		mc.fontRenderer.drawString(topicName, guiLeft + 7, guiTop + 10, 0x404040, false);
		
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
		boolean isMouseDown = Mouse.isButtonDown(0);
		int x1 = guiLeft + 153;
		int y1 = guiTop + 25;
		int x2 = x1 + 14;
		int y2 = y1 + 122;
		if(!wasClicking && isMouseDown && mouseX >= x1 && mouseY >= y1 && mouseX < x2 && mouseY < y2) {
			isScrolling = linkButtons.length > 5;
		}
		if(!isMouseDown) {
			isScrolling = false;
		}
		wasClicking = isMouseDown;
		if(isScrolling) {
			currentScroll = ((float) (mouseY - y1) - 7.5F) / ((float) (y2 - y1) - 15.0F);
			if(currentScroll < 0.0F) {
				currentScroll = 0.0F;
			}
			if(currentScroll > 1.0F) {
				currentScroll = 1.0F;
			}
			scrollTo(currentScroll);
		}
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(WLResources.guiMenu);
		drawTexturedModalRect(guiLeft + 154, guiTop + 26 + (int) (currentScroll * 105), linkButtons.length > 5 ? 176 : 188, 0, 12, 15);
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
