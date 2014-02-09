package elcon.mods.wikilink.link.types;

import java.util.Arrays;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import elcon.mods.wikilink.api.ILinkType;
import elcon.mods.wikilink.web.WebHelper;

public class LinkTypeMinecraftWiki implements ILinkType {

	@Override
	public String getName() {
		return "MinecraftWiki";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Class<?>> getTopics() {
		return Arrays.asList(ItemStack.class, FluidStack.class, Entity.class);
	}
	
	@Override
	public boolean isSearchOnly() {
		return false;
	}

	@Override
	public String generateSearchLink(Object topic) {
		String topicName = "";
		if(topic instanceof ItemStack) {
			topicName = ((ItemStack) topic).getDisplayName();
		} else if(topic instanceof FluidStack) {
			topicName = ((FluidStack) topic).getFluid().getLocalizedName();
		} else if(topic instanceof Entity) {
			topicName = ((Entity) topic).getCommandSenderName();
		}
		return "http://minecraft.gamepedia.com/index.php?search=" + WebHelper.encode(topicName);
	}
	
	@Override
	public String generateLink(Object topic) {
		//TODO: generate link if exists
		return null;
	}
}
