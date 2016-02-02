package main;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Bloodrayne on 02.02.2016.
 */
public class EonaChat extends JavaPlugin {

    private EonaMessageSenderReceiver emsr = null;
    private EonaListener eLsnr = null;


    @Override
    public void onEnable() {
        emsr = new EonaMessageSenderReceiver(this);
        getLogger().info("EonaMessageSenderReceiver initialisiert");
        eLsnr = new EonaListener(this);
        getLogger().info("EonaChatListener initialisiert");
        super.onEnable();
    }

    @Override
    public void onDisable() {
        emsr = null;
        eLsnr = null;
        getLogger().info("EonaChat deaktiviert");
        super.onDisable();
    }

    public EonaMessageSenderReceiver getEmsr() {
        return emsr;
    }

    public EonaListener geteLsnr() {
        return eLsnr;
    }
}
