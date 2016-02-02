package main;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by Bloodrayne on 02.02.2016.
 */
public class EonaListener implements Listener{

    private EonaChat plugin = null;

    public EonaListener(EonaChat pl){
        plugin = pl;
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent x){
        if(!x.isCancelled()){
            EonaMessage m = new EonaMessage();
            String test = "";
            for (Player p: x.getRecipients()
                 ) {
                test += p.getName() + ";";
            }
            plugin.getLogger().info("ChatEvent: " + test);
        }
    }


}
