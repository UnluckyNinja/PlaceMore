/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.unluckyninja.bukkit.placemore;

import com.github.unluckyninja.bukkit.placemore.Cloning.Symmetry;
import com.github.unluckyninja.bukkit.placemore.util.BlockFaceConvertor;
import org.bukkit.ChatColor;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Administrator
 */
public class PlaceMorecCommand implements CommandExecutor {

    private PlaceMore pm;

    public PlaceMorecCommand(PlaceMore placemore) {
        pm = placemore;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)){
            //sender.sendMessage("[PlaceMore]Console can't use this command.");
        } else if (sender.hasPermission("placemore.allow")) {
            Player player = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("plm")) {
                if (args.length == 0) {
                    if (Cloning.contains(player) && Cloning.isEnabled(player)) {
                        player.sendMessage(ChatColor.RED+"[PlaceMore]Cloning is "+ChatColor.GREEN+"enabled"+ChatColor.RED+" now.");
                    } else {
                        player.sendMessage(ChatColor.RED+"[PlaceMore]Cloning is disabled now.");
                    }
                    return true;
                }
                if (args[0].equalsIgnoreCase("info")) {
                    player.sendMessage(ChatColor.DARK_PURPLE+pm.getDescription().getFullName());
                    player.sendMessage(ChatColor.DARK_PURPLE+pm.getDescription().getDescription());
                    return true;
                }
                if (args[0].equalsIgnoreCase("start")) {
                    Cloning c;
                    if (args.length == 1||args[1].equalsIgnoreCase("cc")||args[1].equalsIgnoreCase("dc")) {
                        c = Cloning.get(player, Cloning.getSymmetry("cc"));
                    } else if(args[1].equalsIgnoreCase("c")) {
                        c = Cloning.get(player, Cloning.getSymmetry("c"));
                    } else if(args[1].equalsIgnoreCase("ns")) {
                        c = Cloning.get(player, Cloning.getSymmetry("ns"));
                    } else if(args[1].equalsIgnoreCase("we")) {
                        c = Cloning.get(player, Cloning.getSymmetry("we"));
                    } else if(args[1].equalsIgnoreCase("nswe")||args[1].equalsIgnoreCase("wens")) {
                        c = Cloning.get(player, Cloning.getSymmetry("nswe"));
                    } else {
                        c = Cloning.get(player, Cloning.getSymmetry("cc"));
                    }
                    player.sendMessage(ChatColor.GREEN+"[PlaceMore]Cloning has been enabled.");
                    player.sendMessage(ChatColor.YELLOW+"[PlaceMore]Cloning's mode is: " + c.getSymmetry().toString());
                    return true;
                }
                if(args[0].equalsIgnoreCase("stop")){
                    if(Cloning.contains(player)){
                        Cloning.setEnabled(player, false);
                    }
                    player.sendMessage(ChatColor.RED+"[PlaceMore]Cloning has been disabled.");
                    return true;
                }
            }
        }
        if (cmd.getName().equalsIgnoreCase("plm")) {
            if (args[0].equalsIgnoreCase("test")) {
                if (args.length == 1 || args[1].equalsIgnoreCase("cc") || args[1].equalsIgnoreCase("dc")) {
                    for (BlockFace face : BlockFace.values()) {
                        sender.sendMessage(face + " is converted to: " + BlockFaceConvertor.getOppositeFace(face, Symmetry.DOUBLE_CENTRAL));
                        sender.sendMessage(face + " is rotated deasil to: " + BlockFaceConvertor.getRotatedFace(face));
                        sender.sendMessage(face + " is rotated widdershins to: " + BlockFaceConvertor.getRotatedFace(face));
                    }
                } else if (args[1].equalsIgnoreCase("c")) {
                    for (BlockFace face : BlockFace.values()) {
                        sender.sendMessage(face + " is converted to: " + BlockFaceConvertor.getOppositeFace(face, Symmetry.CENTRAL));
                    }
                } else if (args[1].equalsIgnoreCase("ns")) {
                    for (BlockFace face : BlockFace.values()) {
                        sender.sendMessage(face + " is converted to: " + BlockFaceConvertor.getOppositeFace(face, Symmetry.AXIAL_NS));
                    }
                } else if (args[1].equalsIgnoreCase("we")) {
                    for (BlockFace face : BlockFace.values()) {
                        sender.sendMessage(face + " is converted to: " + BlockFaceConvertor.getOppositeFace(face, Symmetry.AXIAL_WE));
                    }
                } else if (args[1].equalsIgnoreCase("nswe") || args[1].equalsIgnoreCase("wens")) {
                    for (BlockFace face : BlockFace.values()) {
                        sender.sendMessage(face + " is converted to: " + BlockFaceConvertor.getOppositeFace(face, Symmetry.AXIAL_NSWE));
                    }
                } else {
                    for (BlockFace face : BlockFace.values()) {
                        sender.sendMessage(face + " is converted to: " + BlockFaceConvertor.getOppositeFace(face, Symmetry.DOUBLE_CENTRAL));
                        sender.sendMessage(face + " is rotated deasil to: " + BlockFaceConvertor.getRotatedFace(face));
                    }
                }
                return true;
            }
        }
        return false;
    }
}
