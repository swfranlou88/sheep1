package simulateur;

import java.awt.Color;

import affichage2.Afficheur;
import affichage2.Panneau;
import pMath.FPoint;

public class AffichageMonde implements Afficheur{
	
	
	
	private double zoom = 5;
	private double x = 0;
	private double y = 0;
	
	private double affZoom = 5;
	private double affX = 0;
	private double affY = 0;
	






	@Override
	public void affiche(Panneau p) {
		
		affZoom = zoom;
		affX = x;
		affY = y;
		
		
//		System.out.println(p.y(100));
		
		if(Exec.carte != null)
			Exec.carte.dessiner(p, this);
		
		p.drawString(Exec.carte.upComp+"", new FPoint(5,10),Color.BLACK);
		
	}

	
	
	
	
	
	
	
	public double getZoom()
	{
		return zoom;
	}
	public void setZoom(double d)
	{
		zoom = d;
	}
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public void depX(double x) {
		this.x += x;
	}

	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public void depY(double y) {
		this.y += y;
	}
	

	public double getAffZoom() {
		return affZoom;
	}
	public double getAffX() {
		return affX;
	}
	public double getAffY() {
		return affY;
	}



	
	
}
