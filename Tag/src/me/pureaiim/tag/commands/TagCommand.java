package me.pureaiim.tag.commands;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

import me.pureaiim.tag.main.Tag;
import net.md_5.bungee.api.ChatColor;

public class TagCommand implements CommandExecutor {

	public ArrayList<Monster> EnemyMobs = new ArrayList<>();
	Tag tag;

	public TagCommand(Tag tag) {
		this.tag = tag;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("tag")) {
			if (args.length == 0) {
				if (!sender.hasPermission("tag.self")) {
					sender.sendMessage(tag.getPrefix() + ChatColor.RED + "You don't have access to this command!");
					return true;
				}
				if (sender instanceof Player) {
					Player p = (Player) sender;
					for (Entity entity : p.getNearbyEntities(100, 3, 100)) {
						if (entity instanceof Monster) {
							Monster monster = (Monster) entity;
							EnemyMobs.add(monster);
						}
					}

					if (EnemyMobs.size() == 0) {
						p.sendMessage(tag.getPrefix() + ChatColor.RED + "There is no monsters near you!");
						return true;
					}

					Monster monster = EnemyMobs.get(new Random().nextInt(EnemyMobs.size()));
					monster.setTarget(p);
					monster.setRemoveWhenFarAway(false);
					monster.setCustomName(ChatColor.RED + "TAGGER");
					monster.setCustomNameVisible(true);
					monster.setInvulnerable(true);

					p.sendMessage(tag.getPrefix() + ChatColor.GREEN + "Your tagger is " + ChatColor.DARK_GREEN
							+ (int) monster.getLocation().distance(p.getLocation()) + "m" + ChatColor.GREEN + " away!");
					EnemyMobs.clear();
					return true;
				}
			} else {
				if (!sender.hasPermission("tag.others")) {
					sender.sendMessage(tag.getPrefix() + ChatColor.RED + "You don't have access to this command!");
					return true;
				}
				Player target = (Player) Bukkit.getPlayer(args[0]);
				if (target == null || !target.isOnline()) {
					sender.sendMessage(tag.getPrefix() + ChatColor.RED + args[0] + " is not online!");
					return true;
				}
				for (Entity entity : target.getNearbyEntities(100, 3, 100)) {
					if (entity instanceof Monster) {
						Monster monster = (Monster) entity;
						EnemyMobs.add(monster);
					}
				}

				if (EnemyMobs.size() == 0) {
					sender.sendMessage(
							tag.getPrefix() + ChatColor.RED + "There is no monsters near " + target.getName() + "!");
					return true;
				}

				Monster monster = EnemyMobs.get(new Random().nextInt(EnemyMobs.size()));
				monster.setTarget(target);
				monster.setRemoveWhenFarAway(false);
				monster.setCustomName(ChatColor.RED + "TAGGER");
				monster.setCustomNameVisible(true);
				monster.setInvulnerable(true);

				target.sendMessage(tag.getPrefix() + ChatColor.GREEN + "Your tagger is " + ChatColor.DARK_GREEN
						+ (int) monster.getLocation().distance(target.getLocation()) + "m" + ChatColor.GREEN
						+ " away!");
				sender.sendMessage(tag.getPrefix() + ChatColor.GREEN + target.getName() + "'s tagger is "
						+ ChatColor.DARK_GREEN + (int) monster.getLocation().distance(target.getLocation()) + "m"
						+ ChatColor.GREEN + " away from them!");
				EnemyMobs.clear();
				return true;
			}
		}
		return true;
	}

}
