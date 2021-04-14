package me.pureaiim.tag.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.pureaiim.tag.main.Tag;
import net.md_5.bungee.api.ChatColor;

public class TagEvents implements Listener {

	Integer setTimer = 5;

	Tag tag;

	public TagEvents(Tag tag) {
		this.tag = tag;
	}

	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (e.getDamager() instanceof Arrow) {
				Arrow arrow = (Arrow) e.getDamager();
				if (arrow.getShooter() instanceof Skeleton) {
					Skeleton skeleton = (Skeleton) arrow.getShooter();
					if (skeleton.getName().equalsIgnoreCase(ChatColor.RED + "TAGGER")) {
						skeleton.remove();
						startBomb(p);
						Bukkit.broadcastMessage(
								tag.getPrefix() + "" + ChatColor.RED + p.getName() + " has been tagged!");
						return;
					}
				}
			} else {
				if (e.getDamager().getName().equalsIgnoreCase(ChatColor.RED + "TAGGER")) {
					e.getDamager().remove();
					startBomb(p);
					Bukkit.broadcastMessage(tag.getPrefix() + "" + ChatColor.RED + p.getName() + " has been tagged!");
					return;
				}
			}
		}
	}

	public void startBomb(Player player) {
		new BukkitRunnable() {
			int bombTime = setTimer;

			@Override
			public void run() {
				if (player.isDead()) {
					Bukkit.broadcastMessage(tag.getPrefix() + ChatColor.RED + player.getName() + " has been killed!");
					this.cancel();
					return;
				}
				if (bombTime == 0) {
					Bukkit.getWorld(player.getWorld().getName()).createExplosion(player.getLocation(), 5F);
					this.cancel();
				} else {
					Bukkit.broadcastMessage(tag.getPrefix() + "" + ChatColor.RED + player.getName()
							+ " will explode in " + bombTime + " seconds!");
					bombTime--;
				}
			}
		}.runTaskTimer(tag, 0, 20);
	}

}
