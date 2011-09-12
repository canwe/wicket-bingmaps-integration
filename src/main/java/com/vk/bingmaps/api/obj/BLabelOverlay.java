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
