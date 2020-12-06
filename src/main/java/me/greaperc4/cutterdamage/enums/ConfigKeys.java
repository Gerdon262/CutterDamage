package me.greaperc4.cutterdamage.enums;

import me.greaperc4.cutterdamage.interfaces.IConfigEnum;

public enum ConfigKeys implements IConfigEnum {
	CHECK__IN__SECONDS(1),
	DAMAGE(3.0),
    AFFECT__MOBS(false);

    Object o;

    private ConfigKeys(Object o) {
        this.o = o;
    }

    public Object getDefaultValue() {
        return this.o;
    }

    public String getConfigName() {
        return this.name().toLowerCase().replace("__", "-").replace("_", ".");
    }
}
