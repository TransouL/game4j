package cn.love7.jtetris.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cn.love7.jtetris.game.GroupMove;

public class JtetrisFrame extends JFrame {
	private static final long serialVersionUID = -3145874368142099568L;
	JPanel contentPane;
	JMenuBar jMenuBar1 = new JMenuBar();
	JMenu jMenuFile = new JMenu();
	JMenu jMenuHelp = new JMenu();
	JMenuItem jMenuHelpAbout = new JMenuItem();

	JLabel statusBar = new JLabel();
	static JMenuItem jMenuItem1 = new JMenuItem();
	static JMenuItem jMenuItem2 = new JMenuItem();
	static JMenuItem jMenuItem3 = new JMenuItem();
	static JCheckBoxMenuItem jMenuItem4 = new JCheckBoxMenuItem();
	JMenuItem jMenuFileExit = new JMenuItem();

	JMenuItem jMenuItem5 = new JMenuItem();
	InPanel inPanel = new InPanel();

	static TimeThread timeThread = null;
	boolean willFrameShake;

	TetrisKeyListener listener = new TetrisKeyListener();

	public JtetrisFrame() {
		try {
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			jbInit();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * Component initialization.
	 * 
	 * @throws java.lang.Exception
	 */
	private void jbInit() throws Exception {
		// 设置监听退出确认
		this.addWindowListener(new Frame1_windows_Listener(this));

		contentPane = (JPanel) getContentPane();
		setSize(new Dimension(450, 500));
		setTitle("俄罗斯方块");

		statusBar.setText(" ");
		statusBar.setBounds(new Rectangle(240, 10, 6, 15));

		jMenuFile.setFont(new java.awt.Font("Arial", Font.BOLD, 12));
		jMenuFile.setText("Game");
		jMenuHelp.setFont(new java.awt.Font("Arial", Font.BOLD, 12));
		jMenuHelp.setText("Help");

		jMenuFileExit.setText("Exit");
		jMenuFileExit.setFont(new java.awt.Font("Arial", Font.BOLD, 12));
		jMenuFileExit.addActionListener(new JtetrisFrame_jMenuFileExit_ActionAdapter(this));
		jMenuFileExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q,
				java.awt.event.KeyEvent.CTRL_MASK, false));

		jMenuItem1.setFont(new java.awt.Font("Arial", Font.BOLD, 12));
		jMenuItem1.setText("Start");
		jMenuItem1.addActionListener(new JtetrisFrame_jMenuStart_ActionAdapter(this));
		jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
		jMenuItem2.setEnabled(false);
		jMenuItem2.setFont(new java.awt.Font("Arial", Font.BOLD, 12));
		jMenuItem2.setText("Pause");
		jMenuItem2.addActionListener(new JtetrisFrame_jMenuPause_ActionAdapter(this));
		jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P,
				java.awt.event.KeyEvent.CTRL_MASK, false));
		jMenuItem3.setEnabled(false);
		jMenuItem3.setFont(new java.awt.Font("Arial", Font.BOLD, 12));
		jMenuItem3.setText("Stop");
		jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T,
				java.awt.event.KeyEvent.CTRL_MASK, false));
		jMenuItem3.addActionListener(new JtetrisFrame_jMenuStop_ActionAdapter(this));
		jMenuItem4.setEnabled(true);
		jMenuItem4.setFont(new java.awt.Font("Arial", Font.BOLD, 12));
		jMenuItem4.setText("Shake?");
		jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S,
				java.awt.event.KeyEvent.CTRL_MASK, false));
		jMenuItem4.addActionListener(new JtetrisFrame_jMenuShake_ActionAdapter(this));

		inPanel.setBackground(Color.white);
		inPanel.setBounds(new Rectangle(-1, 0, 458, 500));
		inPanel.setLayout(null);
		contentPane.setLayout(null);

		jMenuItem5.setFont(new java.awt.Font("Arial", Font.BOLD, 12));
		jMenuItem5.setText("Records");
		jMenuItem5.addActionListener(new JtetrisFrame_jMenuRecord_ActionAdapter(this));

		jMenuFile.addSeparator();// !@#$%
		jMenuFile.add(jMenuItem1);
		jMenuFile.add(jMenuItem2);
		jMenuFile.add(jMenuItem3);
		jMenuFile.addSeparator();
		jMenuFile.add(jMenuItem4);
		jMenuFile.addSeparator();
		jMenuFile.add(jMenuFileExit);
		jMenuFile.addSeparator();

		jMenuHelp.addSeparator();
		jMenuHelp.add(jMenuItem5);
		jMenuHelp.addSeparator();

		jMenuBar1.add(jMenuFile);
		jMenuBar1.add(jMenuHelp);

		setJMenuBar(jMenuBar1);

		contentPane.add(statusBar, null);
		contentPane.add(inPanel);

		// 监听和线程开始
		addKeyListener(listener);
		timeThread = new TimeThread(inPanel);
		timeThread.start();
	}

	/**
	 * File | Exit action performed.
	 * 
	 * @param actionEvent
	 *            ActionEvent
	 */
	void jMenuFileExit_actionPerformed(ActionEvent actionEvent) {
		if (JOptionPane.showConfirmDialog(this, "Do you love this game?", "Thanks for Playing",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
			System.exit(0);
	}

	//开始
	void jMenuStart_actionPerformed(ActionEvent actionEvent) {
		if (TimeThread.isPaused) {
			TimeThread.isPlaying = true;
			TimeThread.isPaused = false;
			jMenuItem1.setEnabled(false);
			jMenuItem2.setEnabled(true);
			jMenuItem3.setEnabled(true);

			return;
		}
		// 重置分数放在每次开始 结束之后的分数可以一直保存到下一次开始
		inPanel.level = 0;
		inPanel.score = 0;
		inPanel.lines = 0;

		inPanel.renascence();
		inPanel.runFirst();
		inPanel.repaint();
		TimeThread.isPlaying = true;

		jMenuItem1.setEnabled(false);
		jMenuItem2.setEnabled(true);
		jMenuItem3.setEnabled(true);

	}

	// 暂停
	void jMenuPause_actionPerformed(ActionEvent actionEvent) {
		TimeThread.isPlaying = false;
		TimeThread.isPaused = true;

		jMenuItem1.setEnabled(true);
		jMenuItem2.setEnabled(false);

	}

	// 屏幕抖动
	public void locationShake() {
		if (willFrameShake) {
			Dimension frameSize = getSize();
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			setLocation((screenSize.width - frameSize.width) / 2 - 90, (screenSize.height - frameSize.height) / 2 - 90);
			try {
				Thread.sleep(5);
				setLocation((screenSize.width - frameSize.width) / 2 + 80,
						(screenSize.height - frameSize.height) / 2 + 80);
				Thread.sleep(5);
				setLocation((screenSize.width - frameSize.width) / 2 - 70,
						(screenSize.height - frameSize.height) / 2 - 70);
				Thread.sleep(5);
				setLocation((screenSize.width - frameSize.width) / 2 + 60,
						(screenSize.height - frameSize.height) / 2 + 60);
				Thread.sleep(5);
				setLocation((screenSize.width - frameSize.width) / 2 - 50,
						(screenSize.height - frameSize.height) / 2 - 50);
				Thread.sleep(5);
				setLocation((screenSize.width - frameSize.width) / 2 + 40,
						(screenSize.height - frameSize.height) / 2 + 40);
				Thread.sleep(5);
				setLocation((screenSize.width - frameSize.width) / 2 - 30,
						(screenSize.height - frameSize.height) / 2 - 30);
				Thread.sleep(5);
				setLocation((screenSize.width - frameSize.width) / 2 + 20,
						(screenSize.height - frameSize.height) / 2 + 20);
				Thread.sleep(5);
				setLocation((screenSize.width - frameSize.width) / 2 - 10,
						(screenSize.height - frameSize.height) / 2 - 10);
				Thread.sleep(5);
				setLocation((screenSize.width - frameSize.width) / 2 - 90,
						(screenSize.height - frameSize.height) / 2 + 90);
				Thread.sleep(4);
				setLocation((screenSize.width - frameSize.width) / 2 + 80,
						(screenSize.height - frameSize.height) / 2 - 80);
				Thread.sleep(4);
				setLocation((screenSize.width - frameSize.width) / 2 - 70,
						(screenSize.height - frameSize.height) / 2 + 70);
				Thread.sleep(3);
				setLocation((screenSize.width - frameSize.width) / 2 + 60,
						(screenSize.height - frameSize.height) / 2 - 60);
				Thread.sleep(3);
				setLocation((screenSize.width - frameSize.width) / 2 - 50,
						(screenSize.height - frameSize.height) / 2 + 50);
				Thread.sleep(2);
				setLocation((screenSize.width - frameSize.width) / 2 + 40,
						(screenSize.height - frameSize.height) / 2 - 40);
				Thread.sleep(2);
				setLocation((screenSize.width - frameSize.width) / 2 - 30,
						(screenSize.height - frameSize.height) / 2 + 30);
				Thread.sleep(1);
				setLocation((screenSize.width - frameSize.width) / 2 + 20,
						(screenSize.height - frameSize.height) / 2 - 20);
				Thread.sleep(1);
				setLocation((screenSize.width - frameSize.width) / 2 - 10,
						(screenSize.height - frameSize.height) / 2 + 10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		}

	}

	// 停止
	protected void jMenuStop_actionPerformed(ActionEvent actionEvent) {
		// 更改当前状放在在最前面
		TimeThread.isPaused = false;
		TimeThread.isPlaying = false;

		jMenuItem2.setEnabled(false);
		JtetrisFrame.jMenuItem1.setEnabled(true);
		jMenuItem2.setEnabled(false);
		JtetrisFrame.jMenuItem3.setEnabled(false);

		inPanel.gameOver();
		// removeKeyListener(listener);
	}

	// 记录
	void jMenuRecord_actionPerformed(ActionEvent actionEvent) {
		HeroDialog hero = new HeroDialog(new JFrame(), "游戏高手排行榜", true);
		hero.jTextField1.setEditable(false);
		hero.jButton2.setEnabled(false);
		hero.setSize(370, 500);
		// 设置到屏幕中间
		hero.setLocation(1024 / 2 - 370 / 2, 768 / 2 - 500 / 2);
		hero.setVisible(true);

		// TimeThread.isPlaying=false;
		// TimeThread.isPaused=true;
	}

	void jMenuShake_actionPerformed(ActionEvent actionEvent) {
		this.willFrameShake = !this.willFrameShake;

	}

	public void windowClosing(WindowEvent we) {
		if (JOptionPane.showConfirmDialog(this, "Do you love this game?", "Thanks for Playing",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			System.exit(0);
		} else {
			// 默认
		}
	}

	class Frame1_windows_Listener extends WindowAdapter {
		JtetrisFrame ada = null;

		Frame1_windows_Listener(JtetrisFrame f) {
			ada = f;
		}

		public void windowClosing(WindowEvent e) {
			ada.windowClosing(e);
		}
	}

	private class TetrisKeyListener implements KeyListener {
		public void keyTyped(KeyEvent key) {
		}

		public void keyReleased(KeyEvent key) {
		}

		public void keyPressed(KeyEvent key) {
			if (TimeThread.isPlaying && !TimeThread.isPaused) {

				switch (key.getKeyCode()) {
				case KeyEvent.VK_A:
					GroupMove.left(inPanel.curGrid, inPanel.current);
					inPanel.repaint();
					break;
				case KeyEvent.VK_D:
					GroupMove.right(inPanel.curGrid, inPanel.current);
					inPanel.repaint();
					break;
				case KeyEvent.VK_S:
					if (!GroupMove.down(inPanel.curGrid, inPanel.current)) {
						inPanel.delLines();
						if (inPanel.transfer())
							inPanel.getNextBlockGroup();
					}
					inPanel.repaint();
					break;
				case KeyEvent.VK_W:
					GroupMove.rotate(inPanel.curGrid, inPanel.current);
					inPanel.repaint();
					break;
				case KeyEvent.VK_L: // L，上……
					GroupMove.up(inPanel.curGrid, inPanel.current);
					inPanel.repaint();
					break;
				case KeyEvent.VK_K: // 清除当前方块
					locationShake();
					GroupMove.before(inPanel.curGrid, inPanel.current);
					inPanel.transfer();
					inPanel.getNextBlockGroup();
					inPanel.score -= 50;
					inPanel.repaint();
					break;

				case KeyEvent.VK_J: // 一键到底
					locationShake();
					while (GroupMove.down(inPanel.curGrid, inPanel.current)) {
					}
					inPanel.delLines();
					if (inPanel.transfer())
						inPanel.getNextBlockGroup();
					GroupMove.up(inPanel.curGrid, inPanel.current);
					inPanel.repaint();
					break;

				// 以下为习惯按键
				case KeyEvent.VK_BACK_SPACE:
					GroupMove.before(inPanel.curGrid, inPanel.current);
					if (inPanel.transfer())
						inPanel.getNextBlockGroup();
					inPanel.repaint();
					break;

				case KeyEvent.VK_SPACE:
					while (GroupMove.down(inPanel.curGrid, inPanel.current)) {
					}
					inPanel.delLines();
					inPanel.transfer();
					inPanel.getNextBlockGroup();
					inPanel.repaint();

					break;
				}
			}
		}
	}
}

class JtetrisFrame_jMenuFileExit_ActionAdapter implements ActionListener {
	JtetrisFrame adaptee;

	JtetrisFrame_jMenuFileExit_ActionAdapter(JtetrisFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent actionEvent) {
		adaptee.jMenuFileExit_actionPerformed(actionEvent);
	}

}

// 开始
class JtetrisFrame_jMenuStart_ActionAdapter implements ActionListener {
	JtetrisFrame adaptee;

	JtetrisFrame_jMenuStart_ActionAdapter(JtetrisFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent actionEvent) {
		adaptee.jMenuStart_actionPerformed(actionEvent);
	}
}

// 暂停
class JtetrisFrame_jMenuPause_ActionAdapter implements ActionListener {
	JtetrisFrame adaptee;

	JtetrisFrame_jMenuPause_ActionAdapter(JtetrisFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent actionEvent) {
		adaptee.jMenuPause_actionPerformed(actionEvent);
	}
}

// 停止
class JtetrisFrame_jMenuStop_ActionAdapter implements ActionListener {
	JtetrisFrame adaptee;

	protected JtetrisFrame_jMenuStop_ActionAdapter(JtetrisFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent actionEvent) {
		adaptee.jMenuStop_actionPerformed(actionEvent);
	}
}

// 记录
class JtetrisFrame_jMenuRecord_ActionAdapter implements ActionListener {
	JtetrisFrame adaptee;

	protected JtetrisFrame_jMenuRecord_ActionAdapter(JtetrisFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent actionEvent) {
		adaptee.jMenuRecord_actionPerformed(actionEvent);
	}
}

class JtetrisFrame_jMenuShake_ActionAdapter implements ActionListener {
	JtetrisFrame adaptee;

	protected JtetrisFrame_jMenuShake_ActionAdapter(JtetrisFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent actionEvent) {
		adaptee.jMenuShake_actionPerformed(actionEvent);
	}

}
