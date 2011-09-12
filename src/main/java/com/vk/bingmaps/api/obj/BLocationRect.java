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

import com.vk.bingmaps.api.js.Constructor;
import com.vk.bingmaps.api.js.FactoryMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.StringTokenizer;

/**
 * @author victor.konopelko
 *         Date: 03.08.11
 * <a href="http://msdn.microsoft.com/en-us/library/gg427621.aspx">LocationRect Class</a>.
 */
public class BLocationRect implements Serializable {

    /** log. */
	private static final Logger log = LoggerFactory.getLogger(BLocationRect.class);

    /**
	 * Default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	private final BLocation center;
	private final Float height;
	private final Float width;

    private final BLocation NW;
    private final BLocation SE;

    /**
	 * Construct.
	 *
	 * @param center
	 * @param width
	 * @param height
	 */
	public BLocationRect(BLocation center, float width, float height)
	{
		this.center = center;
		this.width = width;
		this.height = height;
        NW = null;
        SE = null;
	}

    /**
	 * Construct.
	 *
	 * @param nw
	 * @param se
	 */
	public BLocationRect(BLocation nw, BLocation se)
	{
		this.center = null;
		this.width = null;
		this.height = null;
        this.NW = nw;
        this.SE = se;
	}

    public BLocation getCenter() {
        return center;
    }

    public Float getHeight() {
        return height;
    }

    public Float getWidth() {
        return width;
    }

    public BLocation getNW() {
        return NW;
    }

    public BLocation getSE() {
        return SE;
    }

    @Override
	public String toString()
	{
		return getJSconstructor();
	}

	/**
	 * @see
     * @return string represents constructor
	 */
	public String getJSconstructor()
	{
		return (this.NW == null || this.SE == null) ?
                new Constructor("Microsoft.Maps.LocationRect").add(center.getJSconstructor()).add(width).add(height).toJS()
              : new FactoryMethod("Microsoft.Maps.LocationRect", "fromCorners").add(NW.getJSconstructor()).add(SE.getJSconstructor()).toJS();
	}

	@Override
	public int hashCode()
	{
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + (height == null ? 0 : Float.floatToIntBits(height));
		result = PRIME * result + (width == null ? 0 : Float.floatToIntBits(width));
		result = PRIME * result + ((center == null) ? 0 : center.hashCode());
		result = PRIME * result + ((NW == null) ? 0 : NW.hashCode());
		result = PRIME * result + ((SE == null) ? 0 : SE.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
        if (this == obj) return true;
		if (!super.equals(obj)) return false;
		if (getClass() != obj.getClass()) return false;
		if (obj instanceof BLocationRect)
		{
            final BLocationRect t = (BLocationRect) obj;

            if (center == null) {
                if (t.center != null) return false;
            } else if (!center.equals(t.center)) return false;

            if (width == null) {
                if (t.width != null) return false;
            } else if (!width.equals(t.width)) return false;

            if (height == null) {
                if (t.height != null) return false;
            } else if (!height.equals(t.height)) return false;

            if (NW == null) {
                if (t.NW != null) return false;
            } else if (!NW.equals(t.NW)) return false;

            if (SE == null) {
                if (t.SE != null) return false;
            } else if (!SE.equals(t.SE)) return false;
		}
		return true;
	}

    /**
	 * [LocationRect [Location -11.524413647969478,54.14062500000014] 351.56250000000006,144.90969170178522]
	 */
	public static BLocationRect parse(String value)
	{
		StringTokenizer tokenizer;
		try
		{
			tokenizer = new StringTokenizer(value, "[, ]");
		}
		catch (NullPointerException e)
		{
			return null;
		}
		if (tokenizer.countTokens() != 6)
		{
            log.warn("tokenizer.countTokens() != 6");
			return null;
		}

        tokenizer.nextToken();
        tokenizer.nextToken();

        try {
            float lat = Float.valueOf(tokenizer.nextToken());
            float lng = Float.valueOf(tokenizer.nextToken());
            BLocation center = BLocation.LatLng(lat, lng);
            Float width = Float.parseFloat(tokenizer.nextToken());
            Float height = Float.parseFloat(tokenizer.nextToken());
            return new BLocationRect(center, width, height);
        } catch (Exception e) {
            log.warn("Parsing error!", e);
            return null;
        }
	}

    public boolean contains(BLocation point) {
        return
                point.getLatitude() < NW.getLatitude() &&
                point.getLatitude() > SE.getLatitude() &&
                point.getLongitude() > NW.getLongitude() &&
                point.getLongitude() < SE.getLongitude();
    }

}
