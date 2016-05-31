package com.tipwheal.el;

/**
 * To start ai program.
 */
public class Main {
	public static void main(String[] argv) {
		GameInfo info = new GameInfo();
		Player p = new NewPlayer();

		while (true) {
			info.readTurnInfo();
			System.out.println("# Turn " + info.getTurn());
			if (info.getCurePeriod() != 0) {
				System.out.println("0");
			} else {
				p.play(info);
				System.out.println("0");
			}
		}
	}
}
