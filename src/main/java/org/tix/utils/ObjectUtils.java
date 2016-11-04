package org.tix.utils;

/**
 * Created by DzianisH on 04.11.2016.
 */
public class ObjectUtils {
	public static boolean same(Object obj1, Object obj2) {
		return obj1 == obj2 || !(obj1 == null || obj2 == null) && obj1.equals(obj2);
	}
}
