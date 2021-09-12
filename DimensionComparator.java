package nearestNeigh;

import java.util.Comparator;

public class DimensionComparator implements Comparator<KDNode> {
	
	
	/** Numbers within this amount are considered to be the same. */
	public static final double epsilon = 1E-9;
	
	/** 
	 * Construct with the given dimensional index (d >= 1).
	 * 
	 * @param d    specific dimension (d >= 1).
	 */
	public DimensionComparator () {}
	
	public static int compareDoubles (double d1, double d2) {
		if (lesser(d1, d2)) { return -1; }
		if (same(d1, d2)) { return 0; }
		
		return +1;
	}
	
	/** Given closeness-to-epsilon, is x < y? */
	public static boolean lesser(double x, double y) {
		return value(x-y) < 0;
	}
	
	/**
	 * When value won't work, because numbers are potentially infinite, then
	 * use this one.
	 * <p>
	 * Standarard means for comparing double when dealing with the special
	 * quantities, NaN and infinite numbers. Also properly ensures that
	 * numbers "close to zero" (within an epsilon) are to be treated as zero
	 * for this computation.
	 * 
	 * @param d1   first number being compared    
	 * @param d2   second number being compared
	 */
	public static boolean same (double d1, double d2) {
		// NaN numbers cannot be compared with '==' and must be treated separately.
		if (Double.isNaN(d1)) {
			return Double.isNaN(d2);
		}
		
		// this covers Infinite and NaN cases.
		if (d1 == d2) return true;
		
		// Infinity values can be compared with '==' as above
		if (Double.isInfinite(d1)) {
			return false;
		}

	
		// try normal value
		return value (d1-d2) == 0;
		
	}
	
	public static double value(double x) {
		if ((x >= 0) && (x <= epsilon)) {
			return 0.0;
		}
		
		if ((x < 0) && (-x <= epsilon)) {
			return 0.0;
		}
		
		return x;
	}
	
	/** 
	 * Compare the two points against a given dimension.
	 * <p>
	 * Note that for performance reasons, there is no check to ensure that the
	 * two points have the same dimensionality. Indeed, if this method is invoked
	 * with an IMultiPoint whose dimensionality is less than d, then the result
	 * is undetermined (it may throw a runtime exception it may not). 
	 *  
	 * @param o1    first IMultiPoint to be compared against
	 * @param o2    second IMultiPoint to be compared against. 
	 */
	@Override
	public int compare(KDNode o1, KDNode o2) {
		return o1.compareAxis(o2);
	}

}
