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
import org.apache.wicket.ajax.WicketAjaxReference;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WicketEventReference;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;

public class BMapHeaderContributor extends HeaderContributor
{
	private static final long serialVersionUID = 1L;

	// URL for Bing Maps' API endpoint.
	private static final String BMAP_API_URL = "http://ecn.dev.virtualearth.net/mapcontrol/mapcontrol.ashx?v=7.0";

	// We have some custom Javascript.
	private static final ResourceReference WICKET_BINGMAP_JS = new JavascriptResourceReference(BingMap.class, "wicket-bingmap.js");

	public BMapHeaderContributor(final String bMapKey)
	{
		super(new IHeaderContributor()
		{
			private static final long serialVersionUID = 1L;

			/**
			 * see: <a
			 * href="http://www.google.com/apis/maps/documentation/#Memory_Leaks">IE
			 * memory leak issues</a>
			 * 
			 * @see org.apache.wicket.markup.html.IHeaderContributor#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
			 */
			public void renderHead(IHeaderResponse response)
			{
				response.renderJavascriptReference(BMAP_API_URL);
				response.renderJavascriptReference(WicketEventReference.INSTANCE);
				response.renderJavascriptReference(WicketAjaxReference.INSTANCE);
				response.renderJavascriptReference(WICKET_BINGMAP_JS);
			}
		});
	}
}
