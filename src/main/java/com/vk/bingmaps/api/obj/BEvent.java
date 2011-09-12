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
 *         Date: 08.08.11
 */
public enum BEvent implements Serializable {

	click,
    dblclick,
    dragstart,
    dragend,
    drag,
    entitychanged,
    mousedown,
    mouseout,
    mouseover,
    mouseup,
    rightclick;

	@Override
	public String toString()
	{
		return super.toString().toLowerCase();
	}

	public String getJSadd(BOverlay overlay)
	{
		return overlay.getParent().getJSinvoke(
				"addOverlayListener('" + overlay.getId() + "', '" + name() + "')");
	}

	public String getJSclear(BOverlay overlay)
	{
		return overlay.getParent().getJSinvoke(
				"clearOverlayListeners('" + overlay.getId() + "', '" + name() + "')");
	}

}