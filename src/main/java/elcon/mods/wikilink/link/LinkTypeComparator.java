package elcon.mods.wikilink.link;

import java.util.Comparator;

import elcon.mods.wikilink.api.ILink;

public class LinkTypeComparator implements Comparator<ILink> {
	
	@Override
	public int compare(ILink link1, ILink link2) {
		if(link1.getLinkType().isSearchOnly() && !link2.getLinkType().isSearchOnly()) {
			return 1;
		} else if(!link1.getLinkType().isSearchOnly() && link2.getLinkType().isSearchOnly()) {
			return -1;
		} else {
			if(link1.getLinkType().hasPreview() && !link2.getLinkType().hasPreview()) {
				return -1;
			} else if(!link1.getLinkType().hasPreview() && link2.getLinkType().hasPreview()) {
				return 1;
			} else {
				return 0;
			}
		}
	}
}
