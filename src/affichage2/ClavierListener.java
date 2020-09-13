package affichage2;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class ClavierListener implements KeyListener{		// touche clavier
	
	
	public boolean[] TouchePresser = new boolean[525];

	

	public void keyPressed(KeyEvent e) {			//activer
		
		TouchePresser[e.getKeyCode()] = true;
		// Pour TField
	
//		for(int i = 0 ; i < TField.TTF.size() ; i++)
//			TField.TTF.get(i).engine(e.getKeyCode()+"Ë"+e.getKeyChar()+"Ë"+(int)(e.getKeyChar()));
	
		
		
//		System.out.println("clavier : " + e.getKeyCode() + " ; " + e.getKeyChar() + " ; " + (int)(e.getKeyChar()));			//affichage
	}


	public void keyReleased(KeyEvent e) {			//desactive
		
		for(int i = 0 ; i < 525 ; i ++)
		if(e.getKeyCode() == i) TouchePresser[i] = false;
		
	}


	public void keyTyped(KeyEvent e) {
	}
	
	
	

	
	
	/*
	 *   0				= ²	ou %
	 *   
	 *   8				= Del / Suprimer
	 *   10				= Enter
	 *   
	 *   16				= Majuscule
	 *   17				= Controle		}	Alt GR
	 *   18				= Alt			}	Alt GR
	 *   19				= Pause / Attn
	 *   20				= Ver. Majuscule
	 *   
	 *   27 			= Echape
	 *   
	 *   32				= Espace								///////////////////////
	 *   33				= Supérieur Pad
	 *   34				= Inférieur Pad
	 *   35				= Fin Pad
	 *   36				= Flêche Pad
	 *   37				= F-> Gauche
	 *   38				= F-> Haut
	 *   39				= F-> Droit
	 *   40				= F-> Bas
	 *   
	 *   44				= ?
	 *   
	 *   48 / 57		= Chifre Clavier						///////////////////////
	 *   
	 *   59				= ;
	 *   61				= 	=
	 *   
	 *   65 / 90 		= Alphabet								///////////////////////
	 *   
	 *   65 a : b : c : 68 d : e : f : 71 g : h : i : 74 j : k : l : 77 m  : n : o : p : 81 q : r : s : 84 t : u : v : 87 w : x : y : 90 z
	 *   
	 *   96 / 105		= Chifre Pad							///////////////////////
	 *   
	 *   106			= * Pad
	 *   107			= + Pad
	 *   
	 *   109			= - Pad
	 *   110			= . Pad
	 *   111			= / Pad
	 *   
	 *   112 / 123		= F1 -> F12
	 *   
	 *   127			= Suppr
	 *   130			=   ^
	 *   
	 *   144			= Verr Num Pad
	 *   145			= Arrêt / Défil
	 *   
	 *   151			= µ
	 *   155			= Inser
	 *   513			= / :
	 *   515			= £
	 *   517			= §
	 *   522			= °
	 *   
	 *   524			= Retour Windows
	 * 	 525 			= Script ?
	 */
	
	
	
	
	
	
	

}
