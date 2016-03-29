package main;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashSet;
import java.util.Set;


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
		Set<String> to = new HashSet<String>();
		for (Player p : e.getRecipients())
		{
			to.add(p.getName());
		}
		EonaChat.getEmsr().handleOutGoingMessages(new EonaMessage(e.getPlayer().getDisplayName(), e.getMessage(), to));
	}
}
}
