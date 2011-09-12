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

import com.vk.bingmaps.api.obj.BLocation;
import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.string.Strings;

/**
 * See "click" in the event section of <a
 * href="http://msdn.microsoft.com/en-us/library/gg427609.aspx">BingMap</a>.
 */
public abstract class ClickListener extends BEventListenerBehavior {

	@Override
	protected String getEvent() {
		return "click";
	}

	@Override
	protected void onEvent(AjaxRequestTarget target) {

        Request request = RequestCycle.get().getRequest();

        String s = request.getParameter("location");
        if (!Strings.isEmpty(s)) {
            BLocation loc = BLocation.parse(s);

	        onClick(target, loc);
        } else {
            //TODO warn
        }
	}

	protected abstract void onClick(AjaxRequestTarget target, BLocation loc);
}
