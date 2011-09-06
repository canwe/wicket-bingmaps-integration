package com.vk.bingmaps.examples.refreshpoint;

import java.util.Collections;

import com.vk.bingmaps.api.BingMap;
import com.vk.bingmaps.api.obj.*;
import com.vk.bingmaps.examples.BMapExampleApplication;
import com.vk.bingmaps.examples.WicketExamplePage;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.protocol.http.RequestUtils;
import org.apache.wicket.util.time.Duration;

/**
 * SimplePage for the wicket-bingmaps-integration project
 */
public class RefreshPointPage extends WicketExamplePage
{

	private static final long serialVersionUID = 1L;

	private final BingMap map;

	public RefreshPointPage()
	{
		map = new BingMap("map", BMapExampleApplication.get().getBingMapsAPIkey());
		add(map);

		BOverlay overlay = createOverlay("Amsterdam", BLocation.LatLng(52.37649f, 4.888573f), "image.png", new BPoint(32, 64));

		map.addOverlay(overlay);

		map.add(new BingMapAutoUpdatingBehavior(Duration.seconds(5))
		{
			private static final long serialVersionUID = 1L;

			private int i = 1;

			@Override
			protected void onTimer(AjaxRequestTarget target, BingMap map)
			{
				BOverlay overlay;
				if (i % 3 == 0)
				{
					overlay = createOverlay("Amsterdam", BLocation.LatLng(52.37649f, 4.888573f), "image.png", new BPoint(32, 64));
					i = 0;
				}
				else if (i % 3 == 1)
				{
					overlay = createOverlay("Amsterdam", BLocation.LatLng(52.37649f, 4.888573f), "image2.png", new BPoint(10, 64));
				}
				else
				{
					overlay = createOverlay("Toulouse", BLocation.LatLng(43.604363f, 1.442951f), "image2.png", new BPoint(10, 64));
				}
				i++;
				map.setOverlays(Collections.singletonList(overlay));
			}
		});
	}

	private BOverlay createOverlay(String title, BLocation location, String image, BPoint anchor)
	{
        String iconPath = RequestUtils.toAbsolutePath(urlFor(new ResourceReference(RefreshPointPage.class, image)).toString());

        BPushpinOptions pushpinOptions = new BPushpinOptions();
        pushpinOptions.setIcon(iconPath);
        pushpinOptions.setText(title);
        pushpinOptions.setHeight(64);
        pushpinOptions.setWidth(40);
        pushpinOptions.setAnchor(anchor);

        BViewOptions viewOptions = new BViewOptions();
        viewOptions.setCenter(location);
        viewOptions.setZoom(14);
		map.setViewOptions(viewOptions);

        return new BPushpin(location, pushpinOptions);
	}
}