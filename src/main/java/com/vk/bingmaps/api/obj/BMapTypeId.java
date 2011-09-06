package com.vk.bingmaps.api.obj;

import com.vk.bingmaps.api.BingMap;

import java.io.Serializable;

/**
 * @author victor.konopelko
 *         Date: 03.08.11
 * <a href="http://msdn.microsoft.com/en-us/library/gg427625.aspx">MapTypeId Enumeration</a>.
 */
public enum BMapTypeId implements Serializable {

    aerial,                 //The aerial map style is being used.
    auto,                   //The map is set to choose the best imagery for the current view.
    birdseye,               //The bird’s eye map type is being used.
    collinsBart,            //Collin’s Bart (mkt=en-gb) map type is being used.
    mercator,               //The Mercator style is being used.
    ordnanceSurvey,         //Ordnance Survey (mkt=en-gb) map type is being used.
    road;                   //The road map style is being used

    private static final String prefix = "Microsoft.Maps.MapTypeId";

	public String getJSsetMapType(BingMap map)
	{
		return map.getJSinvoke("setMapType(" + toString() + ")");
	}

    @Override
    public String toString() {
        return prefix + "." + name();
    }

    public static BMapTypeId fromString(String str) {
        return BMapTypeId.valueOf(str.replace(prefix + ".", ""));
    }
}
