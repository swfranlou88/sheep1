package simulateur;

import java.awt.event.MouseEvent;

import affichage2.SourisListener;
import pMath.FPoint;

public class Souris extends SourisListener{

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {

		
		FPoint s = Exec.fen.coordonnéSouris();
		
//		System.out.println(s);
		
		double vraiX = (s.getX() )/Exec.panel.getZoom()- Exec.panel.getX();
		double vraiY = (s.getY() )/Exec.panel.getZoom()- Exec.panel.getY();
		
//		System.out.println("fzafza");
		
		
		Animal an = null;
		for(int i = 0 ; i < Exec.carte.animaux.size() ; i ++)
		{
			if(new FPoint(vraiX,vraiY).distance(Exec.carte.animaux.get(i).getPosition()) < 1)
				an = Exec.carte.animaux.get(i);
//			System.out.println(new FPoint(vraiX,vraiY).distance(Exec.carte.animaux.get(i).getPosition()));
			
		}
		Exec.carte.setAnimalSelect(an);
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
