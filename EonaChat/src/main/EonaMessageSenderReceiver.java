package main;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;


/**
 * Created by Bloodrayne on 02.02.2016.
 */
public class EonaMessageSenderReceiver implements PluginMessageListener{

    private EonaChat plugin = null;

    public EonaMessageSenderReceiver(EonaChat pl){
        this.plugin = pl;
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin,"BungeeCord");
        plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, "BungeeCord", this);
    }


    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if(!channel.equalsIgnoreCase("bungeecord")){
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
        String subchannel = in.readUTF();
        if(subchannel.equalsIgnoreCase("eonachat")){

            String from = in.readUTF();
            String message = in.readUTF();

            EonaMessage msg = new EonaMessage();
            msg.setMessage(message);
            msg.setFrom(from);
            String s = in.readUTF();
            while(s != null){
                msg.getTo().add(s);
                s = in.readUTF();
            }




            //TODO: Handle incoming messages
            handleIncomingMessages(msg);
        }
    }


    public void handleOutGoingMessages(EonaMessage m){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("eonachat");
        out.writeUTF(m.getFrom());
        out.writeUTF(m.getMessage());
        //TODO: TO
        
    }


    public void handleIncomingMessages(EonaMessage m){

    }
}
