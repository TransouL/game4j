package cn.love7.jtetris.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cn.love7.jtetris.game.Block;
import cn.love7.jtetris.game.GroupMove;

public class InPanel extends JPanel {
	private static final long serialVersionUID = 7725392883661490363L;
	String user = "玩家Ⅰ";
	int level = 0;
	int score = 0;
	int lines = 0;

	static final int ROWS = 14;
	static final int COLS = 10;

	int[][] curGrid = { { 0, 0, 0, 0, 0, 0, 2, 2, 2, 0 }, { 0, 1, 0, 1, 0, 1, 2, 0, 0, 0 },
			{ 0, 1, 0, 1, 0, 1, 2, 2, 2, 0 }, { 0, 1, 0, 1, 0, 1, 2, 0, 0, 0 }, { 0, 0, 1, 0, 1, 0, 2, 2, 2, 0 },
			{ 0, 3, 0, 0, 4, 4, 5, 5, 0, 0 }, { 0, 3, 0, 4, 0, 5, 0, 0, 5, 0 }, { 0, 3, 0, 4, 0, 5, 0, 0, 5, 0 },
			{ 0, 3, 3, 3, 4, 4, 5, 5, 0, 0 }, { 0, 0, 6, 0, 6, 0, 7, 7, 7, 0 }, { 0, 6, 0, 6, 0, 6, 7, 0, 0, 0 },
			{ 0, 6, 0, 6, 0, 6, 7, 7, 7, 0 }, { 0, 6, 0, 6, 0, 6, 7, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 7, 7, 7, 0 }, };

	int[][] preGrid = new int[4][4];

	int middle = COLS / 2;

	Block[] preview = new Block[4];
	Block[] current = new Block[4];

	Color[] colors = new Color[] { Color.DARK_GRAY, Color.RED, new Color(255, 128, 0), Color.YELLOW, Color.GREEN,
			Color.CYAN, Color.BLUE, new Color(255, 0, 255) };
	public final static int NoBlock = 0;

	static Graphics g0;

	int winner = -1;

	public InPanel() {
		try {
			jbInit();
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		for (int i = 0; i <= 3; i++) {
			preview[i] = new Block(0, 0, 0);
			current[i] = new Block(0, 0, 0);
		}
	}

	protected void runFirst() {
		getNextBlockGroup();
		transfer();
		GroupMove.down(curGrid, current);
		GroupMove.up(curGrid, current);
		GroupMove.up(curGrid, current);
		getNextBlockGroup();
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

		if (winner == 1) {

			Font f3 = new Font("黑体", Font.BOLD, 200);
			g.setFont(f3);
			g.setColor(Color.BLUE);
			g.drawString("斩！", 40, 200);
		} else if (winner == 0) {
			Font f3 = new Font("黑体", Font.BOLD, 200);
			g.setFont(f3);
			g.setColor(Color.BLUE);
			g.drawString("败", 40, 200);
		}
	}

	private void jbInit() throws Exception {
		this.setLayout(null);
	}

	protected void getNextBlockGroup() {
		for (int i = 0; i < 4; i += 1) {
			for (int j = 0; j < 4; j += 1) {
				preGrid[j][i] = NoBlock;
			}
		}

		switch ((int) (Math.random() * 7) + 1) {
		case 1:
			preview[0] = new Block(middle, 2, 1);
			preview[1] = new Block(middle, 1, 1);
			preview[2] = new Block(middle, 0, 1);
			preview[3] = new Block(middle, 3, 1);

			preGrid[0][2] = 1;
			preGrid[1][2] = 1;
			preGrid[2][2] = 1;
			preGrid[3][2] = 1;

			break;
		case 2:
			preview[0] = new Block(middle, 1, 2);
			preview[1] = new Block(middle, 0, 2);
			preview[2] = new Block(middle - 1, 1, 2);
			preview[3] = new Block(middle + 1, 1, 2);

			preGrid[1][2] = 2;
			preGrid[2][1] = 2;
			preGrid[2][2] = 2;
			preGrid[2][3] = 2;

			break;
		case 3:
			preview[0] = new Block(middle - 1, 1, 3);
			preview[1] = new Block(middle - 1, 0, 3);
			preview[2] = new Block(middle, 0, 3);
			preview[3] = new Block(middle, 1, 3);

			preGrid[1][1] = 3;
			preGrid[1][2] = 3;
			preGrid[2][1] = 3;
			preGrid[2][2] = 3;

			break;
		case 4:
			preview[0] = new Block(middle, 1, 4);
			preview[1] = new Block(middle, 2, 4);
			preview[2] = new Block(middle, 0, 4);
			preview[3] = new Block(middle + 1, 2, 4);

			preGrid[1][1] = 4;
			preGrid[2][1] = 4;
			preGrid[3][1] = 4;
			preGrid[3][2] = 4;

			break;
		case 5:
			preview[0] = new Block(middle, 1, 5);
			preview[1] = new Block(middle, 2, 5);
			preview[2] = new Block(middle, 0, 5);
			preview[3] = new Block(middle - 1, 2, 5);

			preGrid[1][2] = 5;
			preGrid[2][2] = 5;
			preGrid[3][2] = 5;
			preGrid[3][1] = 5;

			break;
		case 6:
			preview[0] = new Block(middle, 1, 6);
			preview[1] = new Block(middle, 0, 6);
			preview[2] = new Block(middle - 1, 0, 6);
			preview[3] = new Block(middle + 1, 1, 6);

			preGrid[1][1] = 6;
			preGrid[1][2] = 6;
			preGrid[2][2] = 6;
			preGrid[2][3] = 6;

			break;
		case 7:
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

			JtetrisFrame.jMenuItem1.setEnabled(true);
			JtetrisFrame.jMenuItem2.setEnabled(false);
			JtetrisFrame.jMenuItem3.setEnabled(false);

			TimeThread.isPaused = false;
			TimeThread.isPlaying = false;
			gameOver2();
			return false;
		}

		curGrid[current[0].y][current[0].x] = current[0].color;
		curGrid[current[1].y][current[1].x] = current[1].color;
		curGrid[current[2].y][current[2].x] = current[2].color;
		curGrid[current[3].y][current[3].x] = current[3].color;

		return true;

	}

	private void paintBothArea(Graphics g) {

		if (current[0].color == 1) {
			Block temp = new Block(0, 0, 0);

			if (current[0].x > current[1].x) {
				temp = current[1];
				current[1] = current[0];
				current[0] = temp;

				GroupMove.right(curGrid, current);

			}
			if (current[0].x == 1 && current[1].x == 1) { // 两点确定一条直线
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

		if (current[0].color == 6 || current[0].color == 7) {

			if (current[0].y > current[1].y) {
				Block temp = current[1];
				current[1] = current[0];
				current[0] = temp;
			}
		}

		for (int i = 0; i < 4; i += 1) {
			for (int j = 0; j < 4; j += 1) {
				g.setColor(colors[(preGrid[j][i])]);
				g.fill3DRect(20 + i * 27, 245 + j * 27, 27, 27, false);
			}
		}

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
		lines += linesOnce;
		score += (Math.exp((Math.log(2) * linesOnce)) - 1) * 100;

		// 对战版，升级快一些
		if(score >= 5000) {
			level = 10; // 最大10级
		} else {
			level = score / 500;
		}

		/*
		 * 另一种设定，最高11级，领先的将会没办法玩而先结束..
		 * 这种情况，每一级的分数不能太低，否则领先的很快就会升到11级，之后-2500分很可能输掉..
		 */
//		if(score >= 7700) {
//			level = 11; // 最大11级
//		} else {
//			level = score / 700;
//		}

		if (linesOnce >= 2)
			attack(linesOnce);
	}

	private void attack(int linesOnce) { // 攻击 屏幕抖动 & 此消彼长

		InPanel tempInPanel1 = ((JtetrisFrame) (getRootPane().getParent())).getinPanel1();
		InPanel tempInPanel2 = ((JtetrisFrame) (getRootPane().getParent())).getinPanel2();

		int[][] curGrid_BAK = new int[14][10];
		for (int i = 0; i < 10; i += 1) {
			for (int j = 0; j < 14; j += 1) {
				curGrid_BAK[j][i] = curGrid[j][i];
			}
		}

		for (int i = 0; i < 10; i += 1) {
			for (int j = 0; j < 14; j += 1) {
				curGrid[j][i] = NoBlock;
			}
		}
		((JtetrisFrame) (getRootPane().getParent())).locationShake(); // 屏幕抖动
		for (int i = 0; i < 10; i += 1) {
			for (int j = 0; j < 14; j += 1) {
				curGrid[j][i] = curGrid_BAK[j][i];
			}
		}

		int addLines = linesOnce / 2; // 每消两行给对方加一行
		if (tempInPanel1.equals(this)) { // 判断谁被攻击
			GroupMove.before(tempInPanel2.curGrid, tempInPanel2.current);

			for (int i = 0; i <= ROWS - addLines - 1; i++) {
				for (int j = 0; j <= COLS - 1; j++) {
					tempInPanel2.curGrid[i][j] = tempInPanel2.curGrid[i + addLines][j];
				}
			}
			for (int k = 1; k <= addLines; k++) {
				for (int m = 0; m <= COLS - 1; m++) {
					tempInPanel2.curGrid[ROWS - k][m] = (int) (Math.random() * 8);
				}

			}
			// 当加行时碰到当前方块
			for (int i = 0; i <= 3; i++) {
				for (int j = 0; j <= 3; j++) {
					while (tempInPanel2.curGrid[tempInPanel2.current[i].y][tempInPanel2.current[i].x] != InPanel.NoBlock) {
						for (int k = 0; k <= 3; k++) {
							tempInPanel2.current[k].y--;
						}
					}
				}
			}
			GroupMove.after(tempInPanel2.curGrid, tempInPanel2.current);
			tempInPanel2.repaint();
		}

		else {
			GroupMove.before(tempInPanel1.curGrid, tempInPanel1.current);

			for (int i = 0; i <= ROWS - addLines - 1; i++) {
				for (int j = 0; j <= COLS - 1; j++) {
					tempInPanel1.curGrid[i][j] = tempInPanel1.curGrid[i + addLines][j];
				}
			}
			for (int k = 1; k <= addLines; k++) {
				for (int m = 0; m <= COLS - 1; m++) {
					tempInPanel1.curGrid[ROWS - k][m] = (int) (Math.random() * 8);
				}
			}

			for (int i = 0; i <= 3; i++) {
				for (int j = 0; j <= 3; j++) {
					while (tempInPanel1.curGrid[tempInPanel1.current[i].y][tempInPanel1.current[i].x] != InPanel.NoBlock) {
						for (int k = 0; k <= 3; k++) {
							tempInPanel1.current[k].y--;
						}
					}
				}
			}

			GroupMove.after(tempInPanel1.curGrid, tempInPanel1.current);
			tempInPanel1.repaint();
		}
	}

	// 双人结束方法 两人是同时结束的 让其一去调用即可 需要对方的数据 用get
	public void gameOver2() {
		((JtetrisFrame) (getRootPane().getParent())).getinPanel1().forEver();
		((JtetrisFrame) (getRootPane().getParent())).getinPanel2().forEver();
		score -= 2500; // 先结束者减去2500分(一级的分)
		int highscore = Math.max(((JtetrisFrame) (getRootPane().getParent())).getinPanel1().score,
				((JtetrisFrame) (getRootPane().getParent())).getinPanel2().score);
		if (highscore == ((JtetrisFrame) (getRootPane().getParent())).getinPanel1().score) {

			((JtetrisFrame) (getRootPane()).getParent()).getinPanel1().winner = 1;
			((JtetrisFrame) (getRootPane()).getParent()).getinPanel2().winner = 0;
		} else {
			((JtetrisFrame) (getRootPane()).getParent()).getinPanel2().winner = 1;
			((JtetrisFrame) (getRootPane()).getParent()).getinPanel1().winner = 0;
		}
		JOptionPane.showConfirmDialog(getRootPane().getParent(),
				"\n                一切都将永远归于沉寂……\n\n            Ⅰ       "
						+ ((JtetrisFrame) (getRootPane().getParent())).getinPanel1().score + "     VS     "
						+ ((JtetrisFrame) (getRootPane().getParent())).getinPanel2().score + "       Ⅱ",
				"比赛结果", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
		// 使getRootPane().getParent()调用此对话框以使其在整体正中显示
	}

	protected void forEver() {
		for (int i = 0; i < 4; i += 1) {
			current[i] = new Block(0, 0, 0);
		}
		repaint();
	}

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