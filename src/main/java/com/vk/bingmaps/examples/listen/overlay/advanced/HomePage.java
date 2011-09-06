package com.vk.bingmaps.examples.listen.overlay.advanced;

import com.vk.bingmaps.api.BingMap;
import com.vk.bingmaps.api.event.ClickListener;
import com.vk.bingmaps.api.obj.*;
import com.vk.bingmaps.examples.BMapExampleApplication;
import com.vk.bingmaps.examples.WicketExamplePage;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * Example HomePage for the wicket-bingmaps-integration project.
 */
public class HomePage extends WicketExamplePage
{

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public HomePage()
	{
		final BingMap map = new BingMap("map", BMapExampleApplication.get().getBingMapsAPIkey());
		BViewOptions viewOptions = new BViewOptions();
        viewOptions.setCenter(BLocation.LatLng(37.4419f, -122.1419f) /*Paolo Alto*/);
        viewOptions.setZoom(13);
        map.setViewOptions(viewOptions);
		add(map);
		final WebMarkupContainer repeaterParent = new WebMarkupContainer("repeaterParent");
		repeaterParent.setOutputMarkupId(true);
		add(repeaterParent);
		final RepeatingView rv = new RepeatingView("label");
		rv.setOutputMarkupId(true);
		repeaterParent.add(rv);
		map.add(new ClickListener()
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onClick(AjaxRequestTarget target, BLocation latLng)
			{
				if (latLng != null)
				{
					if (map.getOverlays().size() >= 3)
					{
						map.removeOverlay(map.getOverlays().get(0));
					}
                    BPushpinOptions options = new BPushpinOptions();
                    options.setDraggable(true);
					final MyPushpin pushpin = new MyPushpin(latLng, options)
					{
						private static final long serialVersionUID = 1L;

						@Override
                        BEventHandler getDragendHandler()
						{
							return new BEventHandler()
							{
								private static final long serialVersionUID = 1L;

								@Override
								public void onEvent(AjaxRequestTarget target)
								{
									target.addComponent(repeaterParent);
								}
							};
						}

						@Override
						BEventHandler getDblclickHandler()
						{
							return new BEventHandler()
							{
								private static final long serialVersionUID = 1L;

								@Override
								public void onEvent(AjaxRequestTarget target)
								{
									target.addComponent(repeaterParent);
								}
							};
						}

					};
					map.addOverlay(pushpin);
					pushpin.addListener(BEvent.dragend, pushpin.getDragendHandler());
					rv.removeAll();
					for (BOverlay overlay : map.getOverlays())
					{
						final BOverlayPanel label = new BOverlayPanel(overlay.getId(), new CompoundPropertyModel<MyPushpin>(overlay));
						label.setOutputMarkupId(true);
						rv.add(label);
					}

					target.addComponent(repeaterParent);
				}
			}
		});
	}

	/**
	 * Panel for displaying and controlling the state of a BOverlay.
	 */
	private static class BOverlayPanel extends Panel
	{
		private static final long serialVersionUID = 1L;

		public BOverlayPanel(String id, final IModel<MyPushpin> model)
		{
			super(id, model);
			add(new Label("location"));
			final Label dragendLabel = new Label("dragend", new Model<Boolean>()
			{
				private static final long serialVersionUID = 1L;

				/*
				 * (non-Javadoc)
				 * 
				 * @see org.apache.wicket.model.Model#getObject()
				 */
				@Override
				public Boolean getObject()
				{

					return model.getObject().getListeners().containsKey(BEvent.dragend);
				}
			});
			dragendLabel.add(new AjaxEventBehavior("onclick")
			{

				private static final long serialVersionUID = 1L;

				@Override
				protected void onEvent(AjaxRequestTarget target)
				{
					MyPushpin overlay = model.getObject();
					if ((Boolean)dragendLabel.getDefaultModelObject())
					{
						overlay.clearListeners(BEvent.dragend);
					}
					else
					{
						overlay.addListener(BEvent.dragend, overlay.getDragendHandler());
					}
					target.addComponent(BOverlayPanel.this);
				}

			});
			add(dragendLabel);
			final Label dblclickLabel = new Label("dblclick", new Model<Boolean>()
			{
				private static final long serialVersionUID = 1L;

				@Override
				public Boolean getObject()
				{

					return ((BOverlay)getDefaultModelObject()).getListeners().containsKey(BEvent.dblclick);
				}
			});
			dblclickLabel.add(new AjaxEventBehavior("onclick")
			{

				private static final long serialVersionUID = 1L;

				@Override
				protected void onEvent(AjaxRequestTarget target)
				{
					MyPushpin overlay = ((MyPushpin)BOverlayPanel.this.getDefaultModelObject());
					if ((Boolean)dragendLabel.getDefaultModelObject())
					{
						overlay.clearListeners(BEvent.dblclick);
					}
					else
					{
						overlay.addListener(BEvent.dblclick, overlay.getDblclickHandler());
					}
					target.addComponent(BOverlayPanel.this);
				}

			});
			add(dblclickLabel);
		}
	}

	/**
	 * Extend a BPushpin with factory methods for needed handler.
	 * 
	 */
	private static abstract class MyPushpin extends BPushpin
	{

		public MyPushpin(BLocation location, BPushpinOptions options)
		{
			super(location, options);
		}

		abstract BEventHandler getDblclickHandler();

		abstract BEventHandler getDragendHandler();
	}

}
