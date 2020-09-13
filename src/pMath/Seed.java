package pMath;

public class Seed {

	private double lastNum = 0;
	private double initialSeed = 0;
	
	public Seed(double initialSeed)
	{
		this.initialSeed = initialSeed;
		lastNum = initialSeed;
		
		resetSeed();
	}
	
	
	public double newRandom()
	{
		double nr = lastNum;
		
		nr *= Math.pow((initialSeed*895),3);
		nr -= ((int)nr);
		
		lastNum = nr;
		return nr;
	}
	
	
	
	public void resetSeed()
	{
		lastNum = initialSeed;
	}
	
}
