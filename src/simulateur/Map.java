package simulateur;

import java.awt.Color;
import java.util.ArrayList;

import affichage2.Affichable;
import affichage2.Afficheur;
import affichage2.Panneau;
import pMath.FPoint;
import pMath.Seed;

public class Map implements Affichable{

	
	private int tailleX = 1;
	private int tailleY = 1;
	private boolean[][] nonTraversable = new boolean[1][1];
	private double[][] arbres = new double[1][1];
	private int[][] herbe = new int[1][1];
	private double[][] corps = new double[1][1];
	public ArrayList<Animal> animaux = new ArrayList<Animal>();
	public ArrayList<Groupe> troupeaux = new ArrayList<Groupe>();
	//                    256 a 242 pour naissance
	
	private final int TIMEUPDATECHUNK = 5;
	private final int SIZECHUNK = 50;
	private Seed s = new Seed(0.256);
	public long upComp = 0;
	
	private Animal animalSelectionner = null;
	
	
	
	
	public Map(int sizeX,int sizeY)
	{
		tailleX = sizeX;
		tailleY = sizeY;
		nonTraversable = new boolean[tailleX][tailleY];
		arbres = new double[tailleX][tailleY];
		herbe = new int[tailleX][tailleY];
		corps = new double[tailleX][tailleY];
		
		for(int i = 0 ; i < sizeX*sizeY / 20 ; i++)
		{
			int x = (int)(getRandom()*sizeX);
			int y = (int)(getRandom()*sizeY);
			arbres[x][y] = 150*getRandom() + 50;
			nonTraversable[x][y] = true;
		}
		for(int x = 0 ; x < sizeX ; x++)
		for(int y = 0 ; y < sizeY ; y++)
		{
			if(arbres[x][y] < 1)
//				if(getRandom() <= 0.95)
					herbe[x][y] = (int)(3*getRandom()+8);
		}
		
		
		for(int i = 0 ; i < 20 ; i++)
		{
			int x = (int)(getRandom()*sizeX);
			int y = (int)(getRandom()*sizeY);
			animaux.add(new Animal(this,new FPoint(x,y),1,true,true,false,2.0,30,80,100,null,
					(int)(getRandom()*5)+4,4,9));
		}
		for(int i = 0 ; i < 2 ; i++)
		{
			int x = (int)(getRandom()*sizeX);
			int y = (int)(getRandom()*sizeY);
			animaux.add(new Animal(this,new FPoint(x,y),2,false,false,true,2.5,30,100,100,null,
					(int)(getRandom()*5)+4,5,30));
		}
	
		
		
		//corps[10][20] = 100;
	}
	
	
	
	
	
	
	public double getRandom()
	{
		return s.newRandom();
	}
	
	
	
	/*
	
	public void draw(Graphics g, double zoom, int mx, int my)
	{
		
		for(int x = 0 ; x < tailleX ; x++)
		for(int y = 0 ; y < tailleY ; y++)
		{
			if(arbres[x][y] > 1)
				g.setColor(new Color(0,(int)Math.max(0,230-arbres[x][y]),0));
			else
				g.setColor(new Color((int)corps[x][y]*5,255-(int)corps[x][y]*5,0));
			
			g.fillRect((int)(x*zoom), (int)(y*zoom), (int)(zoom), (int)(zoom));
			
//			if(nonTraversable[x][y])
//			{
//				g.setColor(Color.BLACK);
//				g.drawRect((int)(x*zoom), (int)(y*zoom), (int)(zoom), (int)(zoom));
//			}
		}
		
		for(int i = 0 ; i < animaux.size() ; i++)
			animaux.get(i).draw(g, zoom,mx,my);
		
		
	}
	
	*/


	@Override
	public void dessiner(Panneau p, Afficheur a) 
	{
		AffichageMonde aff = (AffichageMonde)a;
		for(int x = 0 ; x < tailleX ; x++)
			for(int y = 0 ; y < tailleY ; y++)
			{
				Color c = null;
				if(arbres[x][y] > 1)
					c = new Color(0,(int)Math.max(0,230-arbres[x][y]),0);
				else if(corps[x][y] > 0)
					c = new Color(200,0,0);
				else if(herbe[x][y] > 0)
					c = new Color(Math.max(0,80-8*herbe[x][y]),Math.min(255,30+22*herbe[x][y]),0);
				else
					c = new Color(80,30,0);
				
//				g.fillRect((int)(x*zoom), (int)(y*zoom), (int)(zoom), (int)(zoom));
				p.fillRect(c, new FPoint((x+aff.getAffX())*aff.getAffZoom()+ aff.getAffZoom()/2,
						(y+aff.getAffY())*aff.getAffZoom() + aff.getAffZoom()/2),aff.getAffZoom(), aff.getAffZoom(),0);
			}
		
		for(int i = 0 ; i < animaux.size() ; i++)
			animaux.get(i).dessiner(p,a);
		
		if(animalSelectionner != null)
		{
			p.drawRect(Color.RED,new FPoint((animalSelectionner.getPosition().getX()+aff.getAffX())*aff.getAffZoom(),
					(animalSelectionner.getPosition().getY()+aff.getAffY())*aff.getAffZoom()),aff.getAffZoom(),aff.getAffZoom(),0);
			
			p.drawString(animalSelectionner.getType()+" : " + (animalSelectionner.getAction() != null ? 
					animalSelectionner.getAction().getInfo() : ""), new FPoint(10,30), Color.BLACK);
			p.drawString("PV " + animalSelectionner.getPV() + "   age : " + animalSelectionner.getAge() 
			+ " " + animalSelectionner.getTimerDead(), new FPoint(10,50), Color.BLACK);
			p.drawString("En " + animalSelectionner.getEnergi(), new FPoint(10,70), Color.BLACK);
			p.drawString("No " + animalSelectionner.getBouffe(), new FPoint(10,90), Color.BLACK);
			
			p.drawString("Gr " + animalSelectionner.troupeau, new FPoint(10,110), Color.BLACK);
			if(animalSelectionner.troupeau != null)
				animalSelectionner.troupeau.dessiner(p, aff);
			p.drawString("Position " + animalSelectionner.getPosition(), new FPoint(10,130), Color.BLACK);
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void update()
	{
		upComp++;
		
		for(int x = 0 ; x < tailleX ; x++)
		for(int y = 0 ; y < tailleY ; y++)
		{
//			if(arbres[x][y] > 1)arbres[x][y] += 0.001;
//			pourrissement des corp			
			if(corps[x][y] > 0)corps[x][y] -= 0.002;
			if(corps[x][y] < 0)corps[x][y] = 0;
		}
		
		
		
		// update des chunk
		if(upComp % TIMEUPDATECHUNK == 0)
		for(int i = 0 ; i < tailleX/SIZECHUNK ; i++)
		for(int j = 0 ; j < tailleY/SIZECHUNK ; j++)
		{
			int x = (int)(s.newRandom()*SIZECHUNK)+i*SIZECHUNK;
			int y = (int)(s.newRandom()*SIZECHUNK)+j*SIZECHUNK;
			
			if(arbres[x][y] < 1)
			{
				boolean pasfini = true;
				for(int x1 = Math.max(0,x-3) ; x1 <= Math.min(tailleX-1,x+3) ; x1++)
				for(int y1 = Math.max(0,y-3) ; y1 <= Math.min(tailleY-1,y+3) ; y1++)
				{
					if(pasfini && herbe[x1][y1] > 0)
					{
						if(herbe[x][y] < 10)
						{
							if(herbe[x][y] >= 9 && getRandom() > 0.5)
								herbe[x][y]--;
							else
								herbe[x][y]++;
						}
						else
							herbe[x][y]--;
						pasfini = false;
						break;
					}
				}
			}
			/*
			for(int x = 0 ; x < 50; x++)
			for(int y = 0 ; y < 50; y++)
			{
				
			}*/
		}
		
		
		for(int i = 0 ; i < troupeaux.size() ; i++)
		{
			troupeaux.get(i).update(this);
		}
		
		for(int i = 0 ; i < animaux.size() ; i++)
		{
			Animal an = animaux.get(i);
			an.update(this);
			if(!an.vivant())
				killAnimal(an);
		}
		
		
		// up quotidient
		if(upComp % 20000 == 0)
		{
			for(int i = 0 ; i < animaux.size() ; i++)
			{
				Animal an = animaux.get(i);
				an.addAnnee();
				if(an.getAge() == an.getAgeVieux())
					an.setTimerDead((int)(an.getAgeAdulte()*getRandom()*20000)+
							(int)(an.nocturne ? (10000 + 10000*getRandom()) : (10000*getRandom()) ));
			}
		}
		
	}
	
	
	
	
	
	
	
	
	public FPoint arbreLePlusProche(Animal a)
	{
		FPoint p = null;
		
		int x1 = (int)a.getPosition().getX();
		int y1 = (int)a.getPosition().getY();
		
		for(int x = Math.max(0, x1-40) ; x < Math.min(tailleX,x1+40) ; x++)
		for(int y = Math.max(0, y1-40) ; y < Math.min(tailleY,y1+40) ; y++)
		{
			if(arbres[x][y] > 1)
				if(p == null || a.getPosition().distance(p) > a.getPosition().distance(x, y)) p = new FPoint(x,y);
		}
		
		return p;
	}
	
	public FPoint herbeLaPlusProche(Animal a)
	{
		FPoint p = null;
		
		int x1 = (int)a.getPosition().getX();
		int y1 = (int)a.getPosition().getY();
		
		for(int x = Math.max(0, x1-5) ; x < Math.min(tailleX,x1+5) ; x++)
		for(int y = Math.max(0, y1-5) ; y < Math.min(tailleY,y1+5) ; y++)
		{
			if(herbe[x][y] > 1)
				if(p == null || a.getPosition().distance(p) > a.getPosition().distance(x, y)) p = new FPoint(x,y);
		}
		if(p == null)
		{
			for(int x = Math.max(0, x1-30) ; x < Math.min(tailleX,x1+30) ; x++)
			for(int y = Math.max(0, y1-30) ; y < Math.min(tailleY,y1+30) ; y++)
			{
				if(herbe[x][y] > 1)
					if(p == null || a.getPosition().distance(p) > a.getPosition().distance(x, y)) p = new FPoint(x,y);
			}
		}
		
		
		return p;
	}
	
	
	
	public FPoint corpLePlusProche(Animal a)
	{
		FPoint p = null;
		
		for(int x = 0 ; x < tailleX ; x++)
		for(int y = 0 ; y < tailleY ; y++)
		{
			if(corps[x][y] > 1)
				if(p == null || a.getPosition().distance(p) > a.getPosition().distance(x, y)) p = new FPoint(x+0.5,y+0.5);
		}
			
		return p;
	}
	
	
	
	
	
	
	
	public Animal animalMELePlusProche(Animal a)
	{
		Animal p = null;
		
		for(int i = 0 ; i < animaux.size() ; i++)
			if(a != animaux.get(i) && animaux.get(i).getType() == a.getType())
				if(p == null ||
				a.getPosition().distance(p.getPosition()) > a.getPosition().distance(animaux.get(i).getPosition()) )
				p = animaux.get(i);
		
		return p;
	}
	
	
	public Animal proieLePlusProche(Animal a)
	{
		Animal p = null;
		
		for(int i = 0 ; i < animaux.size() ; i++)
			if(a != animaux.get(i) && animaux.get(i).getType() != a.getType())
				if(p == null ||
				a.getPosition().distance(p.getPosition()) > a.getPosition().distance(animaux.get(i).getPosition()) )
				p = animaux.get(i);
		
		return p;
	}
	
	public Animal predateurLePlusProche(Animal a)
	{
		Animal p = null;
		
		for(int i = 0 ; i < animaux.size() ; i++)
			if(a.getType() != animaux.get(i).getType() && !animaux.get(i).getRegime())
				if(p == null ||
				a.getPosition().distance(p.getPosition()) > a.getPosition().distance(animaux.get(i).getPosition()) )
				p = animaux.get(i);
		
		return p;
	}
	
	
	
	
	
	public void killAnimal(Animal a)
	{
		for(int i = 0 ; i < animaux.size() ; i++)
		{
			if(animaux.get(i) == a)
			{
				corps[(int) animaux.get(i).getPosition().getX()][(int) animaux.get(i).getPosition().getY()] += animaux.get(i).getPVMax()*2;
				animaux.remove(i--);
			}
		}
	}
	
	
	
	
	
	public double getArbre(FPoint p){return arbres[(int)p.getX()][(int)p.getY()];}
	public int getHerbe(FPoint p){return herbe[(int)p.getX()][(int)p.getY()];}
	public void removeArbre(FPoint p,double quantité)
	{
		arbres[(int)p.getX()][(int)p.getY()] -= Math.min(quantité,arbres[(int)p.getX()][(int)p.getY()]);
		if(arbres[(int)p.getX()][(int)p.getY()] == 0)
			nonTraversable[(int)p.getX()][(int)p.getY()] = false;
	}
	public void removeHerbe(FPoint p,int quantité)
	{
		herbe[(int)p.getX()][(int)p.getY()] -= Math.min(quantité,herbe[(int)p.getX()][(int)p.getY()]);
	}
	
	public double getCorp(FPoint p){return corps[(int)p.getX()][(int)p.getY()];}	
	public void removeCorp(FPoint p,double quantité)
	{
		corps[(int)p.getX()][(int)p.getY()] -= Math.min(quantité,corps[(int)p.getX()][(int)p.getY()]);
		if(corps[(int)p.getX()][(int)p.getY()] == 0)
			nonTraversable[(int)p.getX()][(int)p.getY()] = false;
	}
	
	
	
	
	public boolean estBlocker(int x, int y)
	{
		return nonTraversable[x][y];
	}
	public boolean estBlocker(FPoint p)
	{
		return nonTraversable[(int)p.getX()][(int)p.getY()];
	}
	
	
	public void blockPos(boolean b,FPoint p)
	{
		nonTraversable[(int)p.getX()][(int)p.getY()] = b;
	}
	
	
	public int tailleMapX(){return tailleX;}
	public int tailleMapY(){return tailleY;}

	public void setAnimalSelect(Animal an){animalSelectionner = an;}

	public boolean doitDormir(boolean nocturne)
	{
		double n = ((upComp+20000)/10000.0)%2;
		if(!nocturne && n > 1.2 && n < 1.7)
			return true;
		else 
			if(nocturne && n > 0.2 && n < 0.7)
				return true;
			else
				return false;
	}

	
}
