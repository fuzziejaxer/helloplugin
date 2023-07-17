package me.fuzzie.helloplugin;

import me.fuzzie.helloplugin.hello.hello;
import me.fuzzie.helloplugin.hello.hello_1_16_R3;
import me.fuzzie.helloplugin.hello.hello_1_20_R1;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Helloplugin extends JavaPlugin implements Listener {

    private hello Hello;

    @Override
    public void onEnable() {
        if (setupHello()) {

            Bukkit.getPluginManager().registerEvents(this, this);

            getLogger().info("Actionbar setup was successful!");
            getLogger().info("The plugin setup process is complete!");

        } else {

            getLogger().severe("Failed to setup Actionbar!");
            getLogger().severe("Your server version is not compatible with this plugin!");

            Bukkit.getPluginManager().disablePlugin(this);
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private boolean setupHello() {

        String version;

        try {

            version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

        } catch (ArrayIndexOutOfBoundsException whatVersionAreYouUsingException) {
            return false;
        }

        getLogger().info("Your server is running version " + version);

        if (version.equals("v1_16_R3")) {
            //server is running 1.8-1.8.1 so we need to use the 1.8 R1 NMS class
            Hello = new hello_1_16_R3();

        } else if (version.equals("v1_20_R1")) {
            //server is running 1.8.3 so we need to use the 1.8 R2 NMS class
            Hello = new hello_1_20_R1();
        }
        // This will return true if the server version was compatible with one of our NMS classes
        // because if it is, our actionbar would not be null
        return Hello != null;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Hello.sendActionbar(event.getPlayer(), "Welcome to the server!");
    }
}
