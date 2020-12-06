package me.greaperc4.cutterdamage.tasks;

import me.greaperc4.cutterdamage.CutterDamage;
import me.greaperc4.cutterdamage.enums.ConfigKeys;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class DamageTask implements Runnable {
    double damage;
    boolean mobsAffected;

    public DamageTask(CutterDamage plugin) {
        damage = plugin.config.getDouble(ConfigKeys.DAMAGE);
        mobsAffected = plugin.config.getBoolean(ConfigKeys.AFFECT__MOBS);
    }

    @Override
    public void run() {
        if (mobsAffected) {
            Bukkit.getWorlds().forEach(world -> {
                world.getLivingEntities().forEach(entity -> {
                    // Check if the entity is a player and should receive the damage
                    if (entity instanceof Player && !checkPlayer((Player) entity)) {
                        return;
                    }

                    damageEntity(entity);
                });
            });
        } else {
            Bukkit.getOnlinePlayers().forEach(player -> {
                // Check if the player should receive the damage
                if (!checkPlayer(player)) {
                    return;
                }

                damageEntity(player);
            });
        }
    }

    private void damageEntity(LivingEntity entity) {
        if (checkBlock(entity)) {
            // Set damage to the LivingEntity
            entity.damage(damage);
            entity.setLastDamage(0);
        }
    }

    private boolean checkPlayer(Player player) {
        // Check if the player has the ignore permission
        if (player.hasPermission("cutterdamage.ignore")) {
            return false;
        }

        // Check if the player has the correct GameMode Adventure or Survival
        if (!player.getGameMode().equals(GameMode.ADVENTURE) && !player.getGameMode().equals(GameMode.SURVIVAL)) {
            return false;
        }

        return true;
    }

    private boolean checkBlock(LivingEntity entity) {
        // Check if the player is standing on a stonecutter
        return entity.getLocation().getBlock().getType().equals(Material.STONECUTTER);
    }
}
