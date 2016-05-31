package com.tipwheal.el;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class GameInfo {
	private static final int PLAYER_NUM = 6;
	private static BufferedReader stdReader;
	private int turns;
	private int side;
	private int weapon;
	private int width, height;
	private int maxCure;
	private SamuraiInfo[] samuraiInfo;
	private int turn, curePeriod;
	private int[][] field;
	private MemoryInfo mem = new MemoryInfo();

	/**
	 * Construct a GameInfo thats the same as parameter.
	 * 
	 * @param info
	 *            A GameInfo to be copied.
	 */
	public GameInfo(GameInfo info) {
		turns = info.getTurns();
		side = info.getSide();
		weapon = info.getWeapon();
		width = info.getWidth();
		height = info.getHeight();
		maxCure = info.getMaxCure();
		samuraiInfo = info.getSamuraiInfo();
		turn = info.getTurn();
		curePeriod = info.getCurePeriod();
		field = info.getField();
	}

	/**
	 * Constructs a GameInfo by infos in game system.<br>
	 * Initialize all variables by read lines from System.in.
	 */
	public GameInfo() {
		stdReader = new BufferedReader(new InputStreamReader(System.in));

		String[] res = read();

		turns = Integer.parseInt(res[0]);
		side = Integer.parseInt(res[1]);
		weapon = Integer.parseInt(res[2]);
		width = Integer.parseInt(res[3]);
		height = Integer.parseInt(res[4]);
		maxCure = Integer.parseInt(res[5]);
		samuraiInfo = new SamuraiInfo[GameInfo.PLAYER_NUM];
		for (int i = 0; i < GameInfo.PLAYER_NUM; ++i) {
			samuraiInfo[i] = new SamuraiInfo();
		}
		for (int i = 0; i < GameInfo.PLAYER_NUM; ++i) {
			res = this.read();
			samuraiInfo[i].setHomeX(Integer.parseInt(res[0]));
			samuraiInfo[i].setHomeY(Integer.parseInt(res[1]));
		}
		for (int i = 0; i < GameInfo.PLAYER_NUM; ++i) {
			res = this.read();
			samuraiInfo[i].setRank(Integer.parseInt(res[0]));
			samuraiInfo[i].setScore(Integer.parseInt(res[1]));
		}
		turn = 0;
		curePeriod = 0;
		field = new int[height][width];
		System.out.println("0");
	}

	/**
	 * Read a line of infomations
	 * 
	 * @return A String[] by split read line.
	 */
	public String[] read() {
		String line = "";
		try {
			for (line = stdReader.readLine(); line.startsWith("#"); line = stdReader.readLine())
				;
		} catch (Exception e) {
			e.getStackTrace();
			System.exit(-1);
		}
		return line.split(" ");
	}

	/**
	 * Read turn info.
	 */
	public void readTurnInfo() {
		String[] res = read();

		if (res.length == 0) {
			System.exit(-1);
		}

		turn = Integer.parseInt(res[0]);

		if (turn < 0) {
			System.exit(-1);
		}

		res = read();
		curePeriod = Integer.parseInt(res[0]);

		for (int i = 0; i < GameInfo.PLAYER_NUM; ++i) {
			res = read();
			samuraiInfo[i].setCurX(Integer.parseInt(res[0]));
			samuraiInfo[i].setCurY(Integer.parseInt(res[1]));
			samuraiInfo[i].setHidden(Integer.parseInt(res[2]));
		}

		for (int i = 0; i < height; ++i) {
			Arrays.fill(field[i], 0);
		}

		for (int i = 0; i < height; ++i) {
			res = read();

			for (int j = 0; j < width; ++j) {
				field[i][j] = Integer.parseInt(res[j + 1]);
			}
		}
	}

	/**
	 * Check if an action is valid.
	 * 
	 * @param action
	 *            The action to be checked.
	 * @return Valid action return true.<br>
	 *         Invalid action return false.
	 */
	public Boolean isValid(int action) {
		SamuraiInfo myself = samuraiInfo[weapon];
		int curX = myself.getCurX();
		int curY = myself.getCurY();

		if (action >= 0 && action <= 4) {
			return myself.getHidden() == 0;
		}

		if (action >= 5 && action <= 8) {
			if (action == 5) {
				curY = curY + 1;
			}
			if (action == 6) {
				curX = curX + 1;
			}
			if (action == 7) {
				curY = curY - 1;
			}
			if (action == 8) {
				curX = curX - 1;
			}
			if (curX < 0 || width <= curX || curY < 0 || height <= curY) {
				return false;
			}
			if (myself.getHidden() == 1 && field[curY][curX] >= 3) {
				return false;
			}
			for (int i = 0; i < PLAYER_NUM; ++i) {
				if (curX == samuraiInfo[i].getCurX() && curY == samuraiInfo[i].getCurY()) {
					return false;
				}
				if (i != weapon && (curX == samuraiInfo[i].getHomeX() && curY == samuraiInfo[i].getHomeY())) {
					return false;
				}
			}
			return true;
		}

		if (action == 9) {
			if (myself.getHidden() == 1) {
				return false;
			}
			if (field[curY][curX] >= 3) {
				return false;
			}
			return true;
		}

		if (action == 10) {
			if (myself.getHidden() != 1) {
				return false;
			}
			for (int i = 0; i < PLAYER_NUM; ++i) {
				SamuraiInfo other = samuraiInfo[i];
				if (other.getHidden() != 1 && (other.getCurX() == curX && other.getCurY() == curY)) {
					return false;
				}
			}
			return true;
		}

		System.exit(-1);
		return false;
	}

	/**
	 * Ratate
	 * 
	 * @param direction
	 * @param x0
	 * @param y0
	 * @return
	 */
	public int[] rotate(int direction, int x0, int y0) {
		int[] res = { 0, 0 };
		if (direction == 0) {
			res[0] = x0;
			res[1] = y0;
		}
		if (direction == 1) {
			res[0] = y0;
			res[1] = -x0;
		}
		if (direction == 2) {
			res[0] = -x0;
			res[1] = -y0;
		}
		if (direction == 3) {
			res[0] = -y0;
			res[1] = x0;
		}
		return res;
	}

	public void occupy(int direction) {
		SamuraiInfo myself = samuraiInfo[weapon];
		int curX = myself.getCurX();
		int curY = myself.getCurY();
		int[] size = { 4, 5, 7 };
		int[][] ox = { { 0, 0, 0, 0 }, { 0, 0, 1, 1, 2 }, { -1, -1, -1, 0, 1, 1, 1 } };
		int[][] oy = { { 1, 2, 3, 4 }, { 1, 2, 0, 1, 0 }, { -1, 0, 1, 1, 1, -1, 0 } };

		for (int i = 0; i < size[weapon]; ++i) {
			int[] pos = rotate(direction, ox[weapon][i], oy[weapon][i]);
			pos[0] += curX;
			pos[1] += curY;
			if (0 <= pos[0] && pos[0] < width && 0 <= pos[1] && pos[1] < height) {
				Boolean isHome = false;
				for (int j = 0; j < PLAYER_NUM; ++j) {
					if (samuraiInfo[j].getHomeX() == pos[0] && samuraiInfo[j].getHomeY() == pos[1]) {
						isHome = true;
					}
				}
				if (isHome) {
					field[pos[1]][pos[0]] = weapon;
					for (int j = 3; j < PLAYER_NUM; ++j) {
						SamuraiInfo si = samuraiInfo[j];
						if (si.getCurX() == pos[0] && si.getCurY() == pos[1]) {
							si.setCurX(si.getHomeX());
							si.setCurY(si.getHomeY());
							si.setHidden(0);
							samuraiInfo[j] = si;
						}
					}
				}
			}
		}
	}

	public void doAction(int action) {
		SamuraiInfo myself = samuraiInfo[weapon];
		int curX = myself.getCurX();
		int curY = myself.getCurY();

		if (action >= 1 && action <= 4) {
			occupy(action - 1);
		}
		if (action == 5) {
			curY += 1;
		}
		if (action == 6) {
			curX += 1;
		}
		if (action == 7) {
			curY -= 1;
		}
		if (action == 8) {
			curX -= 1;
		}
		if (action == 9) {
			myself.setHidden(1);
		}
		if (action == 10) {
			myself.setHidden(0);
		}
		myself.setCurX(curX);
		myself.setCurY(curY);
		samuraiInfo[weapon] = myself;
		System.out.print(action + " ");
	}

	public int getCurePeriod() {
		return curePeriod;
	}

	public void setCurePeriod(int curePeriod) {
		this.curePeriod = curePeriod;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public SamuraiInfo[] getSamuraiInfo() {
		return samuraiInfo;
	}

	public void setSamuraiInfo(SamuraiInfo[] samuraiInfo) {
		this.samuraiInfo = samuraiInfo;
	}

	public int getWeapon() {
		return weapon;
	}

	public void setWeapon(int weapon) {
		this.weapon = weapon;
	}

	public int getTurns() {
		return turns;
	}

	public int getMaxCure() {
		return maxCure;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getSide() {
		return side;
	}

	public int[][] getField() {
		return field;
	}

	public MemoryInfo getMemory() {
		return mem;
	}
}
