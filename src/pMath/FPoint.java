package pMath;

public class FPoint{
	
	
	private double x;
	private double y;

	

	public FPoint(double x, double y)
	{
		this.setLocation(x,y);
	}
	

	
	
	
	
	
	
	public FPoint clone()
	{
		return new FPoint(x,y);
	}
	
	public boolean equals(Object o)
	{
		if(o instanceof FPoint)
		{
			FPoint p = (FPoint)(o); 
			if(p.x == x && p.y == y)
				return true;
		}
		return false;
	}
	
	
	
	
	


	public double orientation(FPoint p)
	{
		double t = 0;
		if(p.getX()-this.getX() != 0){
			if(p.getX()-this.getX() > 0)
				t = Math.atan((p.getY()-this.getY())/(p.getX()-this.getX()));
			else if (p.getX()-this.getX() < 0)
				t = Math.atan((p.getY()-this.getY())/(p.getX()-this.getX()))+Math.PI;
		}
		else if(p.getY()-this.getY() > 0)
			t = Math.PI/2;
		else if(p.getY()-this.getY() < 0)
			t = -Math.PI/2;
		
		return t;
		
	}
	
	
	
	
	public String toString()
	{
		return "["+x+","+y+"]";
	}
	




	public double distance(FPoint p) {
		return Math.sqrt((x-p.x)*(x-p.x) + (y-p.y)*(y-p.y));
	}
	
	public double distance(double x1, double y1) {
		return Math.sqrt((x-x1)*(x-x1) + (y-y1)*(y-y1));
	}
	
	public double distanceMan(FPoint p) {
		return (x-p.x) + (y-p.y);
	}
	
	public double distanceMan(double x1, double y1) {
		return (x-x1) + (y-y1);
	}
	
	
	
	
	
	public void setLocation(double x2, double y2) {
		x = x2;
		y = y2;
	}


	
	
	public double getX(){return x;}
	public double getY(){return y;}









	public void translate(double x2, double y2) {
		x += x2;
		y += y2;
	}





	

}
