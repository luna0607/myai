package com.tipwheal.el;

import java.util.ArrayList;
import java.util.Random;

public class ScoreCounter {
	private GameInfo info;
	private ActionLibrary lib;
	private double[] scores;
	private Random rnd;

	/**
	 * 计算总分。
	 * 
	 * @param info
	 */
	public ScoreCounter(GameInfo info) {
		this.info = info;
		lib = new ActionLibrary(this.info);
		scores = new double[lib.getNum()];
		rnd = new Random();
		OcpCount ocp = new OcpCount(this.info);
		AtkCount atk = new AtkCount(this.info);
		AvdCount avd = new AvdCount(this.info);
		OutHomeCount out = new OutHomeCount();

		int ID = this.info.getWeapon();
		int side = this.info.getSide();
		int x = this.info.getSamuraiInfo()[ID].getCurX();
		int y = this.info.getSamuraiInfo()[ID].getCurY();

		for (int i = 0; i < lib.getNum(); i++) {
			String action = lib.getActions(i);
			scores[i] += ocp.getOcpCounts(lib.getActions(i), x, y, ID, side);
			if (avd.intoDgrArea(action, x, y, ID, side) && !atk.canAtk(action, x, y, ID, side)) {
				scores[i] -= Strategy.AvdEnemy;
			}
			if (atk.canAtk(action, x, y, ID, side)) {
				scores[i] += Strategy.AtkEnemy;
			}
			if (avd.intoMemDgrArea(action, x, y, ID, side)) {
				scores[i] -= Strategy.MemAvd;
			}
			if (action.endsWith("9")) {
				if (ActImit.containsEnemyLoc(info, x, y, side)) {
					scores[i] += Strategy.Hide;
				}
				if (avd.intoDgrArea("0", x, y, ID, side)) {
					scores[i] += Strategy.Dgr;
				}
			}
			if (out.getMid(action, x, y, ID, side)) {
				scores[i] += 0.3;
			}
			if (out.outHome(lib.getActions(i), x, y, 1, side)) {
				scores[i] += Strategy.OutHome;
			}
			if (atk.canAtkNextTurn(action, x, y, ID, side) && action.endsWith("9")) {
				scores[i] += Strategy.AtkNext;
			}
		}
	}

	/**
	 * 得到分数最高的行为。
	 * 
	 * @return
	 */
	public String getAction() {
		double max = -100;
		ArrayList<String> actions = new ArrayList<>();
		for (int i = 0; i < scores.length; i++) {
			if (scores[i] > max) {
				max = scores[i];
				actions.clear();
				actions.add(lib.getActions(i));
//			} else if (scores[i] == max) {
//				actions.add(lib.getActions(i));
			}
		}
		int index = rnd.nextInt(actions.size());
		return actions.get(index);
	}
}
