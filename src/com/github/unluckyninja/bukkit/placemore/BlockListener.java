package com.github.unluckyninja.bukkit.placemore;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.material.Bed;
import org.bukkit.material.Directional;

public class BlockListener implements Listener {
    
    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent event) {
        if (event.canBuild()) {
            Player player = event.getPlayer();
            if (Cloning.contains(player)) {
                Block[] blocks = Cloning.copy(player, event.getBlockPlaced());
                if(blocks[0].getState().getData() instanceof Directional){
                    for(int i=0;i<blocks.length;i++){
                        Directional d = (Directional)blocks[i].getState().getData();
                        player.sendMessage(i+1+": "+d.getFacing());
                    }
                }
            }
        }
    }
    
    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
        if (!event.isCancelled()) {
            Player player = event.getPlayer();
            if (Cloning.contains(player)) {
                if (Cloning.isEnabled(player)) {
                    if (event.getBlock().getState().getData() instanceof Bed) {
                        Bed bed = (Bed) event.getBlock().getState().getData();
                        if(bed.isHeadOfBed()){
                            Cloning.delete(player,event.getBlock().getRelative(bed.getFacing().getOppositeFace()));
                            return;
                        }
                    }
                    Cloning.delete(player, event.getBlock());
                }
            }
        }
    }
    
    @EventHandler
    public void onPlayerChangedWorldEvent(PlayerChangedWorldEvent event){
        Player player = event.getPlayer();
        if (Cloning.contains(player)) {
            if (Cloning.setEnabled(player, false)) {
                player.sendMessage(ChatColor.RED+"[PlaceMore]Cloning has been disabled.");
            }
        }
    }
    
    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if (Cloning.contains(player)) {
            Cloning.setEnabled(player, false);
        }
    }
    
    @EventHandler
    public void onBlockDamageEvent(BlockDamageEvent event) {
        if (event.getBlock().getState().getData() instanceof Directional) {
            Directional d = (Directional)event.getBlock().getState().getData();
            event.getPlayer().sendMessage(0 + ": " + d.getFacing());
        }
    }
}
