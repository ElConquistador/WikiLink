package elcon.mods.wikilink.link;

import cpw.mods.fml.common.Mod;
import elcon.mods.wikilink.api.ILinkMod;

public class LinkMod implements ILinkMod {

	public Mod mod;
	public String thread;
	public String site;
	public String forum;
	public String wiki;
	
	public LinkMod(Mod mod, String thread, String site, String forum, String wiki) {
		this.mod = mod;
		this.thread = thread;
		this.site = site;
		this.forum = forum;
		this.wiki = wiki;
	}
	
	@Override
	public String getID() {
		return mod.modid();
	}

	@Override
	public String getName() {
		return mod.name();
	}

	@Override
	public String getVersion() {
		return mod.version();
	}

	@Override
	public String getThread() {
		return thread;
	}
	
	@Override
	public String getSite() {
		return site;
	}
	
	@Override
	public String getForum() {
		return forum;
	}

	@Override
	public String getWiki() {
		return wiki;
	}
}
