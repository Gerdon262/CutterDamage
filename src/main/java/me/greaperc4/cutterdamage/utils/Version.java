package me.greaperc4.cutterdamage.utils;

import org.bukkit.Bukkit;

public enum Version {
    UNKNOWN(0),
    v1_10(10),
    v1_11(11),
    v1_12(12),
    v1_13(13),
    v1_14(14),
    v1_15(15),
    v1_16(16);

    private static Version VERSION = null;
    private final int id;

    Version(int id) {
        this.id = id;
    }

    public static Version get() {
        return VERSION;
    }

    public static void load() {
        if (VERSION == null) {
            //version
            String NAME = Bukkit.getBukkitVersion().split("-", -1)[0];
            int versionId = Integer.parseInt(NAME.split("\\.")[1]);

            for (Version value : values()) {
                if (value.id == versionId) {
                    VERSION = value;
                    break;
                }
            }
        }
    }

    public int getId() {
        return id;
    }

    public String getShortVersionName() {
        return Bukkit.getBukkitVersion().split("-")[0];
    }

    public static boolean equals(int version) {
        return get().id == version;
    }

    public static boolean equals(Version version) {
        return get().id == version.id;
    }

    public static boolean atLeast(int version) {
        return get().id >= version;
    }

    public static boolean atLeast(Version version) {
        return get().id >= version.id;
    }

    public static boolean less(int version) {
        return get().id < version;
    }

    public static boolean less(Version version) {
        return get().id < version.id;
    }
}
