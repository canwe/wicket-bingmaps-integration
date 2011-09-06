package com.vk.bingmaps.api.obj;

import com.vk.bingmaps.api.js.Constructor;
import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.string.Strings;

/**
 * @author victor.konopelko
 *         Date: 11.08.11
 * <a href="http://msdn.microsoft.com/en-us/library/gg675208.aspx">Infobox Class</a>.
 */
public class BInfobox extends BOverlay {

    private BLocation       location;
    private BInfoboxOptions options;

    private Boolean visible;

    public BInfobox(BLocation location) {
        this.location = location;
    }

    public BInfobox(BLocation location, BInfoboxOptions options) {
        this(location);
        this.options = options;
        this.options.setParent(this);
    }

    @Override
    public String getJSconstructor() {
        Constructor c = new Constructor("Microsoft.Maps.Infobox").add(location.getJSconstructor());
        if (options != null) {
            options.setId(getId());
            c.add(options.getJSconstructor());
        } else {
            //set id
            BInfoboxOptions o = new BInfoboxOptions();
            o.setId(getId());
            c.add(o.getJSconstructor());
        }
        return c.toJS();
    }

    /**
	 * Set the options.
	 *
	 * @param options options to set
	 */
	public void setOptions(BInfoboxOptions options)
	{
		if (!options.equals(this.options))
		{
			this.options = options;
            this.options.setParent(this);

			if (AjaxRequestTarget.get() != null)
			{
				AjaxRequestTarget.get().appendJavascript(getParent().getJSsetInfoboxOptions(this, options));
			}
		}
	}

    public BInfoboxOptions getOptions() {
        return null == options ? null : options.clone();
    }

    /**
	 * Set the options.
	 *
	 * @param htmlContent content
	 */
	public void setHtmlContent(String htmlContent)
	{
		if (!Strings.isEmpty(htmlContent))
		{
			if (AjaxRequestTarget.get() != null)
			{
				AjaxRequestTarget.get().appendJavascript(getParent().getJSsetInfoboxHtml(this, htmlContent));
			}
		}
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
				AjaxRequestTarget.get().appendJavascript(getParent().getJSsetInfoboxLocation(this, location));
			}
		}
	}

    public Boolean getVisible() {
        return visible;
    }

    @Override
    protected void updateOnAjaxCall(AjaxRequestTarget target, BEvent overlayEvent) {
        Request request = RequestCycle.get().getRequest();
        String s = request.getParameter("overlay.location");
        if (!Strings.isEmpty(s)) {
		    this.location = BLocation.parse(s);
        } else {
            //TODO warn
        }

        String vis = request.getParameter("overlay.visible");
        if (!Strings.isEmpty(vis)) {
		    visible = Boolean.valueOf(vis);
        }
    }
}
