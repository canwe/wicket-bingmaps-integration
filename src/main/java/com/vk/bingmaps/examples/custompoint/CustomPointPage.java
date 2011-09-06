package com.vk.bingmaps.examples.custompoint;

import com.vk.bingmaps.api.BingMap;
import com.vk.bingmaps.api.obj.*;
import com.vk.bingmaps.examples.BMapExampleApplication;
import com.vk.bingmaps.examples.WicketExamplePage;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.protocol.http.RequestUtils;

/**
 * SimplePage for the wicket-bingmaps-integration project
 */
public class CustomPointPage extends WicketExamplePage
{

	private static final long serialVersionUID = 1L;

	public CustomPointPage()
	{
		BingMap map = new BingMap("map", BMapExampleApplication.get().getBingMapsAPIkey());
        BViewOptions viewOptions = new BViewOptions();
		viewOptions.setCenter(BLocation.LatLng(52.37649f, 4.888573f));
        viewOptions.setZoom(13);
        map.setViewOptions(viewOptions);
		add(map);

		String icon = RequestUtils.toAbsolutePath(urlFor(new ResourceReference(CustomPointPage.class, "BluePushpin.png")).toString());
        BPushpinOptions pushpinOptions = new BPushpinOptions();
        pushpinOptions.setIcon(icon);
        pushpinOptions.setWidth(50);
        pushpinOptions.setHeight(50);
        pushpinOptions.setAnchor(new BPoint(0,50));

		BPushpin pp = new BPushpin(BLocation.LatLng(52.37649f, 4.888573f), pushpinOptions);

		map.addOverlay(pp);
	}
}