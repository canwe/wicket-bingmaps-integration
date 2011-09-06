package com.vk.bingmaps.api.obj;

import com.vk.bingmaps.api.BingMap;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;

import java.io.Serializable;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/**
 * @author victor.konopelko
 *         Date: 08.08.11
 */
public abstract class BOverlay implements Serializable {

    private static final long serialVersionUID = 1L;

	/**
	 * id of this object. it is session unique.
	 */
	private final String id;

	private BingMap parent = null;

	private final Map<BEvent, BEventHandler> events = new EnumMap<BEvent, BEventHandler>(BEvent.class);

	/**
	 * Construct.
	 */
	public BOverlay()
	{
		// id is session unique
		id = String.valueOf(Session.get().nextSequenceValue());
	}

	/**
	 * @return String representing the JavaScript add command for the
	 *         corresponding JavaScript object.
	 */
	public String getJSadd()
	{
		StringBuffer js = new StringBuffer(parent.getJSinvoke("addOverlay('" + getId() + "', "
				+ getJSconstructor() + ")"));
		// Add the Events
		for (BEvent event : events.keySet())
		{
			js.append(event.getJSadd(this));
		}
		return js.toString();
	}

	/**
	 * @return String representing the JavaScript add command for the
	 *         corresponding JavaScript object.
	 */
	public String getJSObjectLiteralWithOvlId()
	{
		StringBuffer js = new StringBuffer("{");
		js.append("'overlayId': '" + getId() + "'");
        js.append(", ");
        js.append("'overlay': " + getJSconstructor());
        js.append("}");
        return js.toString();
	}

	/**
	 * @return String representing the JavaScript remove command for the
	 *         corresponding JavaScript object.
	 */
	public String getJSremove()
	{
		StringBuffer js = new StringBuffer();
		// clear the Events
		for (BEvent event : events.keySet())
		{
			js.append(event.getJSclear(this));
		}
		js.append(parent.getJSinvoke("removeOverlay('" + getId() + "')"));
		return js.toString();
	}

	/**
	 * @return The session unique id of this object as a String.
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * Implement the needed JavaScript constructor for the corresponding
	 * JavaScript object.
	 *
	 * @return String representing the JavaScript constructor.
	 */
	public abstract String getJSconstructor();

	public BingMap getParent()
	{
		return parent;
	}

	public void setParent(BingMap parent)
	{
		this.parent = parent;
	}

	/**
	 * Add a control.
	 *
	 * @param event event
     * @param handler handler to add
	 * @return This
	 */
	public BOverlay addListener(BEvent event, BEventHandler handler)
	{
		events.put(event, handler);

		if (AjaxRequestTarget.get() != null)
		// TODO
		// && getParent().findPage() != null)
		{
			AjaxRequestTarget.get().appendJavascript(event.getJSadd(this));
		}

		return this;
	}

	/**
	 * Return all registered Listeners.
	 *
	 * @return registered listeners
	 */
	public Map<BEvent, BEventHandler> getListeners()
	{
		return Collections.unmodifiableMap(events);
	}

	/**
	 * Clear listeners.
	 *
	 * @param event event to be cleared.
	 * @return This
	 */
	public BOverlay clearListeners(BEvent event)
	{
		events.remove(event);

		if (AjaxRequestTarget.get() != null)
		// TODO
		// && findPage() != null)
		{
			AjaxRequestTarget.get().appendJavascript(event.getJSclear(this));
		}

		return this;
	}

    /**
	 * @return options
	 */
    public abstract BOverlayOptions getOptions();

	/**
	 * Called when an Ajax call occurs.
	 *
	 * @param target ajax request target
	 * @param overlayEvent event
	 */
	public void onEvent(AjaxRequestTarget target, BEvent overlayEvent)
	{
		updateOnAjaxCall(target, overlayEvent);
		events.get(overlayEvent).onEvent(target);
	}

	/**
	 * Implement to handle Ajax calls to your needs.
	 *
	 * @param target ajax request target
	 * @param overlayEvent event
	 */
	protected abstract void updateOnAjaxCall(AjaxRequestTarget target, BEvent overlayEvent);
}
