
package com.vicoquincis.mobcontrol;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.Disguise;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Listeners implements Listener {
    private boolean flip = true;

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if(flip) {
            if (!MobControl.control.isDisguised && MobControl.control.isStarted) {
                if (event.getRightClicked() instanceof LivingEntity) {
                    LivingEntity target = (LivingEntity) event.getRightClicked();
                    Player p = event.getPlayer();
                    if (utils.isControllableMob(target)) {
                        MobControl.control.disguise(target);
                    }
                }
            }
        }
        flip = !flip;
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e) {
        MobControl.control.unDisguise(true);
        ItemManager.setInventory(MobControl.control.getController(), false);
    }

    @EventHandler
    public void onToggleFlight(PlayerToggleFlightEvent e) {
        if (MobControl.control.isController(e.getPlayer())) {
            if (!e.isFlying()) e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerSprint(PlayerToggleSprintEvent e) {
        if (MobControl.control.isController(e.getPlayer()) && MobControl.control.isDisguised) {
            Disguise d = DisguiseAPI.getDisguise(e.getPlayer());
            if (utils.isFlyingMob(d.getType().getEntityType())) {
                System.out.println("[*] Toggled sprint. Sprinting? " + e.isSprinting());
                if (e.isSprinting()) {
                    e.getPlayer().setFlySpeed(0.04F);
                } else {
                    e.getPlayer().setFlySpeed(0.08F);
                }
            }
        }
    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent e) {
        //TODO: more complicated conditions needed (iron golems kill zombies, wolves attack sheep, etc)
        // Tip: Mobs have a setTarget method
        if (e.getTarget() instanceof Player) {
            if (MobControl.control.isController((Player) e.getTarget())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (MobControl.control.isController(e.getPlayer())) {
            if (e.isBlockInHand()) e.setUseInteractedBlock(Event.Result.DENY);
            Player p = MobControl.control.getController();
            if (e.hasItem()) {
                ItemStack item = e.getItem();
                if (!(item.getType() == Material.BOW ||
                      item.getType() == Material.CROSSBOW ||
                      item.getType() == Material.POTION ||
                      item.getType() == Material.TRIDENT)) {
                    e.setUseItemInHand(Event.Result.DENY);
                } else e.setUseItemInHand(Event.Result.ALLOW);
                ItemManager.processItemUse(item);
            }
        }
    }

    @EventHandler
    public void onPotionConsume(PlayerItemConsumeEvent e) {
        if (MobControl.control.isStarted) {
            if (MobControl.control.isController(e.getPlayer())) {
                Player p = e.getPlayer();
                if (e.getItem().getType() == Material.POTION &&
                    p.getCooldown(ItemManager.getItem(ItemManager.items.WITCH_REGENERATE).getType()) == 0) {

                    System.out.println("Consoomed " + e.getItem().getType());
                    p.setCooldown(ItemManager.getItem(ItemManager.items.WITCH_REGENERATE).getType(), 440);
                    (new BukkitRunnable() {
                        public void run() {
                            p.getInventory().setItem(
                                1, ItemManager.getItem(ItemManager.items.WITCH_REGENERATE)
                            );
                        }
                    }).runTaskLater(MobControl.getInstance(), 6L);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        if (MobControl.control.isController(e.getPlayer())) {
            MobControl.control.unSetController();
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if (MobControl.control.isController((Player)e.getEntity())) {
                Player p = (Player) e.getEntity();
                if (e.getDamage() < p.getHealth()) return;
                e.setCancelled(true);
                utils.resetHealth(p);
                if (MobControl.control.isDisguised) MobControl.control.unDisguise(true);
            }
        }
    }

    @EventHandler
    public void onEntityAttack(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            Player p = (Player)e.getDamager();
            if (MobControl.control.isController((Player) e.getDamager()) && MobControl.control.isDisguised) {
                if (utils.isDocile(DisguiseAPI.getDisguise(p).getType().getEntityType())) {
                    e.setCancelled(true);
                    return;
                }
                switch (DisguiseAPI.getDisguise(p).getType()) {
                    case WITHER_SKELETON:
                        if (p.getCooldown(ItemManager.getItem(ItemManager.items.WITHER_SWORD).getType())  == 0) {
                            ((LivingEntity)e.getEntity()).addPotionEffect(new PotionEffect(
                                    PotionEffectType.WITHER,
                                    200,
                                    1,
                                    true,
                                    true,
                                    true
                            ));
                            p.setCooldown(ItemManager.getItem(ItemManager.items.WITHER_SWORD).getType(), 100);
                        } else {
                            e.setCancelled(true);
                        }

                    case ENDERMAN:
                        if (p.getCooldown(ItemManager.getItem(ItemManager.items.ENDERMAN_ATTACK).getType()) == 0) {
                            double damage = 5.0D;
                            switch (p.getWorld().getDifficulty()) {
                                case NORMAL:
                                    damage = 7.0D;
                                    break;

                                case HARD:
                                    damage = 10.0D;
                                    break;
                            }
                            e.setDamage(damage);
                            p.setCooldown(ItemManager.getItem(ItemManager.items.ENDERMAN_ATTACK).getType(), 35);
                        } else {
                            e.setCancelled(true);
                        }

                    case HUSK:
                    case ZOMBIE:
                        double damage = 2.0D;
                        switch (p.getWorld().getDifficulty()) {
                            case NORMAL:
                                damage = 3.0D;

                            case HARD:
                                damage = 5.0D;
                        }
                }
            } else if (MobControl.control.isController((Player) e.getDamager()) && !MobControl.control.isDisguised) {
                e.setCancelled(true);
                e.setDamage(0.0D);
            }
        }
    }

    @EventHandler
    public void onItemPick(EntityPickupItemEvent e) {
        if (MobControl.control.isController((Player)e.getEntity())) e.setCancelled(true);
    }

    @EventHandler
    public void onBowShoot(EntityShootBowEvent e) {
        if (e.getEntity() instanceof Player) {
            if (MobControl.control.isController((Player)e.getEntity())) {
                Player p = (Player)e.getEntity();
                System.out.println(p.getCooldown(Material.BOW));
                if (p.getCooldown(Material.BOW) != 0) {
                    e.setCancelled(true);
                    return;
                }
                System.out.println("SHOOT");
                p.setCooldown(
                    Material.BOW,
                    30
                );
            }
        }
    }
}