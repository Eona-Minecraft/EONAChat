package de.eonaminecraft.eonachat;


import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


/**
 * Listens to AsyncPlayerChatEvent
 */
class EonaListener implements Listener
{

EonaListener(EonaChat pl)
{
	pl.getServer().getPluginManager().registerEvents(this, pl);
}


@EventHandler
public void onChat(AsyncPlayerChatEvent e)
{
	if (!e.isCancelled())
	{
		e.setMessage(BadWordsFilter.filter(e.getMessage()));
		EonaChat.getBungeeChannel().sendMessages(
				new EonaMessage(e.getPlayer().getDisplayName(), e.getMessage(), "ALL"));
	}
}
}
