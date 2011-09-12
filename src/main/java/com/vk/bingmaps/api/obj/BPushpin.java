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

import com.vk.bingmaps.api.js.Constructor;
import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.string.Strings;

/**
 * @author victor.konopelko
 *         Date: 08.08.11
 */
public class BPushpin extends BOverlay {

    private BLocation       location;
    private BPushpinOptions options;

    public BPushpin(BLocation location) {
        this.location = location;
    }

    public BPushpin(BLocation location, BPushpinOptions options) {
        this.location = location;
        this.options = options;
    }

    @Override
    public String getJSconstructor() {
        Constructor c = new Constructor("Microsoft.Maps.Pushpin").add(location.getJSconstructor());
        if (options != null) {
            c.add(options.getJSconstructor());
        }
        return c.toJS();
    }

    /**
	 * Set the options.
	 *
	 * @param options options to set
	 */
	public void setOptions(BPushpinOptions options)
	{
		if (!options.equals(this.options))
		{
			this.options = options;

			if (AjaxRequestTarget.get() != null)
			{
				AjaxRequestTarget.get().appendJavascript(getParent().getJSsetPushpinOptions(this, options));
			}
		}
	}

    public BPushpinOptions getOptions() {
        return null == options ? null : options.clone();
    }

    public BLocation getLocation() {
        return location;
    }

    /**
	 * Set the location.
	 *
	 * @param location location to set
	 */
	public void setLocation(BLocation location)
	{
		if (!location.equals(this.location))
		{
			this.location = location;

			if (AjaxRequestTarget.get() != null)
			{
				AjaxRequestTarget.get().appendJavascript(getParent().getJSsetPushpinLocation(this, location));
			}
		}
	}

    @Override
    protected void updateOnAjaxCall(AjaxRequestTarget target, BEvent overlayEvent) {
        Request request = RequestCycle.get().getRequest();
        String loc = request.getParameter("overlay.location");
        if (!Strings.isEmpty(loc)) {
		    this.location = BLocation.parse(loc);
        } else {
            //TODO warn
        }
    }
}
