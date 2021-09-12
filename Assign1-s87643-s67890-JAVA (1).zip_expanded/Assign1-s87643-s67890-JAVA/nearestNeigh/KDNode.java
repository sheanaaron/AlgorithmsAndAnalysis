package nearestNeigh;

public class KDNode {

	//members 
	public int axis;        //split dimension
	public Point point;        //2-dimensional point 
	public KDNode parent;
	public KDNode left;
	public KDNode right;


	//member functions
	public KDNode ( Point point, int axis){

		this.point = point;
		this.axis = axis;
	}

	public KDNode(Point point) {

		this.point = point;
	}

	public KDNode getParent() {return parent;}

	public void setParent(KDNode parent) {this.parent = parent;}

	public KDNode getLeft() {return left;}

	public void setLeft(KDNode left) {
		this.left = left;
		left.setParent(this);
	}

	public KDNode getRight() {return right;}

	public void setRight(KDNode right) {
		this.right = right;
		right.setParent(this);
	}

	public void setAxis(int axis) {this.axis = axis;}

	public int getAxis() {return this.axis;}

	public double getCoordinate(int axis) {
		if (axis==0) return this.point.lon;
		else return this.point.lat;		
	}

	public int compareAxis(KDNode kd) {

		double mine = this.getCoordinate(this.axis);
		double other = kd.getCoordinate(kd.getAxis());
		if (mine > other) {
			return 1;
		} else if (mine < other) {
			return -1;
		} else {
			return 0;
		}
	}	

	public boolean toggle() {
		
//		latitude = 1 longitude = 0
		if (this.parent == null) {
			return false;
		}else {
			//if parent longitude, then set child to latitude
			if (this.parent.getAxis()==0) {
				this.axis =1;
				return true;
				//if parent latitude, then set child to longitude
			}else if (this.parent.getAxis()==1){
				this.axis = 0;
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "node: " + this.axis;
	}


}
