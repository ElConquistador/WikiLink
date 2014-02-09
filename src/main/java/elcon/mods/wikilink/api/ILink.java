package elcon.mods.wikilink.api;

import java.util.Collection;
import java.util.Map;

public interface ILink {
	
	public Object getTopic();

	public String get(ILinkType linkType);
	
	public Collection<ILinkType> getAllTypes();
	
	public Collection<String> getAllLinks();
	
	public Map<ILinkType, String> getAll();
	
	public void add(ILinkType linkType, String link);
	
	public void remove(ILinkType linkType);
}
