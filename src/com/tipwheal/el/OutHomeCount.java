package com.tipwheal.el;

public class OutHomeCount {
	/**
	 * 是否远离了某个AI的家。。
	 * 
	 * @param action
	 * @param x
	 * @param y
	 * @param ID
	 * @param side
	 * @return
	 */
	public boolean outHome(String action, int x, int y, int ID, int side) {
		int[][] home = { { 0, 5 }, { 0, 14 }, { 9, 14 }, { 14, 9 }, { 14, 0 }, { 5, 0 } };
		int[] homeLoc = home[ID + side * 3];
		int disF = Math.abs(homeLoc[0] - x) + Math.abs(homeLoc[1] - y);
		int[] loc = new int[2];
		loc[0] = x;
		loc[1] = y;
		for (String s : action.split(" ")) {
			int d = Integer.parseInt(s);
			if (d >= 5 && d <= 8) {
				loc = ActImit.getNextCell(d - 5, loc[0], loc[1]);
			}
		}
		int disL = Math.abs(homeLoc[0] - loc[0]) + Math.abs(homeLoc[1] - loc[1]);
		if (disL > disF) {
			return true;
		} else {
			return false;
		}
	}

	public boolean getMid(String action, int x, int y, int ID, int side) {
		int first = ActImit.getDistence(x, y, 7, 7);
		int[] loc = ActImit.getNextCell(action, x, y);
		int last = ActImit.getDistence(loc[0], loc[1], 7, 7);
		if (last < first) {
			return true;
		}
		return false;
	}
}
