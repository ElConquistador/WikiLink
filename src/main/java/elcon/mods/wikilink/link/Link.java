package elcon.mods.wikilink.link;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import elcon.mods.wikilink.api.ILink;
import elcon.mods.wikilink.api.ILinkType;

public class Link implements ILink {

	public Object topic;
	private HashMap<ILinkType, String> links = new HashMap<ILinkType, String>();
	
	public Link(Object topic) {
		this.topic = topic;
	}
	
	@Override
	public Object getTopic() {
		return topic;
	}
	
	@Override
	public String get(ILinkType type) {
		return links.get(type);
	}
	
	@Override
	public Collection<ILinkType> getAllTypes() {
		return links.keySet();
	}
	
	@Override
	public Collection<String> getAllLinks() {
		return links.values();
	}
	
	@Override
	public Map<ILinkType, String> getAll() {
		return links;
	}
	
	@Override
	public void add(ILinkType type, String link) {
		links.put(type, link);
	}
	
	@Override
	public void remove(ILinkType type) {
		links.remove(type);
	}
}
