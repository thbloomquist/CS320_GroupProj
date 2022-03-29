package edu.ycp.cs320.groupProj.model;

// model class for TextQuest
// keeps track of the moves, totaling North-South and East-West


public class DirectionsModel {
	public int northMove = 0;
	public int southMove = 0;
	public int eastMove = 0;
	public int westMove = 0;
	public int totalUp = 0;
	public int totalSide = 0;
	
	public DirectionsModel() {
	}
	
	public void reset() {
		northMove = 0;
		southMove = 0;
		westMove = 0;
		eastMove = 0;
		totalUp = 0;
		totalSide = 0;
	}
	
	public void moveNorth() {
		northMove++;
		calcUp();
	}
	
	public void moveSouth() {
		southMove++;
		calcUp();
	} //doesnt this need to be model.calc or something like that? idk
	
	public void moveEast() {
		eastMove++;
		calcSide();
	}
	
	public void moveWest() {
		westMove++;
		calcSide();
	}
	
	public void calcUp() {
		totalUp = (northMove-southMove);
	} //could change to calcVert and make abs(northMove - soutMove) to get total vertical movement
	
	public void calcSide() {
		totalSide = (eastMove-westMove);
	} //should change to abs(eastMove - westMove) to calculate if movement west is greater
	
	public void setMovements(int NORTH, int SOUTH, int EAST, int WEST) {
		northMove = NORTH;
		southMove = SOUTH;
		eastMove = EAST;
		westMove = WEST;
		calcUp();
		calcSide();
	}
	
	public int getWest() {
		return westMove;
	}
	public int getEast() {
		return eastMove;
	}
	public int getNorth() {
		return northMove;
	}
	public int getSouth() {
		return southMove;
	}
	public int getUp() {
		return totalUp;
	}
	public int getSide() {
		return totalSide;
	}
}
