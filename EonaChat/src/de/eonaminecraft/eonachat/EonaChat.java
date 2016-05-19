package de.eonaminecraft.eonachat;


import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;


/**
 * Main Class
 */
public class EonaChat extends JavaPlugin
{

static Logger log;
private static BungeeChannel emsr = null;


static void log(String msg)
{
	log.info(msg);
}


static BungeeChannel getEmsr()
{
	return emsr;
}


@Override
public void onEnable()
{
	log = getLogger();
	emsr = new BungeeChannel(this);
	getLogger().info("BungeeChannel initialisiert");
	new EonaListener(this);
	getLogger().info("EonaChatListener initialisiert");
}


@Override
public void onDisable()
{
	emsr = null;
	getLogger().info("EonaChat deaktiviert");
}
}
