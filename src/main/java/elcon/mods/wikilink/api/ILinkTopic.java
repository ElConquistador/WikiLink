package elcon.mods.wikilink.api;

public interface ILinkTopic {
	
	/**
	 * @return The general topic name (example: ItemStack)
	 */
	public String getTopicName();
	
	/**
	 * @return The general topic class (example: ItemStack.class)
	 */	
	public Class<?> getTopicClass();
	
	public String getName();
	
	/**
	 * @return The 
	 */
	public Object getObject();
}
