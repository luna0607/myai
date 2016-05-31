package com.tipwheal.el;

public class SamuraiInfo {
	private int homeX, homeY;
	private int curX, curY;
	private int rank, score, hidden;

	public SamuraiInfo(SamuraiInfo info) {
		homeX = info.getHomeX();
		homeY = info.getHomeY();
		curX = info.getCurX();
		curY = info.getCurY();
		rank = info.getRank();
		score = info.getScore();
		hidden = info.getHidden();
	}

	public SamuraiInfo() {
		homeX = 0;
		homeY = 0;
		curX = 0;
		curY = 0;
		rank = 0;
		score = 0;
		hidden = 0;
	}

	public int getHidden() {
		return hidden;
	}

	public void setHidden(int hidden) {
		this.hidden = hidden;
	}

	public int getCurX() {
		return curX;
	}

	public void setCurX(int curX) {
		this.curX = curX;
	}

	public int getCurY() {
		return curY;
	}

	public void setCurY(int curY) {
		this.curY = curY;
	}

	public int getHomeX() {
		return homeX;
	}

	public void setHomeX(int homeX) {
		this.homeX = homeX;
	}

	public int getHomeY() {
		return homeY;
	}

	public void setHomeY(int homeY) {
		this.homeY = homeY;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
