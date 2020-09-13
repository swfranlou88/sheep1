package simulateur;

import java.awt.Color;
import java.util.ArrayList;

import affichage2.Affichable;
import affichage2.Afficheur;
import affichage2.Panneau;
import pMath.Astar;
import pMath.FPoint;

public class Animal implements Affichable{
	
	
	private FPoint position = null;
	private double PVMax = 20;
	private double PV = PVMax;
	
	private double jaugeNouritureMax = 100;
	private double jaugeNouriture = jaugeNouritureMax*0.7;
	
	private double jaugeEnergieMax = 100;
	private double jaugeEnergie = jaugeEnergieMax*0.7;
	
	private double vitesse = 0.025;
	private double vitesseSprint = 0.050;
	
	private int type = 0;
	
	private int age = 0;
	private int ageAdulte = 3;
	private int ageVieux = 7;
	private int timerDead = -1;
	
	public Groupe troupeau = null;
	
	public boolean regroupement = true;
	private boolean vegetarien = true;
	public boolean nocturne = false;
	
	
	
	public Animal(Map m,FPoint pos, int t, boolean regime, boolean group, boolean noctur, double maxV, double maxPV,
			double maxEn, double boufMax, Groupe gro,int ag, int ageAd, int ageVi)
	{
		type = t;
		position = new FPoint((int)pos.getX()+0.5 , (int)pos.getY()+0.5);
		vegetarien = regime;
		regroupement = group;
		nocturne = noctur;
		vitesseSprint = maxV;
		troupeau = gro;
		// PV
		PVMax = maxPV;
		PV = PVMax;
		// Energie
		jaugeEnergieMax = maxEn;
		jaugeEnergie = jaugeEnergieMax*0.7;
		// nouriture
		jaugeNouritureMax = boufMax;
		jaugeNouriture = jaugeNouritureMax*0.7;
		// age
		age = ag;
		ageAdulte = ageAd;
		ageVieux = ageVi;
		
		antiAFKTimer = (int)(m.getRandom()*600);
	}
	
	
	
	
	


	@Override
	public void dessiner(Panneau p, Afficheur a)
	{
		AffichageMonde aff = (AffichageMonde)a;
		
		Color col = null;
		if(type == 1)
			col = new Color(200,200,200);
		if(type == 2)
			col = new Color(0,0,200);
		
		double posX = (position.getX()+aff.getAffX())*aff.getAffZoom();
		double posY = (position.getY()+aff.getAffY())*aff.getAffZoom();
		
		p.fillRect(col,new FPoint(posX,posY),aff.getAffZoom(),aff.getAffZoom(),0);
		
		if(action != null)
			p.drawLine(Color.BLACK,new FPoint(posX,posY),
					new FPoint(((int)(action.getPos().getX()) + 0.5 + aff.getAffX())*aff.getAffZoom(),
							((int)(action.getPos().getY()) + 0.5 + aff.getAffY())*aff.getAffZoom()));
		
	}
	
	
	/*
	public void draw(Graphics g, double getAffZoom() ,int mx, int my)
	{
		if(type == 1)
			g.setColor(new Color(200,200,200));
		if(type == 2)
			g.setColor(new Color(0,0,200));
		
		g.fillRect((int)(position.getX()*getAffZoom() -getAffZoom()/2), (int)(position.getY()*getAffZoom() -getAffZoom()/2), (int)(getAffZoom()), (int)(getAffZoom()));
		
		
		if(action != null)
		{
			g.setColor(Color.BLACK);
			g.drawLine((int)((position.getX())*getAffZoom()),(int)((position.getY())*getAffZoom()),
					(int)((action.getPos().getX()+0.5)*getAffZoom()),(int)((action.getPos().getY()+0.5)*getAffZoom()));
		}
		
		
		if(mx > position.getX()*getAffZoom() && mx < position.getX()*getAffZoom()+getAffZoom() && my > position.getY()*getAffZoom() && my < position.getY()*getAffZoom()+getAffZoom())
		{
			g.setColor(Color.BLACK);
			g.drawString("position : " + position, 5, 15);
			g.drawString("PV : " + PV, 5, 30);
			g.drawString("Nouriturre : " + jaugeNouriture, 5, 45);
			g.drawString("Action : " + (action!= null ? action.getType(): "null"), 5, 60);
			
		}
	}
	*/
	
	
	
	
	
	
	
	
	public String toString()
	{
		String str = (position + " / " + jaugeNouriture + " # " + PV + " % " + action + " - " + antiAFKTimer);
		return str;
	}
	
	
	
	
	
	
	
	
	private int antiAFKTimer = 300;
	
	private Action action = null;
	
	public void update(Map m)
	{
		
		if(timerDead > 0)
			timerDead--;
		jaugeNouriture -= 0.0015;
		if(action != null && action.getType() != Action.DORMIR)
			jaugeNouriture -= 0.0015;
		
		if(jaugeNouriture < 0)
		{
			blesse(m,-jaugeNouriture);
			jaugeNouriture = 0;
		}
		jaugeEnergie += 0.005;
		if(action != null && action.getType() == Action.DORMIR)
			jaugeEnergie += 0.005;
		
		if(jaugeEnergie > jaugeEnergieMax)
			jaugeEnergie = jaugeEnergieMax;
		PV += 0.0005;
		if(PV > PVMax)
			PV = PVMax;
		
		
		
		
		if(action == null || action.getType() != Action.FUIRE)
		{
			actionFuire(m, 6);
		}
		
		if(action == null || action.getType() == Action.DORMIR)
		{
			if(m.doitDormir(nocturne))
				actionDormir(m);
			if(jaugeEnergie/jaugeEnergieMax < 0.1)
				actionDormir(m);
		}
		
		if(action == null && jaugeNouriture/jaugeNouritureMax < 0.9 && !vegetarien)
		{
			actionMange(m,30);
		}
		
		if(action == null && jaugeNouriture/jaugeNouritureMax < 0.4)
		{
			if(vegetarien)
			{
				if(!actionMange(m,15))
					actionEre(m,20);
			}
			if(!vegetarien)
			{
				if(!attaqueProie(m,30));
	//				actionEre(m,20);
			}
		}
		
		if(regroupement && troupeau == null)
			actionRegrouper(m,10);
		//attaqueProie(m,20);
		
		
		if(action != null)
		{
			antiAFKTimer = (int)(m.getRandom()*400+500*(1.0-(jaugeEnergie/jaugeEnergieMax)));
			if(action.update(m))action = null;
		}
		else
		{
			antiAFKTimer += 1;
			if(antiAFKTimer > 1000)
			{
				
			
				if(!seReproduire(m,10))
					if(age >= ageAdulte && age < ageVieux && m.getRandom() > 0.40 || m.getRandom() > 0.80)
					{
						if(vegetarien)
							actionMange(m,10);
						if(!vegetarien && jaugeEnergie/jaugeEnergieMax > 0.2)
							attaqueProie(m,15);
					}
					else
					{
						if(jaugeEnergie/jaugeEnergieMax > 0.1)
						{
							if(vegetarien)
								actionEre(m,5);
							else
								actionEre(m,15);
						}
					}
				
				
			}
		}
				
	}
	
	
	
	
	//TODO
	
	private boolean actionMange(Map m, int distMax)
	{
		if(jaugeNouriture / jaugeNouritureMax < 0.9)
		{
			if(vegetarien)
			{
				FPoint posHer = m.herbeLaPlusProche(this);
				if(posHer != null)
				{
					if(posHer.distance(position) < distMax)
					{
						action = new Action(this, posHer, Action.MANGE);
						return true;
					}
				}
				else return false;
			}
			else
			{
				FPoint posCor = m.corpLePlusProche(this);
				if(posCor != null)
				{
					if(posCor.distance(position) < distMax)
					{
						action = new Action(this, posCor, Action.MANGE);
						return true;
					}
					
				}				
//				System.out.println("pas de target");
				
			}
		}
		return false;
	}
	
	private void actionEre(Map m,int rayon)
	{
		int x = (int)(position.getX()-rayon+((rayon*2)*m.getRandom()-0.5));
			if(x > m.tailleMapX()-1)x = m.tailleMapX()-1;
			if(x < 0)x = 0;
		int y = (int)(position.getY()-rayon+((rayon*2)*m.getRandom()-0.5));
			if(y > m.tailleMapY()-1)y = m.tailleMapY()-1;
			if(y < 0)y = 0;
		
		if(troupeau == null)
			action = new Action(this, new FPoint(x+0.5,y+0.5), Action.MARCHE);
		else
			action = new Action(this, troupeau.pointInZone(new FPoint(x,y)), Action.MARCHE);
	}

	private boolean actionRegrouper(Map m, int rayon)
	{
		Animal an = m.animalMELePlusProche(this);
		if(an != null && an.position.distance(position) <= rayon)
			if(an.troupeau != null)
			{
				troupeau = an.troupeau;
				troupeau.addAnimal(this);
				return true;
			}
			else
			{
				Groupe T = new Groupe(this,an);
				T.update(m);
				m.troupeaux.add(T);
				return true;
			}
		if(an != null && action == null && an.position.distance(position) > rayon)
		{
			double angl = position.orientation(an.position);
			double x = position.getX() + Math.cos(angl)*5;
			double y = position.getY() + Math.sin(angl)*5;
			
			action = new Action(this, new FPoint(x,y), Action.MARCHE);
		}
		return false;
	}
	
	private boolean seReproduire(Map m, int rayon)
	{
		if(jaugeNouriture / jaugeNouritureMax > 0.85 && age >= ageAdulte && age < ageVieux)
		{
			Animal pm = m.animalMELePlusProche(this);
			if(pm != null)
				if(pm.getPosition().distance(position) < rayon)
				{
					action = new Action(this, pm, Action.SEREPRODUIT);
					return true;
				}
			
		}
		return false;
	}
	
	private boolean attaqueProie(Map m, int rayon)
	{
		if(jaugeNouriture / jaugeNouritureMax < 0.85 && jaugeEnergie / jaugeEnergieMax > 0.20)
		{
			
			Animal pm = m.proieLePlusProche(this);
//			System.out.println(pm.position + "");
			if(pm != null && pm.getPosition().distance(position) < rayon)
			{
//				System.out.println(pm.position + "");
				action = new Action(this, pm, Action.CHASSE);
				return true;
			}
		}
		return false;
	}
	
	private boolean actionFuire(Map m,int rayon)
	{
		Animal a = m.predateurLePlusProche(this);
		if(a != null && a.position.distance(position) < rayon)
		{
			double or = a.position.orientation(position);
			int x = (int)(position.getX()+Math.cos(or)*5-3+m.getRandom()*7);
				if(x >= m.tailleMapX())x = m.tailleMapX()-1;
				if(x < 0)x = 0;
			int y = (int)(position.getY()+Math.sin(or)*5-3+m.getRandom()*7);
				if(y >= m.tailleMapY())y = m.tailleMapY()-1;
				if(y < 0)y = 0;
			
			action = new Action(this, new FPoint(x,y), Action.FUIRE);
			return true;
		}
		return false;
	}

	private boolean actionDormir(Map m)
	{
//		if(jaugeEnergie/jaugeEnergieMax < 0.9)
		{
			action = new Action(this,position.clone(), Action.DORMIR);
		}
		return false;
	}
	
	
	
	
	
	private FPoint dernièreDestination = null;
	private ArrayList<FPoint> trajet = new ArrayList<FPoint>();
	private FPoint lastPos = null;
	
	public void déplacement(Map m, FPoint destination)
	{
		
		if(!destination.equals(dernièreDestination))
		{
			if(trajet != null)
			{
				if(trajet.size() == 0 && m.getArbre(position) == 0)
					m.blockPos(false, position);
				else if(trajet.size() > 0 && m.getArbre(trajet.get(trajet.size()-1)) == 0)
					m.blockPos(false, trajet.get(trajet.size()-1));
			}	
			dernièreDestination = destination.clone();
			
			boolean[][] mapT = new boolean[m.tailleMapX()][m.tailleMapY()];
			
			for(int x = 0 ; x < m.tailleMapX() ; x++)
			for(int y = 0 ; y < m.tailleMapY() ; y++)
				mapT[x][y] = !m.estBlocker(x, y);
			
			trajet = Astar.findChemin(position, destination, mapT);
			
			if(trajet == null)
			{
				action = new Action(this,getPosition(), Action.ATTEND);
			}
			else
			{
			
				for(int i = 0 ; i < trajet.size() ; i++)
				{
					trajet.get(i).translate(0.5, 0.5);
				}
				if(trajet.size() > 0)
				{
					if(m.estBlocker(destination))
						trajet.remove(trajet.size()-1);
					if(trajet.size() > 1)
						trajet.remove(0);
					
					if(trajet.size() > 0)
						m.blockPos(true, trajet.get(trajet.size()-1));
				}
			}
			
		}
		
		
		lastPos = position.clone();
		
		if(trajet != null && trajet.size() > 0)
		{
			
			double V = vitesse;
			if(action.getType() == Action.FUIRE || action.getType() == Action.CHASSE)
				if(jaugeEnergie > 5)
					V *= vitesseSprint;
			
			if(jaugeEnergie < V)
			{
				V = jaugeEnergie;
				jaugeEnergie = 0;
			}
			else
			{
				jaugeEnergie -= V;
			}
			
			if(position.distance(trajet.get(0)) < V)
			{
//				System.out.println("TP : " + trajet.get(0));
				V -= position.distance(trajet.get(0)); 
				position.setLocation(trajet.get(0).getX(),trajet.get(0).getY());
				
				trajet.remove(0);
			}
		//	else
			if(trajet.size() > 0)
			{
//				System.out.println("GO : " + trajet.get(0));
				
				double angle = position.orientation(trajet.get(0));
				// enlève l'herbe quant il se déplace
				if(m.getRandom() > 0.98)
					m.removeHerbe(position, 1);
				position.translate(Math.cos(angle) * V,Math.sin(angle) * V);
				
			}
		}
		
	}
	
	
	
	
	
	


	
	
	
	
	
	
	public FPoint getPosition(){return position;}
	
	public Action getAction(){return action;}
	public void setAction(Action a){action = a;}
	
	
	public boolean neSeDeplacePlus()
	{
		if(trajet == null || trajet.size() == 0)
			return true;
		if(lastPos.equals(position))
			return true;
		return false;		
	}
	
	
	
	
	public void mange(Map m, FPoint p)
	{
		if(vegetarien)
		{
			int herbe = m.getHerbe(p);
			
//			System.out.println(arbre);
			
			m.removeHerbe(p, herbe);
			jaugeNouriture += herbe;
			
			
			if(jaugeNouriture > jaugeNouritureMax)
				jaugeNouriture = jaugeNouritureMax;
		}
		else
		{
			double corp = Math.min(m.getCorp(p),10);
			m.removeCorp(p, corp);
			jaugeNouriture += corp;
			
			
			if(jaugeNouriture > jaugeNouritureMax)
				jaugeNouriture = jaugeNouritureMax;
		}
	}
	
	public void affame(int i)
	{
		jaugeNouriture -= i;
	}
	
	
	public boolean getRegime(){return vegetarien;}






	public void blesse(Map m,double d) {
		PV -= d;
		if(PV <= 0)
			m.killAnimal(this);
	}


	
	public Animal enfant(Map m)
	{
		Animal an = new Animal(m,position.clone(), type, vegetarien, regroupement, nocturne, vitesseSprint, PVMax,
				jaugeEnergieMax, jaugeNouritureMax, troupeau, 0, ageAdulte, ageVieux);
		troupeau.addAnimal(an);
		
		return an;
	}




	
	public double getEnergi(){return jaugeEnergie;}
	public double getEnergiMax(){return jaugeEnergieMax;}
	public double getPV(){return PV;}
	public double getPVMax(){return PVMax;}
	public double getBouffe(){return jaugeNouriture;}
	public double getBouffeMax(){return jaugeNouritureMax;}
	public boolean vivant()
	{
		if(PV > 0 && timerDead != 0)
			return true;
		return false;
	}
	public int getType(){return type;}
	public int getAge(){return age;}
	public int getAgeAdulte(){return ageAdulte;}
	public int getAgeVieux(){return ageVieux;}
	public void addAnnee(){age ++;}
	public void setTimerDead(int i){timerDead = i;}
	public int getTimerDead(){return timerDead;}
	

}
