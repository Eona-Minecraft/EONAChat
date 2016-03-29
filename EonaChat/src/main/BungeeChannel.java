package main;


import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.HashSet;
import java.util.Set;


/**
 * Message Handling
 */
class BungeeChannel implements PluginMessageListener
{

private EonaChat plugin = null;


BungeeChannel(EonaChat pl)
{
	this.plugin = pl;
	plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
	plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, "BungeeCord", this);
}


@Override
public void onPluginMessageReceived(String channel, Player player, byte[] bytes)
{
	if (!channel.equalsIgnoreCase("bungeecord"))
	{
		return;
	}
	ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
	String subchannel = in.readUTF();

	System.out.println(subchannel);

	if (subchannel.equalsIgnoreCase("eonachat"))
	{

		String from = in.readUTF();
		String message = in.readUTF();

		Set<String> to = new HashSet<String>();
		String s = in.readUTF();
		while (s != null)
		{
			to.add(s);
			s = in.readUTF();
		}
		EonaMessage msg = new EonaMessage(from, message, to);
		handleIncomingMessages(msg);
	}
}


void handleOutGoingMessages(EonaMessage m)
{
	ByteArrayDataOutput out = ByteStreams.newDataOutput();
	out.writeUTF("eonachat");
	out.writeUTF(m.getFrom());
	out.writeUTF(m.getMessage());
	out.writeUTF(m.getTo().toString());
	Bukkit.getPlayer(m.getFrom()).sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
}


private void handleIncomingMessages(EonaMessage m)
{
	for (String pname : m.getTo())
	{
		Player p = Bukkit.getPlayer(pname);
		if (pname != null)
		{
			p.sendMessage(m.getFrom() + " " + m.getMessage());
		}
	}
}
}
