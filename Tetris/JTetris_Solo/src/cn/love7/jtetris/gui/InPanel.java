package cn.love7.jtetris.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cn.love7.jtetris.game.Block;
import cn.love7.jtetris.game.GroupMove;
import cn.love7.jtetris.game.Top10;

public class InPanel extends JPanel {
	private static final long serialVersionUID = 6573908583331468595L;
	String user = "玩家Ⅰ"; // Ⅱ
	int level = 0; // 等级
	int score = 0; // 得分
	int lines = 0; // 已消行数

	static final int ROWS = 14; // 游戏区总行数
	static final int COLS = 10; // 游戏区总列数

	int[][] curGrid = new int[ROWS][COLS]; // 游戏区
	int[][] preGrid = new int[4][4]; // 预览区

	int middle = COLS / 2; // 行中间

	Block[] preview = new Block[4]; // 预览方块组
	Block[] current = new Block[4]; // 当前方块组

	Color[] colors = new Color[] { // 颜色数组
			Color.DARK_GRAY, Color.RED, // 赤
			new Color(255, 128, 0), // 橙
			Color.YELLOW, // 黄
			Color.GREEN, // 绿
			Color.CYAN, // 青
			Color.BLUE, // 蓝
			new Color(255, 0, 255) }; // 紫
	public final static int NoBlock = 0;

	static Top10 top10 = new Top10();

	static Graphics g0;

	public InPanel() {
		try {
			jbInit();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		curGrid = new int[][] { { 0, 0, 0, 0, 0, 0, 2, 2, 2, 0 }, { 0, 1, 0, 1, 0, 1, 2, 0, 0, 0 },
				{ 0, 1, 0, 1, 0, 1, 2, 2, 2, 0 }, { 0, 1, 0, 1, 0, 1, 2, 0, 0, 0 }, { 0, 0, 1, 0, 1, 0, 2, 2, 2, 0 },
				{ 0, 3, 0, 0, 4, 4, 5, 5, 0, 0 }, { 0, 3, 0, 4, 0, 5, 0, 0, 5, 0 }, { 0, 3, 0, 4, 0, 5, 0, 0, 5, 0 },
				{ 0, 3, 3, 3, 4, 4, 5, 5, 0, 0 }, { 0, 0, 6, 0, 6, 0, 7, 7, 7, 0 }, { 0, 6, 0, 6, 0, 6, 7, 0, 0, 0 },
				{ 0, 6, 0, 6, 0, 6, 7, 7, 7, 0 }, { 0, 6, 0, 6, 0, 6, 7, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 7, 7, 7, 0 }, };

		for (int i = 0; i <= 3; i++) {
			preview[i] = new Block(0, 0, 0);
			current[i] = new Block(0, 0, 0);
		}

	}

	protected void runFirst() {

		getNextBlockGroup();
		transfer(); // 以上两步为第一块
		GroupMove.down(curGrid, current);
		GroupMove.up(curGrid, current);
		GroupMove.up(curGrid, current);
		getNextBlockGroup(); // 第二块
	}

	public void paint(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.black);

		Font f1 = new Font("黑体", Font.BOLD, 22);
		Font f2 = new Font("华文楷体", Font.ITALIC, 16);
		FontMetrics fm2 = g.getFontMetrics(f2);

		g.setFont(f1);
		g.drawString(user, 33, 70);
		int hight = fm2.getHeight();
		g.setFont(f2);
		g.drawString("等级", 50, hight += 75);
		g.drawString(((Integer) level).toString(),
				fm2.stringWidth("等级") + 50 - fm2.stringWidth(Integer.toString(level)), hight += fm2.getHeight());
		g.drawString("得分", 50, hight += fm2.getHeight());
		g.drawString(Integer.toString(score), fm2.stringWidth("得分") + 50 - fm2.stringWidth(Integer.toString(score)),
				hight += fm2.getHeight());
		g.drawString("行数", 50, hight += fm2.getHeight());
		g.drawString(((Integer) lines).toString(),
				fm2.stringWidth("行数") + 50 - fm2.stringWidth(Integer.toString(lines)), hight += fm2.getHeight());
		g.drawString("下一个", 50 - fm2.stringWidth("个"), hight += fm2.getHeight());

		paintBothArea(g);

	}

	private void jbInit() throws Exception {
		this.setLayout(null);
	}

	// 新产生一个方块组
	protected void getNextBlockGroup() {
		for (int i = 0; i < 4; i += 1) {
			for (int j = 0; j < 4; j += 1) {
				preGrid[j][i] = NoBlock;
			}
		}

		switch ((int) (Math.random() * 7) + 1) {
		case 1: // 一
			preview[0] = new Block(middle, 2, 1);
			preview[1] = new Block(middle, 1, 1);
			preview[2] = new Block(middle, 0, 1);
			preview[3] = new Block(middle, 3, 1);

			preGrid[0][2] = 1;
			preGrid[1][2] = 1;
			preGrid[2][2] = 1;
			preGrid[3][2] = 1;

			break;
		case 2: // 土
			preview[0] = new Block(middle, 1, 2);
			preview[1] = new Block(middle, 0, 2);
			preview[2] = new Block(middle - 1, 1, 2);
			preview[3] = new Block(middle + 1, 1, 2);

			preGrid[1][2] = 2;
			preGrid[2][1] = 2;
			preGrid[2][2] = 2;
			preGrid[2][3] = 2;

			break;
		case 3: // 田
			preview[0] = new Block(middle - 1, 1, 3);
			preview[1] = new Block(middle - 1, 0, 3);
			preview[2] = new Block(middle, 0, 3);
			preview[3] = new Block(middle, 1, 3);

			preGrid[1][1] = 3;
			preGrid[1][2] = 3;
			preGrid[2][1] = 3;
			preGrid[2][2] = 3;

			break;
		case 4: // L
			preview[0] = new Block(middle, 1, 4);
			preview[1] = new Block(middle, 2, 4);
			preview[2] = new Block(middle, 0, 4);
			preview[3] = new Block(middle + 1, 2, 4);

			preGrid[1][1] = 4;
			preGrid[2][1] = 4;
			preGrid[3][1] = 4;
			preGrid[3][2] = 4;

			break;
		case 5: // -L
			preview[0] = new Block(middle, 1, 5);
			preview[1] = new Block(middle, 2, 5);
			preview[2] = new Block(middle, 0, 5);
			preview[3] = new Block(middle - 1, 2, 5);

			preGrid[1][2] = 5;
			preGrid[2][2] = 5;
			preGrid[3][2] = 5;
			preGrid[3][1] = 5;

			break;
		case 6: // Z
			preview[0] = new Block(middle, 1, 6);
			preview[1] = new Block(middle, 0, 6);
			preview[2] = new Block(middle - 1, 0, 6);
			preview[3] = new Block(middle + 1, 1, 6);

			preGrid[1][1] = 6;
			preGrid[1][2] = 6;
			preGrid[2][2] = 6;
			preGrid[2][3] = 6;

			break;
		case 7: // -Z
			preview[0] = new Block(middle, 1, 7);
			preview[1] = new Block(middle, 0, 7);
			preview[2] = new Block(middle - 1, 1, 7);
			preview[3] = new Block(middle + 1, 0, 7);

			preGrid[1][2] = 7;
			preGrid[1][3] = 7;
			preGrid[2][1] = 7;
			preGrid[2][2] = 7;

			break;
		}

	}

	// 将预览区的方块转为当前
	protected boolean transfer() {
		for (int i = 0; i < 4; i++) {
			current[i] = preview[i];
		}
		if (curGrid[current[0].y][current[0].x] != NoBlock || curGrid[current[1].y][current[1].x] != NoBlock
				|| curGrid[current[2].y][current[2].x] != NoBlock || curGrid[current[3].y][current[3].x] != NoBlock) {

			curGrid[current[0].y][current[0].x] = current[0].color;
			curGrid[current[1].y][current[1].x] = current[1].color;
			curGrid[current[2].y][current[2].x] = current[2].color;
			curGrid[current[3].y][current[3].x] = current[3].color;

			TimeThread.isPaused = false;
			TimeThread.isPlaying = false;

			JtetrisFrame.jMenuItem1.setEnabled(true);
			JtetrisFrame.jMenuItem2.setEnabled(false);
			JtetrisFrame.jMenuItem3.setEnabled(false);

			gameOver();
			return false;
			// JtetrisFrame.autoDown = null;
		}

		curGrid[current[0].y][current[0].x] = current[0].color;
		curGrid[current[1].y][current[1].x] = current[1].color;
		curGrid[current[2].y][current[2].x] = current[2].color;
		curGrid[current[3].y][current[3].x] = current[3].color;

		return true;
	}

	// 绘制当前预览区以及游戏区所有方块
	private void paintBothArea(Graphics g) {

		// 一
		if (current[0].color == 1) {
			Block temp = new Block(0, 0, 0);

			if (current[0].x > current[1].x) {
				temp = current[1];
				current[1] = current[0];
				current[0] = temp;

				GroupMove.right(curGrid, current);

			}
			if (current[0].x == 1 && current[1].x == 1) {
				if (current[0].y < current[1].y) {
					temp = current[1];
					current[1] = current[0];
					current[0] = temp;
				}
			}
			if (current[0].x == InPanel.COLS - 2 && current[1].x == InPanel.COLS - 2) {
				if (current[0].y > current[1].y) {
					temp = current[1];
					current[1] = current[0];
					current[0] = temp;
				}
			}

		}

		// Z&-Z
		if (current[0].color == 6 || current[0].color == 7) {

			if (current[0].y > current[1].y) {
				Block temp = current[1];
				current[1] = current[0];
				current[0] = temp;

			}

		}

		// 预览区
		for (int i = 0; i < 4; i += 1) {
			for (int j = 0; j < 4; j += 1) {
				g.setColor(colors[(preGrid[j][i])]);
				g.fill3DRect(20 + i * 27, 245 + j * 27, 27, 27, false);

			}
		}

		// 游戏区
		for (int i = 0; i < 10; i += 1) {
			for (int j = 0; j < 14; j += 1) {

				g.setColor(colors[(curGrid[j][i])]);

				g.fill3DRect(150 + i * 27, 30 + j * 27, 27, 27, false);

				if (current[0].color != 0) {

					g.setColor(colors[current[0].color]);
					g.fill3DRect(150 + current[0].x * 27, 30 + current[0].y * 27, 27, 27, true);
					g.fill3DRect(150 + current[1].x * 27, 30 + current[1].y * 27, 27, 27, true);
					g.fill3DRect(150 + current[2].x * 27, 30 + current[2].y * 27, 27, 27, true);
					g.fill3DRect(150 + current[3].x * 27, 30 + current[3].y * 27, 27, 27, true);
				}
			}

		}

	}

	// 消行
	protected void delLines() {
		int linesOnce = 0;

		outter: for (int i = ROWS - 1; i >= 1; i--) {
			for (int j = 0; j <= COLS - 1; j++)
				if (curGrid[i][j] == InPanel.NoBlock)
					continue outter;
			for (int k = i; k >= 1; k--) {
				for (int l = 0; l <= COLS - 1; l++) {
					curGrid[k][l] = curGrid[k - 1][l];
				}
			}
			i++;
			linesOnce++;
		}
		// 计算消去行数
		lines += linesOnce;
		// 计算得分
		score += (Math.exp((Math.log(2) * linesOnce)) - 1) * 100;
		if (score >= 25000) {
			level = 10; // 最大10级
		} else {
			level = score / 2500;
		}
	}

	// 游戏结束
	protected void gameOver() {
		forEver();
		top10.read();
		if (top10.inTop10(score)) {
			if (JOptionPane.showConfirmDialog(this, "        恭喜您打破纪录！！\n                留名青史？", "游戏结束",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				HeroDialog hero = new HeroDialog(new JFrame(), "游戏高手排行榜", true);
				hero.score = score;
				hero.jTextField1.setText("无名氏");
				hero.jTextField1.selectAll();
				hero.jTextField1.setEditable(true);
				hero.jButton2.setEnabled(true);
				hero.setSize(370, 500);
				hero.setLocation(1024 / 2 - 370 / 2, 768 / 2 - 500 / 2);
				hero.setVisible(true);

			}

		} else if (JOptionPane.showConfirmDialog(this, "很遗憾，您未能打破纪录……", "游戏结束", JOptionPane.YES_OPTION,
				JOptionPane.PLAIN_MESSAGE) == JOptionPane.YES_OPTION) {
		}
	}

	protected void forEver() {
		for (int i = 0; i < 4; i += 1) {
			current[i] = new Block(0, 0, 0);
		}

		repaint();
	}

	// 重新开始
	protected void renascence() {
		for (int i = 0; i < 4; i += 1) {
			current[i] = new Block(0, 0, 0);
			preview[i] = new Block(0, 0, 0);
		}

		for (int i = 0; i < 4; i += 1) {
			for (int j = 0; j < 4; j += 1) {
				preGrid[j][i] = 0;
			}
		}

		for (int i = 0; i < 10; i += 1) {
			for (int j = 0; j < 14; j += 1) {
				curGrid[j][i] = 0;
			}
		}
	}

}
