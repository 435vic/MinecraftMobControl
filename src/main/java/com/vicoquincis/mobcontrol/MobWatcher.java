package com.vicoquincis.mobcontrol;

import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class MobWatcher extends BukkitRunnable {
    @Override
    public void run() {
        if (MobControl.control.isStarted) {
            Player p = MobControl.control.getController();
            if (p.getFoodLevel() < 8) p.setFoodLevel(8);
            if (MobControl.control.isDisguised) {
                DisguiseType type = MobControl.control.getDisguisedEntityType();
                if (utils.isUndeadMob(type) &&
                        (p.getWorld().getTime() <= 12542 ||
                                p.getWorld().getTime() >= 23460) &&
                        p.getLocation().getBlock().getType() != Material.WATER &&
                        p.getWorld().getHighestBlockYAt(p.getLocation()) <= p.getLocation().getY() &&
                        !p.getWorld().hasStorm() &&
                        !p.getWorld().isThundering()
                ) p.setFireTicks(60);
                else if (type == DisguiseType.ENDERMAN && p.getLocation().getBlock().isLiquid()) {
                    p.damage(2D);
                    utils.randomTeleport(p);
                } else if (utils.isWaterMob(type.getEntityType())) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 30, 3, true, false, false));
                }
                switch (type) {
                    case CREEPER:
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 30, 2, true, false, false));
                        break;

                    case ZOMBIE:
                    case HUSK:
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 30, 1, true, false, false));
                        break;

                    case IRON_GOLEM:
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 30, 3, true, false, false));
                        break;

                    case BLAZE:
                    case PIG_ZOMBIE:
                    case WITHER_SKELETON:
                    case GHAST:
                        p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 6000, 9, true, false, false));
                        p.setFireTicks(0);
                        break;

                    case DOLPHIN:
                        p.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 30, 3, true, false, false));
                        break;
                }
            } else p.setFireTicks(0);
        }
    }
}
