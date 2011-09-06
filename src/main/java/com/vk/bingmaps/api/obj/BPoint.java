package com.vk.bingmaps.api.obj;

import com.vk.bingmaps.api.js.Constructor;

import java.io.Serializable;

/**
 * @author victor.konopelko
 *         Date: 03.08.11
 */
public class BPoint implements Serializable {

    private final int x;
    private final int y;

    public BPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getJSconstructor()
	{
		return new Constructor("Microsoft.Maps.Point").add(x).add(y).toJS();
	}

	@Override
	public int hashCode()
	{
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + x;
		result = PRIME * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (!super.equals(obj)) return false;
		if (getClass() != obj.getClass()) return false;
		final BPoint other = (BPoint)obj;
		if (x != other.x) return false;
		if (y != other.y) return false;
		return true;
	}
}
