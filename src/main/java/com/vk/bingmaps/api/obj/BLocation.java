package com.vk.bingmaps.api.obj;

import com.vk.bingmaps.api.js.Constructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * @author victor.konopelko
 *         Date: 03.08.11
 * <a href="http://msdn.microsoft.com/en-us/library/gg427612.aspx">Location Class</a>.
 */
public class BLocation implements Serializable {

    /** log. */
	private static final Logger log = LoggerFactory.getLogger(BLocation.class);

    private static Random r;

    /**
	 * Default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	private float longitude;
	private float latitude;

    public static BLocation LatLng(float lat, float lng) {
        return new BLocation(lng, lat);
    }

    public static BLocation LngLat(float lng, float lat) {
        return new BLocation(lng, lat);
    }

    public static BLocation LatLng(double lat, double lng) {
        return new BLocation(Double.valueOf(lng).floatValue(), Double.valueOf(lat).floatValue());
    }

    public static BLocation LngLat(double lng, double lat) {
        return new BLocation(Double.valueOf(lng).floatValue(), Double.valueOf(lat).floatValue());
    }

	protected BLocation(float longitude, float latitude)
	{
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public float getLongitude()
	{
		return longitude;
	}

	public float getLatitude()
	{
		return latitude;
	}

    @Override
	public String toString()
	{
		return getJSconstructor();
	}

	public String getJSconstructor()
	{
		return new Constructor("Microsoft.Maps.Location").add(latitude).add(longitude).toJS();
	}

	@Override
	public int hashCode()
	{
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + Float.floatToIntBits(latitude);
		result = PRIME * result + Float.floatToIntBits(longitude);
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (!super.equals(obj)) return false;
		if (getClass() != obj.getClass()) return false;
		final BLocation other = (BLocation)obj;
		if (Float.floatToIntBits(latitude) != Float.floatToIntBits(other.latitude)) return false;
		if (Float.floatToIntBits(longitude) != Float.floatToIntBits(other.longitude)) return false;
		return true;
	}

    /**
	 * [Location 60.93043220292313,-121.64062499999989]
	 */
	public static BLocation parse(String value)
	{
		try
		{
			StringTokenizer tokenizer = new StringTokenizer(value, "[ ,]");

            tokenizer.nextToken();
			float lat = Float.valueOf(tokenizer.nextToken());
			float lng = Float.valueOf(tokenizer.nextToken());
			return BLocation.LatLng(lat, lng);
		}
		catch (Exception e)
		{
            log.warn("Parsing error!", e);
			return null;
		}
	}

    /**
     * Creates a random latitude and longitude. (Not inclusive of (-90, 0))
     * @return BLocation
     */
    public static BLocation random() {
        return random(r = new Random());
    }

    /**
     * Creates a random latitude and longitude. (Not inclusive of (-90, 0))
     * @return BLocation
     */
    public static BLocation next() {
        if (r == null) r = new Random();
        return random(r = new Random());
    }

    /**
     * Creates a random latitude and longitude. (Not inclusive of (-90, 0))
     *
     * @param r the random number generator to use, if you want to be
     *          specific or are creating many BLocations at once.
     * @return BLocation
     */
    public static BLocation random(Random r) {
        return
                BLocation.LatLng(
                        Double.valueOf(r.nextDouble() * -180.0 + 90.0).floatValue(),
                        Double.valueOf((r.nextDouble() * -360.0) + 180.0).floatValue()
                );
    }

    /**
     * Creates a random latitude and longitude. (Not inclusive of (-90, 0))
     *
     * @param count positive integer
     * @return BLocation list
     */
    public static List<BLocation> random(int count) {
        List<BLocation> list = new ArrayList<BLocation>(count);
        Random r = new Random();
        for (int i = 0; i < count; i++) {
            list.add(random(r));
        }
        return list;
    }

}
