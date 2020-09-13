package affichage2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.JPanel;

import pMath.FPoint;

/**créer l'espace d'affichage*/
public class Panneau extends JPanel{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**longeur de la fenetre*/
	public int largeur = 0;
	/**largeur de la fenetre*/
	public int hauteur = 0;
	
	
	
	private String messageError = "";
	private Long errorTime = 0L;
	private final int TIME_ERROR_PRINT = 4000;
	public boolean affichageFini = true;
	
	
	
	/**ratio de la taille de l'écran X/Y  */
	public double ratio = 1;
	private boolean abscicePlusPetit = false;
	/**taille X de l'ecran*/
	private double longX = 0;
	private double longY = 0;

	private Graphics2D g2d = null;
	private Afficheur aff = null;
	
	
	public Panneau(double ratioEcran,Afficheur a)
	{
		ratio = ratioEcran;
		aff = a;
	}
	
	
	
	
	
	
	
	
	
	public void paintComponent (Graphics g){
		 
		
		if(affichageFini)
		{
			affichageFini = false;
		
			Graphics2D g2 = (Graphics2D)g;
			g2d = g2; 
			
			largeur = getSize().width ;
			hauteur = getSize().height;		 
			 
			g.setColor(new Color(250,250,250));
			g2.fillRect(0, 0, largeur, hauteur);
			g.setFont(new Font("Monospaced",Font.BOLD,20));
			
			corrige();
		
			
			
			if(aff != null)
				aff.affiche(this);
			
			
			
			g.setColor(Color.black);
			g.fillRect(-1,-1, x(0)+1, hauteur+1);
			g.fillRect(-1,-1, largeur+1, y(0)+1);
			g.fillRect(x(100),-1, x(0)+1, hauteur+1);
			g.fillRect(-1, y(100), largeur+1, y(0)+1);
			
			
			/* Affichage Erreur */
			g.setFont(new Font("Monospaced",Font.BOLD,20));
			g.setColor(Color.RED);
			if(System.currentTimeMillis() <= errorTime)
				g.drawString(messageError, 10, hauteur-10);
			
	/*		g.setColor(Color.red);
			System.out.println(largeur);
			g.fillRect(largeur-10, 300, 20, 20);	
	*/		
			affichageFini = true;
		}
		
		
	}
	
	
	
	/**Affiche l'image au cordonner indiquer*/
	public void drawImage(Image img, FPoint p1, double sizeX, double sizeY)
	{
		g2d.drawImage(img,
				(int)(p1.getX() + 0.5),
				(int)(p1.getY() + 0.5 + sizeY - y(100)), 
				(int)(sizeX + 0.5), (int)(sizeY + 0.5), null);
	}
	
	
	
	/**affiche l'image centrer sur le point avec l'orientation indiquer*/
	public void drawImage(Image img, double x,double y, double sizeX, double sizeY, double orientation)
	{
		int tx = (int)(  x - 1.5 - sizeX/2 + Math.cos(orientation) + x(0));
		int ty = (int)(-(y - 0.5 - sizeY/2 + Math.sin(orientation))+ y(100));
		g2d.translate(tx, ty);
		g2d.rotate(orientation);
		
		g2d.drawImage(img,0,-(int)(sizeY + 0.5),(int)(sizeX + 0.5), (int)(sizeY + 0.5), null);
		
		g2d.rotate(-orientation);
		g2d.translate(-tx, -ty);
	}
	public void drawImage(Image img, FPoint p1, double sizeX, double sizeY, double orientation)
	{
		drawImage(img,p1.getX(),p1.getY(),sizeX,sizeY,orientation);
	}
	
	
	
	/**dessine simplement le contour du rectangle*/
	public void drawRect(Color col, double x,double y, double sizeX, double sizeY, double orientation)
	{
		int tx = (int)(  x - 1.5 - sizeX/2 + Math.cos(orientation) + x(0));
		int ty = (int)(-(y - 0.5 - sizeY/2 + Math.sin(orientation))+ y(100));
		
		g2d.translate(tx, ty);
		g2d.rotate(orientation);

		g2d.setColor(col);
		g2d.drawRect(0,-(int)(sizeY + 0.5),(int)(sizeX + 0.5), (int)(sizeY + 0.5));
		
		g2d.rotate(-orientation);
		g2d.translate(-tx, -ty);
	}
	public void drawRect(Color col, FPoint p1, double sizeX, double sizeY, double orientation)
	{
		drawRect(col,p1.getX(),p1.getY(),sizeX,sizeY,orientation);
	}
	
	
	
	
	/**dessine simplement le contour du rectangle*/
	public void drawArc(Color col, double x, double y, double sizeX, double sizeY, double startAngle, double finalAngle)
	{
		int tx = (int)(  x - 0.5 - sizeX/2 + x(0));
		int ty = (int)(-(y - 0.5 - sizeY/2)+ y(100));
		
		g2d.translate(tx, ty);
//		g2d.rotate(orientation);

		g2d.setColor(col);
		g2d.drawArc(0,-(int)(sizeY + 0.5),(int)(sizeX + 0.5), (int)(sizeY + 0.5),(int)(startAngle/Math.PI*180 + 0.5),(int)(finalAngle/Math.PI*180 + 0.5));
		
//		g2d.rotate(-orientation);
		g2d.translate(-tx, -ty);
	}
	public void drawArc(Color col, FPoint p1, double sizeX, double sizeY,double startAngle, double finalAngle)
	{
		drawArc(col,p1.getX(),p1.getY(),sizeX,sizeY,startAngle,finalAngle);
	}
	
	
	
	
	
	/**dessiner un rectangle de couleur*/
	public void fillRect(Color col, double x,double y, double sizeX, double sizeY, double orientation)
	{
//		System.out.println(col);
		
		int tx = (int)(  x - 1.5 - sizeX/2 + Math.cos(orientation) + x(0));
		int ty = (int)(-(y - 0.5 - sizeY/2 + Math.sin(orientation))+ y(100));
		
		g2d.translate(tx, ty);
		g2d.rotate(orientation);

		g2d.setColor(col);
		g2d.fillRect(0,-(int)(sizeY + 0.5),(int)(sizeX + 0.5), (int)(sizeY + 0.5));
		
		g2d.rotate(-orientation);
		g2d.translate(-tx, -ty);
	}
	public void fillRect(Color col, FPoint p1, double sizeX, double sizeY, double orientation)
	{
		fillRect(col,p1.getX(),p1.getY(),sizeX,sizeY,orientation);
	}
	
	



	/** affiche une chaine de caratère avec la couleur indiquée*/
	public void drawString(String string, FPoint p1, Color col) {

		g2d.setColor(col);
		g2d.drawString(string,
				(int)(p1.getX() - 0.5 + x(0)),
				(int)(-(p1.getY() - 0.5)+ y(100)));
	}
	
	
	
	/**permet de tracer une ligne*/
	public void drawLine(Color col, FPoint p1,FPoint p2)
	{
		g2d.setColor(col);
		g2d.drawLine((int)(p1.getX() - 0.5 + x(0)),(int)(-(p1.getY() - 0.5)+ y(100)),
				(int)(p2.getX() - 0.5 + x(0)),(int)(-(p2.getY() - 0.5)+ y(100)));
	}
	
	
	
	
	/** Definit la taille de l'ecran en fonction du ratio*/
	private void corrige()
	{
		abscicePlusPetit = largeur < hauteur * ratio;
		if(abscicePlusPetit){
			longX = largeur / 100.0;
			longY = longX / ratio;
		}
		else
		{
			longY = hauteur / 100.0;
			longX = longY * ratio;
		}
	}
	
	int x(double pos)
	{
		return (int)(((largeur / 2.0) - (longX * 50.0)) + longX * pos);
	}
	int y(double pos)
	{
		return (int)(((hauteur / 2.0) - (longY * 50.0)) + longY * pos);
	}
	

	public void error(String str)
	{
		messageError = str;
		errorTime = System.currentTimeMillis() + TIME_ERROR_PRINT;
	}




	

	
	
}