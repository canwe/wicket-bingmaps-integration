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