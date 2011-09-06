package com.vk.bingmaps.examples.clustering;

import com.vk.bingmaps.api.BingMap;
import com.vk.bingmaps.api.event.ClickListener;
import com.vk.bingmaps.api.obj.*;
import com.vk.bingmaps.examples.BMapExampleApplication;
import com.vk.bingmaps.examples.WicketExamplePage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.MinimumValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * Example HomePage for the wicket-bingmaps-integration project
 */
public class HomePage extends WicketExamplePage
{

	private static final long serialVersionUID = 1L;

    private final TextField<Integer>     sizeTextField;

    private final AjaxFallbackLink<Void> generatePushpinsLink;

	public HomePage()
	{
        final BMapOptions options = new BMapOptions(BMapExampleApplication.get().getBingMapsAPIkey());
        options.setClusteringEnabled(true);
		final BingMap map = new BingMap("topPanel", options);
		add(map);
		map.add(new ClickListener()
		{
			private static final long serialVersionUID = 1L;

            private int clicks = 0;

			@Override
			protected void onClick(AjaxRequestTarget target, BLocation loc)
			{
				if (loc != null)
				{
                    System.out.println(loc);
					map.addOverlay(new BPushpin(loc));
                    clicks++;

                    if (clicks > 9) {
                        map.placeClusteredPushpins();
                        clicks = 0;
                    }
				}
			}
		});

        sizeTextField = new TextField<Integer>("dataSize", Model.<Integer>of());
        sizeTextField.setType(int.class);
        sizeTextField.add(new MinimumValidator<Integer>(0));
        sizeTextField.add(new OnChangeAjaxBehavior() {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                System.out.println(sizeTextField.getDefaultModelObject());
            }
        });
        add(sizeTextField);

        generatePushpinsLink = new AjaxFallbackLink<Void>("generatePushpins") {

            private double randomLatitude;
            private double randomLongitude;

            private List<BOverlay> list;

            @Override
            public void onClick(AjaxRequestTarget target) {
                try {
                    if (!sizeTextField.isValid()) return;
                    int dataSize = (Integer) sizeTextField.getDefaultModelObject();
                    if (dataSize > 0) {
                        map.removeAllOverlays();
                    }
                    list = new ArrayList<BOverlay>(dataSize);
                    while (dataSize > 0) {
                        randomLatitude = Math.random() * 181 - 90;
                        randomLongitude = Math.random() * 361 - 180;
                        BPushpinOptions options = new BPushpinOptions(); //only for experiment
                        options.setHeight(39);
                        options.setWidth(25);
                        options.setDraggable(true);
                        list.add(new BPushpin(BLocation.LatLng((float)randomLatitude,
                                                               (float)randomLongitude),
                                                               options));
                        //map.addOverlay(new BPushpin(BLocation.LatLng((float)randomLatitude,
                        //                                             (float)randomLongitude)));
                        dataSize--;
                    }
                    map.addOverlays(list);
                    map.placeClusteredPushpins();
                } catch (Throwable tx) {
                    //;
                }
            }
        };
        add(generatePushpinsLink);
	}
}
