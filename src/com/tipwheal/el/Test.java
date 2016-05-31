package com.tipwheal.el;

public class Test {
	public static void main(String[] args) {
		System.out.println(ActImit.getManhattan(0, 0, 5).size());
		for(int i = 0;i<ActImit.getManhattan(0, 0, 5).size();i++) {
			System.out.println(ActImit.getManhattan(0, 0, 5).get(i)[0] + " "+ActImit.getManhattan(0, 0, 5).get(i)[1]);
		}
	}
}
