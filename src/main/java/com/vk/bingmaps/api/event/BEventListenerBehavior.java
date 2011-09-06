/*
 * $Id: org.eclipse.jdt.ui.prefs 5004 2006-03-17 20:47:08 -0800 (Fri, 17 Mar 2006) eelco12 $
 * $Revision: 5004 $
 * $Date: 2006-03-17 20:47:08 -0800 (Fri, 17 Mar 2006) $
 * 
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
package com.vk.bingmaps.api.event;

import com.vk.bingmaps.api.BingMap;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;

public abstract class BEventListenerBehavior extends AbstractDefaultAjaxBehavior
{
	private static final long serialVersionUID = 1L;

	@Override
	protected void onBind()
	{
		if (!(getComponent() instanceof BingMap))
		{
			throw new IllegalArgumentException("must be bound to BingMap");
		}
	}

	public String getJSaddListener()
	{
		return getBingMap().getJSinvoke("addListener('" + getEvent() + "', '" + getCallbackUrl() + "')");
	}

	protected final BingMap getBingMap()
	{
		return (BingMap)getComponent();
	}

	/**
	 * @see org.apache.wicket.ajax.AbstractDefaultAjaxBehavior#respond(org.apache.wicket.ajax.AjaxRequestTarget)
	 */
	@Override
	protected final void respond(AjaxRequestTarget target)
	{
		getBingMap().update(target);

		onEvent(target);
	}

	/**
	 * Typically response parameters that are meant for this event are picket up
	 * and made available for the further processing.
	 * 
	 * @param target
	 *            Target to add the Components, that need to be redrawn, to.
	 */
	protected abstract void onEvent(AjaxRequestTarget target);

	/**
	 * See:
     * Event table after <a href="http://msdn.microsoft.com/en-us/library/gg427609.aspx">BingMap</a>
	 * 
	 * @return The name of the BingMap Event that this Listener ought to listen to.
	 */
	protected abstract String getEvent();
}