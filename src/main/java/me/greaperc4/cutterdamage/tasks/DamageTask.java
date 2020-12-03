package me.greaperc4.cutterdamage.tasks;

import me.greaperc4.cutterdamage.CutterDamage;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;

public class DamageTask implements Runnable {
    double damage = 10.0d;

    public DamageTask(CutterDamage plugin) {
        try {
            damage = plugin.getConfig().getDouble("Damage");
        } catch (Exception ignored) {}
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            // Check if the player has the ignore permission
            if (player.hasPermission("cutterdamage.ignore")) {
                return;
            }

            // Check if the player has the correct GameMode Adventure or Survival
            if (!player.getGameMode().equals(GameMode.ADVENTURE) && !player.getGameMode().equals(GameMode.SURVIVAL)) {
                return;
            }

            // Check if the player is standing on a stonecutter
            if (!player.getLocation().getBlock().getType().equals(Material.STONECUTTER)) {
                return;
            }

            player.damage(damage);
            player.setNoDamageTicks(0);
        });
    }
}
