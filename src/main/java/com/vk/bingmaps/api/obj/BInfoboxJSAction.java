package com.vk.bingmaps.api.obj;

import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * @author victor.konopelko
 *         Date: 11.08.11
 */
public class BInfoboxJSAction extends BInfoboxAction {

    private String js;

    public BInfoboxJSAction(String label, String js) {
        super(label);
        this.js = js;
    }

    public String getJs() {
        return js;
    }

    public void setJs(String js) {
        this.js = js;
    }

    public String getJSActionLiteral()
	{
		return "{label :'" + getLabel() + "', eventHandler: " + getJs() + "}";
	}

    @Override
    protected void onEvent(AjaxRequestTarget target) {
        //no callbacks
    }
}
