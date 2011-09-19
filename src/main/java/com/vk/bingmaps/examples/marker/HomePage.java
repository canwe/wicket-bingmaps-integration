package com.vk.bingmaps.examples.marker;

import com.vk.bingmaps.api.BingMap;
import com.vk.bingmaps.api.event.ClickListener;
import com.vk.bingmaps.api.event.TileDownloadCompleteListener;
import com.vk.bingmaps.api.event.ViewChangeEndListener;
import com.vk.bingmaps.api.obj.BLocation;
import com.vk.bingmaps.api.obj.BPushpin;
import com.vk.bingmaps.api.obj.BPushpinOptions;
import com.vk.bingmaps.examples.BMapExampleApplication;
import com.vk.bingmaps.examples.WicketExamplePage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;

/**
 * Example HomePage for the wicket-bingmaps-integration project
 */
public class HomePage extends WicketExamplePage
{

	private static final long serialVersionUID = 1L;
    private BLocation center;

	public HomePage()
	{
		final BingMap map = new BingMap("topPanel", BMapExampleApplication.get().getBingMapsAPIkey());
		add(map);
        map.add(new TileDownloadCompleteListener() {
            @Override
            protected void onTileDownloadComplete(AjaxRequestTarget target) {
                center = map.getCenter();
            }
        });
        map.add(new ViewChangeEndListener() {
            @Override
            protected void onViewChangeEnd(AjaxRequestTarget target) {
                center = map.getCenter();
            }
        });
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
        add(new AjaxLink<Void>("add-marker") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                map.addOverlay(new BPushpin(center));
            }
        });

	}
}