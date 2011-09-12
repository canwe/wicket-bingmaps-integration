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

import com.vk.bingmaps.api.js.ObjectLiteral;

import java.io.Serializable;

/**
 * @author victor.konopelko
 *         Date: 08.08.11
 * <a href="http://msdn.microsoft.com/en-us/library/gg427629.aspx">PushpinOptions Object</a>.
 */
public class BPushpinOptions implements Serializable, Cloneable, BOverlayOptions {

    /**
	 * Default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

    /**
     * The point on the pushpin icon which is anchored to the pushpin location.
     * An anchor of (0,0) is the top left corner of the icon.
     * The default anchor is the bottom center of the icon.
     */
    private BPoint anchor;

    /**
     * A boolean indicating whether the pushpin can be dragged to a new position with the mouse.
     */
    private boolean draggable = false;

    /**
     * The path of the image to use as the pushpin icon.
     */
    private String icon;

    /**
     * The height of the pushpin, which is the height of the pushpin icon. The default value is 39.
     */
    private Integer height;

    /**
     * The text associated with the pushpin.
     */
    private String text;

    /**
     * The amount the text is shifted from the pushpin icon. The default value is (0,5).
     */
    private BPoint textOffset;

    /**
     * The type of the pushpin, as a string.
     * The pushpin DOM (document object model) node created
     * for the pushpin will have the specified typeName.
     */
    private String typeName;

    /**
     * A boolean indicating whether to show or hide the pushpin. The default value is true.
     * A value of false indicates that the pushpin is hidden, although it is still an entity on the map.
     */
    private boolean visible = true;

    /**
     * The width of the pushpin, which is the width of the pushpin icon. The default value is 25.
     */
    private Integer width;

    /**
     * The z-index of the pushpin with respect to other items on the map.
     */
    private Integer zIndex;

    public BPoint getAnchor() {
        return anchor;
    }

    public void setAnchor(BPoint anchor) {
        this.anchor = anchor;
    }

    public Boolean getDraggable() {
        return draggable;
    }

    public void setDraggable(Boolean draggable) {
        this.draggable = draggable;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getHeight() {
        return height == null ? 39 : height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public BPoint getTextOffset() {
        return textOffset == null ? new BPoint(0, 5) : textOffset;
    }

    public void setTextOffset(BPoint textOffset) {
        this.textOffset = textOffset;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Integer getWidth() {
        return width == null ? 25 : width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getzIndex() {
        return zIndex;
    }

    public void setzIndex(Integer zIndex) {
        this.zIndex = zIndex;
    }

    @Override
    public String toString() {
        return getJSconstructor();
    }

    public String getJSconstructor()
	{
        ObjectLiteral literal = new ObjectLiteral();

        if(icon != null)
        {
            literal.setString("icon", icon);
        }
        if (draggable)
        {
            literal.set("draggable", "true");
        }
        if (anchor != null)
		{
            literal.set("anchor", anchor.getJSconstructor());
        }
		if(height != null)
		{
			literal.set("height", Integer.toString(height));
		}
		if(width != null)
		{
			literal.set("width", Integer.toString(width));
		}
		if(zIndex != null)
		{
			literal.set("zIndex", Integer.toString(zIndex));
		}
		if(text != null)
		{
			literal.setString("text", text);
		}
		if(textOffset != null)
		{
			literal.set("textOffset", textOffset.getJSconstructor());
		}
		if(typeName != null)
		{
			literal.setString("typeName", typeName);
		}
		if(!visible)
		{
			literal.set("visible", "true");
		}

		return literal.toJS();
	}

    @Override
	public int hashCode()
	{
		final int PRIME = 17;
		int result = 1;

        result = PRIME * result + (draggable ? 1231 : 1237);
        result = PRIME * result + (visible ? 1231 : 1237);
		result = PRIME * result + ((anchor == null) ? 0 : anchor.hashCode());
		result = PRIME * result + ((height == null) ? 0 : height.hashCode());
		result = PRIME * result + ((width == null) ? 0 : width.hashCode());
		result = PRIME * result + ((zIndex == null) ? 0 : zIndex.hashCode());
		result = PRIME * result + ((text == null) ? 0 : text.hashCode());
		result = PRIME * result + ((textOffset == null) ? 0 : textOffset.hashCode());
		result = PRIME * result + ((icon == null) ? 0 : icon.hashCode());
		result = PRIME * result + ((typeName == null) ? 0 : typeName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final BPushpinOptions other = (BPushpinOptions)obj;
		if (draggable != other.draggable) return false;
		if (visible != other.visible) return false;
		if (anchor == null) {
			if (other.anchor != null) return false;
		}
		else if (!anchor.equals(other.anchor)) return false;
		if (height == null) {
			if (other.height != null) return false;
		}
		else if (!height.equals(other.height)) return false;
		if (width == null) {
			if (other.width != null) return false;
		}
		else if (!width.equals(other.width)) return false;
		if (zIndex == null) {
			if (other.zIndex != null) return false;
		}
		else if (!zIndex.equals(other.zIndex)) return false;
		if (text == null) {
			if (other.text != null) return false;
		}
		else if (!text.equals(other.text)) return false;
		if (textOffset == null) {
			if (other.textOffset != null) return false;
		}
		else if (!textOffset.equals(other.textOffset)) return false;
		if (icon == null) {
			if (other.icon != null) return false;
		}
		else if (!icon.equals(other.icon)) return false;
		if (typeName == null) {
			if (other.typeName != null) return false;
		}
		else if (!typeName.equals(other.typeName)) return false;
		return true;
	}

    public BPushpinOptions clone() {
		try
		{
			return (BPushpinOptions)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new Error(e);
		}
	}

}
