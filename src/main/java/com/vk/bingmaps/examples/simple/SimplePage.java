package com.vk.bingmaps.examples.simple;

import com.vk.bingmaps.api.BingMap;
import com.vk.bingmaps.api.event.TileDownloadCompleteListener;
import com.vk.bingmaps.api.obj.BLocation;
import com.vk.bingmaps.api.obj.BViewOptions;
import com.vk.bingmaps.examples.BMapExampleApplication;
import com.vk.bingmaps.examples.WicketExamplePage;
import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * SimplePage
 */
public class SimplePage extends WicketExamplePage
{

	private static final long serialVersionUID = 1L;

	public SimplePage()
	{
		final BingMap map = new BingMap("map", BMapExampleApplication.get().getBingMapsAPIkey());

        map.add(new TileDownloadCompleteListener() {
            @Override
            protected void onTileDownloadComplete(AjaxRequestTarget target) {

                BViewOptions viewOptions = new BViewOptions();
                viewOptions.setCenter(BLocation.LatLng(53.9f, 27.56f));
                viewOptions.setZoom(14);

                map.setViewOptions(viewOptions);
            }
        });

		add(map);
	}
}
