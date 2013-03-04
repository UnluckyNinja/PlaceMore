package com.github.unluckyninja.bukkit.placemore.util;

import com.github.unluckyninja.bukkit.placemore.Cloning.Symmetry;
import org.bukkit.Location;

public class LocationConvertor {
    
    private final Location center;
    private final Symmetry mode;
    
    public LocationConvertor(Location center, Symmetry mode){
        this.center = new Location(center.getWorld(), center.getBlockX() + 0.5, center.getBlockY() + 0.5, center.getBlockZ() + 0.5);
        this.mode = mode;
    }

    public Location getSymmetricalLocation(Location loc) {
        return getSymmetricalLocation(loc, mode);
    }

    public Location getSymmetricalLocation(Location loc, Symmetry mode) {
        switch (mode) {
            default:
            case CENTRAL:
            case AXIAL_NSWE:
            case DOUBLE_CENTRAL:
                return getCenSymLocation(loc);
            case AXIAL_NS:
                return getNSSymLocation(loc);
            case AXIAL_WE:
                return getWESymLocation(loc);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Get Symmtrical Block">
    public Location getCenSymLocation(Location loc) {
        int y = loc.getBlockY();
        loc.setX(loc.getBlockX() + 0.5);
        loc.setZ(loc.getBlockZ() + 0.5);
        Location point = center.clone().add(center);
        point.subtract(loc);
        point.setY(y + 0.5);
        return point;
    }

    public Location getWESymLocation(Location loc) {
        int y = loc.getBlockY();
        int x = loc.getBlockX();
        loc.setZ(loc.getBlockZ() + 0.5);
        Location point = center.clone().add(center);
        point.subtract(loc);
        point.setY(y + 0.5);
        point.setX(x + 0.5);
        return point;
    }

    public Location getNSSymLocation(Location loc) {
        int y = loc.getBlockY();
        int z = loc.getBlockZ();
        loc.setX(loc.getBlockX() + 0.5);
        Location point = center.clone().add(center);
        point.subtract(loc);
        point.setY(y + 0.5);
        point.setZ(z + 0.5);
        return point;
    }
    //</editor-fold>

    public Location getRotatedLocation(Location loc, boolean clockwise) {
        if(clockwise){
            return getRotatedLocation(loc);
        }else{
            return getInverseRotatedLocation(loc);
        }
    }
    
    public Location getRotatedLocation(Location loc) {
        int y = loc.getBlockY();
        loc.setX(loc.getBlockX() + 0.5);
        loc.setZ(loc.getBlockZ() + 0.5);
        Location point = center.clone();
        point.subtract(loc);
        double x = point.getZ();
        double z = -point.getX();
        point.setX(x);
        point.setZ(z);
        point.add(center);
        point.setY(y + 0.5);
        return point;
    }

    public Location getInverseRotatedLocation(Location loc) {
        int y = loc.getBlockY();
        loc.setX(loc.getBlockX() + 0.5);
        loc.setZ(loc.getBlockZ() + 0.5);
        Location point = center.clone();
        point.subtract(loc);
        double x = -point.getZ();
        double z = point.getX();
        point.setX(x);
        point.setZ(z);
        point.add(center);
        point.setY(y + 0.5);
        return point;
    }
    
}
