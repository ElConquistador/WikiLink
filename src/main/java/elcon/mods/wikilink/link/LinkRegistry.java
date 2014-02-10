package elcon.mods.wikilink.link;

import java.util.HashMap;

import cpw.mods.fml.common.Mod;
import elcon.mods.wikilink.WikiLink;
import elcon.mods.wikilink.api.ILinkMod;
import elcon.mods.wikilink.api.ILinkRegistry;
import elcon.mods.wikilink.api.ILinkType;

public class LinkRegistry implements ILinkRegistry {

	public static LinkRegistry instance;
	
	private HashMap<String, ILinkType> linkTypes = new HashMap<String, ILinkType>();
	private HashMap<String, ILinkMod> linkMods = new HashMap<String, ILinkMod>();
	
	private HashMap<Object, Link> linkCache = new HashMap<Object, Link>();
	
	public LinkRegistry() {
		instance = this;
	}

	@Override
	public void registerLinkType(ILinkType linkType) {
		linkTypes.put(linkType.getName(), linkType);
		WikiLink.log.info("Registered link type: " + linkType.getName());
	}
	
	@Override
	public void registerLinkMod(ILinkMod linkMod) {
		linkMods.put(linkMod.getID(), linkMod);
	}
	
	@Override
	public ILinkMod generateLinkMod(Object mod) {
		return generateLinkMod(mod, "", "", "", "");
	}
	
	@Override
	public ILinkMod generateLinkMod(Object mod, String thread, String site, String forum, String wiki) {
		if(mod.getClass().getAnnotation(Mod.class) != null) {
			return new LinkMod(mod.getClass().getAnnotation(Mod.class), thread, site, forum, wiki);
		}
		return null;
	}
	
	@Override
	public Link getLink(Object topic) {
		if(linkCache.containsKey(topic)) {
			return linkCache.get(topic);
		}
		Link link = new Link(topic);
		for(ILinkType linkType : linkTypes.values()) {
			if(linkType.getTopics().contains(topic.getClass())) {
				link.add(linkType, linkType.generateSearchLink(topic));
			}
		}
		linkCache.put(topic, link);
		return link;
	}
	
	@Override
	public ILinkMod getLinkMod(String mod) {
		return linkMods.get(mod);
	}
}
