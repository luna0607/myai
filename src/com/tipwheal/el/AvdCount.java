package com.tipwheal.el;

public class AvdCount {
	private GameInfo info;

	public AvdCount(GameInfo info) {
		this.info = info;
	}

	/**
	 * 是否进入了危险区域。
	 * 
	 * @param action
	 * @param x
	 * @param y
	 * @param ID
	 * @return
	 */
	public boolean intoDgrArea(String action, int x, int y, int ID, int side) {
		for (int i = 3; i < 6; i++) {
			if (this.inAtkArea(action, x, y, ID, i, side)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 进入记忆中的危险区域。
	 * 
	 * @param action
	 * @param x
	 * @param y
	 * @param ID
	 * @param side
	 * @return
	 */
	public boolean intoMemDgrArea(String action, int x, int y, int ID, int side) {
		for (int i = 3; i < 6; i++) {
			if (this.inMemAtkArea(action, x, y, ID, i, side)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否进入某敌人打击范围。
	 * 
	 * @param action
	 * @param x
	 * @param y
	 * @param ID
	 * @param enemyID
	 * @return
	 */
	private boolean inAtkArea(String action, int x, int y, int ID, int enemyID, int side) {
		int enemyX = info.getSamuraiInfo()[enemyID].getCurX();
		int enemyY = info.getSamuraiInfo()[enemyID].getCurY();
		return this.inPosAtkArea(action, x, y, ID, enemyX, enemyY, enemyID, side);
	}

	/**
	 * 是否进入记忆中某敌人打击范围。
	 * 
	 * @param action
	 * @param x
	 * @param y
	 * @param ID
	 * @param enemyID
	 * @param side
	 * @return
	 */
	private boolean inMemAtkArea(String action, int x, int y, int ID, int enemyID, int side) {
		int memX = info.getMemory().getLastX(enemyID);
		int memY = info.getMemory().getLastY(enemyID);
		if (this.inPosAtkArea(action, x, y, ID, memX, memY, enemyID, side)) {
			return true;
		}
		for (int i = 0; i < 4; i++) {
			int px = memX + ActImit.rotate(i, 0, 1)[0];
			int py = memY + ActImit.rotate(i, 0, 1)[1];
			if (this.inPosAtkArea(action, x, y, ID, px, py, enemyID, side)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否进入某可能位置敌人打击范围。
	 * 
	 * @param action
	 * @param x
	 * @param y
	 * @param ID
	 * @param enemyX
	 * @param enemyY
	 * @param enemyID
	 * @param side
	 * @return
	 */
	private boolean inPosAtkArea(String action, int x, int y, int ID, int enemyX, int enemyY, int enemyID, int side) {
		if (enemyX == -1) {
			return false;
		} else {
			int[] loc = new int[2];
			loc[0] = x;
			loc[1] = y;
			for (String s : action.split(" ")) {
				int d = Integer.parseInt(s);
				if (d >= 5 && d <= 8) {
					loc = ActImit.getNextCell(d - 5, loc[0], loc[1]);
				}
			}
			for (int[] dgr : ActImit.psbAtkArea(enemyX, enemyY, enemyID, side)) {
				if (loc[0] == dgr[0] && loc[1] == dgr[1]) {
					return true;
				}
			}
		}
		return false;
	}

}
