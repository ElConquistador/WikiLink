package elcon.mods.wikilink.link.types;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.html.HTMLDocument;

import net.minecraft.util.StatCollector;
import cpw.mods.fml.common.Mod;
import elcon.mods.wikilink.api.ILink;
import elcon.mods.wikilink.api.ILinkType;
import elcon.mods.wikilink.api.WikiLinkAPI;

public class LinkTypeMinecraftForum implements ILinkType {

	@Override
	public String getName() {
		return "MinecraftForum";
	}

	@Override
	public String getDisplayName() {
		return StatCollector.translateToLocal("wikilink.types." + getName());
	}

	@Override
	public String getTopicName(Object topic) {
		String topicName = "";
		if(topic instanceof Mod) {
			topicName = ((Mod) topic).name();
		}
		return topicName;
	}

	@Override
	public List<Class<?>> getTopics() {
		ArrayList<Class<?>> list = new ArrayList<Class<?>>();
		list.add(Mod.class);
		return list;
	}

	@Override
	public boolean isSearchOnly() {
		return false;
	}

	@Override
	public ILink generateSearchLink(Object topic) {
		String topicName = getTopicName(topic);
		return WikiLinkAPI.linkRegistry.createLink(topic, this, "http://minecraftforum.net/index.php?app=core&module=search&do=search&search_term=" + topicName, topicName);
	}

	@Override
	public ILink generateLink(Object topic) {
		String topicName = getTopicName(topic);
		String forum = WikiLinkAPI.linkRegistry.getLinkMod(topicName).getForum();
		if(forum != null && !forum.isEmpty() && forum.contains("minecraftforum.net")) {
			return WikiLinkAPI.linkRegistry.createLink(topic, this, forum, topicName);
		}
		return null;
	}

	@Override
	public boolean hasPreview() {
		return true;
	}

	@Override
	public String getPreviewTitle(ILink link, HTMLDocument html) {
		return (String) html.getProperty(HTMLDocument.TitleProperty);
	}

	@Override
	public String getPreview(ILink link, HTMLDocument html) {
		return null;
	}
}
