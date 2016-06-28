package de.eonaminecraft.eonachat;


import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.logging.Logger;


/**
 * Main Class
 */
public class EonaChat extends JavaPlugin
{

static Logger log;
static HashMap<String, String> lastChat = new HashMap<String, String>();
private static BungeeChannel bungeeChannel = null;


static void log(String msg)
{
	log.info(msg);
}


static BungeeChannel getBungeeChannel()
{
	return bungeeChannel;
}


@Override
public void onEnable()
{
	log = getLogger();
	BadWordsFilter.loadFiles(getDataFolder());
	bungeeChannel = new BungeeChannel(this);
	getLogger().info("BungeeChannel initialisiert");
	new EonaListener(this);
	getLogger().info("EonaChatListener initialisiert");
}


@Override
public void onDisable()
{
	bungeeChannel = null;
	getLogger().info("EonaChat deaktiviert");
}


@Override
public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
{

	if (command.getName().equals("r"))
	{
		if (args.length > 0)
		{
			if (sender instanceof Player)
			{
				String from = ((Player) sender).getDisplayName();
				String to = lastChat.get(from);
				if (to == null)
				{
					sender.sendMessage(ChatColor.RED + "Du führst derzeit kein Gespräch mit einem anderen Spieler");
				} else
				{
					String message = "";
					for (String arg : args)
					{
						message += " " + arg;
					}
					getBungeeChannel().sendMessages(new EonaMessage(from, BadWordsFilter.filter(message), to));
				}
			} else sender.sendMessage("Dieser Befehl ist nur für Spieler gedacht!");
			return true;
		} else return false;
	} else if (command.getName().equals("m") || command.getName().equals("msg") ||
			command.getName().equals("w") || command.getName().equals("whisper") ||
			command.getName().equals("tell"))
	{
		if (args.length > 1)
		{
			String from;
			if (sender instanceof Player)
			{
				from = ((Player) sender).getDisplayName();
				lastChat.put(from, args[0]);
				lastChat.put(args[0], from);
			} else
			{
				from = sender.getName();
			}
			String message = "";
			for (int i = 1; i < args.length; i++)
			{
				message += " " + args[i];
			}
			getBungeeChannel().sendMessages(new EonaMessage(from, BadWordsFilter.filter(message), args[0]));
			return true;
		} else return false;
	} else return true;
}
}
