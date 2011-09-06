package com.vk.bingmaps.examples.marker;

import com.vk.bingmaps.api.BingMap;
import com.vk.bingmaps.api.event.ClickListener;
import com.vk.bingmaps.api.obj.BLocation;
import com.vk.bingmaps.api.obj.BPushpin;
import com.vk.bingmaps.api.obj.BPushpinOptions;
import com.vk.bingmaps.examples.BMapExampleApplication;
import com.vk.bingmaps.examples.WicketExamplePage;
import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * Example HomePage for the wicket-bingmaps-integration project
 */
public class HomePage extends WicketExamplePage
{

	private static final long serialVersionUID = 1L;

	public HomePage()
	{
		final BingMap map = new BingMap("topPanel", BMapExampleApplication.get().getBingMapsAPIkey());
		add(map);
		map.add(new ClickListener()
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onClick(AjaxRequestTarget target, BLocation loc/*, GOverlay overlay*/)
			{
				if (loc != null)
				{
                    System.out.println(loc);
					if (map.getOverlays().size() >= 3)
					{
						map.removeOverlay(map.getOverlays().get(0));
					}
                    BPushpinOptions options = new BPushpinOptions();
                    options.setHeight(39);
                    options.setWidth(25);
                    options.setDraggable(true);
					map.addOverlay(new BPushpin(loc, options));
				}
			}
		});
	}
}