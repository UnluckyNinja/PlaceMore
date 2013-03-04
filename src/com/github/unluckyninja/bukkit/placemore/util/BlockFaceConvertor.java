package com.github.unluckyninja.bukkit.placemore.util;

import com.github.unluckyninja.bukkit.placemore.Cloning.Symmetry;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

public class BlockFaceConvertor {

    public static enum BlockFaceVector {
    }
    private static Map<Symmetry, BlockFaceConvertor> map = new HashMap<>();
    private final Symmetry mode;
    private static final Vector EAST = new Vector(1, 0, 0);
    private static final Vector WEST = new Vector(-1, 0, 0);

    private BlockFaceConvertor(Symmetry mode) {
        this.mode = mode;
    }

    public static BlockFaceConvertor get(Symmetry mode) {
        if (map.containsKey(mode)) {
            return map.get(mode);
        } else {
            BlockFaceConvertor bfc = new BlockFaceConvertor(mode);
            map.put(mode, bfc);
            return bfc;
        }
    }

    public BlockFace getOppositeFace(BlockFace face) {
        return getOppositeFace(face, mode);
    }

    public Symmetry getMode() {
        return mode;
    }

    public static BlockFace getOppositeFace(BlockFace face, Symmetry mode) {
        switch (mode) {
            default:
            case CENTRAL:
            case AXIAL_NSWE:
            case DOUBLE_CENTRAL:
                return getCenSymFace(face);
            case AXIAL_NS:
                return getNSSymFace(face);
            case AXIAL_WE:
                return getWESymFace(face);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Get Symmtrical Face">
    public static BlockFace getCenSymFace(BlockFace face) {
        switch (face) {
            default:
            case UP:
            case DOWN:
            case SELF:
                return face;
            case NORTH:
                return BlockFace.SOUTH;
            case SOUTH:
                return BlockFace.NORTH;
            case EAST:
                return BlockFace.WEST;
            case WEST:
                return BlockFace.EAST;
            case NORTH_EAST:
                return BlockFace.SOUTH_WEST;
            case NORTH_WEST:
                return BlockFace.SOUTH_EAST;
            case SOUTH_EAST:
                return BlockFace.NORTH_WEST;
            case SOUTH_WEST:
                return BlockFace.NORTH_EAST;
            case WEST_NORTH_WEST:
                return BlockFace.EAST_SOUTH_EAST;
            case NORTH_NORTH_WEST:
                return BlockFace.SOUTH_SOUTH_EAST;
            case NORTH_NORTH_EAST:
                return BlockFace.SOUTH_SOUTH_WEST;
            case EAST_NORTH_EAST:
                return BlockFace.WEST_SOUTH_WEST;
            case EAST_SOUTH_EAST:
                return BlockFace.WEST_NORTH_WEST;
            case SOUTH_SOUTH_EAST:
                return BlockFace.NORTH_NORTH_WEST;
            case SOUTH_SOUTH_WEST:
                return BlockFace.NORTH_NORTH_EAST;
            case WEST_SOUTH_WEST:
                return BlockFace.EAST_NORTH_EAST;
        }
    }

    public static BlockFace getNSSymFace(BlockFace face) {
        switch (face) {
            default:
            case NORTH:
            case SOUTH:
            case UP:
            case DOWN:
            case SELF:
                return face;
            //<editor-fold defaultstate="collapsed" desc="code">
            case WEST:
                return BlockFace.EAST;
            case EAST:
                return BlockFace.WEST;
            case SOUTH_EAST:
                return BlockFace.SOUTH_WEST;
            case SOUTH_WEST:
                return BlockFace.SOUTH_EAST;
            case NORTH_WEST:
                return BlockFace.NORTH_EAST;
            case NORTH_EAST:
                return BlockFace.NORTH_WEST;
            case SOUTH_SOUTH_EAST:
                return BlockFace.SOUTH_SOUTH_WEST;
            case SOUTH_SOUTH_WEST:
                return BlockFace.SOUTH_SOUTH_EAST;
            case WEST_SOUTH_WEST:
                return BlockFace.EAST_SOUTH_EAST;
            case WEST_NORTH_WEST:
                return BlockFace.EAST_NORTH_EAST;
            case NORTH_NORTH_WEST:
                return BlockFace.NORTH_NORTH_EAST;
            case NORTH_NORTH_EAST:
                return BlockFace.NORTH_NORTH_WEST;
            case EAST_NORTH_EAST:
                return BlockFace.WEST_NORTH_WEST;
            case EAST_SOUTH_EAST:
                return BlockFace.WEST_SOUTH_WEST;
            //</editor-fold>
        }
    }

    public static BlockFace getWESymFace(BlockFace face) {
        switch (face) {
            default:
            case EAST:
            case WEST:
            case UP:
            case DOWN:
            case SELF:
                return face;
            //<editor-fold defaultstate="collapsed" desc="code">
            case SOUTH:
                return BlockFace.NORTH;
            case NORTH:
                return BlockFace.SOUTH;
            case SOUTH_EAST:
                return BlockFace.NORTH_EAST;
            case SOUTH_WEST:
                return BlockFace.NORTH_WEST;
            case NORTH_WEST:
                return BlockFace.SOUTH_WEST;
            case NORTH_EAST:
                return BlockFace.SOUTH_EAST;
            case SOUTH_SOUTH_EAST:
                return BlockFace.NORTH_NORTH_EAST;
            case SOUTH_SOUTH_WEST:
                return BlockFace.NORTH_NORTH_WEST;
            case WEST_SOUTH_WEST:
                return BlockFace.WEST_NORTH_WEST;
            case WEST_NORTH_WEST:
                return BlockFace.WEST_SOUTH_WEST;
            case NORTH_NORTH_WEST:
                return BlockFace.SOUTH_SOUTH_WEST;
            case NORTH_NORTH_EAST:
                return BlockFace.SOUTH_SOUTH_EAST;
            case EAST_NORTH_EAST:
                return BlockFace.EAST_SOUTH_EAST;
            case EAST_SOUTH_EAST:
                return BlockFace.EAST_NORTH_EAST;
            //</editor-fold>
        }
    }
    //</editor-fold>

    public static BlockFace getRotatedFace(BlockFace face, boolean clockwise) {
        if(clockwise){
            return getRotatedFace(face);
        }else{
            return getInverseRotatedFace(face);
        }
    }
    
    /**
     * Rotate the face clockwise by 90 degrees(from above).
     *
     * @param face
     * @return
     */
    public static BlockFace getRotatedFace(BlockFace face) {
        switch (face) {
            case UP:
            case DOWN:
            case SELF:
                return face;
            //<editor-fold defaultstate="collapsed" desc="code">
            case SOUTH:
                return BlockFace.WEST;
            case WEST:
                return BlockFace.NORTH;
            case NORTH:
                return BlockFace.EAST;
            case EAST:
                return BlockFace.SOUTH;
            case SOUTH_EAST:
                return BlockFace.SOUTH_WEST;
            case SOUTH_WEST:
                return BlockFace.NORTH_WEST;
            case NORTH_WEST:
                return BlockFace.NORTH_EAST;
            case NORTH_EAST:
                return BlockFace.SOUTH_EAST;
            case SOUTH_SOUTH_EAST:
                return BlockFace.WEST_SOUTH_WEST;
            case SOUTH_SOUTH_WEST:
                return BlockFace.WEST_NORTH_WEST;
            case WEST_SOUTH_WEST:
                return BlockFace.NORTH_NORTH_WEST;
            case WEST_NORTH_WEST:
                return BlockFace.NORTH_NORTH_EAST;
            case NORTH_NORTH_WEST:
                return BlockFace.EAST_NORTH_EAST;
            case NORTH_NORTH_EAST:
                return BlockFace.EAST_SOUTH_EAST;
            case EAST_NORTH_EAST:
                return BlockFace.SOUTH_SOUTH_EAST;
            case EAST_SOUTH_EAST:
                return BlockFace.SOUTH_SOUTH_WEST;
            //</editor-fold>
        }
        return face.getOppositeFace();
    }

    public static BlockFace getInverseRotatedFace(BlockFace face) {
        switch (face) {
            case UP:
            case DOWN:
            case SELF:
                return face;
            //<editor-fold defaultstate="collapsed" desc="code">
            case SOUTH:
                return BlockFace.EAST;
            case WEST:
                return BlockFace.SOUTH;
            case NORTH:
                return BlockFace.WEST;
            case EAST:
                return BlockFace.NORTH;
            case SOUTH_EAST:
                return BlockFace.NORTH_EAST;
            case SOUTH_WEST:
                return BlockFace.SOUTH_EAST;
            case NORTH_WEST:
                return BlockFace.SOUTH_WEST;
            case NORTH_EAST:
                return BlockFace.NORTH_WEST;
            case SOUTH_SOUTH_EAST:
                return BlockFace.WEST_SOUTH_WEST;
            case SOUTH_SOUTH_WEST:
                return BlockFace.WEST_NORTH_WEST;
            case WEST_SOUTH_WEST:
                return BlockFace.NORTH_NORTH_WEST;
            case WEST_NORTH_WEST:
                return BlockFace.NORTH_NORTH_EAST;
            case NORTH_NORTH_WEST:
                return BlockFace.EAST_NORTH_EAST;
            case NORTH_NORTH_EAST:
                return BlockFace.EAST_NORTH_EAST;
            case EAST_NORTH_EAST:
                return BlockFace.SOUTH_SOUTH_EAST;
            case EAST_SOUTH_EAST:
                return BlockFace.SOUTH_SOUTH_WEST;
            //</editor-fold>
        }
        return face.getOppositeFace();
    }

    public static BlockFace getRelativeFace(Location reference, Location target){
        return get16BlockFace(reference.clone().subtract(target).toVector());
    }
    
    public static BlockFace get16BlockFace(Vector direction) {
        Vector vec = direction.clone();
        double x = vec.getX();
        double y = vec.getY();
        double z = vec.getZ();
        if (x != 0 && z != 0) {
            vec.setY(0);
            float f;
            if (x >= 0) {
                if (x == z) {
                    return BlockFace.SOUTH_EAST;
                } else if (x == -z) {
                    return BlockFace.NORTH_EAST;
                }
                f = vec.angle(EAST);
                if (z >= 0) {
                    if (f < Math.PI / 4) {
                        return BlockFace.EAST_SOUTH_EAST;
                    } else {
                        return BlockFace.SOUTH_SOUTH_EAST;
                    }
                } else {
                    if (f < Math.PI / 4) {
                        return BlockFace.EAST_NORTH_EAST;
                    } else {
                        return BlockFace.NORTH_NORTH_EAST;
                    }
                }
            } else {
                if (x == z) {
                    return BlockFace.NORTH_WEST;
                } else if (x == -z) {
                    return BlockFace.SOUTH_WEST;
                }
                f = vec.angle(WEST);
                if (z >= 0) {
                    if (f < Math.PI / 4) {
                        return BlockFace.WEST_SOUTH_WEST;
                    } else {
                        return BlockFace.SOUTH_SOUTH_WEST;
                    }
                } else {
                    if (f < Math.PI / 4) {
                        return BlockFace.WEST_NORTH_WEST;
                    } else {
                        return BlockFace.NORTH_NORTH_WEST;
                    }
                }
            }
        } else if (x == 0 ^ z == 0) {
            if (x == 0) {
                if (z < 0) {
                    return BlockFace.NORTH;
                } else {
                    return BlockFace.SOUTH;
                }
            } else {
                if (x < 0) {
                    return BlockFace.WEST;
                } else {
                    return BlockFace.EAST;
                }
            }
        } else if (y != 0) {
            if (y < 0) {
                return BlockFace.DOWN;
            } else {
                return BlockFace.UP;
            }
        } else {
            return BlockFace.SELF;
        }
    }

    public static BlockFace get8BlockFace(Vector direction) {
        Vector vec = direction.clone();
        double x = vec.getX();
        double y = vec.getY();
        double z = vec.getZ();
        if (x != 0 && z != 0) {
            if (x >= 0) {
                if (z >= 0) {
                    return BlockFace.SOUTH_EAST;
                } else {
                    return BlockFace.NORTH_EAST;
                }
            } else {
                if (z >= 0) {
                    return BlockFace.SOUTH_WEST;
                } else {
                    return BlockFace.NORTH_WEST;
                }
            }
        } else if (x == 0 ^ z == 0) {
            if (x == 0) {
                if (z < 0) {
                    return BlockFace.NORTH;
                } else {
                    return BlockFace.SOUTH;
                }
            } else {
                if (x < 0) {
                    return BlockFace.WEST;
                } else {
                    return BlockFace.EAST;
                }
            }
        } else if (y != 0) {
            if (y < 0) {
                return BlockFace.DOWN;
            } else {
                return BlockFace.UP;
            }
        } else {
            return BlockFace.SELF;
        }
    }

    public static BlockFace get4BlockFace(Vector direction) {
        Vector vec = direction.clone();
        double x = vec.getX();
        double y = vec.getY();
        double z = vec.getZ();
        if (x != 0 && z != 0) {
            vec.setY(0);
            float f;
            if (x >= 0) {
                if (x == z) {
                    return BlockFace.SOUTH;
                } else if (x == -z) {
                    return BlockFace.EAST;
                }
                f = vec.angle(EAST);
                if (z >= 0) {
                    if (f < Math.PI / 4) {
                        return BlockFace.EAST;
                    } else {
                        return BlockFace.SOUTH;
                    }
                } else {
                    if (f < Math.PI / 4) {
                        return BlockFace.EAST;
                    } else {
                        return BlockFace.NORTH;
                    }
                }
            } else {
                if (x == z) {
                    return BlockFace.NORTH;
                } else if (x == -z) {
                    return BlockFace.WEST;
                }
                f = vec.angle(WEST);
                if (z >= 0) {
                    if (f < Math.PI / 4) {
                        return BlockFace.WEST;
                    } else {
                        return BlockFace.SOUTH;
                    }
                } else {
                    if (f < Math.PI / 4) {
                        return BlockFace.WEST;
                    } else {
                        return BlockFace.NORTH;
                    }
                }
            }
        } else if (x == 0 ^ z == 0) {
            if (x == 0) {
                if (z < 0) {
                    return BlockFace.NORTH;
                } else {
                    return BlockFace.SOUTH;
                }
            } else {
                if (x < 0) {
                    return BlockFace.WEST;
                } else {
                    return BlockFace.EAST;
                }
            }
        } else if (y != 0) {
            if (y < 0) {
                return BlockFace.DOWN;
            } else {
                return BlockFace.UP;
            }
        } else {
            return BlockFace.SELF;
        }
    }
    
    /**
     *
     * @param direction - the two locations' vector.
     * @param player - the direction of player.
     * @return
     */
    public static BlockFace getPistonBlockFace(Vector direction, Vector player) {
        Vector vec = direction.clone();
        int x = vec.getBlockX();
        int y = vec.getBlockY();
        int z = vec.getBlockZ();
        if(y==0||y==1||x>=2||x<-2||z>=2||z<-2){
            return get4BlockFace(player);
        } else {
            if(y<0){
                return BlockFace.DOWN;
            } else{
                return BlockFace.UP;
            }
        }
    }
    
    public Vector getEastVector() {
        return EAST.clone();
    }
    
    public Vector getWestVector() {
        return WEST.clone();
    }
}
