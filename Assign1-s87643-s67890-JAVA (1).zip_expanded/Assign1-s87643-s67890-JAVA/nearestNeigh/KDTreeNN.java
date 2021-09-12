package nearestNeigh;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This class is required to be implemented.  Kd-tree implementation.
 *
 * 
 */
public class KDTreeNN implements NearestNeigh{

	private KDNode root;
	private long size;
	private int depth = 0;


	public KDTreeNN() {

		root = new KDNode(null);
		root.setAxis(1);
		size = 0;
	}

	@Override
	public void buildIndex(List<Point> points) {
		//create list of nodes
		ArrayList<KDNode> list = new ArrayList<>();
		for (Point p: points)
			list.add(new KDNode(p));

		DimensionComparator comparator = new DimensionComparator(); 
		//Collections.sort(list, comparator); //sort the list using comparator : ascending by axis i
		this.root= this.recursiveBuild(list, depth, this.root); 
	}

	public List<KDNode> sort(List<KDNode> points, KDNode parent){

		List<KDNode> sorted = new ArrayList<>();
		List<KDNode> unsorted = new ArrayList<>(points);
		while(sorted.size()!=points.size()){
			if (parent.getAxis()==0) {

				double minXCoordinate = unsorted.get(0).point.lon;
				int minIndex = 0;
				for (int i = 0; i < unsorted.size(); i++) {
					if (minXCoordinate > points.get(i).point.lon){
						minXCoordinate = points.get(i).point.lon;
						minIndex = i;
					}
				}
				sorted.add(unsorted.get(minIndex));
				unsorted.remove(minIndex);
			} else {
				double minXCoordinate = unsorted.get(0).point.lat;
				int minIndex = 0;
				for (int i = 0; i < unsorted.size(); i++) {
					if (minXCoordinate > points.get(i).point.lat){
						minXCoordinate = points.get(i).point.lat;
						minIndex = i;
					}
				}
				sorted.add(unsorted.get(minIndex));
				unsorted.remove(minIndex);
			}
		}

		return sorted;

		//		List<KDNode> sorted = new ArrayList<>();
		//		List<KDNode> pointsList = new ArrayList<>();
		//		pointsList.addAll(points);
		//
		//		while(sorted.size() < points.size()){
		//
		//			KDNode shortest = pointsList.get(0);
		//			//latitude = 1
		//			if (parent.getAxis()==true) {
		//				for (KDNode currNode : pointsList) {
		//					if (    shortest.point.lat > currNode.point.lat) {
		//						System.out.println("jidjd");
		//						shortest = currNode;
		//					}
		//				}
		//				sorted.add(shortest);
		//				pointsList.remove(shortest);
		//				//longitude = 0
		//			} else if (parent.getAxis()==false) {
		//				for (KDNode currNode : pointsList) {
		//					if (!currNode.point.equals(shortest.point) && shortest.point.lon > currNode.point.lon)
		//							shortest = currNode;
		//				}				
		//				sorted.add(shortest);
		//				pointsList.remove(shortest);
		//			}
		//		}
		//		return sorted;
	}

	public KDNode recursiveBuild(Collection<KDNode> points, int depth, KDNode parent) {
		List<KDNode> list = new ArrayList<KDNode>(points); //build list from the collection
		int size = list.size();

		if (depth < 0) {
			System.err.println("ERROR: KDTree.recursiveBuild(): Cannot build tree from a negative depth!");
			return null;
		}
		if (size == 0) {
			System.err.println("ERROR: KDTree.recursiveBuild(): Cannot build tree from an empty list.");
			return null;
		}

		list = this.sort(list, parent);

		//split list by median
		KDNode median;
		int mid = size/2; //floor of size/2
		median = list.get(mid); //get median object

		//create node, split list around median and recur
		//if there is only one node left in the list, it's our median,
		//so we're done with building the tree and we can return the 
		//last node which we just created from the median.
		if (depth == 0)
			root = median;

		if (depth>1) {
			for (KDNode node: list) {
				node.toggle();			}
		}

		if (size > 2) {
			median.setLeft(this.recursiveBuild(list.subList(0, mid), depth+1, median)); //Recur on sublist of everything before midpoint
			median.setRight(this.recursiveBuild(list.subList(mid+1, size), depth+1, median)); //recur on sublist of everything after midpoint
		} else if (size == 2) { //mid must be 1
			if (list.get(0).compareAxis(list.get(1)) >= 0)
				median.setRight(this.recursiveBuild(list.subList(0, 1), depth+1, median)); //the node before mid
			else
				median.setLeft(this.recursiveBuild(list.subList(0, 1), depth+1, median)); //node before mid
		}
		size++;

		return median;
	}



	@Override
	public List<Point> search(Point searchTerm, int k) {

		return new ArrayList<Point>();
	}

	@Override
	public boolean addPoint(Point point) {

		//		if(root == null ){
		//			root = new KDNode (point, 0);
		//			return true;
		//		}
		//
		//		if (this.isPointIn(point)) {
		//			return false;
		//		}else {
		//			KDNode curr = root;
		//			KDNode node = null;
		//			List<KDNode> values = new ArrayList<>();
		//			int axis, depth= 0;
		//
		//
		//			while(curr!= null 	) {
		//
		//
		//				axis = depth % 2;
		//				if (axis == 0) {node = new KDNode (point, 0); }
		//				else {node = new KDNode (point, 1); }
		//
		//				int result = node.compareAxis(curr);
		//				if (result > 0) {
		//					if (curr.getRight()==null) {
		//						values.add(curr);
		//						values.add(node);
		//					}
		//					curr = curr.getRight();
		//				}
		//				else if (result < 0) {
		//					if (curr.getLeft()==  null) {
		//						values.add(curr);
		//						values.add(node);
		//
		//					}
		//					curr = curr.getLeft();
		//				}
		//				depth++;
		//			}
		//
		//			KDNode parent = values.get(0);
		//			KDNode child = values.get(1);
		//			int result = parent.compareAxis(child);
		//
		//			if (result>0) {
		//				parent.setRight(child);
		//				child.setParent(parent);
		//				System.out.println("curr:" + parent.axis + " node " + child.axis);
		//				values.clear();
		//				return true;
		//			}else {
		//				parent.setLeft(child);
		//				child.setParent(parent);
		//				System.out.println("curr:" + parent.axis + " node " + child.axis);
		//				values.clear();
		//				return true;
		//			}
		//		}
		return true;
	}

	@Override
	public boolean deletePoint(Point point) {

		return false;
	}

	@Override
	public boolean isPointIn(Point point) {

		if(root == null ){
			System.err.println("ERROR: KDTree.contains(): Tree is not defined.");
			return false;
		}

		KDNode curr = root;
		int axis, depth= 0;
		while(curr != null) {
			axis = depth % 2;
			KDNode node = null;
			if (axis == 0) {node = new KDNode (point, 0); }
			else {node = new KDNode (point, 1); }

			int result = node.compareAxis(curr);
			if (result > 0) {
				curr = curr.getRight();
			} else if (result < 0) {
				curr = curr.getLeft();
			} else {
				if (point.equals(curr.point))
					return true;
				else
					curr = curr.getLeft();
			}
			depth++;

		}
		return false;
	}

}
