package elcon.mods.wikilink.api;

import java.util.List;

public interface ILinkType {

	public String getName();
	
	public List<String> getTopics();
	
	public String generateLink(ILinkTopic topic);
}
