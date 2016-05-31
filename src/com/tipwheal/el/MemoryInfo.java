package com.tipwheal.el;

public class MemoryInfo {
	private int[][] lastLoc = { { 0, 0 }, { 0, 0 }, { 0, 0 } };

	public void setLastLoc(int enemyID, int enemyX, int enemyY) {
		lastLoc[enemyID - 3][0] = enemyX;
		lastLoc[enemyID - 3][1] = enemyY;
	}

	public int getLastX(int enemyID) {
		return lastLoc[enemyID - 3][0];
	}

	public int getLastY(int enemyID) {
		return lastLoc[enemyID - 3][1];
	}
}
