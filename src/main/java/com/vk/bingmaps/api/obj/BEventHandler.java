package com.vk.bingmaps.api.obj;

import org.apache.wicket.ajax.AjaxRequestTarget;

import java.io.Serializable;

/**
 * @author victor.konopelko
 *         Date: 08.08.11
 */
public abstract class BEventHandler implements Serializable
{
	private static final long serialVersionUID = 1L;

	public abstract void onEvent(AjaxRequestTarget target);
}
