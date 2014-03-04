package elcon.mods.wikilink.link;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import elcon.mods.wikilink.api.ILink;
import elcon.mods.wikilink.api.ILinkCollection;
import elcon.mods.wikilink.api.ILinkType;

public class LinkCollection implements ILinkCollection {

	public Object topic;
	public String topicName;
	private HashMap<ILinkType, ILink> links = new HashMap<ILinkType, ILink>();
	
	public LinkCollection(Object topic) {
		this.topic = topic;
	}
	
	@Override
	public Object getTopic() {
		return topic;
	}
	
	@Override
	public String getTopicName() {
		return topicName;
	}
	
	@Override
	public ILink get(ILinkType type) {
		return links.get(type);
	}
	
	@Override
	public Collection<ILinkType> getAllTypes() {
		return links.keySet();
	}
	
	@Override
	public Collection<ILink> getAllLinks() {
		return links.values();
	}
	
	@Override
	public Map<ILinkType, ILink> getAll() {
		return links;
	}
	
	@Override
	public void add(ILinkType type, ILink link) {
		if(topicName == null || topicName.isEmpty()) {
			topicName = type.getTopicName(topic);
		}
		links.put(type, link);
	}
	
	@Override
	public void remove(ILinkType type) {
		links.remove(type);
	}
}
