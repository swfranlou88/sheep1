package simulateur;

import pMath.FPoint;

public class Action {

	
	private Animal marionette = null;
	private Animal target = null;
	private FPoint position = null;
	private int tempsCanalisation = 0;
	private int typeAction = 0;
	
	
	
	public static final int MARCHE = 0;
	public static final int MANGE = 1;
	public static final int SEREPRODUIT = 2;
	public static final int ATTEND = 3;
	public static final int CHASSE = 4;
	public static final int FUIRE = 5;
	public static final int DORMIR = 6;
	
	
	
	public Action(Animal a,FPoint p,int type)
	{
		marionette = a;
		position = new FPoint((int)p.getX() + 0.5, (int)p.getY() + 0.5);
		typeAction = type;
	}
	
	public Action(Animal a,Animal targ,int type)
	{
		target = targ;
		marionette = a;
		position = targ.getPosition();
		typeAction = type;
	}
	
	
	
	
	
	
	
	
	public boolean update(Map m)
	{
		
		marionette.déplacement(m, position);
		
//		System.out.println(tempsCanalisation);
		if(typeAction == CHASSE && marionette.getEnergi()/marionette.getEnergiMax() < 0.01)
			return true;
		
		if(marionette.neSeDeplacePlus())
			if(marionette.getPosition().distance(position) > 1.9)
			{
				marionette.déplacement(m, position.clone());
			}
			else
			{
				tempsCanalisation++;
				if(typeAction == MARCHE)
				{
					return true;
				}
				if(typeAction == MANGE && tempsCanalisation > 200)
				{
					marionette.mange(m, position);
					return true;
				}
				if(typeAction == SEREPRODUIT)
				{
					if(target.getAction() == null)
					{
						target.setAction(new Action(target, target.getPosition(), ATTEND));
					}
					if(tempsCanalisation > 200)
					{
						m.animaux.add(marionette.enfant(m));
						marionette.affame(50);
						target.affame(30);
						return true;
					}
				}
				if(typeAction == CHASSE)
				{
					if(tempsCanalisation > 2)
					{
			//			System.out.println("at");
						target.blesse(m,m.getRandom()*1);
						if(!target.vivant())
							typeAction = MANGE;
							//return true;
					}
				}
				if(typeAction == FUIRE)
				{
					return true;
				}
				if(typeAction == ATTEND && tempsCanalisation > 230)
				{
					return true;
				}
				if(typeAction == DORMIR && tempsCanalisation > 1000)
				{
					return true;
				}
			}
		else
		{
			tempsCanalisation = 0;
		}
			
			
		
		
		return false;
	}
	
	
	
	
	
	public String getInfo()
	{
		String str = "";
		if(typeAction == DORMIR)
			str = "se reposer :";
		if(typeAction == FUIRE)
			str = "fuire :";
		if(typeAction == MANGE)
			str = "manger :";
		if(typeAction == SEREPRODUIT)
			str = "copuler :";
		if(typeAction == ATTEND)
			str = "attend :";
		if(typeAction == CHASSE)
			str = "chasse une proie :";
		if(typeAction == MARCHE)
			str = "marche :";
		return str + " " + position + " " + marionette.getPosition().distance(position);
	}
	
	
	
	public FPoint getPos(){return position;}
	public int getType(){return typeAction;}
	
	
	
}
