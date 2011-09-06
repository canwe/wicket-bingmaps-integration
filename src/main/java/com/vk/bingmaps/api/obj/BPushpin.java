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
