package com.vk.bingmaps.examples.simple;

import com.vk.bingmaps.examples.BMapExampleApplication;
import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.HttpSessionStore;
import org.apache.wicket.session.ISessionStore;

public class SimpleBingMapApplication extends BMapExampleApplication
{
	@Override
	protected void init()
	{
		super.init();
	}

	@Override
	public Class<? extends Page> getHomePage()
	{
		return SimplePage.class;
	}

	@Override
	protected ISessionStore newSessionStore()
	{
		return new HttpSessionStore(this);
	}
}
