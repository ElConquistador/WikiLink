package elcon.mods.wikilink.web.link;

import java.util.HashMap;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;


import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.ItemData;
import elcon.mods.wikilink.plg.PluginRegistrar;

public abstract class Link extends PluginRegistrar
{    
    protected EnumLink type;
    protected ItemStack item;
    
    public static HashMap<Integer, String> modIdItemIdMapping = new HashMap<Integer, String>();

    public Link(ItemStack item, EnumLink type)
    {
        this.item = item;
        this.type = type;
    }
    
    public abstract String getDisplay();
    public abstract String getHyperlink();
    
    /** <b>getModId</b><br>
     *  Returns the @ModId of the given ItemStack based on the HashMap
     *  generated in the PostInit stage of WikiLink. 
     *  **/
    public static String getModId(ItemStack i)
    {
        return modIdItemIdMapping.get(i.itemID);
    }
    
    public static void buildmodIdItemIdHashMap()
    {
        NBTTagList itemDataList = new NBTTagList();

        GameData.writeItemData(itemDataList);
        for (int i = 0; i < itemDataList.tagCount(); i++)
        {
            ItemData data = new ItemData((NBTTagCompound) itemDataList.tagAt(i));
                modIdItemIdMapping.put(data.getItemId(), data.getModId());
        }
    }   
}
