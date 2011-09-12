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
 * See "tiledownloadcomplete" in the event section of
 * <a href="http://msdn.microsoft.com/en-us/library/gg427609.aspx">BingMap</a>.
 */
public abstract class TileDownloadCompleteListener extends BEventListenerBehavior {

	@Override
	protected String getEvent() {
		return "tiledownloadcomplete";
	}

	@Override
	public String getJSaddListener() {
		// where notifying immediately (instead of adding a listener) because
		// BingMap will not fire the "tiledownloadcomplete" event when we are finished adding our
		// listeners :(
		return getBingMap().getJSinvoke("onEvent('" + getCallbackUrl() + "', {})");

	}

	@Override
	protected void onEvent(AjaxRequestTarget target) {
		onTileDownloadComplete(target);
	}

	protected abstract void onTileDownloadComplete(AjaxRequestTarget target);
}