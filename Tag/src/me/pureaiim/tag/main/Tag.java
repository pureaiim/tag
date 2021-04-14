package me.pureaiim.tag.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.pureaiim.tag.commands.TagCommand;
import me.pureaiim.tag.events.TagEvents;
import net.md_5.bungee.api.ChatColor;

public class Tag extends JavaPlugin {

	private String prefix = ChatColor.WHITE + "[" + ChatColor.GRAY + "Tag" + ChatColor.WHITE + "] ";

	public void onEnable() {

		getCommand("tag").setExecutor(new TagCommand(this));
		Bukkit.getPluginManager().registerEvents(new TagEvents(this), this);

	}

	public String getPrefix() {
		return prefix;
	}

}
