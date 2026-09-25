package com.avl.cagApp.model;

public class ControlSwitchItem {
    private final String displayName;
    private boolean state;
    private final int portNumber;

    public ControlSwitchItem(String displayName, boolean state, int portNumber ) {
        this.displayName = displayName;
        this.state = state;
        this.portNumber = portNumber;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean getState() {
        return state;
    }
    public void setState(boolean state) {
        this.state = state;
    }
    public int getPortNumber() {
        return portNumber;
    }
}
