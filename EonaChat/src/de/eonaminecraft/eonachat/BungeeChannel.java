package de.eonaminecraft.eonachat;


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

	EonaChat.log("Channel: " + subchannel);

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
			EonaChat.log(from);
			String message = msgin.readUTF();
			EonaChat.log(message);
			String to = msgin.readUTF();
			EonaChat.log(to);

			EonaMessage msg = new EonaMessage(from, message, to);
			handleIncomingMessages(msg);
		} catch (IOException e)
		{
			EonaChat.log.warning(e.getMessage());
		}
	}
}


void handleOutGoingMessages(EonaMessage m)
{
	ByteArrayDataOutput out = ByteStreams.newDataOutput();
	out.writeUTF("Forward");
	out.writeUTF("ALL");
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

	out.writeShort(msgbytes.toByteArray().length);
	out.write(msgbytes.toByteArray());

	Bukkit.getPlayer(m.getFrom()).sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
}


private void handleIncomingMessages(EonaMessage m)
{
	if (m.getFrom().equals(m.getTo()))
	{
		for (Player p : Bukkit.getOnlinePlayers())
		{
			p.sendMessage("<" + m.getFrom() + "> " + ChatColor.RESET + m.getMessage());
		}
	} else
	{
		Player p = Bukkit.getPlayer(m.getTo());
		if (p != null)
		{
			p.sendMessage("<" + m.getFrom() + "> " + ChatColor.RESET + m.getMessage());
		}
	}
}
}
