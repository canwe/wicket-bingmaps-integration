package com.vk.bingmaps.examples.info;

import com.vk.bingmaps.api.BingMap;
import com.vk.bingmaps.api.event.RightClickListener;
import com.vk.bingmaps.api.event.TileDownloadCompleteListener;
import com.vk.bingmaps.api.event.ViewChangeEndListener;
import com.vk.bingmaps.api.obj.*;
import com.vk.bingmaps.examples.BMapExampleApplication;
import com.vk.bingmaps.examples.WicketExamplePage;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

/**
 * Example HomePage for the wicket-bingmaps-integration project
 */
public class HomePage extends WicketExamplePage
{

	private static final long serialVersionUID = 1L;

	private final FeedbackPanel feedback;

	private final BingMap map;

	private final Label infoWindow;

    private final Label pushPin;

	public HomePage()
	{
		feedback = new FeedbackPanel("feedback");
		feedback.setOutputMarkupId(true);
		add(feedback);

		map = new BingMap("bottomPanel", BMapExampleApplication.get().getBingMapsAPIkey());
		BViewOptions viewOptions = new BViewOptions();
        viewOptions.setCenter(BLocation.LatLng(37.4419f, -122.1419f) /*Paolo Alto*/);
        viewOptions.setZoom(13);
        map.setViewOptions(viewOptions);
        add(map);
        map.add(new TileDownloadCompleteListener() {
            @Override
            protected void onTileDownloadComplete(AjaxRequestTarget target) {
                //nothing to do
                //behaviour added in order to set bounds for the map object
            }
        });
        map.add(new ViewChangeEndListener() {
            @Override
            protected void onViewChangeEnd(AjaxRequestTarget target) {
                //nothing to do
                //behaviour added in order to set bounds for the map object
            }
        });
		map.add(new RightClickListener() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onClick(AjaxRequestTarget target, BLocation location) {
                if (location != null) {

                    BInfoboxOptions infoboxOptions = new BInfoboxOptions();
                    infoboxOptions.setDescription("My Description 1");
                    infoboxOptions.setTitle("My Title 1");
                    BInfoboxAction action = new BInfoboxAjaxAction("ajax");
                    map.add(action);
                    infoboxOptions.addActions(action);
                    action = new BInfoboxJSAction("simple js", "function() { alert('simple alert'); }");
                    map.add(action);
                    infoboxOptions.addActions(action);

                    final BInfobox infobox = new BInfobox(location, infoboxOptions);
                    map.addOverlay(infobox);
                    infobox.addListener(BEvent.entitychanged, new BEventHandler() {
                        @Override
                        public void onEvent(AjaxRequestTarget target) {
                            if (infobox.getVisible() != null && !infobox.getVisible()) {
                                map.removeOverlay(infobox);
                            }
                        }
                    });
                }
            }

        });

		infoWindow = new Label("infoWindow", "openInfoWindow");
		infoWindow.add(new AjaxEventBehavior("onclick") {
            private static final long serialVersionUID = 1L;

            BInfoboxTitleClickHandler titleClickHandler;

            /**
             * @see org.apache.wicket.ajax.AjaxEventBehavior#onEvent(org.apache.wicket.ajax.AjaxRequestTarget)
             */
            @Override
            protected void onEvent(AjaxRequestTarget target) {
                BInfoboxOptions infoboxOptions = new BInfoboxOptions();
                infoboxOptions.setDescription("My Description");
                infoboxOptions.setTitle("My Title");

                titleClickHandler = new BInfoboxTitleClickHandler();
                map.add(titleClickHandler);
                infoboxOptions.setTitleClickHandler(titleClickHandler);
                final BInfobox infobox = new BInfobox(BLocation.LatLng(37.5f, -122.1f), infoboxOptions);
                map.addOverlay(infobox);
                infobox.addListener(BEvent.entitychanged, new BEventHandler() {
                    @Override
                    public void onEvent(AjaxRequestTarget target) {
                        if (infobox.getVisible() != null && !infobox.getVisible()) {
                            map.removeOverlay(infobox);
                        }
                    }
                });
            }
        });
		add(infoWindow);

		pushPin = new Label("pushPin", "place pushpin");
		pushPin.add(new AjaxEventBehavior("onclick")
		{
			private static final long serialVersionUID = 1L;

			/**
			 * @see org.apache.wicket.ajax.AjaxEventBehavior#onEvent(org.apache.wicket.ajax.AjaxRequestTarget)
			 */
			@Override
			protected void onEvent(AjaxRequestTarget target)
			{
                //http://stackoverflow.com/questions/3648609/random-marker-within-a-rectangular-bounds
                BLocationRect locRect = map.getBoundsFromCorners();
                float lat_min = locRect.getSE().getLatitude();
                float lat_range = locRect.getNW().getLatitude() - lat_min;
                float lng_min = locRect.getNW().getLongitude();
                float lng_range = locRect.getSE().getLongitude() - lng_min;

                BLocation loc = BLocation.LatLng(lat_min + (Math.random() * lat_range),
                                                 lng_min + (Math.random() * lng_range));

                final BPushpin pushpin = new BPushpin(loc);

                map.addOverlay(pushpin);

                pushpin.addListener(BEvent.click, new BEventHandler() {
                    @Override
                    public void onEvent(AjaxRequestTarget target) {

                    }
                });
			}
		});
		add(pushPin);
		add(new Link<Object>("reload")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick()
			{
			}
		});
	}
}
