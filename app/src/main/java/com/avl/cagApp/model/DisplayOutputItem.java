package com.avl.cagApp.model;

public class DisplayOutputItem {

    private final String displayName;
    private final int imgResId;
    private final int portNumber;

    public DisplayOutputItem(String displayName, int imgResId, int portNumber) {
        this.displayName = displayName;
        this.imgResId = imgResId;
        this.portNumber = portNumber;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getImgResId() {
        return imgResId;
    }

    public int getPortNumber() {
        return portNumber;
    }
}
