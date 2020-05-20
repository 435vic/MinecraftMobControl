package com.vicoquincis.mobcontrol;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;

public class ControlManager {
    public boolean isStarted = false;
    public boolean isDisguised = false;
    public boolean isTracking = false;
    public Player tracking = null;
    private Player controller = null;
    private Plugin plugin;

    public ControlManager(Plugin pl) {
        this.plugin = pl;
    }

    public void setController(Player p) {
        this.controller = p;
        this.isStarted = true;
        ItemManager.setInventory(p, false);
        this.setInvisible(true);
    }

    public void unSetController() {
        if (this.isStarted) {
            this.isStarted = false;
            this.controller.getInventory().clear();
            if (isDisguised) {
                this.unDisguise(true);
            }
            this.setInvisible(false);
            this.controller = null;
        }
    }

    public Player getController() {
        return this.controller;
    }

    public boolean isController(Player p) {
        return (this.controller == p);
    }

    public void disguise(Entity mob) {
        this.unDisguise(false);
        MobDisguise disguise = utils.prepareDisguise(this.controller, mob);
        disguise.setEntity(this.controller);
        disguise.setViewSelfDisguise(false);
        disguise.setModifyBoundingBox(true);
        disguise.setMobsIgnoreDisguise(false);
        disguise.setHearSelfDisguise(true);

        this.controller.teleport(mob.getLocation());
        mob.remove();
        this.setInvisible(false);
        disguise.startDisguise();
        ItemManager.setInventory(this.controller, true);
        this.isDisguised = true;
    }

    public DisguiseType getDisguisedEntityType() {
        if (this.isDisguised) {
            Disguise disguise = DisguiseAPI.getDisguise(this.controller);
            DisguiseType mob = disguise.getType();
            return mob;
        }
        return null;
    }

    public void unDisguise(boolean died) {
        if (this.isDisguised) {
            Disguise disguise = DisguiseAPI.getDisguise(this.controller);
            this.controller.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
            this.controller.setFlying(false);
            this.controller.setAllowFlight(false);
            this.controller.setFireTicks(0);
            LivingEntity ent = (LivingEntity) this.controller.getWorld().spawnEntity(this.controller.getLocation(), disguise.getType().getEntityType());
            if (died) ent.setHealth(0D);
            else {
                double health = this.controller.getHealth();
                double entmax = ent.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
                ent.setHealth((health > entmax) ? entmax : health);
            }
            utils.resetAttributes(this.controller);
            disguise.stopDisguise();
            this.setInvisible(true);
            disguise.removeDisguise();
            this.controller.sendMessage(ChatColor.GREEN + "You are no longer disguised.");
            utils.resetHealth(this.controller);
            ItemManager.setInventory(this.controller, false);
            this.isDisguised = false;
        }
    }

    public void setInvisible(boolean invisible) {
        if (this.isStarted) {
            for (Player t : Bukkit.getOnlinePlayers()) {
                if (t == this.controller) continue;
                if (invisible) t.hidePlayer(this.plugin, this.controller);
                else t.showPlayer(this.plugin, this.controller);
            }
        }
    }
}
