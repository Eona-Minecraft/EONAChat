package de.eonaminecraft.eonachat;


import org.bukkit.entity.Player;
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
	EonaChat.log("Chat: " + e.getPlayer().getDisplayName() + " | " + e.getMessage());
	for (Player r : e.getRecipients())
	{
		EonaChat.log("to: " + r.getDisplayName());
	}

	if (!e.isCancelled())
	{
		e.setMessage(BadWordsFilter.filter(e.getMessage()));
		EonaChat.getBungeeChannel().handleOutGoingMessages(
				new EonaMessage(e.getPlayer().getDisplayName(), e.getMessage(), null));
	}
}
}
