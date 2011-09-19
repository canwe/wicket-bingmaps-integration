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

import com.vk.bingmaps.api.BingMap;
import com.vk.bingmaps.api.js.ObjectLiteral;

import java.io.Serializable;

/**
 * @author victor.konopelko
 *         Date: 03.08.11
 * <a href="http://msdn.microsoft.com/en-us/library/gg427628.aspx">ViewOptions Object</a>.
 */
public class BViewOptions implements Serializable, Cloneable {

    /**
	 * Default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

    /**
     * A boolean that specifies whether to animate map navigation.
     * Note that this option is associated with each setView call and defaults to true if not specified.
     */
    private boolean animate = true;

    /**
     * A constant indicating how map labels are displayed.
     */
    private BLabelOverlay labelOverlay;

    /**
     * The map type of the view. Valid map types are found in the
     * <a href="http://msdn.microsoft.com/en-us/library/gg427625.aspx">MapTypeId Enumeration</a> topic.
     *
     * @see BMapTypeId
     */
    private BMapTypeId mapTypeId;

    /**
     * The zoom level of the map view.
     */
    private Integer zoom;

    /**
     * The location of the center of the map view.
     * If both are specified, bounds takes precedence over center.
     */
    private BLocation center;

    /**
     * The bounding rectangle of the map view.
     * If both are specified, bounds takes precedence over center.
     */
    private BLocationRect bounds;

    /**
     * The amount of padding to be added to each side of the bounds of the map view.
     */
    private Float padding;

    public boolean isAnimate() {
        return animate;
    }

    public void setAnimate(boolean animate) {
        this.animate = animate;
    }

    public BLabelOverlay getLabelOverlay() {
        return labelOverlay;
    }

    public void setLabelOverlay(BLabelOverlay labelOverlay) {
        this.labelOverlay = labelOverlay;
    }

    public BMapTypeId getMapTypeId() {
        return mapTypeId;
    }

    public void setMapTypeId(BMapTypeId mapTypeId) {
        this.mapTypeId = mapTypeId;
    }

    public Integer getZoom() {
        return zoom;
    }

    public void setZoom(Integer zoom) {
        this.zoom = zoom;
    }

    public BLocation getCenter() {
        return center;
    }

    public void setCenter(BLocation center) {
        this.center = center;
    }

    public BLocationRect getBounds() {
        return bounds;
    }

    public void setBounds(BLocationRect bounds) {
        this.bounds = bounds;
    }

    public Float getPadding() {
        return padding;
    }

    public void setPadding(Float padding) {
        this.padding = padding;
    }

    @Override
    public String toString() {
        return getJSconstructor();
    }

    public String getJSconstructor()
	{
		ObjectLiteral literal = new ObjectLiteral();

		if (mapTypeId != null)
		{
			literal.set("mapTypeId", mapTypeId.toString());
		}
		if (!animate)
		{
			literal.set("animate", "false");
		}
		if(labelOverlay != null)
		{
			literal.set("labelOverlay", labelOverlay.toString());
		}
		if(zoom != null)
		{
			literal.set("zoom", Integer.toString(zoom));
		}
		if(center != null)
		{
			literal.set("center", center.getJSconstructor());
		}
		if(bounds != null)
		{
			literal.set("bounds", bounds.getJSconstructor());
		}
		if(padding != null)
		{
			literal.set("padding", Float.toString(padding));
		}

		return literal.toJS();
	}

    @Override
	public int hashCode()
	{
		final int PRIME = 17;
		int result = 1;

        result = PRIME * result + (animate ? 1231 : 1237);
		result = PRIME * result + ((padding == null) ? 0 : Float.floatToIntBits(padding));
		result = PRIME * result + ((bounds == null) ? 0 : bounds.hashCode());
		result = PRIME * result + ((center == null) ? 0 : center.hashCode());
		result = PRIME * result + ((mapTypeId == null) ? 0 : mapTypeId.hashCode());
		result = PRIME * result + ((zoom == null) ? 0 : zoom.hashCode());
		result = PRIME * result + ((labelOverlay == null) ? 0 : labelOverlay.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final BViewOptions other = (BViewOptions)obj;
		if (animate != other.animate) return false;
		if (padding == null) {
			if (other.padding != null) return false;
		}
		else if (!padding.equals(other.padding)) return false;
		if (bounds == null) {
			if (other.bounds != null) return false;
		}
		else if (!bounds.equals(other.bounds)) return false;
		if (center == null) {
			if (other.center != null) return false;
		}
		else if (!center.equals(other.center)) return false;
		if (mapTypeId == null) {
			if (other.mapTypeId != null) return false;
		}
		else if (!mapTypeId.equals(other.mapTypeId)) return false;
		if (zoom == null) {
			if (other.zoom != null) return false;
		}
		else if (!zoom.equals(other.zoom)) return false;
		if (labelOverlay == null) {
			if (other.labelOverlay != null) return false;
		}
		else if (!labelOverlay.equals(other.labelOverlay)) return false;
		return true;
	}

    public String getJSsetView(BingMap map)
	{
		return map.getJSinvoke("setView(" + toString() + ")");
	}

    public BViewOptions clone() {
		try
		{
			return (BViewOptions)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new Error(e);
		}
	}

}
