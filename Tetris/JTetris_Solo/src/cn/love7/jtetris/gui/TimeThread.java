package cn.love7.jtetris.gui;

import cn.love7.jtetris.game.Block;
import cn.love7.jtetris.game.GroupMove;

public class TimeThread extends Thread {
	private InPanel inPanel;
	// 共3种状态
	static boolean isPaused = false, isPlaying = false;

	public TimeThread(InPanel inPanel) {
		super();
		this.inPanel = inPanel;
	}

	public void run() {
		while (true) {
			try {
				do {
					// 最大10级为77ms..
					// 另一种设定最大11级，为7ms 没办法玩的..
					Thread.sleep(777 - inPanel.level * 70);
				} while (!isPlaying);
			} catch (InterruptedException exc) {
			}
			GroupMove.down(inPanel.curGrid, inPanel.current);
			inPanel.repaint();

			Block[] afterDown = { new Block((inPanel.current[0].x), inPanel.current[0].y + 1, inPanel.current[0].color),
					new Block((inPanel.current[1].x), inPanel.current[1].y + 1, inPanel.current[1].color),
					new Block((inPanel.current[2].x), inPanel.current[2].y + 1, inPanel.current[2].color),
					new Block((inPanel.current[3].x), inPanel.current[3].y + 1, inPanel.current[3].color) };

			if (!GroupMove.canMove(inPanel.curGrid, inPanel.current, afterDown)) {
				try {
					// transfer方块之前等待0.2s
					// 这个过程允许最后的移动和变形
					Thread.sleep(200);
				} catch (InterruptedException exc) {
				}

				if (!GroupMove.down(inPanel.curGrid, inPanel.current)) {
					inPanel.delLines();
					if (inPanel.transfer())
						inPanel.getNextBlockGroup();
					GroupMove.up(inPanel.curGrid, inPanel.current);
					inPanel.repaint();
				}
			} // if
		} // while
	} // run
}