package com.vicoquincis.mobcontrol;

import com.google.common.collect.Lists;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class CommandHandler implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 2) {
            if (args[0].equals("add")) {
                Player p = Bukkit.getServer().getPlayer(args[1]);
                if (p != null) {
                    MobControl.control.setController(p);
                    p.sendMessage(ChatColor.GREEN + "You can now control mobs! Right click an entity or select an option in your hotbar.");
                    return true;
                }
            } else if (args[0].equals("remove")) {
                Player p = Bukkit.getServer().getPlayer(args[1]);
                if (p != null) {
                    if (MobControl.control.isController(p)) {
                        MobControl.control.unSetController();
                        p.sendMessage(ChatColor.GREEN + "You have lost the ability to control mobs.");
                    } else sender.sendMessage("Cannot remove player; not added as mob controller");
                    return true;
                }
            } else if (args[0].equals("show")) {
                if (MobControl.control.isStarted && MobControl.control.isDisguised) {
                    ((MobDisguise)DisguiseAPI.getDisguise(MobControl.control.getController())).setViewSelfDisguise(true);
                }
            } else if (args[0].equals("hide")) {
                if (MobControl.control.isStarted && MobControl.control.isDisguised) {
                    ((MobDisguise)DisguiseAPI.getDisguise(MobControl.control.getController())).setViewSelfDisguise(false);
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        String[] l = {};
        if (args.length == 0 || args.length == 1) {
            l = new String[]{"add", "remove"};
        } else if (args.length == 2) {
            List<Player> c = (List<Player>)Bukkit.getServer().getOnlinePlayers();
            return Lists.transform(c, Player::getName);
        }
        return Arrays.asList(l);
    }
}
