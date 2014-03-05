package elcon.mods.wikilink.api;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.Fluid;

public interface ILinkMod {

	public String getID();
	
	public String getName();
	
	public String getVersion();
	
	public String getThread();
	
	public String getSite();
	
	public String getForum();
	
	public String getWiki();
	
	public void addBlocks(Block... blocks);
	
	public void addItems(Item... items);
	
	public void addFluids(Fluid... fluids);
	
	public void addEntities(int... entities);
	
	/**
	 * @param topic The topic
	 * @return Whether the topic is from this mod
	 */
	public boolean ownsTopic(Object topic);
}
