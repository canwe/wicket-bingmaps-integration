package com.vk.bingmaps.api.obj;

import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * @author victor.konopelko
 *         Date: 11.08.11
 */
public class BInfoboxAjaxAction extends BInfoboxAction {

    public BInfoboxAjaxAction(String label) {
        super(label);
    }

    @Override
    protected void onEvent(AjaxRequestTarget target) {
        System.out.println("BInfoboxAjaxAction");
    }
}
