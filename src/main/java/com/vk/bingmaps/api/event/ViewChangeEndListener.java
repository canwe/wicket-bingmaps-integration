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
package com.vk.bingmaps.api.event;

import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * See "viewchangeend" in the event section of <a
 * href="http://msdn.microsoft.com/en-us/library/gg427609.aspx">BingMap</a>.
 */
public abstract class ViewChangeEndListener extends BEventListenerBehavior
{

	@Override
	protected String getEvent() {
		return "viewchangeend";
	}

	@Override
	protected void onEvent(AjaxRequestTarget target)
	{
		onViewChangeEnd(target);
	}


	/**
	 * Override this method to provide handling of a viewchangeend.<br>
	 * You can get the new center coordinates of the map by calling
	 * {@link com.vk.bingmaps.api.BingMap#getCenter()}.
	 * 
	 * @param target
	 *            the target that initiated the move
	 */
	protected abstract void onViewChangeEnd(AjaxRequestTarget target);
}