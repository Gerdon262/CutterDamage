package me.greaperc4.cutterdamage;

import me.greaperc4.cutterdamage.config.Config;
import me.greaperc4.cutterdamage.enums.ConfigKeys;
import me.greaperc4.cutterdamage.tasks.DamageTask;
import me.greaperc4.cutterdamage.utils.Version;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public final class CutterDamage extends JavaPlugin {
    Version minimalVersion = Version.v1_14;
    public Config config;

    @Override
    public void onEnable() {
        Version.load();

        if (!Version.atLeast(minimalVersion)) {
            disablePlugin();
            sendError(Arrays.asList(
                String.format("The server is currently running version %s", Version.getTrueVersion(Version.get())),
                String.format("The server has to be at least running version %s", Version.getTrueVersion(minimalVersion)))
            );
        }

        config = new Config(this);

        getServer().getScheduler().runTaskTimer(this, new DamageTask(this), 20, config.getInt(ConfigKeys.CHECK__IN__SECONDS) * 20);
    }

    private void disablePlugin() {
        getServer().getPluginManager().disablePlugin(this);
    }

    public void sendError(List<String> messages) {
        Bukkit.getConsoleSender().sendMessage("==========");
        messages.forEach(message -> Bukkit.getConsoleSender().sendMessage(message));
        Bukkit.getConsoleSender().sendMessage("==========");
    }
}
