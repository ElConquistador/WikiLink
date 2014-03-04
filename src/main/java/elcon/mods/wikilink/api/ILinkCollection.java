package elcon.mods.wikilink.api;

import java.util.Collection;
import java.util.Map;

public interface ILinkCollection {
	
	public Object getTopic();
	
	public String getTopicName();

	public ILink get(ILinkType linkType);
	
	public Collection<ILinkType> getAllTypes();
	
	public Collection<ILink> getAllLinks();
	
	public Map<ILinkType, ILink> getAll();
	
	public void add(ILinkType linkType, ILink link);
	
	public void remove(ILinkType linkType);
}
