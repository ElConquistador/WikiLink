package elcon.mods.wikilink.link.types;

import java.util.Arrays;
import java.util.List;

import javax.swing.text.html.HTMLDocument;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.common.Mod;
import elcon.mods.wikilink.api.ILink;
import elcon.mods.wikilink.api.ILinkType;
import elcon.mods.wikilink.link.Link;
import elcon.mods.wikilink.web.WebHelper;

public class LinkTypeGoogle implements ILinkType {

	@Override
	public String getName() {
		return "Google";
	}
	
	@Override
	public String getDisplayName() {
		return StatCollector.translateToLocal("wikilink.types." + getName());
	}

	@Override
	public String getTopicName(Object topic) {
		String topicName = "";
		if(topic instanceof ItemStack) {
			topicName = ((ItemStack) topic).getDisplayName();
		} else if(topic instanceof FluidStack) {
			topicName = ((FluidStack) topic).getFluid().getLocalizedName();
		} else if(topic instanceof Entity) {
			topicName = ((Entity) topic).getCommandSenderName();
		} else if(topic instanceof Mod) {
			topicName = ((Mod) topic).name();
		}
		return topicName;
	}
	
	@Override
	public List<Class<?>> getTopics() {
		return Arrays.asList(ItemStack.class, FluidStack.class, Entity.class, Mod.class);
	}
	
	@Override
	public boolean isSearchOnly() {
		return true;
	}

	@Override
	public ILink generateSearchLink(Object topic) {
		String topicName = getTopicName(topic);
		return new Link(topic, this, "http://google.com/search?q=minecraft+" + WebHelper.encode(topicName), topicName);
	}
	
	@Override
	public ILink generateLink(Object topic) {
		return null;
	}
	
	@Override
	public boolean hasPreview() {
		return false;
	}
	
	@Override
	public String getPreviewTitle(ILink link, HTMLDocument html) {
		return null;
	}
	
	@Override
	public String getPreview(ILink link, HTMLDocument html) {
		return null;
	}
}
