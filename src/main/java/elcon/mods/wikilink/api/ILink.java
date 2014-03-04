package elcon.mods.wikilink.api;

public interface ILink {

	public Object getTopic();
	
	public ILinkType getLinkType();
	
	public String getURL();
	
	public String getName();
}
