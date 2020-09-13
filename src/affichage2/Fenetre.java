package affichage2;

import java.awt.MouseInfo;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import pMath.FPoint;

/**permet la création de fenetre*/
public class Fenetre extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Panneau panel = null;
	private Afficheur aff = null;
	
	/**position X de la souris*/
	public int mx = 0 ;
	/**position Y de la souris*/
	public int my = 0 ;
	
	
//	private int maxSizeX = 0;
//	private int maxSizeY = 0;
	private double ratioSize = 1;
	
	
	private JFrame frame = new JFrame();
	
	
	/**constructeur de la fenetre*/
	public Fenetre(String titre , boolean changerTaille,int tailleX ,int tailleY,double ratioFenetre, Afficheur affich,
			MouseListener ml,KeyListener k1)
	{

		aff = affich;
		panel = new Panneau(ratioFenetre,aff);
		
		ratioSize = ratioFenetre;
		
		frame = new JFrame();
		frame.setResizable(changerTaille);										//redimantionnement
	    frame.setTitle(titre);											//nom de la fenètre
	    frame.setSize(tailleX,tailleY);										//taille
	    frame.setLocationRelativeTo(null);								//apparait au milieu
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);			//fin du programme a la fin de la fenètre
	    frame.setVisible(true);											//visible
	    frame.setContentPane(panel);									//objet panel
	    frame.addKeyListener(k1);								//afficher keycode clavier
	    frame.addMouseListener(ml);								//afficher keycode sourit
	    frame.addWindowListener(null);
	}
	
	public Fenetre()
	{
		this("Undifined",true,640,400,1,null,null,null);
	}
	
	
	
	
	
	
	/**suprime la frame*/
	public void Suprimer()
	{
		frame.dispose();
	}
	
	
	
	
	
	/**retourn les dimention de l'écran*/
	public int[] dimention()
	{
		return new int[] {panel.largeur,panel.hauteur};
	}
	
	

	
	
	/**actualise l'affiche*/
	public void afficher()
	{
		
		panel.ratio = ratioSize;
		if(panel.affichageFini)
		{
//			System.out.println(panel.affichageFini);
			panel.repaint();
		}
		
	}
	
	
	
	
	
	
	/***/
	
	
	public FPoint coordonnéSouris()
	{

//		System.out.println(panel.largeur + " dad " + frame.getWidth());
		mx = MouseInfo.getPointerInfo().getLocation().x-panel.getLocationOnScreen().x;
		my = MouseInfo.getPointerInfo().getLocation().y-panel.getLocationOnScreen().y;
		
		double x =  mx - panel.x(0) ;
		double y = -my + panel.y(100);
		
		return new FPoint(x,y);
	}
	
	
	
	
	/**permet d'actualiser les entrés clavier / souris*/
/*	public void getter(){
		
		mx = MouseInfo.getPointerInfo().getLocation().x-getLocationOnScreen().x;
		my = MouseInfo.getPointerInfo().getLocation().y-getLocationOnScreen().y;
		
		keyListener.Reset();
		
		keyListener.Apply();
		
		keyListener.Save();
	}
*/
			
}
