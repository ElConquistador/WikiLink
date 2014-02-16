package elcon.mods.wikilink;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
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
			Minecraft.getMinecraft().displayGuiScreen(new GuiWikiLinkMenu(new ItemStack(Blocks.stone)));
		}
	}
}
