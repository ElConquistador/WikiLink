package elcon.mods.wikilink.link.types;

import java.util.Arrays;
import java.util.List;

import javax.swing.text.Element;
import javax.swing.text.html.HTMLDocument;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;
import elcon.mods.wikilink.api.ILink;
import elcon.mods.wikilink.api.ILinkType;
import elcon.mods.wikilink.api.WikiLinkAPI;
import elcon.mods.wikilink.web.WebHelper;

public class LinkTypeMinecraftWiki implements ILinkType {

	@Override
	public String getName() {
		return "MinecraftWiki";
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
		}
		return topicName;
	}
	
	@Override
	public List<Class<?>> getTopics() {
		return Arrays.asList(ItemStack.class, FluidStack.class, Entity.class);
	}
	
	@Override
	public boolean isSearchOnly() {
		return true;
	}

	@Override
	public ILink generateSearchLink(Object topic) {
		String topicName = getTopicName(topic);
		return WikiLinkAPI.linkRegistry.createLink(topic, this, "http://minecraft.gamepedia.com/index.php?search=" + WebHelper.encode(topicName), topicName);
	}
	
	@Override
	public ILink generateLink(Object topic) {
		return null;
	}
	
	@Override
	public boolean hasPreview() {
		return true;
	}
	
	@Override
	public String getPreviewTitle(ILink link, HTMLDocument html) {
		return (String) html.getProperty(HTMLDocument.TitleProperty);
	}
	
	@Override
	public String getPreview(ILink link, HTMLDocument html) {
		String preview = "";
		Element element = html.getElement("mw-content-text");
		if(element == null) {
			return null;
		}
		for(int i = 0; i < element.getElementCount(); i++) {
			Element subElement = element.getElement(i);
			if(subElement.getName().equals("table")) {
				break;
			} else if(subElement.getName().equals("p")) {
				try {
					preview += html.getText(subElement.getStartOffset(), subElement.getEndOffset() - subElement.getStartOffset());
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		return preview;
	}
}
