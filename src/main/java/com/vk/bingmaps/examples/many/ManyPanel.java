package com.vk.bingmaps.examples.many;

import com.vk.bingmaps.api.BingMap;
import com.vk.bingmaps.api.obj.BLocation;
import com.vk.bingmaps.api.obj.BViewOptions;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.panel.Panel;

public abstract class ManyPanel extends Panel
{

	final BingMap bMap;

	public ManyPanel(String id, String bMapKey)
	{
		super(id);
		bMap = new BingMap("bMap", bMapKey);
        BViewOptions viewOptions = new BViewOptions();
        viewOptions.setZoom(7);
        viewOptions.setCenter(BLocation.LatLng(37.4419f, -122.1419f) /*Paolo Alto*/);
		bMap.setViewOptions(viewOptions);
		bMap.setOutputMarkupId(true);
		add(bMap);

		AjaxFallbackLink<Object> close = new AjaxFallbackLink<Object>("close")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				closing(target);
			}
		};
		add(close);
	}

	protected abstract void closing(AjaxRequestTarget target);
}
