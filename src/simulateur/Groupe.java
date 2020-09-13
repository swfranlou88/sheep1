package simulateur;

import java.awt.Color;
import java.util.ArrayList;

import affichage2.Affichable;
import affichage2.Afficheur;
import affichage2.Panneau;
import pMath.FPoint;

public class Groupe implements Affichable{

	
	private ArrayList<Animal> membre = new ArrayList<Animal>();
	private double rayonTerritoireMoyenne = 7;
	private double rayonTerritoire = 0;
	private FPoint centre = null;
	
	
	public Groupe(Animal first, Animal second)
	{
		membre.add(first);
		membre.add(second);
		
		first.troupeau = this;
		second.troupeau = this;
	}
	
	
	
	
	
	
	
	
	public void update(Map m)
	{	
//		System.out.println(membre.size());
		
		/**réunion de plusieur troupeau de même espece*/
		for(int i = 0 ; i < m.troupeaux.size() ; i++)
		{
			Groupe g2 = m.troupeaux.get(i);
			if(g2.membre.size() == 0)
			{
				m.troupeaux.remove(i);
				i--;
			}
			else
				if(g2 != this && g2.membre.get(0).getType() == membre.get(0).getType())
				{
					if(centre!= null && centre.distance(g2.centre) < rayonTerritoire+g2.rayonTerritoire)
					{
						while(g2.membre.size() > 0)
						{
							addAnimal(g2.membre.get(0));
							g2.membre.remove(0);
						}
						m.troupeaux.remove(i);
						i--;
					}
				}
		}
		
		int x = 0;
		int y = 0;
		for(int i = 0 ; i < membre.size() ; i ++)
		{
			if(!membre.get(i).vivant())
			{
				membre.remove(i);
				i--;
			}
			else
			{
				x += membre.get(i).getPosition().getX();
				y += membre.get(i).getPosition().getY();
			}
		}
		
		centre = new FPoint(x/(double)(membre.size()),y/(double)(membre.size()));
		rayonTerritoire = rayonTerritoireMoyenne * Math.log(membre.size()-1);
		if(membre.size() <= 2)
			rayonTerritoire = rayonTerritoireMoyenne;
	}
	
	
	
	
	public FPoint pointInZone(FPoint p)
	{
//		System.out.println(centre);
		
		double dist = centre.distance(p);
		if(dist <= rayonTerritoire)
			return p;
		
		double an = centre.orientation(p);
		
		double x = (int)(centre.getX()+Math.cos(an)/dist*rayonTerritoire) + 0.5;
		double y = (int)(centre.getY()+Math.sin(an)/dist*rayonTerritoire) + 0.5;

		return new FPoint(x,y);
	}








	@Override
	public void dessiner(Panneau p, Afficheur a)
	{
		AffichageMonde aff = (AffichageMonde)a;
		
		p.drawArc(Color.BLUE, new FPoint((centre.getX() + aff.getAffX()) * aff.getAffZoom(), 
				(centre.getY() + aff.getAffY()) * aff.getAffZoom()), 
				rayonTerritoire * aff.getAffZoom() * 2, rayonTerritoire * aff.getAffZoom() * 2, 0, Math.PI*2);
		
	}
	
	
	
	
	
	
	
	public void addAnimal(Animal a)
	{
		membre.add(a);
		a.troupeau = this;
	}
	
	
	
	
}
