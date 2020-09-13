package affichage2;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class SourisListener implements MouseListener {	
	
	public static boolean[] Souris = new boolean[20];
	private static boolean[] LastSouris = new boolean[20];
	
	public static boolean[] DebutSouris = new boolean[20];
	public static boolean[] FinSouris = new boolean[20];
	
	public void mouseClicked(MouseEvent e) {
		
//		System.out.println("clavier : " + e.getKeyCode());			//affichage

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {		
		
		for(int i = 1 ; i < 10 ; i ++)
		if(e.getButton() == i) Souris[i] = true;
		
	}

	public void mouseReleased(MouseEvent e) {
		
		for(int i = 1 ; i < 10 ; i ++)
			if(e.getButton() == i) Souris[i] = false;	
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void Reset(){
		DebutSouris = new boolean[10];
		FinSouris = new boolean[10];
	}
	public static void Save(){
		for(int i = 1 ; i < 10 ; i ++)
			LastSouris[i] = Souris[i];
	}
	public static void Apply(){
		for(int i = 1 ; i < 10 ; i ++){
			if(LastSouris[i] == false && Souris[i] == true)DebutSouris[i] = true;
			if(LastSouris[i] == true && Souris[i] == false)FinSouris[i] = true;
		}
	}
	
	
	
	
	
	
	
	
	
	

}
