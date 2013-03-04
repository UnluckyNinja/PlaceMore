/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.unluckyninja.bukkit.placemore;

import java.util.logging.Level;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Administrator
 */
public class PlaceMore extends JavaPlugin{

    @Override
    public void onEnable(){
        getServer().getPluginManager().registerEvents(new BlockListener(), this);
        getCommand("plm").setExecutor(new PlaceMorecCommand(this));
        getLogger().log(Level.INFO, "PlaceMore v{0} has been enabled.", this.getDescription().getVersion());
    }
    
    @Override
    public void onDisable(){
        getLogger().log(Level.INFO, "PlaceMore v{0} has been Disabled.", this.getDescription().getVersion());
    }
}
