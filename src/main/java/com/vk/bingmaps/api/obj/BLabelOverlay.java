package com.vk.bingmaps.api.obj;

import java.io.Serializable;

/**
 * @author victor.konopelko
 *         Date: 03.08.11
 * <a href="http://msdn.microsoft.com/en-us/library/gg427602.aspx">LabelOverlay Enumeration</a>.
 */
public enum BLabelOverlay implements Serializable {

    hidden,         //Map labels are not shown on top of imagery.
    visible;        //Map labels are shown on top of imagery.

    private static final String prefix = "Microsoft.Maps.LabelOverlay";

    @Override
    public String toString() {
        return prefix + "." + name();
    }

    public static BLabelOverlay fromString(String str) {
        return BLabelOverlay.valueOf(str.replace(prefix + ".", ""));
    }
}
