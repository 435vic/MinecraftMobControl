package me.vicoquincis.mobcontrol;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.watchers.BlazeWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.CreeperWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.GhastWatcher;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class ItemManager {
    static int witchIndex = 0;
    static ItemStack[] pots = new ItemStack[4];


    public enum items {
        UNDISGUISE,
        NEAREST_DOCILE,
        NEAREST_HOSTILE,
        NEAREST_CREEPER,
        NEAREST_SKELETON,
        NEAREST_ZOMBIE,
        NEAREST_SPIDER,
        NEAREST_ENDERMAN,
        NEAREST_BLAZE,
        NEAREST_GHAST,
        NEAREST_WITCH,
        NEAREST_ZOMBIE_PIGMAN,
        NEAREST_WITHER_SKELETON,
        ENDERMAN_ATTACK,
        DROWNED_TRIDENT,
        WITCH_REGENERATE,
        WITCH_THROW_POTION,
        CREEPER_EXPLODE,
        SKELETON_BOW,
        PILLAGER_CROSSBOW,
        ENDERMAN_TELEPORT,
        ENDERMAN_FLEE,
        BLAZE_FIREBALL,
        GHAST_FIREBALL,
        WITHER_SWORD,
        COMPASS,
    }

    public static PotionType[] potions = {
        PotionType.WEAKNESS,
        PotionType.SLOWNESS,
        PotionType.INSTANT_DAMAGE,
        PotionType.POISON
    };

    public static ItemStack getItem(items id) {
        Material out = Material.AIR;
        String name = null;
        ItemStack stack;
        ItemMeta m;
        switch (id) {
            case UNDISGUISE:
                out = Material.BARRIER;
                name = "Undisguise";
                break;

            case NEAREST_DOCILE:
                out = Material.FEATHER;
                name = "Control nearest Docile Mob";
                break;

            case NEAREST_HOSTILE:
                out = Material.ROTTEN_FLESH;
                name = "Control nearest Monster";
                break;

            case NEAREST_CREEPER:
                out = Material.CREEPER_SPAWN_EGG;
                name = "Control nearest Creeper";
                break;

            case NEAREST_BLAZE:
                out = Material.BLAZE_SPAWN_EGG;
                name = "Control nearest Blaze";
                break;

            case NEAREST_SKELETON:
                out = Material.SKELETON_SPAWN_EGG;
                name = "Control nearest Skeleton";
                break;

            case NEAREST_SPIDER:
                out = Material.SPIDER_SPAWN_EGG;
                name = "Control nearest Spider";
                break;

            case NEAREST_ZOMBIE:
                out = Material.ZOMBIE_SPAWN_EGG;
                name = "Control nearest Zombie";
                break;

            case NEAREST_ENDERMAN:
                out = Material.ENDERMAN_SPAWN_EGG;
                name = "Control nearest Enderman";
                break;

            case NEAREST_GHAST:
                out = Material.GHAST_SPAWN_EGG;
                name = "Control nearest Ghast";
                break;

            case NEAREST_WITHER_SKELETON:
                out = Material.WITHER_SKELETON_SPAWN_EGG;
                name = "Control nearest Wither Skeleton";
                break;

            case NEAREST_ZOMBIE_PIGMAN:
                out = Material.ZOMBIE_PIGMAN_SPAWN_EGG;
                name = "Control nearest Zombie Pigman";
                break;

            case NEAREST_WITCH:
                out = Material.WITCH_SPAWN_EGG;
                name = "Control nearest Witch";
                break;

            case ENDERMAN_ATTACK:
                out = Material.STICK;
                name = "Attack";
                break;

            case WITCH_THROW_POTION:
                out = Material.SPLASH_POTION;
                name = "Throw a random potion";
                break;

            case WITCH_REGENERATE:
                out = Material.POTION;
                stack = new ItemStack(out);
                PotionMeta meta = (PotionMeta)stack.getItemMeta();
                meta.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL, false, true));
                meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Heal");
                stack.setItemMeta(meta);
                return stack;

            case CREEPER_EXPLODE:
                out = Material.TNT;
                stack = new ItemStack(out);
                stack.addUnsafeEnchantment(Enchantment.RIPTIDE, 1);
                utils.setName(ChatColor.GREEN + "Explode!", stack);
                return stack;

            case DROWNED_TRIDENT:
                out = Material.TRIDENT;
                stack = new ItemStack(out);
                stack.addEnchantment(Enchantment.LOYALTY, 3);
                return stack;

            case SKELETON_BOW:
                out = Material.BOW;
                stack = new ItemStack(out);
                stack.addEnchantment(Enchantment.ARROW_INFINITE, 1);
                stack.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
                return stack;

            case PILLAGER_CROSSBOW:
                out = Material.CROSSBOW;
                stack = new ItemStack(out);
                stack.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
                return stack;

            case ENDERMAN_TELEPORT:
                out = Material.ENDER_PEARL;
                stack = new ItemStack(out);
                stack.addUnsafeEnchantment(Enchantment.RIPTIDE, 1);
                m = stack.getItemMeta();
                m.setDisplayName(ChatColor.LIGHT_PURPLE + "Teleport");
                stack.setItemMeta(m);
                return stack;

            case ENDERMAN_FLEE:
                out = Material.CHORUS_FRUIT;
                stack = new ItemStack(out);
                stack.addUnsafeEnchantment(Enchantment.RIPTIDE, 1);
                m = stack.getItemMeta();
                m.setDisplayName(ChatColor.LIGHT_PURPLE + "Flee");
                stack.setItemMeta(m);
                return stack;

            case BLAZE_FIREBALL:
                out = Material.FIREWORK_STAR;
                stack = new ItemStack(out);
                stack.addUnsafeEnchantment(Enchantment.RIPTIDE, 1);
                m = stack.getItemMeta();
                m.setDisplayName(ChatColor.GOLD + "Fireball!");
                stack.setItemMeta(m);
                return stack;

            case GHAST_FIREBALL:
                out = Material.FIRE_CHARGE;
                stack = new ItemStack(out);
                stack.addUnsafeEnchantment(Enchantment.RIPTIDE, 1);
                m = stack.getItemMeta();
                m.setDisplayName(ChatColor.GOLD + "Fireball!");
                stack.setItemMeta(m);
                return stack;

            case WITHER_SWORD:
                out = Material.STONE_SWORD;
                stack = new ItemStack(out);
                stack.addEnchantment(Enchantment.DURABILITY, 3);
                m = stack.getItemMeta();
                m.setDisplayName(ChatColor.BOLD + "Ancient Sword");
                stack.setItemMeta(m);
                return stack;

            case COMPASS:
                out = Material.COMPASS;
                stack = new ItemStack(out);
                stack.addUnsafeEnchantment(Enchantment.RIPTIDE, 1);
                return stack;

        }
        ItemStack tmp = new ItemStack(out);
        if (name != null) {
            utils.setName(ChatColor.AQUA + name, tmp);
        }
        return tmp;
    }

    public static void setInventory(Player p, boolean disguised) {
        PlayerInventory hotbar = p.getInventory();
        hotbar.clear();
        if (disguised) {
            Disguise dis = DisguiseAPI.getDisguise(p);
            hotbar.setItem(0, getItemForEntity(dis.getType()));
            hotbar.setItem(1, getItem(items.COMPASS));
            hotbar.setItem(8, getItem(items.UNDISGUISE));

            switch (p.getLocation().getWorld().getEnvironment()) {
                case NORMAL:
                    hotbar.setItem(2, getItem(items.NEAREST_HOSTILE));
                    hotbar.setItem(3, getItem(items.NEAREST_ZOMBIE));
                    hotbar.setItem(4, getItem(items.NEAREST_SKELETON));
                    hotbar.setItem(5, getItem(items.NEAREST_SPIDER));
                    hotbar.setItem(6, getItem(items.NEAREST_CREEPER));
                    hotbar.setItem(7, getItem(items.NEAREST_ENDERMAN));
                    break;

                case NETHER:
                    hotbar.setItem(2, getItem(items.NEAREST_ZOMBIE_PIGMAN));
                    hotbar.setItem(3, getItem(items.NEAREST_SKELETON));
                    hotbar.setItem(4, getItem(items.NEAREST_WITHER_SKELETON));
                    hotbar.setItem(5, getItem(items.NEAREST_GHAST));
                    hotbar.setItem(6, getItem(items.NEAREST_BLAZE));
                    hotbar.setItem(7, getItem(items.NEAREST_ENDERMAN));
                    break;

                case THE_END:
                    hotbar.setItem(3, getItem(items.NEAREST_ENDERMAN));
                    break;

            }
            switch (dis.getType()) {
                case SKELETON:
                case PILLAGER:
                case STRAY:
                    p.getInventory().setItem(9, new ItemStack(Material.ARROW, 64));
                    break;
                case ENDERMAN:
                    if (p.getWorld().getEnvironment() != World.Environment.NETHER) {
                        p.getInventory().setItem(1, getItem(items.ENDERMAN_FLEE));
                    }
                    p.getInventory().setItem(2, getItem(items.ENDERMAN_ATTACK));
                    p.getInventory().setItem(3, getItem(items.COMPASS));
                    break;

                case WITCH:
                    hotbar.setItem(1, getItem(items.WITCH_REGENERATE));
                    hotbar.setItem(2, getItem(items.COMPASS));
                    break;
            }
        } else {
            hotbar.setItem(1, getItem(items.COMPASS));
            switch (p.getLocation().getWorld().getEnvironment()) {
                case NORMAL:
                    hotbar.setItem(0, getItem(items.COMPASS));
                    hotbar.setItem(1, getItem(items.NEAREST_HOSTILE));
                    hotbar.setItem(2, getItem(items.NEAREST_DOCILE));
                    hotbar.setItem(3, getItem(items.NEAREST_ZOMBIE));
                    hotbar.setItem(4, getItem(items.NEAREST_SKELETON));
                    hotbar.setItem(5, getItem(items.NEAREST_SPIDER));
                    hotbar.setItem(6, getItem(items.NEAREST_CREEPER));
                    hotbar.setItem(7, getItem(items.NEAREST_WITCH));
                    hotbar.setItem(8, getItem(items.NEAREST_ENDERMAN));
                    break;

                case NETHER:
                    hotbar.setItem(2, getItem(items.NEAREST_HOSTILE));
                    hotbar.setItem(3, getItem(items.NEAREST_BLAZE));
                    hotbar.setItem(4, getItem(items.NEAREST_SKELETON));
                    hotbar.setItem(5, getItem(items.NEAREST_WITHER_SKELETON));
                    hotbar.setItem(6, getItem(items.NEAREST_ZOMBIE_PIGMAN));
                    hotbar.setItem(7, getItem(items.NEAREST_GHAST));
                    hotbar.setItem(8, getItem(items.NEAREST_ENDERMAN));
                    break;

                case THE_END:
                    hotbar.setItem(3, getItem(items.NEAREST_ENDERMAN));
                    break;
            }
        }
    }

    public static ItemStack getItemForEntity(DisguiseType type) {
        ItemStack out = new ItemStack(Material.AIR);
        switch (type) {
            case BLAZE: return getItem(items.BLAZE_FIREBALL);
            case GHAST: return getItem(items.GHAST_FIREBALL);
            case CREEPER: return getItem(items.CREEPER_EXPLODE);
            case WITHER_SKELETON: return getItem(items.WITHER_SWORD);
            case SKELETON:
            case STRAY: return getItem(items.SKELETON_BOW);
            case PILLAGER: return getItem(items.PILLAGER_CROSSBOW);
            case ENDERMAN: return getItem(items.ENDERMAN_TELEPORT);
            case WITCH: return getItem(items.WITCH_THROW_POTION);
            case DROWNED:
                float randf = new Random().nextFloat();
                if (randf < 0.20) {
                    MobControl.control.getController().sendMessage(ChatColor.YELLOW + "Jackpot!" +
                                                                   ChatColor.GREEN + " You got a trident.");
                    return getItem(items.DROWNED_TRIDENT);
                }
        }
        return out;
    }

    public static void processItemUse(ItemStack item) {
        Player p = MobControl.control.getController();
        if (item.isSimilar(getItem(items.UNDISGUISE))) {
            MobControl.control.unDisguise(false);
        }

        else if (item.isSimilar(getItem(items.NEAREST_HOSTILE))) {
            Entity nearest = utils.getNearestMob(p, 60D, utils::isHostile);
            if (nearest != null) {
                MobControl.control.disguise(nearest);
            } else {
                p.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "No monsters nearby!");
            }
        } else if (item.isSimilar(getItem(items.NEAREST_DOCILE))) {
            Entity nearest = utils.getNearestMob(p, 60D, utils::isDocile);
            if (nearest != null) {
                MobControl.control.disguise(nearest);
            } else {
                p.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "No creatures nearby!");
            }
        }
        double range = 60D;
        if (p.getWorld().getEnvironment() == World.Environment.NETHER) {
            range = 120D;
        }

        if (item.isSimilar(getItem(items.NEAREST_ZOMBIE)))
            utils.disguiseAsNearestMob(p, range, EntityType.ZOMBIE);
        else if (item.isSimilar(getItem(items.NEAREST_CREEPER)))
            utils.disguiseAsNearestMob(p, range, EntityType.CREEPER);
        else if (item.isSimilar(getItem(items.NEAREST_SKELETON)))
            utils.disguiseAsNearestMob(p, range, EntityType.SKELETON);
        else if (item.isSimilar(getItem(items.NEAREST_SPIDER)))
            utils.disguiseAsNearestMob(p, range, EntityType.SPIDER);
        else if (item.isSimilar(getItem(items.NEAREST_ENDERMAN)))
            utils.disguiseAsNearestMob(p, range, EntityType.ENDERMAN);
        else if (item.isSimilar(getItem(items.NEAREST_ZOMBIE_PIGMAN)))
            utils.disguiseAsNearestMob(p, range, EntityType.PIG_ZOMBIE);
        else if (item.isSimilar(getItem(items.NEAREST_BLAZE)))
            utils.disguiseAsNearestMob(p, range, EntityType.BLAZE);
        else if (item.isSimilar(getItem(items.NEAREST_WITHER_SKELETON)))
            utils.disguiseAsNearestMob(p, range, EntityType.WITHER_SKELETON);
        else if (item.isSimilar(getItem(items.NEAREST_GHAST)))
            utils.disguiseAsNearestMob(p, range, EntityType.GHAST);
        else if (item.isSimilar(getItem(items.NEAREST_WITCH)))
            utils.disguiseAsNearestMob(p, range, EntityType.WITCH);


        else if (item.isSimilar(getItem(items.ENDERMAN_FLEE)) &&
            p.getCooldown(getItem(items.ENDERMAN_FLEE).getType()) == 0) {

            utils.randomTeleport(p);
            p.setCooldown(
                getItem(items.ENDERMAN_FLEE).getType(),
                150
            );
        } else if (item.isSimilar(getItem(items.ENDERMAN_TELEPORT)) &&
                   p.getCooldown(getItem(items.ENDERMAN_TELEPORT).getType()) == 0) {

            Block bl = p.getTargetBlockExact(180);
            Location newLoc = p.getLocation();
            if (bl != null) {
                newLoc = bl.getLocation();
            }
            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 2.0F, 1.0F);
            p.teleport(newLoc);
            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 2.0F, 1.0F);

            p.setCooldown(
                    getItem(items.ENDERMAN_TELEPORT).getType(),
                    300
            );
        } else if (item.isSimilar(getItem(items.CREEPER_EXPLODE)) &&
                   p.getCooldown(getItem(items.CREEPER_EXPLODE).getType()) == 0) {
            ((CreeperWatcher) DisguiseAPI.getDisguise(p).getWatcher()).setIgnited(true);
            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_CREEPER_PRIMED, 2.0F, 1.0F);
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 30, 6, true, false, false));
            (new BukkitRunnable() {
                public void run() {
                    MobControl.control.unDisguise(true);
                    p.getWorld().createExplosion(p.getLocation(), 3.0F);
                }
            }).runTaskLater(MobControl.getInstance(), 30L);
            p.setCooldown(getItem(items.CREEPER_EXPLODE).getType(), 200);
        } else if (item.isSimilar(getItem(items.BLAZE_FIREBALL)) &&
                   p.getCooldown(getItem(items.BLAZE_FIREBALL).getType()) == 0) {

            ((BlazeWatcher) DisguiseAPI.getDisguise(p).getWatcher()).setBlazing(true);
            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 2.0F, 1.0F);
            (new BukkitRunnable() {
                int i = 3;
                public void run() {
                    if (this.i == 0) {
                        cancel();
                        return;
                    }
                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 3.0F, 1.0F);
                    p.getWorld().spawnEntity(p.getEyeLocation(), EntityType.SMALL_FIREBALL);
                    i--;
                }
            }).runTaskTimer(MobControl.getInstance(), 40L, 7L);
            p.setCooldown(getItem(items.BLAZE_FIREBALL).getType(), 100);
        } else if (item.isSimilar(getItem(items.GHAST_FIREBALL)) &&
                   p.getCooldown(getItem(items.GHAST_FIREBALL).getType()) == 0) {

            ((GhastWatcher) DisguiseAPI.getDisguise(p).getWatcher()).setAggressive(true);
            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GHAST_WARN, 2.0F, 1.0F);
            p.getWorld().spawnEntity(p.getEyeLocation(), EntityType.FIREBALL);
            p.setCooldown(getItem(items.GHAST_FIREBALL).getType(), 60);
            (new BukkitRunnable() {
                @Override
                public void run() {
                    ((GhastWatcher) DisguiseAPI.getDisguise(p).getWatcher()).setAggressive(false);
                }
            }).runTaskLater(MobControl.getInstance(), 10);
        } else if (item.isSimilar(getItem(items.WITCH_THROW_POTION)) &&
                   p.getCooldown(getItem(items.WITCH_THROW_POTION).getType()) == 0) {

            // If 4 types are thrown we have to regenerate a new list
            if (witchIndex == 0) {
                Random rng = new Random();
                PotionType[] types = potions.clone();
                boolean extended;
                boolean upgraded;
                utils.shuffleArray(types);
                for (int i = 0; i < 4; i++) {
                    extended = false;
                    upgraded = false;
                    ItemStack stack = new ItemStack(Material.SPLASH_POTION);
                    PotionMeta m = (PotionMeta) stack.getItemMeta();
                    float rand = rng.nextFloat();
                    if (rand < 0.05) extended = types[i].isExtendable();
                    else if (rand > 0.95) upgraded = types[i].isUpgradeable();
                    m.setBasePotionData(new PotionData(types[i], extended, upgraded));
                    stack.setItemMeta(m);
                    pots[i] = stack;
                }
            }

            ThrownPotion potion =  (ThrownPotion) p.getWorld().spawnEntity(p.getEyeLocation(), EntityType.SPLASH_POTION);
            potion.setItem(pots[witchIndex]);
            potion.setVelocity(p.getEyeLocation().getDirection().multiply(0.75));
            witchIndex++;
            if (witchIndex > 3) witchIndex = 0;

            p.setCooldown(getItem(items.WITCH_THROW_POTION).getType(), 50);
        } else if (item.isSimilar(getItem(items.COMPASS))) {
            if (Bukkit.getOnlinePlayers().size() < 2) {
                p.sendMessage(ChatColor.RED + "No players to track");
                return;
            }

            Player closest = p;
            Location pLoc = p.getLocation();
            for (Player pl : Bukkit.getOnlinePlayers()) {
                if (pl == p) continue;
                Location oldLoc = closest.getLocation();
                Location entLoc = pl.getLocation();
                if (pLoc.distance(entLoc) < pLoc.distance(oldLoc) || closest == p) {
                    closest = pl;
                }
            }
            p.sendMessage(ChatColor.AQUA + "Currently tracking " + closest.getName());
            MobControl.control.isTracking = true;
            MobControl.control.tracking = closest;
        }
    }
}
