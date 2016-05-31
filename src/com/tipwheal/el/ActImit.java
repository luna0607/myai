package com.tipwheal.el;

import java.util.ArrayList;

public abstract class ActImit {

	/**
	 * 走一步之后在的格子。
	 * 
	 * @param dir
	 * @param x
	 * @param y
	 * @return
	 */
	public static int[] getNextCell(int dir, int x, int y) {
		int[] cell = new int[2];
		int[] add = ActImit.rotate(dir, 0, 1);
		cell[0] = x + add[0];
		cell[1] = y + add[1];
		for (int num : cell) {
			if (num < 0 || num > 14) {
				return null;
			}
		}
		return cell;
	}

	/**
	 * 得到一串行为后的位置，传入时要确保行为正确。
	 * 
	 * @param action
	 * @param x
	 * @param y
	 * @return
	 */
	public static int[] getNextCell(String action, int x, int y) {
		int[] loc = new int[2];
		loc[0] = x;
		loc[1] = y;
		for (String s : action.split(" ")) {
			int d = Integer.parseInt(s);
			if (d >= 5 && d <= 8) {
				loc = ActImit.getNextCell(d - 5, loc[0], loc[1]);
			}
		}
		return loc;
	}

	/**
	 * 一次行动占领的所有格子。
	 * 
	 * @param dir
	 * @param x
	 * @param y
	 * @param ID
	 * @return
	 */
	public static ArrayList<int[]> getNextOcyCells(int dir, int x, int y, int ID, int side) {
		ArrayList<int[]> result = new ArrayList<>();
		int weapon = ID % 3;
		int[] size = { 4, 5, 7 };
		int[][] dx = { { 0, 0, 0, 0 }, { 0, 0, 1, 1, 2 }, { -1, -1, -1, 0, 1, 1, 1 } };
		int[][] dy = { { 1, 2, 3, 4 }, { 1, 2, 0, 1, 0 }, { -1, 0, 1, 1, 1, -1, 0 } };
		for (int i = 0; i < size[weapon]; i++) {
			int[] loc = ActImit.rotate(dir, dx[weapon][i], dy[weapon][i]);
			loc[0] += x;
			loc[1] += y;
			if (loc[0] < 0 || loc[0] > 14 || loc[1] < 0 || loc[1] > 14) {
				break;
			}
			if (ActImit.isOthersHome(loc, ID, side)) {
				break;
			}
			result.add(loc);
		}
		return result;
	}

	/**
	 * 一串行动过后占领的所有格子。
	 * 
	 * @param action
	 * @param x
	 * @param y
	 * @param ID
	 * @return
	 */
	public static ArrayList<int[]> getOcyCells(String action, int x, int y, int ID, int side) {
		ArrayList<int[]> result = new ArrayList<>();
		int[] loc = new int[2];
		loc[0] = x;
		loc[1] = y;
		for (String s : action.split(" ")) {
			int i = Integer.parseInt(s);
			if (i >= 5 && i <= 8) {
				loc = ActImit.getNextCell(i - 5, loc[0], loc[1]);
			}
			if (i >= 1 && i <= 4) {
				for (int[] ocp : ActImit.getNextOcyCells(i - 1, loc[0], loc[1], ID, side)) {
					if (!result.contains(ocp)) {
						result.add(ocp);
					}
				}
			}
		}
		return result;
	}

	/**
	 * 旋转。
	 * 
	 * @param dir
	 * @param dx
	 * @param dy
	 * @return
	 */
	public static int[] rotate(int dir, int dx, int dy) {
		int[] loc = { 0, 0 };
		switch (dir) {
		case 0:
			loc[0] = dx;
			loc[1] = dy;
			break;
		case 1:
			loc[0] = dy;
			loc[1] = -dx;
			break;
		case 2:
			loc[0] = -dx;
			loc[1] = -dy;
			break;
		case 3:
			loc[0] = -dy;
			loc[1] = dx;
			break;
		}
		return loc;
	}

	/**
	 * 检查是不是别人的家。
	 * 
	 * @param loc
	 * @param ID
	 * @return
	 */
	public static boolean isOthersHome(int[] loc, int ID, int side) {
		int[][] home = { { 0, 5 }, { 0, 14 }, { 9, 14 }, { 14, 9 }, { 14, 0 }, { 5, 0 } };
		for (int i = 0; i < 6; i++) {
			if (i != ID + side * 3) {
				if (home[i][0] == loc[0] && home[i][1] == loc[1]) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 某武士可能的攻击范围。
	 * 
	 * @param x
	 * @param y
	 * @param ID
	 * @return
	 */
	public static ArrayList<int[]> psbAtkArea(int x, int y, int ID, int side) {
		ArrayList<int[]> result = new ArrayList<>();
		int[] curLoc = new int[2];
		curLoc[0] = x;
		curLoc[1] = y;
		for (int i = 0; i < 4; i++) {
			for (int[] loc : ActImit.getNextOcyCells(i, curLoc[0], curLoc[1], ID, side)) {
				if (!result.contains(loc)) {
					result.add(loc);
				}
			}
		}
		for (int i = 0; i < 4; i++) {
			int[] add = ActImit.rotate(i, 0, 1);
			curLoc[0] = x + add[0];
			curLoc[1] = y + add[1];
			for (int j = 0; j < 4; j++) {
				for (int[] loc : ActImit.getNextOcyCells(j, curLoc[0], curLoc[1], ID, side)) {
					if (!result.contains(loc)) {
						result.add(loc);
					}
				}
			}
		}
		return result;
	}

	/**
	 * 得到可以看见的格子。
	 * 
	 * @param x
	 * @param y
	 * @param size
	 * @return
	 */
	public static ArrayList<int[]> getManhattan(int x, int y, int size) {
		ArrayList<int[]> result = new ArrayList<>();
		int[] loc = { x, y };
		result.add(loc);
		for (int dir = 0; dir < 4; dir++) {
			for (int i = 1; i <= size; i++) {
				for (int j = 1; j <= i; j++) {
					loc = ActImit.rotate(dir, j, i - j);
					loc[0] = loc[0] + x;
					loc[1] = loc[1] + y;
					if (loc[0] <= 14 && loc[0] >= 0 && loc[1] <= 14 && loc[1] >= 0) {
						result.add(loc);
					}
				}
			}
		}
		return result;
	}

	/**
	 * 得到两点间的距离。
	 * 
	 * @param sx
	 * @param sy
	 * @param ex
	 * @param ey
	 * @return
	 */
	public static int getDistence(int sx, int sy, int ex, int ey) {
		int dx = Math.abs(sx - ex);
		int dy = Math.abs(sy - ey);
		return dx + dy;
	}

	/**
	 * 看看看得见的格子里有没有敌方格子。
	 * 
	 * @param info
	 * @param x
	 * @param y
	 * @param side
	 * @return
	 */
	public static boolean containsEnemyLoc(GameInfo info, int x, int y, int side) {
		for (int[] loc : ActImit.getManhattan(x, y, 5)) {
			int state = info.getField()[loc[0]][loc[1]];
			int add = (1 - side) * 3;
			if (state >= add && state <= add + 2) {
				return true;
			}
		}
		return false;
	}
}