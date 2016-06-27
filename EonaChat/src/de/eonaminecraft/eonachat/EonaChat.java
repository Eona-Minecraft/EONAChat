package de.eonaminecraft.eonachat;


import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;


/**
 * Main Class
 */
public class EonaChat extends JavaPlugin
{

static Logger log;
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

	if (args.length > 1 &&
			(
					command.getName().equals("m") || command.getName().equals("msg") ||
							command.getName().equals("w") || command.getName().equals("whisper") ||
							command.getName().equals("tell")
			))
	{
		String from;
		if (sender instanceof Player)
		{
			from = ((Player) sender).getDisplayName();
		} else
		{
			from = sender.getName();
		}
		String message = "";
		for (int i = 1; i < args.length; i++)
		{
			message += " " + args[i];
		}

		getBungeeChannel().handleOutGoingMessages(new EonaMessage(from, BadWordsFilter.filter(message), args[0]));
		return true;
	} else return false;
}
}
