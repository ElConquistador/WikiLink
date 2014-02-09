package elcon.mods.wikilink.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;


import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.Player;
import elcon.mods.wikilink.Reference;
import elcon.mods.wikilink.WikiLink;

public class ConnectionHandler implements IConnectionHandler
{

    @Override
    public void playerLoggedIn(Player player, NetHandler netHandler, INetworkManager manager)
    {
        try
        {URL version = new URL("https://raw.github.com/DrEinsteinium/WikiLink/master/version.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(version.openStream())); 
            
            String line; 
            String wholeString = "";
            
            while((line = in.readLine()) != null)
                wholeString += " "+line;

            if(!wholeString.contains(Reference.VER_MODHASH))
                netHandler.getPlayer().addChatMessage("\u00A76[\u00A7aWikiLink\u00A76]\u00A7c WikiLink is out of date for your Minecraft version. Please update at www.catacombs.co for the latest version.");
                in.close();
        } 
        catch (Exception e)
        {
            e.printStackTrace();
            netHandler.getPlayer().addChatMessage("\u00A76[\u00A7aWikiLink\u00A76]\u00A74 There was a problem while checking the version of WikiLink. Please report this at \u00A79www.catacombs.co/contact-me/");
        }
    }

    @Override
    public String connectionReceived(NetLoginHandler netHandler, INetworkManager manager)
    {
        return null;
    }

    @Override
    public void connectionOpened(NetHandler netClientHandler, String server, int port, INetworkManager manager)
    {
    }

    @Override
    public void connectionOpened(NetHandler netClientHandler, MinecraftServer server, INetworkManager manager)
    {
    }

    @Override
    public void connectionClosed(INetworkManager manager)
    {
    }

    @Override
    public void clientLoggedIn(NetHandler clientHandler, INetworkManager manager, Packet1Login login)
    {
    }

}
