package elcon.mods.wikilink.link.types;

import java.util.Arrays;
import java.util.List;

import elcon.mods.wikilink.api.ILinkTopic;
import elcon.mods.wikilink.api.ILinkType;
import elcon.mods.wikilink.web.WebHelper;

public class LinkTypeGoogle implements ILinkType {

	@Override
	public String getName() {
		return "Google";
	}

	@Override
	public List<String> getTopics() {
		return Arrays.asList("ItemStack", "FluidStack", "Entity", "Mod");
	}

	@Override
	public String generateLink(ILinkTopic topic) {
		return "http://google.com/search?q=minecraft+" + WebHelper.encode(topic.getName());
	}
}
