package com.vicoquincis.mobcontrol;

import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.EndermanWatcher;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;
import java.util.function.Predicate;

public class utils {
    // Hostile mobs can attack the player
    public static EntityType[] hostileMobs = {
        EntityType.SKELETON,
        EntityType.STRAY,
        EntityType.ZOMBIE,
        EntityType.HUSK,
        EntityType.CREEPER,
        EntityType.SPIDER,
        EntityType.CAVE_SPIDER,
        EntityType.ZOMBIE_VILLAGER,
        EntityType.WITCH,
        EntityType.PILLAGER,
        EntityType.ENDERMAN,
        EntityType.BLAZE,
        EntityType.PIG_ZOMBIE,
        EntityType.GHAST,
        EntityType.WITHER_SKELETON,
        EntityType.IRON_GOLEM,
        EntityType.DROWNED
    };

    // Docile mobs only move around
    public static EntityType[] docileMobs = {
        EntityType.COW,
        EntityType.PIG,
        EntityType.SHEEP,
        EntityType.HORSE,
        EntityType.DONKEY,
        EntityType.CHICKEN,
        EntityType.MULE,
        EntityType.SKELETON_HORSE,
        EntityType.WOLF,
        EntityType.FOX
    };

    // Undead mobs burn in the sun
    public static DisguiseType[] undeadMobs = {
        DisguiseType.SKELETON,
        DisguiseType.ZOMBIE,
        DisguiseType.STRAY,
        DisguiseType.DROWNED
    };

    public static EntityType[] flyingMobs = {
        EntityType.BLAZE,
        EntityType.PHANTOM,
        EntityType.GHAST,
        EntityType.BAT
    };

    public static EntityType[] waterMobs = {
        EntityType.SQUID,
        EntityType.DROWNED,
        EntityType.COD,
        EntityType.PUFFERFISH,
        EntityType.TROPICAL_FISH,
        EntityType.DOLPHIN
    };

    // Once the player disguises as an entity,
    // the following attributes will be applied to
    // them, like health, speed, etc.
    public static Attribute[] replicableAttributes = {
        Attribute.GENERIC_MAX_HEALTH,
        Attribute.GENERIC_ATTACK_SPEED,
        Attribute.GENERIC_MOVEMENT_SPEED,
        Attribute.GENERIC_KNOCKBACK_RESISTANCE,
    };

    public static MobDisguise prepareDisguise(Player p, Entity target) {
        DisguiseType type = DisguiseType.getType((target));
        MobDisguise out = new MobDisguise(type, true);
        //TODO:
        // Drowned tridents
        if (type == DisguiseType.ENDERMAN) {
            ((EndermanWatcher) out.getWatcher()).setAggressive(true);
        }
        if (isFlyingMob(target)) {
            p.setAllowFlight(true);
            p.setFlying(true);
        }
        utils.copyAttributes((LivingEntity)target, p);
        return out;
    }

    public static boolean isControllableMob(Entity ent) {
        return isFlyingMob(ent) || isHostile(ent) || isDocile(ent) || isWaterMob(ent);
    }

    public static boolean isFlyingMob(Entity ent) {
        boolean out = false;
        for (EntityType t : flyingMobs) {
            out = out || (ent.getType() == t);
        }
        return out;
    }

    public static boolean isFlyingMob(EntityType ent) {
        boolean out = false;
        for (EntityType t : flyingMobs) {
            out = out || (ent == t);
        }
        return out;
    }


    public static boolean isUndeadMob(DisguiseType ent) {
        boolean out = false;
        for (DisguiseType t : undeadMobs) {
            out = out || (ent == t);
        }
        return out;
    }

    public static boolean isHostile(Entity ent) {
        boolean out = false;
        for (EntityType t : hostileMobs) {
            out = out || (ent.getType() == t);
        }
        return out;
    }

    public static boolean isDocile(Entity ent) {
        boolean out = false;
        for (EntityType t : docileMobs) {
            out = out || (ent.getType() == t);
        }
        return out;
    }

    public static boolean isDocile(EntityType ent) {
        boolean out = false;
        for (EntityType t : docileMobs) {
            out = out || (ent == t);
        }
        return out;
    }

    public static boolean isWaterMob(EntityType ent) {
        boolean out = false;
        for (EntityType t : waterMobs) {
            out = out || (ent == t);
        }
        return out;
    }

    public static boolean isWaterMob(Entity ent) {
        boolean out = false;
        for (EntityType t : waterMobs) {
            out = out || (ent.getType() == t);
        }
        return out;
    }


    public static void setName(String name, ItemStack in) {
        ItemMeta m = in.getItemMeta();
        m.setDisplayName(name);
        in.setItemMeta(m);
    }

    public static void resetHealth(Player p) {
        p.setHealth(
            p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()
        );
    }

    public static void copyAttributes(LivingEntity from, LivingEntity to) {
        for(Attribute a : replicableAttributes) {
            try {
                to.getAttribute(a).setBaseValue(
                        from.getAttribute(a).getValue()
                );
            } catch (NullPointerException npe) {
                continue;
            }
        }
//        System.out.println(to.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        to.setHealth(from.getHealth());
    }

    public static void resetAttributes(LivingEntity target) {
        for (Attribute a : replicableAttributes) {
            try {
                target.getAttribute(a).setBaseValue(
                        target.getAttribute(a).getDefaultValue()
                );
            } catch (NullPointerException e) {
                continue;
            }
        }
    }

    public static void randomTeleport(Player p) {
        Random r = new Random();
        double x,y,z;
        boolean validLocation = false;
        Location target = p.getLocation();
        World w = p.getWorld();
        int counter = 0;
        while((!validLocation) && counter < 64) {
            x = p.getLocation().getX() + (r.nextDouble() - 0.5D) * 64.0D;
            z = p.getLocation().getZ() + (r.nextDouble() - 0.5D) * 64.0D;
            y = p.getWorld().getHighestBlockYAt((int)x, (int)z);
            target = new Location(w, x, y, z);
            validLocation = (
                    !target.getBlock().getType().isSolid() &&
                    !target.getBlock().isLiquid() &&
                    !w.getBlockAt(target.getBlockX(), target.getBlockY()-1, target.getBlockZ()).isLiquid()
            );
            counter++;
        }
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 2.0F, 1.0F);
        p.teleport(target);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 2.0F, 1.0F);
    }

    static void shuffleArray(Object[] ar) {
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            Object a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    public static Entity getNearestMob(Player p, double range, Predicate<Entity> filter) {
        Entity closest = p;
        Location pLoc = p.getLocation();
        for (Entity ent : p.getNearbyEntities(range, range, range)) {
            Location oldLoc = closest.getLocation();
            Location entLoc = ent.getLocation();
            closest = ((closest == p || (entLoc.distance(pLoc) <= oldLoc.distance(pLoc))) && filter.test(ent)) ? ent : closest;
//            System.out.println("[*] " + ent.getName() + " stats: filter? " + filter.test(ent) + ". closest? " + closest.getName());
        }
        return (closest instanceof Player) ? null : closest;
    }

    public static void disguiseAsNearestMob(Player p, double range, EntityType mobType) {
        System.out.println("[*] disguise: " + mobType);
        Predicate<Entity> pr = en -> en.getType() == mobType;
        if (mobType == EntityType.SKELETON)
            pr = en -> (en.getType() == mobType) || (en.getType() == EntityType.STRAY);
        else if (mobType == EntityType.ZOMBIE)
            pr = en -> (en.getType() == mobType) || (en.getType() == EntityType.HUSK);

        Entity nearest = utils.getNearestMob(p, range, pr);
        if (nearest != null) {
            MobControl.control.disguise(nearest);
        } else {
            p.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "No " + mobType.toString().toLowerCase() + "s" + " nearby!");
        }
    }

//    protected boolean eq() {
//        if (!this.world.p_() && isAlive()) {
//            double d0 = locX() + (this.random.nextDouble() - 0.5D) * 64.0D;
//            double d1 = locY() + (this.random.nextInt(64) - 32);
//            double d2 = locZ() + (this.random.nextDouble() - 0.5D) * 64.0D;
//            return o(d0, d1, d2);
//        }
//        return false;
//    }
}
