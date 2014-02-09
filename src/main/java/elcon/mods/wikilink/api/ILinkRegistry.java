package elcon.mods.wikilink.api;

public interface ILinkRegistry {

	public void registerLinkType(ILinkType linkType);
	
	public void registerLinkTopic(ILinkTopic linkTopic);
}
