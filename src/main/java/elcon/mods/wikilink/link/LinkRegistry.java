package elcon.mods.wikilink.link;

import java.util.HashMap;

import elcon.mods.wikilink.api.ILinkRegistry;
import elcon.mods.wikilink.api.ILinkTopic;
import elcon.mods.wikilink.api.ILinkType;

public class LinkRegistry implements ILinkRegistry {

	public static LinkRegistry instance;
	
	private HashMap<String, ILinkType> linkTypes = new HashMap<String, ILinkType>();
	private HashMap<String, ILinkTopic> linkTopics = new HashMap<String, ILinkTopic>();
	
	private HashMap<ILinkTopic, Link> linkCache = new HashMap<ILinkTopic, Link>();
	
	public LinkRegistry() {
		instance = this;
	}

	@Override
	public void registerLinkType(ILinkType linkType) {
		linkTypes.put(linkType.getName(), linkType);
	}
	
	@Override
	public void registerLinkTopic(ILinkTopic linkTopic) {
		linkTopics.put(linkTopic.getName(), linkTopic);
	}
	
	public Link getLink(ILinkTopic topic) {
		if(linkCache.containsKey(topic)) {
			return linkCache.get(topic);
		}
		Link link = new Link(topic);
		for(ILinkType linkType : linkTypes.values()) {
			if(linkType.getTopics().contains(topic.getTopicName())) {
				
			}
		}
		return link;
	}
}
