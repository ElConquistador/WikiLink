package elcon.mods.wikilink.link;

import elcon.mods.wikilink.api.ILink;
import elcon.mods.wikilink.api.ILinkType;

public class Link implements ILink {

	public Object topic;
	public ILinkType linkType;
	public String url;
	public String name;
	
	public Link(Object topic, ILinkType linkType, String url, String name) {
		this.topic = topic;
		this.linkType = linkType;
		this.url = url;
		this.name = name;
	}
	
	@Override
	public Object getTopic() {
		return topic;
	}
	
	@Override
	public ILinkType getLinkType() {
		return linkType;
	}
	
	@Override
	public String getURL() {
		return url;
	}
	
	@Override
	public String getName() {
		return name;
	}
}
