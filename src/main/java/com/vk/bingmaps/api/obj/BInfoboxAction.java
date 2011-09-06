package com.vk.bingmaps.api.obj;

import com.vk.bingmaps.api.BingMap;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.protocol.http.RequestUtils;

/**
 * @author victor.konopelko
 *         Date: 11.08.11
 */
public abstract class BInfoboxAction extends AbstractDefaultAjaxBehavior {

    private static final long serialVersionUID = 1L;

    private String label;

    public BInfoboxAction(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
	protected void onBind()
	{
		if (!(getComponent() instanceof BingMap))
		{
			throw new IllegalArgumentException("must be bound to BingMap");
		}
	}

	public String getJSActionLiteral()
	{
		return "{label :'" + getLabel() + "', eventHandler: '" + getCallbackUrl() + "'}";
	}

	protected final BingMap getBingMap()
	{
		return (BingMap)getComponent();
	}

	/**
	 * @see org.apache.wicket.ajax.AbstractDefaultAjaxBehavior#respond(org.apache.wicket.ajax.AjaxRequestTarget)
	 */
	@Override
	protected final void respond(AjaxRequestTarget target)
	{
		onEvent(target);
	}

	/**
	 * Typically response parameters that are meant for this event are picket up
	 * and made available for the further processing.
	 *
	 * @param target
	 *            Target to add the Components, that need to be redrawn, to.
	 */
	protected abstract void onEvent(AjaxRequestTarget target);

}
