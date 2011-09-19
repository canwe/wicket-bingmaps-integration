/*
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vk.bingmaps.api;

import com.vk.bingmaps.api.event.BEventListenerBehavior;
import com.vk.bingmaps.api.js.ArrayLiteral;
import com.vk.bingmaps.api.obj.*;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class BingMap extends Panel
{
	/** log. */
	private static final Logger log = LoggerFactory.getLogger(BingMap.class);

	private static final long serialVersionUID = 1L;

	private BViewOptions  viewOptions;

    private BMapOptions   mapOptions;

	private BMapTypeId    mapType = BMapTypeId.road;

    private AtomicBoolean clusteringEnabled = new AtomicBoolean(false);

	private Integer       zoom;
    private BLocationRect bounds;
    private BLocationRect boundsFromCorners;
    private BLocation     center;
    private BLocation     NW;
    private BLocation     SE;

    private final OverlayListener    overlayListener;

    private final List<BOverlay>     overlays = new ArrayList<BOverlay>();

	private final WebMarkupContainer map;

	/**
	 * Construct.
	 *
	 * @param id string
	 * @param bMapKey bing map API KEY
	 */
	public BingMap(final String id, final String bMapKey)
	{
		this(id, new BMapHeaderContributor(bMapKey), new BMapOptions(bMapKey));
	}

	/**
	 * Construct.
	 *
	 * @param id string
     * @param mapOptions predefined options
	 */
	public BingMap(final String id, BMapOptions mapOptions)
	{
		this(id, new BMapHeaderContributor(mapOptions.getCredentials()), mapOptions);
	}

	/**
	 * Construct.
	 *
	 * @param id string
	 * @param headerContrib BMapHeaderContributor
     * @param mapOptions predefined options
	 */
	public BingMap(final String id, final BMapHeaderContributor headerContrib, BMapOptions mapOptions)
	{
		super(id);

        this.mapOptions = mapOptions;

        add(headerContrib);
        if (this.mapOptions.isClusteringEnabled()) {
            enableClustering();
        }
        add(new HeaderContributor(new IHeaderContributor()
		{
			private static final long serialVersionUID = 1L;

			public void renderHead(IHeaderResponse response)
			{
				response.renderOnLoadJavascript(getJSinit());
                response.renderOnEventJavascript("window", "onUnload", BingMap.this.getJSinvoke("dispose();"));
			}
		}));
		map = new WebMarkupContainer("map");
		map.setOutputMarkupId(true);
		add(map);
        overlayListener = new OverlayListener();
		add(overlayListener);
	}

	/**
	 * @see org.apache.wicket.MarkupContainer#onRender(org.apache.wicket.markup.MarkupStream)
	 */
	@Override
	protected void onRender(MarkupStream markupStream)
	{
		super.onRender(markupStream);
		if (Application.DEVELOPMENT.equalsIgnoreCase(Application.get().getConfigurationType())
				&& !Application.get().getMarkupSettings().getStripWicketTags())
		{
			log.warn("Application is in DEVELOPMENT mode && Wicket tags are not stripped,"
					+ " Firefox 3.0 will not render the GMap."
					+ " Change to DEPLOYMENT mode  || turn on Wicket tags stripping." + " See:"
					+ " http://www.nabble.com/Gmap2-problem-with-Firefox-3.0-to18137475.html.");
		}
	}

    /**
	 * Add an overlay.
	 *
	 * @param overlay overlay to add
	 * @return This
	 */
	public BingMap addOverlay(BOverlay overlay)
	{
		overlays.add(overlay);
		overlay.setParent(this);

        AjaxRequestTarget target = AjaxRequestTarget.get();
		if (target != null && findPage() != null)
		{
            BOverlayOptions ovlOptions = overlay.getOptions();
            if (null != ovlOptions) {
                target.appendJavascript(getJSinvoke("overlaysOptions['" + overlay.getId() + "'] = " + ovlOptions.getJSconstructor()));
            }
			target.appendJavascript(overlay.getJSadd());
		}

		return this;
	}

    /**
	 * Add the overlays.
	 *
	 * @param overlaysToAdd overlays to add
	 * @return This
	 */
	public <T extends Collection<? extends BOverlay>> BingMap addOverlays(T overlaysToAdd)
	{
		overlays.addAll(overlaysToAdd);
        ArrayLiteral arLiteral = new ArrayLiteral();

        StringBuilder optionsSet = new StringBuilder();
        for (BOverlay ovl : overlaysToAdd) {
            ovl.setParent(this);
            arLiteral.addObjectLiteral(ovl.getJSObjectLiteralWithOvlId());

            BOverlayOptions ovlOptions = ovl.getOptions();
            if (null != ovlOptions) {
                optionsSet.append(getJSinvoke("overlaysOptions['" + ovl.getId() + "'] = " + ovlOptions.getJSconstructor()));
            }
        }

		if (AjaxRequestTarget.get() != null && findPage() != null)
		{
            AjaxRequestTarget.get().appendJavascript(optionsSet.toString());
			AjaxRequestTarget.get().appendJavascript(getJSinvoke("addOverlays(" + arLiteral.toJS() + ")"));
		}

		return this;
	}

	/**
	 * Remove an overlay.
	 *
	 * @param overlay overlay to remove
	 * @return This
	 */
	public BingMap removeOverlay(BOverlay overlay)
	{
		while (overlays.contains(overlay))
		{
			overlays.remove(overlay);
		}

		if (AjaxRequestTarget.get() != null && findPage() != null)
		{
			AjaxRequestTarget.get().appendJavascript(overlay.getJSremove());
		}

		overlay.setParent(null);

		return this;
	}

	/**
	 * Clear all overlays.
	 *
	 * @return This
	 */
	public BingMap removeAllOverlays()
	{
		for (BOverlay overlay : overlays)
		{
			overlay.setParent(null);
		}
		overlays.clear();
		if (AjaxRequestTarget.get() != null && findPage() != null)
		{
			AjaxRequestTarget.get().appendJavascript(getJSinvoke("clearOverlays()"));
		}
		return this;
	}

	public List<BOverlay> getOverlays()
	{
		return Collections.unmodifiableList(overlays);
	}

    public void setOverlays(List<BOverlay> overlays)
    {
        removeAllOverlays();
        for (BOverlay overlay : overlays)
        {
            addOverlay(overlay);
        }
    }


    public BMapTypeId getMapType()
	{
		return mapType;
	}

	public void setMapType(BMapTypeId mapType)
	{
		if (this.mapType != mapType)
		{
			this.mapType = mapType;

			if (AjaxRequestTarget.get() != null && findPage() != null)
			{
				AjaxRequestTarget.get().appendJavascript(mapType.getJSsetMapType(BingMap.this));
			}
		}
	}

    public String getJSsetPushpinOptions(BPushpin pushpin, BPushpinOptions options)
	{
		if (options != null)
			return getJSinvoke("resolveOverlay('" + pushpin.getId() + "').setOptions(" + options.getJSconstructor() + ")");
		else
			return "";
	}

    public String getJSsetPushpinLocation(BPushpin pushpin, BLocation location)
	{
		if (location != null)
			return getJSinvoke("resolveOverlay('" + pushpin.getId() + "').setLocation(" + location.getJSconstructor() + ")");
		else
			return "";
	}

    public String getJSsetInfoboxHtml(BInfobox infobox, String html)
	{
		if (html != null)
			return getJSinvoke("resolveOverlay('" + infobox.getId() + "').setHtmlContent(" + html + ")");
		else
			return "";
	}

    public String getJSsetInfoboxLocation(BInfobox infobox, BLocation location)
	{
		if (location != null)
			return getJSinvoke("resolveOverlay('" + infobox.getId() + "').setLocation(" + location.getJSconstructor() + ")");
		else
			return "";
	}

    public String getJSsetInfoboxOptions(BInfobox infobox, BInfoboxOptions options)
	{
		if (options != null)
			return getJSinvoke("resolveOverlay('" + infobox.getId() + "').setOptions(" + options.getJSconstructor() + ")");
		else
			return "";
	}

	/**
	 * Set the view options.
	 *
	 * @param viewOptions options to set
	 */
	public void setViewOptions(BViewOptions viewOptions)
	{
		if (!viewOptions.equals(this.viewOptions))
		{
			this.viewOptions = viewOptions;

			if (AjaxRequestTarget.get() != null && findPage() != null)
			{
				AjaxRequestTarget.get().appendJavascript(getJSsetView(viewOptions));
			}
		}
	}

	/**
	 * Set the view options.
	 *
	 * @param pushpinOptions options to set
	 */
	public void setClusteredPushpinOptions(BPushpinOptions pushpinOptions)
	{
		if (null != pushpinOptions)
		{
			if (AjaxRequestTarget.get() != null && findPage() != null)
			{
				AjaxRequestTarget.get().appendJavascript(getJSinvoke("myLayer.setClusteredPushpinOptions(" + pushpinOptions.getJSconstructor() + ")"));
			}
		}
	}

	/**
	 * lockMap
	 */
	public void lockMap()
	{
    	if (AjaxRequestTarget.get() != null && findPage() != null)
		{
			AjaxRequestTarget.get().appendJavascript(getJSinvoke("lockMap()"));
		}
	}

	/**
	 * unlockMap
	 */
	public void unlockMap()
	{
    	if (AjaxRequestTarget.get() != null && findPage() != null)
		{
			AjaxRequestTarget.get().appendJavascript(getJSinvoke("unlockMap()"));
		}
	}

	/**
	 * placeClusteredPushpins
	 */
	public void placeClusteredPushpins()
	{
    	if (AjaxRequestTarget.get() != null && findPage() != null)
		{
			AjaxRequestTarget.get().appendJavascript(getJSinvoke("placeClusteredPushpins()"));
		}
	}

    /**
     * The map can support clustering.
     * You will not be able to switch your choice through ajax call later. :(
     */
    private void enableClustering() {

        add(new ClientSideClusteringHeaderContributor());

        clusteringEnabled.set(true);
    }

    public BViewOptions getViewOptions() {
        return null == viewOptions ? null : viewOptions.clone();
    }

    /**
	 * Generates the JavaScript used to instantiate this BingMap as an JavaScript
	 * class on the client side.
	 *
	 * @return The generated JavaScript
	 */
	private String getJSinit()
	{
		StringBuffer js = new StringBuffer("new WicketBingMap('" + map.getMarkupId() + "', " + mapOptions.getJSconstructor() + ");\n");

        js.append(overlayListener.getJSinit());
        if (mapType != null) {
		    js.append(mapType.getJSsetMapType(this));
        }

        if (viewOptions != null) {
            js.append(viewOptions.getJSsetView(this));
        }

        // Add the overlays.
		for (BOverlay overlay : overlays)
		{
			js.append(overlay.getJSadd());
		}

        for (Object behavior : getBehaviors(BEventListenerBehavior.class))
		{
			js.append(((BEventListenerBehavior)behavior).getJSaddListener());
		}

        if (mapOptions.isClusteringEnabled()) {
            js.append(getClusteringInit());
        }

        return js.toString();
	}

    /**
	 * Generates the JavaScript used to instantiate the clustering on BingMap
	 * on the client side.
	 *
	 * @return The generated JavaScript
	 */
	private String getClusteringInit()
	{
		StringBuffer js = new StringBuffer();

        js.append("\n");
        js.append("Microsoft.Maps.Pushpin.prototype.title = null;\n");
        js.append("Microsoft.Maps.Pushpin.prototype.description = null;\n");

        //js.append(getJSWrappedBingMapReference()).append(".myLayer");
        //js.append(" = new ClusteredEntityCollection(");
        //js.append(getJSBingMapReference());
        //js.append(", ");
        //js.append("{ \n");
        //js.append("  singlePinCallback: " + getJSinvokeReference("addOverlay") + ", \n");
        //js.append("  clusteredPinCallback: createClusteredPin \n");
        //js.append("}");
        //js.append("); \n");

        js.append(getJSinvoke("clearOverlays()"));
        js.append(getJSinvoke("initializeClustering()"));

        return js.toString();
	}

	/**
	 * Convenience method for generating a JavaScript call on this BingMap with
	 * the given invocation.
	 *
	 * @param invocation The JavaScript call to invoke on this BingMap.
	 * @return The generated JavaScript.
	 */
	// TODO Could this become default or protected?
	public String getJSinvoke(String invocation)
	{
		return "Wicket.bingmaps['" + map.getMarkupId() + "']." + invocation + ";\n";
	}

	public String getJSinvokeReference(String invocation)
	{
		return "Wicket.bingmaps['" + map.getMarkupId() + "']." + invocation + "";
	}

	/**
	 * Convenience method for getting a JavaScript reference to this BingMap

	 * @return The generated JavaScript reference.
	 */
	public String getJSBingMapReference()
	{
		return "Wicket.bingmaps['" + map.getMarkupId() + "'].map";
	}

	/**
	 * Convenience method for getting a JavaScript reference wrapper to this BingMap

	 * @return The generated JavaScript reference.
	 */
	public String getJSWrappedBingMapReference()
	{
		return "Wicket.bingmaps['" + map.getMarkupId() + "']";
	}

	private String getJSsetView(BViewOptions viewOptions)
	{
		if (viewOptions != null)
			return getJSinvoke("setView(" + viewOptions.getJSconstructor() + ")");
		else
			return "";
	}

    public Integer getZoom() {
        return zoom;
    }

    public BLocationRect getBounds() {
        return bounds;
    }

    public BLocationRect getBoundsFromCorners() {
        return boundsFromCorners;
    }

    public BLocation getCenter() {
        return center;
    }

    public BLocation getNW() {
        return NW;
    }

    public BLocation getSE() {
        return SE;
    }

    /**
	 * Update state from a request to an AJAX target.
     * @param target ajax request target
     */
	public void update(AjaxRequestTarget target)
	{
        Request request = RequestCycle.get().getRequest();

		// Attention: don't use setters as this will result in an endless
		// AJAX request loop
		bounds = BLocationRect.parse(request.getParameter("bounds"));
		center = BLocation.parse(request.getParameter("center"));
		NW = BLocation.parse(request.getParameter("northwest"));
		SE = BLocation.parse(request.getParameter("southeast"));
        boundsFromCorners = new BLocationRect(NW, SE);
		zoom = Double.valueOf(request.getParameter("zoom")).intValue();
		mapType = BMapTypeId.fromString(request.getParameter("currentMapType"));
	}

    private abstract class JSMethodBehavior extends AbstractBehavior
	{

		private static final long serialVersionUID = 1L;

		private final String attribute;

		public JSMethodBehavior(final String attribute)
		{
			this.attribute = attribute;
		}

		/**
		 * @see org.apache.wicket.behavior.AbstractBehavior#onComponentTag(org.apache.wicket.Component,
		 *      org.apache.wicket.markup.ComponentTag)
		 */
		@Override
		public void onComponentTag(Component component, ComponentTag tag)
		{
			String invoke = getJSinvoke();

			if (attribute.equalsIgnoreCase("href"))
			{
				invoke = "javascript:" + invoke;
			}

			tag.put(attribute, invoke);
		}

		protected abstract String getJSinvoke();
	}

    public class SetMapTypeBehavior extends JSMethodBehavior
	{
		private static final long serialVersionUID = 1L;

		private final BMapTypeId mapType;

		public SetMapTypeBehavior(String event, BMapTypeId mapType)
		{
			super(event);
			this.mapType = mapType;
		}

		@Override
		protected String getJSinvoke()
		{
			return mapType.getJSsetMapType(BingMap.this);
		}
	}

    public class SetViewOptionsBehavior extends JSMethodBehavior
	{
		private static final long serialVersionUID = 1L;

		private final BViewOptions viewOptions;

		public SetViewOptionsBehavior(String event, BViewOptions viewOptions)
		{
			super(event);
			this.viewOptions = viewOptions;
		}

		@Override
		protected String getJSinvoke()
		{
			return viewOptions.getJSsetView(BingMap.this);
		}
	}

	public class OverlayListener extends AbstractDefaultAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		@Override
		protected void respond(AjaxRequestTarget target)
		{
			Request request = RequestCycle.get().getRequest();

			String overlayId = request.getParameter("overlay.overlayId");
			String event = request.getParameter("overlay.event");
			// TODO this is ugly
			// the id's of the Overlays are unique within the ArrayList
			// maybe we should change that collection
			for (BOverlay overlay : overlays)
			{
				if (overlay.getId().equals(overlayId))
				{
					overlay.onEvent(target, BEvent.valueOf(event));
					break;
				}
			}
		}

		public Object getJSinit()
		{
			return BingMap.this.getJSinvoke("overlayListenerCallbackUrl = '" + this.getCallbackUrl() + "'");
		}
	}
}