package nearestNeigh;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

/**
 * This class is required to be implemented. Kd-tree implementation.
 *
 * 
 */
public class KDTreeNN implements NearestNeigh {

	ArrayList<Point> list;
	TreeMap<Double, Double> treemap;

	public KDTreeNN() {
		list = new ArrayList<>();
		treemap = new TreeMap<Double, Double>();
	}

	@Override
	public void buildIndex(List<Point> points) {
		// Input: The tree T, and a given point P

		// Step 1: Search for the parent node as if we are to add the point, and use the
		// parent as the first approximation for the nearest neighbour (NN). Set min =
		// dist(P, parent)

		// Step 2: Recursively search from a node U of the tree, starting with the root
		// node, for a potential node that is closer to P as follows:

		// if d = dist(P,U) < min then update min = dist(P, U).

		// -set dp = distance from P to the axis of U (either x or y), if dp < min then
		// apply the search to both children of U, otherwise only search the child node
		// of U on the side containing P.

		// Stop when reaching a leaf.

		Iterator<Point> iterator = points.iterator();
		while (iterator.hasNext()) {
			Point nextPoint = iterator.next();
			// Adding all points to the Tree
			// Get X and Y
			double x = nextPoint.lat;
			double y = nextPoint.lon;

			treemap.put(x, y);
		}
	}

	@Override
	public List<Point> search(Point searchTerm, int k) {
		// Initial List to Return if any Matches
		List<Point> searchFound = new ArrayList<Point>();

		// Iterator to go through one by one of the List
		Iterator<Point> iterator = list.iterator();
		while (iterator.hasNext()) {

			// Retrieve next in the List
			Point nextPoint = iterator.next();

			// Check Distance between Next Point from the List to the SearchTerm Point
			double minDist = nextPoint.distTo(searchTerm);

			// If Search Distance MATCHES to k
			if (minDist == k) {
				// Add to List
				searchFound.add(nextPoint);
			}
		}
		return searchFound;
	}

	@Override
	public boolean addPoint(Point point) {
		// A <id> <category> <x-coordinates> <y-coordinates>– adds a new point (x,y) to
		// the pointset of specified category. If point exists already (id, category,
		// both coordinates all match), return false.

		// returns true when the point is added
		if (list.contains(point)) {
			return false;
		} else {
			list.add(point);
			return true;
		}
	}

	@Override
	public boolean deletePoint(Point point) {
		// Default boolean of False should the Point not match
		boolean match = false;

		// If the Data Structure contains the Point, or identical point, remove it
		// Checks using an Iterator 1 by 1
		Iterator<Point> iterator = list.iterator();

		while (iterator.hasNext()) {
			Point nextPoint = iterator.next();

			if (point.id == nextPoint.id && point.cat == nextPoint.cat && point.lat == nextPoint.lat
					&& point.lon == nextPoint.lon) {
				list.remove(point);
				match = true;
			}
		}
		return match;
	}

	@Override
	public boolean isPointIn(Point point) {
		// Default boolean of False should the Point not match
		boolean match = false;

		// Check if a point (x,y) is in pointset of specified category.
		if (list.contains(point)) {
			// Id, category and coordinates must all match for check to be success.
			// Checks using an Iterator 1 by 1
			Iterator<Point> iterator = list.iterator();

			while (iterator.hasNext()) {
				Point nextPoint = iterator.next();
				if (point.id == nextPoint.id && point.cat == nextPoint.cat && point.lat == nextPoint.lat
						&& point.lon == nextPoint.lon) {
					match = true;
				}
			}
		}
		return match;
	}
}
