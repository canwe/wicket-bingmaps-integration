package com.vk.bingmaps.api;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;

/**
 * @author victor.konopelko
 *         Date: 01.09.11
 */
public class ClientSideClusteringHeaderContributor extends HeaderContributor
{
	private static final long serialVersionUID = 1L;

	// We have some custom Javascript.
	private static final ResourceReference BINGMAP_CLUSTERING_JS = new JavascriptResourceReference(BingMap.class, "V7ClientSideClustering.js");

	public ClientSideClusteringHeaderContributor()
	{
		super(new IHeaderContributor()
		{
			private static final long serialVersionUID = 1L;

			public void renderHead(IHeaderResponse response)
			{
				response.renderJavascriptReference(BINGMAP_CLUSTERING_JS);
			}
		});
	}
}