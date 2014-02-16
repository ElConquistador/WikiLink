package elcon.mods.wikilink;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class WLClientProxy extends WLCommonProxy {

	@Override
	public void registerRenderingInformation() {
		//register event handler
		WikiLink.eventHandlerClient = new WLEventHandlerClient();
		FMLCommonHandler.instance().bus().register(WikiLink.eventHandlerClient);
	}
}
