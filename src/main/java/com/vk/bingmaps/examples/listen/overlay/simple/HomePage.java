package com.vk.bingmaps.examples.listen.overlay.simple;

import com.vk.bingmaps.api.BingMap;
import com.vk.bingmaps.api.obj.*;
import com.vk.bingmaps.examples.BMapExampleApplication;
import com.vk.bingmaps.examples.WicketExamplePage;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.protocol.http.RequestUtils;

/**
 * Example HomePage for the wicket-bingmaps-integration project
 */
public class HomePage extends WicketExamplePage
{

	private static final long serialVersionUID = 1L;

	public HomePage()
	{
		final BingMap topMap = new BingMap("topPanel", BMapExampleApplication.get().getBingMapsAPIkey());
        BViewOptions viewOptions = new BViewOptions();
        viewOptions.setCenter(BLocation.LatLng(37.4419f, -122.1419f) /*Paolo Alto*/);
        viewOptions.setZoom(13);
        topMap.setViewOptions(viewOptions);
		add(topMap);

		BPushpinOptions options = new BPushpinOptions();
        options.setDraggable(true);
		final BPushpin pushpin = new BPushpin(BLocation.LatLng(37.4419f, -122.1419f), options);
		final Label label = new Label("label", new PropertyModel<BLocation>(pushpin, "location"));
		label.setOutputMarkupId(true);
		add(label);

        pushpin.addListener(BEvent.dragend, new BEventHandler()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(AjaxRequestTarget target)
			{
				target.addComponent(label);
			}
		});

        pushpin.addListener(BEvent.click, new BEventHandler() {

            private int i = 0;

            private String bigIcon = RequestUtils.toAbsolutePath(urlFor(new ResourceReference(HomePage.class, "poi_search_35x55.png")).toString());
            private String smallIcon = RequestUtils.toAbsolutePath(urlFor(new ResourceReference(HomePage.class, "poi_search.png")).toString());

            @Override
            public void onEvent(AjaxRequestTarget target) {
                BPushpinOptions options = pushpin.getOptions();
                if (options == null) {
                    options = new BPushpinOptions();
                }
                if (i % 2 == 1) {
                    options.setHeight((int) (39 * 1.4));
                    options.setWidth((int) (25 * 1.4));
                    options.setIcon(bigIcon);
                } else {
                    options.setHeight(39);
                    options.setWidth(25);
                    options.setIcon(smallIcon);
                }
                i++;
                pushpin.setOptions(options);
            }
        });
		topMap.addOverlay(pushpin);
	}
}
