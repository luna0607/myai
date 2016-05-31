package com.tipwheal.el;

public class NewPlayer extends Player {

	@Override
	/**
	 * 这是一个开始的方法。
	 */
	public GameInfo play(GameInfo info) {
		ScoreCounter counter = new ScoreCounter(info);
		String action = counter.getAction();
		this.setMemory(info, action);
		String[] actions = action.split(" ");
		for (String s : actions) {
			info.doAction(Integer.parseInt(s));
		}
		return info;
	}

	/**
	 * 输出我想要的信息。
	 * 
	 * @param info
	 * @return
	 */
	public boolean print(GameInfo info) {
		int[][] field = info.getField();
		System.err.println("Turn: " + info.getTurn());
		System.err.println("My id: " + info.getWeapon());
		System.err.println("My side: " + info.getSide());
		System.err.println("My X: " + info.getSamuraiInfo()[info.getWeapon()].getCurX());
		System.err.println("My Y: " + info.getSamuraiInfo()[info.getWeapon()].getCurY());
		System.err.println("Is hid? " + info.getSamuraiInfo()[info.getWeapon()].getHidden());
		System.err.println("Enemy 3: " + info.getSamuraiInfo()[3].getCurX() + " " + info.getSamuraiInfo()[3].getCurY());
		System.err.println("Enemy 4: " + info.getSamuraiInfo()[4].getCurX() + " " + info.getSamuraiInfo()[4].getCurY());
		System.err.println("Enemy 5: " + info.getSamuraiInfo()[5].getCurX() + " " + info.getSamuraiInfo()[5].getCurY());
		System.err.println(" 0 1 2 3 4 5 6 7 8 9 A B C D E");
		for (int i = 0; i < 15; i++) {
			String[] string = { "A", "B", "C", "D", "E" };
			if (i < 10) {
				System.err.print(i);
			} else {
				System.err.print(string[i - 10]);
			}
			for (int j = 0; j < 15; j++) {
				int state = field[i][j];
				if (state == 9) {
					System.err.print(" ");
				} else if (state >= 0 && state <= 2) {
					System.err.print("+");
				} else if (state == 8) {
					System.err.print("o");
				} else {
					System.err.print("-");
				}
				if (j != 14) {
					System.err.print(" ");
				} else {
					if (i < 10) {
						System.err.print(i);
					} else {
						System.err.print(string[i - 10]);
					}
					if(j == 14) {
						System.err.println();
					}
				}
			}

		}
		System.err.println(" 0 1 2 3 4 5 6 7 8 9 A B C D E");
		return true;
	}

	public void setMemory(GameInfo info, String action) {
		int ID = info.getWeapon();
		int[] loc = new int[2];
		loc[0] = info.getSamuraiInfo()[ID].getCurX();
		loc[1] = info.getSamuraiInfo()[ID].getCurY();
		for (int i = 3; i < 6; i++) {
			if (new AtkCount(info).canAtk(action, loc[0], loc[1], ID, info.getSide())) {
				info.getMemory().setLastLoc(i, -1, -1);
			}
			int x = info.getSamuraiInfo()[i].getCurX();
			int y = info.getSamuraiInfo()[i].getCurY();
			info.getMemory().setLastLoc(i, x, y);
		}
	}

}
