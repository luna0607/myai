package com.tipwheal.el;

public class OcpCount {
	private GameInfo info;

	public OcpCount(GameInfo info) {
		this.info = info;
	}

	/**
	 * 得到占领格子所得的分数。
	 * 
	 * @param action
	 * @param x
	 * @param y
	 * @param ID
	 * @return
	 */
	public double getOcpCounts(String action, int x, int y, int ID, int side) {
		double num = 0;
		for (int[] ocp : ActImit.getOcyCells(action, x, y, ID, side)) {
			switch (info.getField()[ocp[1]][ocp[0]]) {
			case 8:
				num += 1;
				break;
			case 0:
			case 1:
			case 2:
				break;
			case 3:
			case 4:
			case 5:
				num += 2;
				break;
			}
		}
		return num;
	}
}
