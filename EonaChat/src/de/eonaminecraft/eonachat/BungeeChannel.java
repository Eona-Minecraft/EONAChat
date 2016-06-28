package de.eonaminecraft.eonachat;


import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.*;


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
	if (!channel.equals("BungeeCord"))
	{
		return;
	}
	ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
	String subchannel = in.readUTF();

	if (subchannel.equalsIgnoreCase("eonachat"))
	{
		short lengh = in.readShort();
		byte[] msgbytes = new byte[lengh];
		in.readFully(msgbytes);

		//Read Data Packet
		DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
		try
		{
			String from = msgin.readUTF();
			String message = msgin.readUTF();
			String to = msgin.readUTF();

			printIncomingMessages(new EonaMessage(from, message, to));
		} catch (IOException e)
		{
			EonaChat.log.warning(e.getMessage());
		}
	}
}


void sendMessages(EonaMessage m)
{
	ByteArrayDataOutput out = ByteStreams.newDataOutput();
	if (m.getTo().equals("ALL"))
	{
		out.writeUTF("Forward");
		out.writeUTF("ALL");
		EonaChat.log("Sending global message");
	} else
	{
		out.writeUTF("ForwardToPlayer");
		out.writeUTF(m.getTo());
		EonaChat.log("Sending private message to " + m.getTo());
	}

	out.writeUTF("eonachat");

	//Data Packet
	ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
	DataOutputStream msgout = new DataOutputStream(msgbytes);
	try
	{
		msgout.writeUTF(m.getFrom());
		msgout.writeUTF(m.getMessage());
		msgout.writeUTF(m.getTo());
	} catch (IOException e)
	{
		EonaChat.log.warning(e.getMessage());
	}
	byte[] byteArray = msgbytes.toByteArray();
	out.writeShort(byteArray.length);
	out.write(byteArray);

	Player from = Bukkit.getPlayer(m.getFrom());
	if (from == null)
	{
		from = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
		if (from != null) from.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
		else EonaChat.log.warning("Message could not be sent, no players online");
	} else
	{
		from.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
	}
}


private void printIncomingMessages(EonaMessage m)
{
	if (m.getTo().equals("ALL"))
	{
		for (Player p : Bukkit.getOnlinePlayers())
		{
			p.sendMessage("<" + m.getFrom() + "> " + ChatColor.RESET + m.getMessage());
		}
	} else
	{
		Player p = Bukkit.getPlayer(m.getTo());
		if (p == null)
		{
			sendMessages(new EonaMessage(Bukkit.getServerName(), net.md_5.bungee.api.ChatColor.RED +
					"Spieler " + m.getTo() + " nicht gefunden!", m.getFrom()));
		} else
		{
			EonaChat.lastChat.put(m.getFrom(), m.getTo());
			EonaChat.lastChat.put(m.getTo(), m.getFrom());
			p.sendMessage("<" + m.getFrom() + " -> " + m.getTo() + "> " + ChatColor.RESET + m.getMessage());
			//TODO Add feedback on success
		}
	}
}
}
