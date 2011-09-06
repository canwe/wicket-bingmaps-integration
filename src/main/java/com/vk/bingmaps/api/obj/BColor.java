package com.vk.bingmaps.api.obj;

import com.vk.bingmaps.api.js.Constructor;

import java.io.Serializable;

/**
 * @author victor.konopelko
 *         Date: 03.08.11
 * <a href="http://msdn.microsoft.com/en-us/library/gg427627.aspx">Color Class</a>.
 */
public class BColor implements Serializable {

    /**
	 * Default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	private final int a;
	private final int r;
	private final int g;
	private final int b;

    public BColor(int a, int r, int g, int b) {
        this.a = a;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public int getA() {
        return a;
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

    public String getJSconstructor()
	{
		return new Constructor("Microsoft.Maps.Color").add(a).add(r).add(g).add(b).toJS();
	}

	@Override
	public int hashCode()
	{
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + a;
		result = PRIME * result + r;
		result = PRIME * result + g;
		result = PRIME * result + b;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (!super.equals(obj)) return false;
		if (getClass() != obj.getClass()) return false;
		final BColor other = (BColor)obj;
		if (a != other.a) return false;
		if (r != other.r) return false;
		if (g != other.g) return false;
		if (b != other.b) return false;
		return true;
	}
}
