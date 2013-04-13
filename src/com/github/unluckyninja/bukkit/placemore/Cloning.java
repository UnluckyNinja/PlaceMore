package com.github.unluckyninja.bukkit.placemore;

import com.github.unluckyninja.bukkit.placemore.util.BlockFaceConvertor;
import com.github.unluckyninja.bukkit.placemore.util.LocationConvertor;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.material.*;
import org.bukkit.util.Vector;

/**
 * The base class to repeat a player's behavior.
 * 
 * The process:
 * 1. A player performs the command, creates an instance of this class.
 * 2. To be continued...
 * 
 * @author UnluckyNinja
 * @see BlockFaceConvertor
 * @see LocationConvertor
 */
public class Cloning {

    public static enum Symmetry {

        CENTRAL("Central Symmetry"),
        DOUBLE_CENTRAL("Double Central Symmetry"),
        AXIAL_NS("Axial Symmetry by Nouth-South"),
        AXIAL_WE("Axial Symmetry by West-East"),
        AXIAL_NSWE("Axial Symmetry by Nouth-South and West-East");

        private Symmetry(String string) {
            mode = string;
        }
        private String mode;

        @Override
        public String toString() {
            return mode;
        }
    }
    private static Map<Player, Cloning> clonings = new HashMap<>();
    private Player player;
    private LocationConvertor lc;
    private BlockFaceConvertor bfc;
    private boolean enabled;

    private Cloning(Player player, Symmetry mode) {
        this.player = player;
        reset(mode);
    }

    private void reset(Symmetry mode) {
        Location loc = player.getLocation();
        lc = new LocationConvertor(loc, mode);
        bfc = BlockFaceConvertor.get(mode);
        enabled = true;
    }

    public static Cloning get(Player player, Symmetry mode) {
        if (clonings.containsKey(player)) {
            Cloning c = clonings.get(player);
            c.reset(mode);
            return c;
        } else {
            Cloning c = new Cloning(player, mode);
            clonings.put(player, c);
            return c;
        }
    }

    public static boolean contains(Player player) {
        return clonings.containsKey(player);
    }

    public static Block[] copy(Player player, Block from) {
        if (clonings.containsKey(player)) {
            Cloning c = clonings.get(player);
            if (c.isEnabled()) {
                return c.copy(from);
            }
        }
        return new Block[]{from};
    }

    public Block[] copy(Block from) {
        Block[] blocks;
        switch (bfc.getMode()) {
            case CENTRAL:
                blocks = new Block[2];
                blocks[0] = from;
                blocks[1] = copyInCentral(from);
                return blocks;
            case AXIAL_NS:
                blocks = new Block[2];
                blocks[0] = from;
                blocks[1] = copyInNS(from);
                return blocks;
            case AXIAL_WE:
                blocks = new Block[2];
                blocks[0] = from;
                blocks[1] = copyInWE(from);
                return blocks;
            default:
            case DOUBLE_CENTRAL:
                blocks = new Block[4];
                blocks[0] = from;
                blocks[1] = copyInCentral(from);
                blocks[2] = rotate(from, true);
                blocks[3] = rotate(from, false);
                return blocks;
            case AXIAL_NSWE:
                blocks = new Block[4];
                blocks[0] = from;
                blocks[1] = copyInCentral(from);
                blocks[2] = copyInNS(from);
                blocks[3] = copyInWE(from);
                return blocks;
        }
    }

    //<editor-fold defaultstate="collapsed" desc="noted code">
    private Block copyInCentral(Block from) {
        return convert(from, Symmetry.CENTRAL);
    }

    private Block copyInNS(Block from) {
        return convert(from, Symmetry.AXIAL_NS);

    }

    private Block copyInWE(Block from) {
        return convert(from, Symmetry.AXIAL_WE);
    }
    //</editor-fold>

    @Deprecated
    public Block convert(Block from) {
        //<editor-fold defaultstate="collapsed" desc="noted code">
        //        if (from.getType() == Material.AIR) {
        //            Block to = getSymmetricalBlock(from);
        //            to.setTypeId(0);
        //            return to;
        //        } else {
        //            BlockState bs1 = from.getState();
        //            MaterialData md = bs1.getData().clone();
        //            if (md instanceof Directional) {
        //                Directional d = (Directional) md;
        //                if (d instanceof Stairs) {
        //                    Stairs s = (Stairs)d;
        //                    BlockFace bf = s.getDescendingDirection();
        //                    player.sendMessage(ChatColor.YELLOW+"The original face is: "+bf.toString());
        //                    s.setFacingDirection(getOppositeFace(bf));
        //                    player.sendMessage(ChatColor.YELLOW+"The converted face is: "+getOppositeFace(bf).toString());
        //                    d = (Directional)s;
        //                } else {
        //                    d.setFacingDirection(getOppositeFace(d.getFacing()));
        //                }
        //                md = (MaterialData)d;
        //            }
        //            Block to = getSymmetricalBlock(from);
        //            BlockState bs2 = to.getState();
        //            bs2.setType(from.getType());
        //            bs2.setData(md);
        //            bs2.update(true);
        //            return to;
        //        }
        //</editor-fold>
        return convert(from, bfc.getMode());
    }

    private Block convert(Block from, Symmetry mode) {
        if (from.getType() == Material.AIR) {
            Block to = lc.getSymmetricalLocation(from.getLocation(), mode).getBlock();
            to.setTypeId(0);
            return to;
        } else {
            BlockState bs1 = from.getState();
            MaterialData md = bs1.getData().clone();
            if (md instanceof Bed) {
                Bed bed1 = (Bed) md.clone();
                BlockFace bf = BlockFaceConvertor.get4BlockFace(player.getLocation().getDirection());
                if (!bed1.isHeadOfBed()) {
                    Block head = from.getRelative(bf);
                    bed1.setHeadOfBed(true);
                    BlockState headstate = head.getState();
                    headstate.setType(from.getType());
                    headstate.setData((MaterialData) bed1);
                    headstate.update(true);
                    copy(head);
                }
                Bed bed2 = (Bed) md;
                bed2.setFacingDirection(BlockFaceConvertor.getOppositeFace(bf, mode));
            } else if (md instanceof Tree) {
                Tree t = (Tree) md;
                BlockFace bf = t.getDirection();
                t.setDirection(BlockFaceConvertor.getOppositeFace(bf, mode));
            } else if (md instanceof Attachable) {
                Attachable a = (Attachable) md;
                BlockFace bf = BlockFaceConvertor.getOppositeFace(a.getAttachedFace(), Symmetry.CENTRAL);
                if (a instanceof Lever) {
                    Lever l = (Lever) a;
                    if (l.getAttachedFace() == BlockFace.UP || l.getAttachedFace() == BlockFace.DOWN) {
                        bf = BlockFaceConvertor.get4BlockFace(player.getLocation().getDirection());
                    }
                }
                a.setFacingDirection(BlockFaceConvertor.getOppositeFace(bf, mode));
            } else if (md instanceof Directional) {
                Directional d = (Directional) md;
                BlockFace bf;
                if (d instanceof Stairs || d instanceof Diode || d instanceof DirectionalContainer || d instanceof FurnaceAndDispenser || d instanceof Gate) {
                    bf = BlockFaceConvertor.get4BlockFace(player.getLocation().getDirection());
                } else if(d instanceof Door){
                    bf = BlockFaceConvertor.getOppositeFace(BlockFaceConvertor.get4BlockFace(player.getLocation().getDirection()), Symmetry.CENTRAL);
                } else {
                    if (d instanceof PistonBaseMaterial) {
                        Vector vec = toBlock(player.getEyeLocation()).subtract(from.getLocation()).toVector();
                        bf = BlockFaceConvertor.getOppositeFace(BlockFaceConvertor.getPistonBlockFace(vec,player.getEyeLocation().getDirection()), Symmetry.CENTRAL);
//                        player.sendMessage(vec.toString());
                    } else {
                        bf = d.getFacing();
                    }
                }
//                player.sendMessage(ChatColor.YELLOW + "The original face is: " + bf.toString());
                d.setFacingDirection(BlockFace.WEST);
//                d.setFacingDirection(BlockFaceConvertor.getOppositeFace(bf, mode));
//                player.sendMessage(ChatColor.YELLOW + "The converted face is: " + BlockFaceConvertor.getOppositeFace(bf, mode).toString());
            }
            Block to = lc.getSymmetricalLocation(from.getLocation(), mode).getBlock();
            BlockState bs2 = to.getState();
            bs2.setType(from.getType());
            bs2.setData(md);
            bs2.update(true);
//            player.sendMessage(ChatColor.YELLOW +""+ bs2.update(true));
            return to;
        }
    }

    @Deprecated
    public Block rotate(Block from) {
        return rotate(from, true);
    }

    private Block rotate(Block from, boolean clockwise) {
        if (from.getType() == Material.AIR) {
            Block to;
            to = lc.getRotatedLocation(from.getLocation(), clockwise).getBlock();
            to.setTypeId(0);
            return to;
        } else {
            BlockState bs1 = from.getState();
            MaterialData md = bs1.getData().clone();
            if (md instanceof Tree) {
                Tree t = (Tree) md;
                BlockFace bf1 = t.getDirection();
                BlockFace bf2;
                bf2 = BlockFaceConvertor.getRotatedFace(bf1, clockwise);
                t.setDirection(bf2);
            } else if (md instanceof Attachable) {
                Attachable a = (Attachable) md;
                BlockFace bf1 = BlockFaceConvertor.getOppositeFace(a.getAttachedFace(), Symmetry.CENTRAL);
                if (a instanceof Lever) {
                    Lever l = (Lever) a;
                    if (l.getAttachedFace() == BlockFace.UP || l.getAttachedFace() == BlockFace.DOWN) {
                        bf1 = BlockFaceConvertor.get4BlockFace(player.getLocation().getDirection());
                    }
                }
                BlockFace bf2;
                bf2 = BlockFaceConvertor.getRotatedFace(bf1, clockwise);
                a.setFacingDirection(bf2);
            } else if (md instanceof Directional) {
                Directional d = (Directional) md;
                BlockFace bf1;
                if (d instanceof Stairs || d instanceof Diode || d instanceof DirectionalContainer || d instanceof FurnaceAndDispenser || d instanceof Gate) {
                    bf1 = BlockFaceConvertor.get4BlockFace(player.getLocation().getDirection());
                } else if(d instanceof Door){
                    bf1 = BlockFaceConvertor.getOppositeFace(BlockFaceConvertor.get4BlockFace(player.getLocation().getDirection()), Symmetry.CENTRAL);
                } else {
                    if (d instanceof PistonBaseMaterial) {
                        Vector vec = toBlock(player.getEyeLocation()).subtract(from.getLocation()).toVector();
                        bf1 = BlockFaceConvertor.getOppositeFace(BlockFaceConvertor.getPistonBlockFace(vec,player.getEyeLocation().getDirection()), Symmetry.CENTRAL);
//                        player.sendMessage(vec.toString());
                    } else {
                        bf1 = d.getFacing();
                    }
                }
//                player.sendMessage(ChatColor.YELLOW + "The original face is: " + bf1.toString());
                BlockFace bf2;
                bf2 = BlockFaceConvertor.getRotatedFace(bf1, clockwise);
                d.setFacingDirection(bf2);
//                player.sendMessage(ChatColor.YELLOW + "The rotated face is: " + bf2.toString());
            }
            Block to;
            to = lc.getRotatedLocation(from.getLocation(), clockwise).getBlock();
            BlockState bs2 = to.getState();
            bs2.setType(from.getType());
            bs2.setData(md);
            bs2.update(true);
            return to;
        }
    }

    public static Block[] delete(Player player, Block from) {
        if (clonings.containsKey(player)) {
            Cloning c = clonings.get(player);
            if (c.isEnabled()) {
                return c.delete(from);
            }
        }
        return new Block[]{from};
    }

    public Block[] delete(Block from) {
        Block[] blocks;
        switch (bfc.getMode()) {
            case CENTRAL:
            case AXIAL_NS:
            case AXIAL_WE:
                blocks = new Block[2];
                blocks[0] = from;
                blocks[1] = lc.getSymmetricalLocation(from.getLocation()).getBlock();
                blocks[1].setType(Material.AIR);
                return blocks;
            default:
            case DOUBLE_CENTRAL:
                blocks = new Block[4];
                blocks[0] = from;
                blocks[1] = lc.getSymmetricalLocation(from.getLocation()).getBlock();
                blocks[2] = lc.getRotatedLocation(from.getLocation()).getBlock();
                blocks[3] = lc.getInverseRotatedLocation(from.getLocation()).getBlock();
                blocks[1].setType(Material.AIR);
                blocks[2].setType(Material.AIR);
                blocks[3].setType(Material.AIR);
                return blocks;
            case AXIAL_NSWE:
                blocks = new Block[4];
                blocks[0] = from;
                blocks[1] = lc.getSymmetricalLocation(from.getLocation(), Symmetry.CENTRAL).getBlock();
                blocks[2] = lc.getSymmetricalLocation(from.getLocation(), Symmetry.AXIAL_NS).getBlock();
                blocks[3] = lc.getSymmetricalLocation(from.getLocation(), Symmetry.AXIAL_WE).getBlock();
                blocks[1].setType(Material.AIR);
                blocks[2].setType(Material.AIR);
                blocks[3].setType(Material.AIR);
                return blocks;
        }
    }

    public static boolean isEnabled(Player player) {
        if (clonings.containsKey(player)) {
            Cloning c = clonings.get(player);
            return c.isEnabled();
        } else {
            return false;
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    private void setEnabled(boolean bool) {
        enabled = bool;
    }

    public static boolean setEnabled(Player player, boolean bool) {
        if (clonings.containsKey(player)) {
            Cloning c = clonings.get(player);
            c.setEnabled(bool);
            return true;
        } else {
            return false;
        }
    }

    public Symmetry getSymmetry() {
        return bfc.getMode();
    }

    public static Symmetry getSymmetry(String s) {
        switch (s) {
            default:
            case "cc":
            case "dc":
                return Symmetry.DOUBLE_CENTRAL;
            case "c":
                return Symmetry.CENTRAL;
            case "ns":
                return Symmetry.AXIAL_NS;
            case "ew":
            case "we":
                return Symmetry.AXIAL_WE;
            case "nswe":
            case "wens":
                return Symmetry.AXIAL_NSWE;
        }
    }
    
    private Location toBlock(Location loc){
        loc.setX(loc.getBlockX());
        loc.setY(loc.getBlockY());
        loc.setZ(loc.getBlockZ());
        return loc;
    }
}
