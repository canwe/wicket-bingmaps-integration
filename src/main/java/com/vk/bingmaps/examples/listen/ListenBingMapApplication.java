package com.vk.bingmaps.examples.listen;

import com.vk.bingmaps.examples.BMapExampleApplication;
import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.HttpSessionStore;
import org.apache.wicket.session.ISessionStore;

public class ListenBingMapApplication extends BMapExampleApplication
{

	/**
	 * @see org.apache.wicket.protocol.http.WebApplication#init()
	 */
	@Override
	protected void init()
	{
		super.init();
	}

	@Override
	public Class<? extends Page> getHomePage()
	{
		return HomePage.class;
	}

	@Override
	protected ISessionStore newSessionStore()
	{
		return new HttpSessionStore(this);
	}
}
