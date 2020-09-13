
package pMath;

/**
 * This class represents an Node. It has all the appropriate fields as well
 * as getter and setter to be used by the A* algorithm.
 * <p>
 * <p>
 * An <code>Node</code> has x- and y-coordinates and can be walkable or not.
 * A previous Node may be set, as well as the
 * <code>fCosts</code>, <code>gCosts</code> and <code>hCosts</code>.
 * <p>
 * <p>
 * <code>fCosts</code>: <code>gCosts</code> + <code>hCosts</code>
 * <p>
 * <code>gCosts</code>: calculated costs from start Node to this Node
 * <p>
 * <code>hCosts</code>: estimated costs to get from this Node to end Node
 * <p>
 * <p>
 * A subclass has to override the heuristic function
 * <p>
 * <code>sethCosts(Node endNode)</code>
 * <p>
 * @see ExampleNode#sethCosts(Node endNode) example Implementation using manhatten method
 * <p>
 *
 * @version 2.0
 */
public class Node {

    /** costs to move sideways from one square to another. */
    protected static final int BASICMOVEMENTCOST = 10;
    /** costs to move diagonally from one square to another. */
    protected static final int DIAGONALMOVEMENTCOST = 14;

    private int xPosition;
    private int yPosition;
    private boolean walkable;

    // for pathfinding:

    /** the previous Node of this one on the currently calculated path. */
    private Node previous;

    /** weather or not the move from previous to this Node is diagonally. */
    private boolean diagonally;

    /** optional extra penalty. */
    private int movementPanelty;


    /** calculated costs from start Node to this Node. */
    private int gCosts;

    /** estimated costs to get from this Node to end Node. */
    private int hCosts;

    /**
     * constructs a walkable Node with given coordinates.
     *
     * @param xPosition
     * @param yPosition
     */
    public Node(int xPosition, int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.walkable = true;
        this.movementPanelty = 0;
    }

    /**
     * returns weather or not the move from the <code>previousNode</code> was
     * diagonally. If it is not diagonal, it is sideways.
     *
     * @return
     */
    public boolean isDiagonaly() {
        return diagonally;
    }

    /**
     * sets weather or not the move from the <code>previousNode</code> was
     * diagonally. If it is not diagonal, it is sideways.
     *
     * @param isDiagonaly
     */
    public void setIsDiagonaly(boolean isDiagonaly) {
        this.diagonally = isDiagonaly;
    }

    /**
     * sets x and y coordinates.
     *
     * @param x
     * @param y
     */
    public void setCoordinates(int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
    }

    /**
     * @return the xPosition
     */
    public int getxPosition() {
        return xPosition;
    }

    /**
     * @return the yPosition
     */
    public int getyPosition() {
        return yPosition;
    }

    /**
     * @return the walkable
     */
    public boolean isWalkable() {
        return walkable;
    }

    /**
     * @param walkable the walkable to set
     */
    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

    /**
     * returns the node set as previous node on the current path.
     *
     * @return the previous
     */
    public Node getPrevious() {
        return previous;
    }

    /**
     * @param previous the previous to set
     */
    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    /**
     * sets a general penalty for the movement on this node.
     *
     * @param movementPanelty the movementPanelty to set
     */
    public void setMovementPanelty(int movementPanelty) {
        this.movementPanelty = movementPanelty;
    }

    /**
     * returns <code>gCosts</code> + <code>hCosts</code>.
     * <p>
     *
     *
     * @return the fCosts
     */
    public int getfCosts() {
        return gCosts + hCosts;
    }

    /**
     * returns the calculated costs from start Node to this Node.
     *
     * @return the gCosts
     */
    public int getgCosts() {
        return gCosts;
    }

    /**
     * sets gCosts to <code>gCosts</code> plus <code>movementPanelty</code>
     * for this Node.
     *
     * @param gCosts the gCosts to set
     */
    private void setgCosts(int gCosts) {
        this.gCosts = gCosts + movementPanelty;
    }

    /**
     * sets gCosts to <code>gCosts</code> plus <code>movementPanelty</code>
     * for this Node given the previous Node as well as the basic cost
     * from it to this Node.
     *
     * @param previousNode
     * @param basicCost
     */
    public void setgCosts(Node previousNode, int basicCost) {
        setgCosts(previousNode.getgCosts() + basicCost);
    }

    /**
     * sets gCosts to <code>gCosts</code> plus <code>movementPanelty</code>
     * for this Node given the previous Node.
     * <p>
     * It will assume <code>BASICMOVEMENTCOST</code> as the cost from
     * <code>previousNode</code> to itself if the movement is not diagonally,
     * otherwise it will assume <code>DIAGONALMOVEMENTCOST</code>.
     * Weather or not it is diagonally is set in the Map class method which
     * finds the adjacent Nodes.
     *
     * @param previousNode
     */
    public void setgCosts(Node previousNode) {
        if (diagonally) {
            setgCosts(previousNode, DIAGONALMOVEMENTCOST);
        } else {
            setgCosts(previousNode, BASICMOVEMENTCOST);
        }
    }

    /**
     * calculates - but does not set - g costs.
     * <p>
     * It will assume <code>BASICMOVEMENTCOST</code> as the cost from
     * <code>previousNode</code> to itself if the movement is not diagonally,
     * otherwise it will assume <code>DIAGONALMOVEMENTCOST</code>.
     * Weather or not it is diagonally is set in the Map class method which
     * finds the adjacent Nodes.
     *
     * @param previousNode
     * @return gCosts
     */
    public int calculategCosts(Node previousNode) {
        if (diagonally) {
            return (previousNode.getgCosts()
                    + DIAGONALMOVEMENTCOST + movementPanelty);
        } else {
            return (previousNode.getgCosts()
                    + BASICMOVEMENTCOST + movementPanelty);
        }
    }

    /**
     * calculates - but does not set - g costs, adding a movementPanelty.
     *
     * @param previousNode
     * @param movementCost costs from previous Node to this Node.
     * @return gCosts
     */
    public int calculategCosts(Node previousNode, int movementCost) {
        return (previousNode.getgCosts() + movementCost + movementPanelty);
    }

    /**
     * returns estimated costs to get from this Node to end Node.
     *
     * @return the hCosts
     */
    public int gethCosts() {
        return hCosts;
    }

    /**
     * sets hCosts.
     *
     * @param hCosts the hCosts to set
     */
    protected void sethCosts(int hCosts) {
        this.hCosts = hCosts;
    }

    /**
     * calculates hCosts for this Node to a given end Node.
     * Uses Manhatten method.
     *
     * @param endNode
     */
    public void sethCosts(Node endNode){
            this.sethCosts((absolute(this.getxPosition() - endNode.getxPosition())
                    + absolute(this.getyPosition() - endNode.getyPosition()))
                    * BASICMOVEMENTCOST);
        }

        private int absolute(int a) {
            return a > 0 ? a : -a;
        }


    /**
     * returns a String containing the coordinates, as well as h, f and g
     * costs.
     *
     * @return
     */
    @Override
    public String toString() {
        return "(" + getxPosition() + ", " + getyPosition() + "): h: "
                + gethCosts() + " g: " + getgCosts() + " f: " + getfCosts();
    }

    /**
     * returns weather the coordinates of Nodes are equal.
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Node other = (Node) obj;
        if (this.xPosition != other.xPosition) {
            return false;
        }
        if (this.yPosition != other.yPosition) {
            return false;
        }
        return true;
    }

    /**
     * returns hash code calculated with coordinates.
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + this.xPosition;
        hash = 17 * hash + this.yPosition;
        return hash;
    }

}