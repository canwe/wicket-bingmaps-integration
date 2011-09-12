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