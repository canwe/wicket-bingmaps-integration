package com.vk.bingmaps.examples.listen;

import com.vk.bingmaps.api.BingMap;
import com.vk.bingmaps.api.event.TileDownloadCompleteListener;
import com.vk.bingmaps.api.event.ViewChangeEndListener;
import com.vk.bingmaps.api.obj.BLocationRect;
import com.vk.bingmaps.examples.BMapExampleApplication;
import com.vk.bingmaps.examples.WicketExamplePage;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.convert.IConverter;

import java.util.Locale;

public class HomePage extends WicketExamplePage
{

	private static final long serialVersionUID = 1L;

	private final Label zoomLabel;

	private final MultiLineLabel boundsLabel;

	private ViewChangeEndListener viewChangeEndBehavior;

    private final BingMap map;

	public HomePage()
	{
		map = new BingMap("map", BMapExampleApplication.get().getBingMapsAPIkey());
		add(map.setOutputMarkupId(true));
		viewChangeEndBehavior = new MyViewChangeEndListener();
		map.add(viewChangeEndBehavior);
		map.add(new TileDownloadCompleteListener()
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onTileDownloadComplete(AjaxRequestTarget target)
			{
                System.out.println("TileDownloadComplete");
				target.addComponent(boundsLabel);
			}
		});

		zoomLabel = new Label("zoom", new PropertyModel<String>(map, "zoom"));
		zoomLabel.setOutputMarkupId(true);
		add(zoomLabel);

		boundsLabel = new MultiLineLabel("bounds", new PropertyModel<BLocationRect>(map, "boundsFromCorners"))
		{
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			@Override
			public IConverter getConverter(Class type)
			{
				if (BLocationRect.class.isAssignableFrom(type))
				{
					return new IConverter()
					{
						private static final long serialVersionUID = 1L;

						public BLocationRect convertToObject(String value, Locale locale)
						{
							throw new UnsupportedOperationException();
						}

						public String convertToString(Object value, Locale locale)
						{
							BLocationRect bounds = (BLocationRect)value;

							StringBuffer buffer = new StringBuffer();
							buffer.append("NW (");
							buffer.append(bounds.getNW().getLatitude());
							buffer.append(",");
							buffer.append(bounds.getNW().getLongitude());
							buffer.append(")\nSE (");
							buffer.append(bounds.getSE().getLatitude());
							buffer.append(",");
							buffer.append(bounds.getSE().getLongitude());
							buffer.append(")");
							return buffer.toString();
						}
					};
				}
				else
				{
					return super.getConverter(type);
				}
			}

		};
		boundsLabel.setOutputMarkupId(true);
		add(boundsLabel);
		final Label enabledLabel = new Label("enabled", new Model<Boolean>()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public Boolean getObject()
			{
				return map.getBehaviors().contains(viewChangeEndBehavior);
			}
		});
		enabledLabel.add(new AjaxEventBehavior("onclick")
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onEvent(AjaxRequestTarget target)
			{
				if (map.getBehaviors().contains(viewChangeEndBehavior))
				{
					map.remove(viewChangeEndBehavior);
				}
				else
				{
					// TODO AbstractAjaxBehaviors are not reusable, so
					// we have to recreate:
					// https://issues.apache.org/jira/browse/WICKET-713
					viewChangeEndBehavior = new MyViewChangeEndListener();
					map.add(viewChangeEndBehavior);
				}
				target.addComponent(map);
				target.addComponent(enabledLabel);
			}
		});
		add(enabledLabel);
	}

	private class MyViewChangeEndListener extends ViewChangeEndListener
	{
		private static final long serialVersionUID = 1L;

		@Override
		protected void onViewChangeEnd(AjaxRequestTarget target)
		{
            System.out.println("ViewChangeEnd");
			target.addComponent(zoomLabel);
			target.addComponent(boundsLabel);

            //map.addOverlay(new BPushpin(map.getBounds().getCenter()));
		}
	}
}
