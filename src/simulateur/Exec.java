package simulateur;

import affichage2.Fenetre;

public class Exec {

//	public static Frame f = new Frame("bioWorld",false,1200,800,0.1);
	public static AffichageMonde panel = new AffichageMonde();
	public static Souris souris = new Souris();
	public static Clavier clavier = new Clavier();
	public static Fenetre fen = new Fenetre("Fenetre",true,1000,700,1.5,panel,souris,clavier);
//	public static Fenetre fen2 = new Fenetre("Fenetre",true,1200,600,1.5,panel);
	
//	public static Map carte = new Map(240,160);
//	public static Map carte = new Map(200,150);
	public static Map carte = new Map(200,200);
	
	public static int TIME = 10;
	
	
	public static void main(String[] args) {

		
	/*	Animal m1 = new Animal(new FPoint(40,30),1,true);
		Animal m2 = new Animal(new FPoint(60,20),1,true);
		Animal m3 = new Animal(new FPoint(20,35),1,true);
		Animal m4 = new Animal(new FPoint(35,25),1,true);
		
		Animal l1 = new Animal(new FPoint(20,20),2,false);
		
		carte.animaux.add(m1);
		carte.animaux.add(m2);
		carte.animaux.add(m3);
		carte.animaux.add(m4);
		
		carte.animaux.add(l1);*/
/*		carte.animaux.add(new Animal(new FPoint(45,30.0)));
		carte.animaux.add(new Animal(new FPoint(40,35.0)));
		carte.animaux.add(new Animal(new FPoint(42,32.0)));*/
		
		
		
//		for(int i = 0 ; i < 2000000 ; i++)
		{
			carte.update();			
			fen.afficher();
		}
		
		while(true)
		{
			
			if(!clavier.TouchePresser[10])
			{
				try {
					Thread.sleep(TIME);
				} catch (InterruptedException e) {e.printStackTrace();}
			}
			
			double dir = 0.2;
			
			if(clavier.TouchePresser[37])
				panel.depX(dir);
			if(clavier.TouchePresser[38])
				panel.depY(-dir);
			if(clavier.TouchePresser[39])
				panel.depX(-dir);
			if(clavier.TouchePresser[40])
				panel.depY(dir);
			
			
			
			
			
			carte.update();
//			System.out.println(m1);
			
			fen.afficher();
			
		}

	}

}
