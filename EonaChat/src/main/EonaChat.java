package main;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main Class
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
    }

    @Override
    public void onDisable() {
        emsr = null;
        eLsnr = null;
        getLogger().info("EonaChat deaktiviert");
    }

    public EonaMessageSenderReceiver getEmsr() {
        return emsr;
    }

    public EonaListener geteLsnr() {
        return eLsnr;
    }
}
