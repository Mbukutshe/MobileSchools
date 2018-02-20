package com.payghost.mobileschools.Objects;

/**
 * Created by Wiseman on 2018-01-29.
 */

public class Dashboard {
    private String name;
    private int icon;
    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Dashboard(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
