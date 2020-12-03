package me.greaperc4.cutterdamage;

import me.greaperc4.cutterdamage.tasks.DamageTask;
import me.greaperc4.cutterdamage.utils.Version;
import org.bukkit.plugin.java.JavaPlugin;

public final class CutterDamage extends JavaPlugin {

    @Override
    public void onEnable() {
        Version.load();

        if (!Version.atLeast(Version.v1_14)) {
            this.disablePlugin();
        }

        saveDefaultConfig();

        try {
            getServer().getScheduler().runTaskTimer(this, new DamageTask(this), 20, getConfig().getInt("CheckInSeconds") * 20);
        } catch (Exception ignored) {
            this.disablePlugin();
        }
    }

    private void disablePlugin() {
        getServer().getPluginManager().disablePlugin(this);
    }
}
