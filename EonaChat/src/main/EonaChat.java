package main;


import org.bukkit.plugin.java.JavaPlugin;


/**
 * Main Class
 */
public class EonaChat extends JavaPlugin
{

private static BungeeChannel emsr = null;


@Override
public void onEnable()
{
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


static BungeeChannel getEmsr()
{
	return emsr;
}
}
