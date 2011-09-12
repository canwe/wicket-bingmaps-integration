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
