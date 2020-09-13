package pMath;

import java.util.ArrayList;

public class Astar {

	
	private int width = 0;
	private int height = 0;
	private Node[][] nodes = null;
	
	public ArrayList<Node> openList = new ArrayList<Node>();
	private ArrayList<Node> closedList = new ArrayList<Node>();
	
	private static final int MAX_ERREUR = 500;
	
	
	
	private Astar(int x1 , int x2)
	{
		width = x1;
		height = x2;
		nodes = new Node[width][height];
		for(int x = 0 ; x < width ; x++)
		for(int y = 0 ; y < height ; y++)
			nodes[x][y] = new Node(x,y);
		
	}
	
	
	
		
	
	private void setObstacle(int x, int y)
	{
		nodes[x][y].setWalkable(false);
	}
	
	/*
	
	private void affichage()
	{
		for(int y = 0 ; y < height ; y++)
		{
			for(int x = 0 ; x < width ; x++)
			{
				if(nodes[x][y].isWalkable())
					System.out.print(": ");
				else
					System.out.print("X ");
			}
			System.out.println("");
		}
	}
	
	
	
	*/
	
	/*
	private void affichage(ArrayList<Node> nl)
	{
		for(int y = 0 ; y < height ; y++)
		{
			for(int x = 0 ; x < width ; x++)
			{
				if(nodes[x][y].isWalkable())
					if(nl.contains(nodes[x][y]))
						System.out.print(nl.indexOf(nodes[x][y])+" ");
					else
						System.out.print(": ");
				else
					System.out.print("X ");
			}
			System.out.println("");
		}
	}
	
	*/
	
	
	
	
	
	
	private Node getNodeWithLowestCost()
	{
		Node res = null;
		for(int i = 0 ; i < openList.size() ; i++)
		{
			if(res == null || openList.get(i).getfCosts() < res.getfCosts())
				res = openList.get(i);
		}
		return res;
	}
	
	
	
	
	private ArrayList<Node> getAdjacent(Node curent)
	{
		ArrayList<Node> nl = new ArrayList<Node>();
		
	//	Node n00 = nodes[curent.getxPosition()-1][curent.getyPosition()-1];
		for(int x = -1 ; x <= 1 ; x++)
		for(int y = -1 ; y <= 1 ; y++)
		{
			try{
				Node n = nodes[curent.getxPosition()+x][curent.getyPosition()+y];
				if(!closedList.contains(n))
					if(!(x == 0 && y == 0))
					{
						if(x == 0 || y == 0)
						{
							if(n.isWalkable())
								nl.add(n);
							else
								closedList.add(n);
						}
						else
						{
							Node n3 = nodes[curent.getxPosition()][curent.getyPosition()+y];
							Node n2 = nodes[curent.getxPosition()+x][curent.getyPosition()];
							if(n.isWalkable() && n3.isWalkable() && n2.isWalkable())
							{
								nl.add(n);
		//						System.out.println(n.getxPosition() + " , " + n.getyPosition());
							}
		//					else
		//						closedList.add(n);
						}
					}
			}catch(Exception e){}
		}
		return nl;
	}
	
	
	
	private void findPath(Node sourceNode, Node targetNode)
	{

		openList.remove(sourceNode);
		closedList.add(sourceNode);
		ArrayList<Node> nl = getAdjacent(sourceNode);
		for(int i = 0 ; i < nl.size() ; i ++)
			if(!openList.contains(nl.get(i)))
			{
				nl.get(i).setgCosts(sourceNode);
				nl.get(i).sethCosts(targetNode);
				nl.get(i).setPrevious(sourceNode);
				openList.add(nl.get(i));
			}
	}
	
	
	
	
	private ArrayList<Node> calcPath(Node sourceNode, Node targetNode)
	{
		ArrayList<Node> nl2 = new ArrayList<Node>();
		
		Node ns = sourceNode;
		openList.add(ns);
		
		while(openList.size() != 0 && !ns.equals(targetNode))
		{
			findPath(ns,targetNode);		
			ns = getNodeWithLowestCost();
			
			if(closedList.size() >= MAX_ERREUR)
				return null;
					
		}
		
		if(openList.size() == 0)
			return null;
		
	//	System.out.println(ns);
		if(ns != null)
			if(ns.equals(targetNode))
			{
				while(ns != null)
				{
	//				System.out.println(nl2.size());
					nl2.add(ns);
					ns = ns.getPrevious();
				}
			}
		
		return nl2;
	}
	
	
	
	public Node[][] tab(){return nodes;}
	
	
	
	
	
	
	
	
	
	public static ArrayList<FPoint> findChemin(FPoint origin, FPoint destination, boolean[][] bs)
	{
		
		try{
			Astar c = new Astar(bs.length,bs[0].length);
			
			if(bs[(int)destination.getX()][(int)destination.getY()] == false)
				bs[(int)destination.getX()][(int)destination.getY()] = true;
			
			for(int x = 0 ; x < bs.length ; x ++)
			for(int y = 0 ; y < bs[0].length ; y++)
			{
				if(!bs[x][y])
					c.setObstacle(x,y);
			}
			
			ArrayList<Node> nl = c.calcPath(
					c.tab()[(int)(origin.getX())][(int)(origin.getY())],
					c.tab()[(int)(destination.getX())][(int)(destination.getY())]);
			if(nl == null)
				return null;
			
			ArrayList<FPoint> np = new ArrayList<FPoint>();
			
	//		c.affichage(nl);
			
			while(nl.size() > 0)
			{
				np.add(0,new FPoint(nl.get(0).getxPosition(),nl.get(0).getyPosition()));
				nl.remove(0);
			}
			
	//		System.out.println(origin + " ; " + destination);
			
			return np;
			
		}catch(Exception e)
		{
			return null;
		}
		
	}
	
	
	
	
	
	
	
	
}
