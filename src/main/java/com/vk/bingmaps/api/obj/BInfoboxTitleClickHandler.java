package com.vk.bingmaps.api.obj;

import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * @author victor.konopelko
 *         Date: 11.08.11
 */
public class BInfoboxTitleClickHandler extends BInfoboxAction {

    public BInfoboxTitleClickHandler() {
        super("");
    }

    public String getJSActionLiteral()
	{
		return getCallbackUrl().toString();
	}

    @Override
    protected void onEvent(AjaxRequestTarget target) {
        System.out.println("BInfoboxTitleClickHandler");
    }
}
