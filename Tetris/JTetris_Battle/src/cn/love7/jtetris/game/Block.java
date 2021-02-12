package cn.love7.jtetris.game;

public class Block {
	public int x, y;
	public int color;

	public Block(int x, int y, int c) {
		this.x = x;
		this.y = y;
		this.color = c;
	}

	public boolean equals(Block b) {
		return (x == b.x && y == b.y && color == b.color);
	}

	public boolean isInRange() {
		return (x >= 0 && x <= 9 && y >= 0 && y <= 13);
	}
}
