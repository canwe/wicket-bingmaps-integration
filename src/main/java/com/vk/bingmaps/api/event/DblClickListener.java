package com.vk.bingmaps.api.event;

import com.vk.bingmaps.api.obj.BLocation;
import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.string.Strings;

/**
 * See "dblclick" in the event section of <a
 * href="http://msdn.microsoft.com/en-us/library/gg427609.aspx">BingMap</a>.
 */
public abstract class DblClickListener extends BEventListenerBehavior {

	@Override
	protected String getEvent() {
		return "dblclick";
	}

	@Override
	protected void onEvent(AjaxRequestTarget target) {

        Request request = RequestCycle.get().getRequest();

        String s = request.getParameter("location");
        if (!Strings.isEmpty(s)) {
            BLocation loc = BLocation.parse(s);

	        onClick(target, loc);
        } else {
            //TODO warn
        }
	}

	protected abstract void onClick(AjaxRequestTarget target, BLocation loc);
}
