package cn.love7.jtetris.game;

import cn.love7.jtetris.gui.InPanel;

public class GroupMove {

	static Block[] to = new Block[4];

	// 下落
	public static boolean down(int[][] curGrid, Block[] current) {

		for (int i = 0; i <= 3; i++) {
			to[i] = new Block(0, 0, 0);
		}

		for (int i = 0; i <= 3; i++) {
			to[i].x = current[i].x;
			to[i].y = current[i].y + 1;
			to[i].color = current[i].color;
		}
		if (canMove(curGrid, current, to)) {

			before(curGrid, current);
			for (int i = 0; i <= 3; i++) {
				current[i].y++;
			}
			after(curGrid, current);
			return true;
		}
		return false;
	}

	// 左移
	public static void left(int[][] curGrid, Block[] current) {

		for (int i = 0; i <= 3; i++) {
			to[i].x = current[i].x - 1;
			to[i].y = current[i].y;
			to[i].color = current[i].color;
		}
		if (canMove(curGrid, current, to)) {

			before(curGrid, current);
			for (int i = 0; i <= 3; i++) {
				current[i].x--;
			}
			after(curGrid, current);
		}
	}

	// 右移
	public static void right(int[][] curGrid, Block[] current) {

		for (int i = 0; i <= 3; i++) {
			to[i].x = current[i].x + 1;
			to[i].y = current[i].y;
			to[i].color = current[i].color;
		}
		if (canMove(curGrid, current, to)) {

			before(curGrid, current);
			for (int i = 0; i <= 3; i++) {
				current[i].x++;
			}
			after(curGrid, current);
		}

	}

	// 上移……
	public static void up(int[][] curGrid, Block[] current) {

		for (int i = 0; i <= 3; i++) {
			to[i].x = current[i].x;
			to[i].y = current[i].y - 1;
			to[i].color = current[i].color;
		}
		if (canMove(curGrid, current, to)) {

			before(curGrid, current);
			for (int i = 0; i <= 3; i++) {
				current[i].y--;
			}
			after(curGrid, current);
		}
	}

	// 旋转
	public static void rotate(int[][] curGrid, Block[] current) {
		if (current[0].color == 3) {
			// 对田字形do nothing
		} else {
			Block[] newPos = new Block[4];

			for (int i = 0; i < 4; i++) {
				int dx = current[i].x - current[0].x;
				int dy = current[i].y - current[0].y;

				newPos[i] = new Block(current[0].x - dy, current[0].y + dx, current[0].color);
			}

			if (canMove(curGrid, current, newPos)) {
				before(curGrid, current);
				for (int j = 0; j < 4; j++) {
					current[j] = newPos[j];
				}
				after(curGrid, current);
			}

		}
	}

	public static void regret() {

	}

	// 是否可以向目标位置移动
	public static boolean canMove(int[][] curGrid, Block[] current, Block[] to) {

		for (int i = 0; i <= 3; i++) {
			if (!to[i].isInRange()) {
				return false;
			}
		}
		outter: for (int i = 0; i <= 3; i++) {
			for (int j = 0; j <= 3; j++) {
				if (to[i].equals(current[j])) {
					continue outter;
				}
			}

			if (curGrid[to[i].y][to[i].x] != InPanel.NoBlock) {
				return false;
			}
		}

		return true;
	}

	public static void before(int[][] curGrid, Block[] current) {
		curGrid[current[0].y][current[0].x] = InPanel.NoBlock;
		curGrid[current[1].y][current[1].x] = InPanel.NoBlock;
		curGrid[current[2].y][current[2].x] = InPanel.NoBlock;
		curGrid[current[3].y][current[3].x] = InPanel.NoBlock;
	}

	public static void after(int[][] curGrid, Block[] current) {
		curGrid[current[0].y][current[0].x] = current[0].color;
		curGrid[current[1].y][current[1].x] = current[1].color;
		curGrid[current[2].y][current[2].x] = current[2].color;
		curGrid[current[3].y][current[3].x] = current[3].color;
	}
}
