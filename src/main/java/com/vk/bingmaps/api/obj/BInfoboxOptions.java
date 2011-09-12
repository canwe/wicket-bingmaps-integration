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

import com.vk.bingmaps.api.js.ArrayLiteral;
import com.vk.bingmaps.api.js.ObjectLiteral;

import java.io.Serializable;
import java.util.*;

/**
 * @author victor.konopelko
 *         Date: 10.08.11
 * <a href="http://msdn.microsoft.com/en-us/library/gg675210.aspx">InfoboxOptions Object</a>.
 */
public class BInfoboxOptions implements Serializable, Cloneable, BOverlayOptions {

    /**
	 * Default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

    private BInfobox parent = null;

    private String  description;      //The string displayed inside the info box.
    private String  id;               //The ID associated with the info box.
    private String  title;            //The title of the info box.
    private Integer height;           //The height of the info box. The default value is 126.
    private Integer width;            //The width of the info box. The default value is 256.
    private Integer zIndex;           //The z-index of the info box with respect to other items on the map.

    private BInfoboxTitleClickHandler titleClickHandler;
    private BLocation location;       //The location on the map where the info box’s anchor is attached.

    private Set<BInfoboxAction> actions = new HashSet<BInfoboxAction>();

    /**
     * A boolean indicating whether to show or hide the info box.
     * The default value is true.
     * A value of false indicates that the info box is hidden,
     * although it is still an entity on the map.
     */
    private boolean visible = true;

    /**
     * A boolean indicating whether to show the close dialog button on the info box.
     * The default value is true.
     * By default the close button is displayed as an X in the top right corner of the info box.
     *
     * This property is ignored if custom HTML is used to represent the info box.
     */
    private boolean showCloseButton = true;

    /**
     * The HTML that represents the info box.
     * If custom HTML is used to represent the info box,
     * the info box is anchored at the top-left corner.
     * var infoboxOptions = {
     *                          width :200,
     *                          height :100,
     *                          showCloseButton: true,
     *                          zIndex: 0,
     *                          offset:new Microsoft.Maps.Point(10,0),
     *                          showPointer: true,
     *                          htmlContent:'<b>Custom HTML</b>'
     *                      };
     */
    private String htmlContent;

    public BInfobox getParent()
	{
		return parent;
	}

	public void setParent(BInfobox parent)
	{
		this.parent = parent;
	}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getHeight() {
        return height == null ? 126 : height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width == null ? 256 : width;
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

    public BInfoboxTitleClickHandler getTitleClickHandler() {
        return titleClickHandler;
    }

    public void setTitleClickHandler(BInfoboxTitleClickHandler titleClickHandler) {
        this.titleClickHandler = titleClickHandler;
    }

    public BLocation getLocation() {
        return location;
    }

    public void setLocation(BLocation location) {
        this.location = location;
    }

    public Set<BInfoboxAction> getActions() {
        return Collections.unmodifiableSet(actions);
    }

    public void addActions(Collection<BInfoboxAction> actions) {
        this.actions.addAll(actions);
    }

    public void addActions(BInfoboxAction... actions) {
        this.actions.addAll(Arrays.asList(actions));
    }

    public void clearActions() {
        this.actions.clear();
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isShowCloseButton() {
        return showCloseButton;
    }

    public void setShowCloseButton(boolean showCloseButton) {
        this.showCloseButton = showCloseButton;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    @Override
    public String toString() {
        return getJSconstructor();
    }

    public String getJSconstructor()
	{
        ObjectLiteral literal = new ObjectLiteral();

        if(description != null)
        {
            literal.setString("description", description);
        }
        if (!visible)
        {
            literal.set("visible", "false");
        }
        if (!showCloseButton)
		{
            literal.set("showCloseButton", "false");
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
		if(title != null)
		{
			literal.setString("title", title);
		}
		if(id != null)
		{
			literal.setString("id", id);
		}
		if(htmlContent != null)
		{
			literal.setString("htmlContent", htmlContent);
		}
		if(location != null)
		{
			literal.set("location", location.getJSconstructor());
		}
		if(titleClickHandler != null)
		{
			literal.setString("titleClickHandler", titleClickHandler.getJSActionLiteral());
		}
		if(!actions.isEmpty())
		{
			literal.set("actions", ArrayLiteral(actions).toJS());
		}

		return literal.toJS();
	}

    @Override
	public int hashCode()
	{
		final int PRIME = 17;
		int result = 1;

        result = PRIME * result + (showCloseButton ? 1231 : 1237);
        result = PRIME * result + (visible ? 1231 : 1237);
		result = PRIME * result + ((location == null) ? 0 : location.hashCode());
		result = PRIME * result + ((height == null) ? 0 : height.hashCode());
		result = PRIME * result + ((width == null) ? 0 : width.hashCode());
		result = PRIME * result + ((zIndex == null) ? 0 : zIndex.hashCode());
		result = PRIME * result + ((title == null) ? 0 : title.hashCode());
		result = PRIME * result + ((titleClickHandler == null) ? 0 : titleClickHandler.hashCode());
		result = PRIME * result + ((actions == null) ? 0 : actions.hashCode());
		result = PRIME * result + ((htmlContent == null) ? 0 : htmlContent.hashCode());
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((description == null) ? 0 : description.hashCode());
		return result;
	}

    private ArrayLiteral ArrayLiteral(Set<BInfoboxAction> actions) {
        ArrayLiteral arLiteral = new ArrayLiteral();
        for (BInfoboxAction action : actions) {
            //arLiteral.setObjectLiteral("{label :'" + action.getLabel() + "', eventHandler: " + actionEventHandler(action) + "}");
            arLiteral.addObjectLiteral(action.getJSActionLiteral());
        }
        return arLiteral;
    }

    private String actionEventHandler(BInfoboxAction action) {
        return getParent().getParent().getJSinvokeReference("overlaysActions[''" + getId() + "':'" + action.getLabel() + "]");
    }

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final BInfoboxOptions other = (BInfoboxOptions)obj;
		if (showCloseButton != other.showCloseButton) return false;
		if (visible != other.visible) return false;
		if (location == null) {
			if (other.location != null) return false;
		}
		else if (!location.equals(other.location)) return false;
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
		if (title == null) {
			if (other.title != null) return false;
		}
		else if (!title.equals(other.title)) return false;
		if (titleClickHandler == null) {
			if (other.titleClickHandler != null) return false;
		}
		else if (!titleClickHandler.equals(other.titleClickHandler)) return false;
		if (description == null) {
			if (other.description != null) return false;
		}
		else if (!description.equals(other.description)) return false;
		if (id == null) {
			if (other.id != null) return false;
		}
		else if (!id.equals(other.id)) return false;
		if (htmlContent == null) {
			if (other.htmlContent != null) return false;
		}
		else if (!htmlContent.equals(other.htmlContent)) return false;
		if (location == null) {
			if (other.location != null) return false;
		}
		else if (!location.equals(other.location)) return false;
		if (!actions.equals(other.actions)) return false;
		return true;
	}

    public BInfoboxOptions clone() {
		try
		{
			return (BInfoboxOptions)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new Error(e);
		}
	}
}
