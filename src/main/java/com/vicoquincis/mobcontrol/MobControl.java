package com.vicoquincis.mobcontrol;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class MobControl extends JavaPlugin {

    public static ControlManager control;

    private static Plugin plug;

    @Override
    public void onEnable() {
        plug = this;
        getServer().getPluginManager().registerEvents(new Listeners(), this);
        // The control manager has useful methods to manage the player that is
        // controlling mobs
        control = new ControlManager((Plugin)this);
        this.getCommand("mobcontrol").setExecutor(new CommandHandler());
        this.getCommand("mobcontrol").setTabCompleter(new CommandHandler());
        (new MobWatcher()).runTaskTimer(this, 0, 2);
    }

    public static Plugin getInstance() {
        return plug;
    }
}
