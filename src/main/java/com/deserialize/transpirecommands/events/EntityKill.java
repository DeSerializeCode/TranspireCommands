package com.deserialize.transpirecommands.events;

import com.deserialize.transpirecommands.TranspireCommands;
import com.deserialize.transpirecommands.objects.RandomCollection;
import com.deserialize.transpirecommands.objects.Reward;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityKill implements Listener {
    private final TranspireCommands main;

    public EntityKill(TranspireCommands main) {
        this.main = main;
    }

    @EventHandler(
            ignoreCancelled = true
    )
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() instanceof Player) {
            Player player = event.getEntity().getKiller();
            if (!this.main.getRewards().containsKey(event.getEntity().getType().toString())) {
                return;
            }

            double chance = this.main.random();
            if (chance < (double)this.main.getMobChance()) {
                Reward reward = (Reward)((RandomCollection)this.main.getRewards().get(event.getEntity().getType().toString())).next();
                String command = reward.getCommand();
                if (reward.isSendMessage()) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', reward.getMessage()));
                }

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName()));
            }
        }

    }
}