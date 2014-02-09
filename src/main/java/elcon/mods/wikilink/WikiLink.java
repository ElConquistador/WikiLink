package elcon.mods.wikilink;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import elcon.mods.wikilink.api.WikiLinkAPI;
import elcon.mods.wikilink.link.LinkRegistry;

@Mod(modid = WLReference.MOD_ID, name = WLReference.NAME, version = WLReference.VERSION, acceptedMinecraftVersions = WLReference.MC_VERSION, dependencies = WLReference.DEPENDENCIES)
public class WikiLink {

	@Instance(WLReference.MOD_ID)
	public static WikiLink instance;
	
	@SidedProxy(clientSide = WLReference.CLIENT_PROXY_CLASS, serverSide = WLReference.SERVER_PROXY_CLASS)
	public static WLCommonProxy proxy;
	
	public static Logger log = LogManager.getLogger(WLReference.MOD_ID);
	
	public static WLConfig config;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		config = new WLConfig(event.getSuggestedConfigurationFile());
		config.load();
		config.save();
		
		WikiLinkAPI.linkRegistry = new LinkRegistry();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.registerRenderingInformation();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
}
