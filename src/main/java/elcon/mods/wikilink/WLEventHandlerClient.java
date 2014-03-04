package elcon.mods.wikilink;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.wikilink.gui.GuiWikiLinkMenu;

@SideOnly(Side.CLIENT)
public class WLEventHandlerClient {

	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event) {
		if(WikiLink.keyBinding.isPressed()) {
			Minecraft mc = Minecraft.getMinecraft();
			Object topic = null;
			if(mc.inGameHasFocus) {
				if(mc.pointedEntity != null) {
					topic = mc.pointedEntity;
				} else if(mc.objectMouseOver != null) {
					MovingObjectPosition mop = mc.objectMouseOver;
					if(mop.typeOfHit == MovingObjectType.BLOCK) {
						Block block = mc.theWorld.getBlock(mop.blockX, mop.blockY, mop.blockZ);
						topic = block.getPickBlock(mop, mc.theWorld, mop.blockX, mop.blockY, mop.blockZ);
						((ItemStack) topic).stackSize = 1;
					} else if(mop.typeOfHit == MovingObjectType.ENTITY) {
						topic = mop.entityHit;
					}
				}
			}
			if(topic != null) {
				mc.displayGuiScreen(new GuiWikiLinkMenu(topic));
			}
		}
	}

	public boolean isMouseOverSlot(int guiLeft, int guiTop, Slot slot, int mouseX, int mouseY) {
		return isMouseOver(guiLeft, guiTop, slot.xDisplayPosition, slot.yDisplayPosition, 16, 16, mouseX, mouseY);
	}

	public boolean isMouseOver(int guiLeft, int guiTop, int x, int y, int width, int length, int mouseX, int mouseY) {
		mouseX -= guiLeft;
		mouseY -= guiTop;
		return mouseX >= x - 1 && mouseX < x + width + 1 && mouseY >= y - 1 && mouseY < y + length + 1;
	}
}
