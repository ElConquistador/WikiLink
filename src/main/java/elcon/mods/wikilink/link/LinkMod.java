package elcon.mods.wikilink.link;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.common.Mod;
import elcon.mods.wikilink.api.ILinkMod;

public class LinkMod implements ILinkMod {

	public Mod mod;
	public String thread;
	public String site;
	public String forum;
	public String wiki;
	
	public ArrayList<Block> blocks = new ArrayList<Block>();
	public ArrayList<Item> items = new ArrayList<Item>();
	public ArrayList<Fluid> fluids = new ArrayList<Fluid>();
	public ArrayList<Integer> entities = new ArrayList<Integer>();
	
	public LinkMod(Mod mod, String thread, String site, String forum, String wiki) {
		this.mod = mod;
		this.thread = thread;
		this.site = site;
		this.forum = forum;
		this.wiki = wiki;
	}
	
	@Override
	public String getID() {
		return mod.modid();
	}

	@Override
	public String getName() {
		return mod.name();
	}

	@Override
	public String getVersion() {
		return mod.version();
	}

	@Override
	public String getThread() {
		return thread;
	}
	
	@Override
	public String getSite() {
		return site;
	}
	
	@Override
	public String getForum() {
		return forum;
	}

	@Override
	public String getWiki() {
		return wiki;
	}
	
	@Override
	public void addBlocks(Block... blocks) {
		for(int i = 0; i < blocks.length; i++) {
			this.blocks.add(blocks[i]);
		}
	}
	
	@Override
	public void addItems(Item... items) {
		for(int i = 0; i < items.length; i++) {
			this.items.add(items[i]);
		}
	}
	
	@Override
	public void addFluids(Fluid... fluids) {
		for(int i = 0; i < fluids.length; i++) {
			this.fluids.add(fluids[i]);
		}
	}
	
	@Override
	public void addEntities(int... entities) {
		for(int i = 0; i < entities.length; i++) {
			this.entities.add(Integer.valueOf(entities[i]));
		}
	}
	
	@Override
	public boolean ownsTopic(Object topic) {
		if(topic instanceof ItemStack) {
			ItemStack stack = (ItemStack) topic;
			Block block = Block.getBlockFromItem(stack.getItem());
			if(block != null && block != Blocks.air) {
				return blocks.contains(block);
			}
			return items.contains(stack.getItem());
		} else if(topic instanceof FluidStack) {
			return fluids.contains(((FluidStack) topic).getFluid());
		} else if(topic instanceof Entity) {
			return entities.contains(Integer.valueOf(((Entity) topic).getEntityId()));
		}
		return false;
	}
}
