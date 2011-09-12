/*
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
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
