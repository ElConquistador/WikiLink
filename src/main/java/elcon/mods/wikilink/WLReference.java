package elcon.mods.wikilink;

public class WLReference {

	public static final String MOD_ID = "WikiLink";
	public static final String NAME = "WikiLink";
	public static final String VERSION = "${version} (build ${buildnumber})";
	public static final String MC_VERSION = "[1.7.2]";
	public static final String DEPENDENCIES = "required-after:Forge@[10.12.0.1024,)";
	public static final String SERVER_PROXY_CLASS = "elcon.mods.wikilink.WLCommonProxy";
    public static final String CLIENT_PROXY_CLASS = "elcon.mods.wikilink.WLClientProxy";
    
    public static final String VERSION_URL = "https://raw.github.com/ItsMeElConquistador/WikiLink/master/version.xml";
}
