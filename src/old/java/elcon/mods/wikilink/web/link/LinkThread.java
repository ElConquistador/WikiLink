package elcon.mods.wikilink.web.link;

import elcon.mods.wikilink.plg.PluginRegistrar;
import elcon.mods.wikilink.web.WebHelper;

import net.minecraft.item.ItemStack;

public class LinkThread extends Link
{
    public LinkThread(ItemStack item)
    {
        super(item, EnumLink.THREAD);
    }

    @Override
    public String getDisplay()
    {
        return getModId(this.item) + " Forum Thread";
    }

    @Override
    public String getHyperlink()
    {
        return "http://www.minecraftforum.net/topic/" + PluginRegistrar.getThreadMap().get(getModId(this.item));
    }

}
