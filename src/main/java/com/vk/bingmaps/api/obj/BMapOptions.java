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
import org.apache.wicket.util.string.Strings;

import java.io.Serializable;

/**
 * @author victor.konopelko
 *         Date: 03.08.11
 * <a href="http://msdn.microsoft.com/en-us/library/gg427603.aspx">MapOptions Object</a>.
 */
public class BMapOptions implements Serializable, Cloneable {

    private BColor backgroundColor = new BColor(0, 244, 242, 238);

    private String credentials;

    private boolean disableBirdseye = false;
    private boolean disableKeyboardInput = false;
    private boolean disableMouseInput = false;
    private boolean disablePanning = false;
    private boolean disableTouchInput = false;
    private boolean disableUserInput = false;
    private boolean disableZooming = false;
    private boolean enableClickableLogo = true;
    private boolean enableSearchLogo = true;
    private boolean fixedMapPosition = false;
    private boolean showBreadcrumb = false;
    private boolean showCopyright = true;
    private boolean showDashboard = true;
    private boolean showMapTypeSelector = true;
    private boolean showScalebar = true;
    private boolean useInertia = true;
    private int tileBuffer = 0;
    private float inertiaIntensity = 0.85f;
    private Integer zoom;
    private BLocation center;

    private boolean clusteringEnabled = false;

    private Integer height = null;
    private Integer width = null;

    public BMapOptions(String credentials) {
        if (Strings.isEmpty(credentials)) throw new IllegalArgumentException("BingMaps API Key cannot be null");
        this.credentials = credentials;
    }

    public String getCredentials() {
        return credentials;
    }

    public BColor getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(BColor backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public boolean isDisableBirdseye() {
        return disableBirdseye;
    }

    public void setDisableBirdseye(boolean disableBirdseye) {
        this.disableBirdseye = disableBirdseye;
    }

    public boolean isDisableKeyboardInput() {
        return disableKeyboardInput;
    }

    public void setDisableKeyboardInput(boolean disableKeyboardInput) {
        this.disableKeyboardInput = disableKeyboardInput;
    }

    public boolean isDisableMouseInput() {
        return disableMouseInput;
    }

    public void setDisableMouseInput(boolean disableMouseInput) {
        this.disableMouseInput = disableMouseInput;
    }

    public boolean isDisablePanning() {
        return disablePanning;
    }

    public void setDisablePanning(boolean disablePanning) {
        this.disablePanning = disablePanning;
    }

    public boolean isDisableTouchInput() {
        return disableTouchInput;
    }

    public void setDisableTouchInput(boolean disableTouchInput) {
        this.disableTouchInput = disableTouchInput;
    }

    public boolean isDisableUserInput() {
        return disableUserInput;
    }

    public void setDisableUserInput(boolean disableUserInput) {
        this.disableUserInput = disableUserInput;
    }

    public boolean isDisableZooming() {
        return disableZooming;
    }

    public void setDisableZooming(boolean disableZooming) {
        this.disableZooming = disableZooming;
    }

    public boolean isEnableClickableLogo() {
        return enableClickableLogo;
    }

    public void setEnableClickableLogo(boolean enableClickableLogo) {
        this.enableClickableLogo = enableClickableLogo;
    }

    public boolean isEnableSearchLogo() {
        return enableSearchLogo;
    }

    public void setEnableSearchLogo(boolean enableSearchLogo) {
        this.enableSearchLogo = enableSearchLogo;
    }

    public boolean isFixedMapPosition() {
        return fixedMapPosition;
    }

    public void setFixedMapPosition(boolean fixedMapPosition) {
        this.fixedMapPosition = fixedMapPosition;
    }

    public boolean isShowBreadcrumb() {
        return showBreadcrumb;
    }

    public void setShowBreadcrumb(boolean showBreadcrumb) {
        this.showBreadcrumb = showBreadcrumb;
    }

    public boolean isShowCopyright() {
        return showCopyright;
    }

    public void setShowCopyright(boolean showCopyright) {
        this.showCopyright = showCopyright;
    }

    public boolean isShowDashboard() {
        return showDashboard;
    }

    public void setShowDashboard(boolean showDashboard) {
        this.showDashboard = showDashboard;
    }

    public boolean isShowMapTypeSelector() {
        return showMapTypeSelector;
    }

    public void setShowMapTypeSelector(boolean showMapTypeSelector) {
        this.showMapTypeSelector = showMapTypeSelector;
    }

    public boolean isShowScalebar() {
        return showScalebar;
    }

    public void setShowScalebar(boolean showScalebar) {
        this.showScalebar = showScalebar;
    }

    public boolean isUseInertia() {
        return useInertia;
    }

    public void setUseInertia(boolean useInertia) {
        this.useInertia = useInertia;
    }

    public int getTileBuffer() {
        return tileBuffer;
    }

    public void setTileBuffer(int tileBuffer) {
        this.tileBuffer = tileBuffer;
    }

    public float getInertiaIntensity() {
        return inertiaIntensity;
    }

    public void setInertiaIntensity(float inertiaIntensity) {
        this.inertiaIntensity = inertiaIntensity;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public boolean isClusteringEnabled() {
        return clusteringEnabled;
    }

    public void setClusteringEnabled(boolean clusteringEnabled) {
        this.clusteringEnabled = clusteringEnabled;
    }

    public BLocation getCenter() {
        return center;
    }

    public void setCenter(BLocation center) {
        this.center = center;
    }

    public Integer getZoom() {
        return zoom;
    }

    public void setZoom(Integer zoom) {
        this.zoom = zoom;
    }

    public String getJSconstructor()
	{
		ObjectLiteral literal = new ObjectLiteral();

        literal.set("tileBuffer", Integer.toString(tileBuffer));
        literal.set("inertiaIntensity", Float.toString(inertiaIntensity));
        literal.setString("credentials", credentials);
		if (height != null && width != null)
		{
			literal.set("height", Integer.toString(height));
			literal.set("width", Integer.toString(width));
		}
		if (disableBirdseye)
		{
			literal.set("disableBirdseye", "true");
		}
		if (disableKeyboardInput)
		{
			literal.set("disableKeyboardInput", "true");
		}
		if (disableMouseInput)
		{
			literal.set("disableMouseInput", "true");
		}
		if (disablePanning)
		{
			literal.set("disablePanning", "true");
		}
		if (disableTouchInput)
		{
			literal.set("disableTouchInput", "true");
		}
		if (disableUserInput)
		{
			literal.set("disableUserInput", "true");
		}
		if (disableZooming)
		{
			literal.set("disableZooming", "true");
		}
		if (fixedMapPosition)
		{
			literal.set("fixedMapPosition", "true");
		}
		if (showBreadcrumb)
		{
			literal.set("showBreadcrumb", "true");
		}
		if (!enableClickableLogo)
		{
			literal.set("enableClickableLogo", "false");
		}
		if (!enableSearchLogo)
		{
			literal.set("enableSearchLogo", "false");
		}
		if (!showCopyright)
		{
			literal.set("showCopyright", "false");
		}
		if (!showDashboard)
		{
			literal.set("showDashboard", "false");
		}
		if (!showMapTypeSelector)
		{
			literal.set("showMapTypeSelector", "false");
		}
		if (!showScalebar)
		{
			literal.set("showScalebar", "false");
		}
		if (!useInertia)
		{
			literal.set("useInertia", "false");
		}
		if(backgroundColor != null)
		{
			literal.set("backgroundColor", backgroundColor.getJSconstructor());
		}
		if(zoom != null)
		{
			literal.set("zoom", zoom.toString());
		}
		if(center != null)
		{
			literal.set("center", center.getJSconstructor());
		}

		return literal.toJS();
	}

    @Override
	public int hashCode()
	{
		final int PRIME = 17;
		int result = 1;

		result = PRIME * result + (disableBirdseye ? 1231 : 1237);
		result = PRIME * result + (disableKeyboardInput ? 1231 : 1237);
		result = PRIME * result + (disableMouseInput ? 1231 : 1237);
		result = PRIME * result + (disablePanning ? 1231 : 1237);
		result = PRIME * result + (disableTouchInput ? 1231 : 1237);
		result = PRIME * result + (disableUserInput ? 1231 : 1237);
		result = PRIME * result + (disableZooming ? 1231 : 1237);
		result = PRIME * result + (enableClickableLogo ? 1231 : 1237);
		result = PRIME * result + (enableSearchLogo ? 1231 : 1237);
		result = PRIME * result + (fixedMapPosition ? 1231 : 1237);
		result = PRIME * result + (showBreadcrumb ? 1231 : 1237);
		result = PRIME * result + (showCopyright ? 1231 : 1237);
		result = PRIME * result + (showDashboard ? 1231 : 1237);
		result = PRIME * result + (showMapTypeSelector ? 1231 : 1237);
		result = PRIME * result + (showScalebar ? 1231 : 1237);
		result = PRIME * result + (useInertia ? 1231 : 1237);
		result = PRIME * result + tileBuffer;
		result = PRIME * result + Float.floatToIntBits(inertiaIntensity);
		result = PRIME * result + ((credentials == null) ? 0 : credentials.hashCode());
		result = PRIME * result + ((height == null) ? 0 : height.hashCode());
		result = PRIME * result + ((width == null) ? 0 : width.hashCode());
		result = PRIME * result + ((backgroundColor == null) ? 0 : backgroundColor.hashCode());
		result = PRIME * result + ((zoom == null) ? 0 : zoom.hashCode());
		result = PRIME * result + ((center == null) ? 0 : center.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final BMapOptions other = (BMapOptions)obj;
		if (disableBirdseye != other.disableBirdseye) return false;
		if (disableKeyboardInput != other.disableKeyboardInput) return false;
		if (disableMouseInput != other.disableMouseInput) return false;
		if (disablePanning != other.disablePanning)return false;
		if (disableTouchInput != other.disableTouchInput) return false;
		if (disableUserInput != other.disableUserInput) return false;
		if (disableZooming != other.disableZooming) return false;
		if (enableClickableLogo != other.enableClickableLogo) return false;
		if (enableSearchLogo != other.enableSearchLogo) return false;
		if (fixedMapPosition != other.fixedMapPosition) return false;
		if (showBreadcrumb != other.showBreadcrumb) return false;
		if (showCopyright != other.showCopyright) return false;
		if (showDashboard != other.showDashboard) return false;
		if (showMapTypeSelector != other.showMapTypeSelector) return false;
		if (showScalebar != other.showScalebar) return false;
		if (useInertia != other.useInertia) return false;
		if (tileBuffer != other.tileBuffer) return false;
		if (inertiaIntensity != other.inertiaIntensity) return false;
		if (height == null) {
			if (other.height != null) return false;
		}
		else if (!height.equals(other.height)) return false;
		if (width == null) {
			if (other.width != null) return false;
		}
		else if (!width.equals(other.width)) return false;
		if (backgroundColor == null) {
			if (other.backgroundColor != null) return false;
		}
		else if (!backgroundColor.equals(other.backgroundColor)) return false;
		if (credentials == null) {
			if (other.credentials != null) return false;
		}
		else if (!credentials.equals(other.credentials)) return false;
		if (zoom == null) {
			if (other.zoom != null) return false;
		}
		else if (!zoom.equals(other.zoom)) return false;
		if (center == null) {
			if (other.center != null) return false;
		}
		else if (!center.equals(other.center)) return false;
		return true;
	}

    public BMapOptions clone() {
		try
		{
			return (BMapOptions)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new Error(e);
		}
	}
}
