package nearestNeigh;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This class is required to be implemented. Naive approach implementation.
 *
 * 
 */
public class NaiveNN implements NearestNeigh {

	ArrayList<Point> list;

	public NaiveNN() {
		list = new ArrayList<>();
	}

	@Override
	public void buildIndex(List<Point> points) {

		// adding all points to the list
		list.addAll(points);

	}

	@Override
	public List<Point> search(Point searchTerm, int k) {

		ArrayList<Point> nearestList = new ArrayList<>();
		// populate the list with all existing points
		nearestList.addAll(list);
		ArrayList<Point> sorted = new ArrayList<>();

		nearestList.remove(searchTerm);

		if (nearestList.size() >= 1) {

			for (int i = 0; i < k; i++) {
				Point shortest = nearestList.get(0);

				for (Point currentPoint : nearestList) {

					if ((currentPoint.distTo(searchTerm) < shortest.distTo(searchTerm))
							&& (currentPoint.cat).equals(searchTerm.cat)) {
						shortest = currentPoint;
					}
				}
				sorted.add(shortest);
				nearestList.remove(shortest);
			}

		}
		return sorted;

	}

//	public List<Point> search(Point searchTerm, int k) {
//        ArrayList<Point> tempPoints = new ArrayList<>();
//        ArrayList<Point> returnPoints = new ArrayList<>();
//        tempPoints.addAll(points);
//        int counter = 0;
//        tempPoints.remove(searchTerm);
//        while (counter < k) {
//            counter++;
//            double minDist = tempPoints.get(0).distTo(searchTerm);
//            int minIndex = 0;
//            for (int i = 0; i < tempPoints.size(); i++) {
//                if (minDist > tempPoints.get(i).distTo(searchTerm)) {
//                    minDist = tempPoints.get(i).distTo(searchTerm);
//                    minIndex = i;
//                }
//            }
//            returnPoints.add(tempPoints.get(minIndex));
//            tempPoints.remove(minIndex);
//        }
//        return returnPoints;
//    }

	@Override
	public boolean addPoint(Point point) {

		// returns true when the point is added
		if (list.contains(point))
			return false;
		else {
			list.add(point);
			return true;
		}

	}

	@Override
	public boolean deletePoint(Point point) {

		if (list.contains(point)) {
			list.remove(point);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isPointIn(Point point) {
		if (list.contains(point)) {
			return true;
		} else {
			return false;
		}
	}
}
