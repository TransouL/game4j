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

import javax.swing.ButtonGroup;
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
	private static final long serialVersionUID = -990518333190938806L;
	JPanel contentPane;
	JMenuBar jMenuBar1 = new JMenuBar();
	JMenu jMenuFile = new JMenu();
	JLabel statusBar = new JLabel();
	static JMenuItem jMenuItem1 = new JMenuItem();
	static JMenuItem jMenuItem2 = new JMenuItem();
	static JMenuItem jMenuItem3 = new JMenuItem();
	static JCheckBoxMenuItem jMenuItem4 = new JCheckBoxMenuItem();
	JMenuItem jMenuFileExit = new JMenuItem();
	ButtonGroup buttonGroup1 = new ButtonGroup();
	ButtonGroup buttonGroup2 = new ButtonGroup();

	// 对战只需添加新的panel和线程即可
	InPanel inPanel1 = new InPanel();
	InPanel inPanel2 = new InPanel();

	static TimeThread timeThread = null;
	static TimeThread timeThread2 = null;

	// 监听不需要两个
	TetrisKeyListener listener = new TetrisKeyListener();

	boolean willFrameShake;

	public JtetrisFrame() {
		try {
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			jbInit();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		this.addWindowListener(new Frame1_windows_Listener(this));
		contentPane = (JPanel) getContentPane();
		setSize(new Dimension(910, 500));
		setTitle("对战俄罗斯");

		statusBar.setText(" ");
		statusBar.setBounds(new Rectangle(240, 10, 6, 15));

		jMenuFile.setFont(new java.awt.Font("Arial", Font.BOLD, 12));
		jMenuFile.setText("Game");
		jMenuFileExit.setText("Exit");
		jMenuFileExit.setFont(new java.awt.Font("Arial", Font.BOLD, 12));
		jMenuFileExit.addActionListener(new JtetrisFrame_jMenuFileExit_ActionAdapter(this));
		jMenuFileExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X,
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
		jMenuItem3.addActionListener(new JtetrisFrame_jMenuStop_ActionAdapter(this));
		jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T,
				java.awt.event.KeyEvent.CTRL_MASK, false));
		jMenuItem4.setEnabled(true);
		jMenuItem4.setFont(new java.awt.Font("Arial", Font.BOLD, 12));
		jMenuItem4.setText("Shake?");
		jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S,
				java.awt.event.KeyEvent.CTRL_MASK, false));
		jMenuItem4.addActionListener(new JtetrisFrame_jMenuShake_ActionAdapter(this));

		inPanel1.setBackground(Color.white);
		inPanel1.setBounds(new Rectangle(-1, 0, 458, 500));
		inPanel1.setLayout(null);
		inPanel2.setBackground(Color.white);
		inPanel2.setBounds(new Rectangle(457, 0, 458, 500));
		inPanel2.setLayout(null);
		inPanel2.user = "玩家Ⅱ";

		contentPane.setLayout(null);
		jMenuBar1.add(jMenuFile);
		setJMenuBar(jMenuBar1);
		jMenuFile.add(jMenuItem1);
		jMenuFile.add(jMenuItem2);
		jMenuFile.add(jMenuItem3);
		jMenuFile.addSeparator();
		jMenuFile.add(jMenuItem4);
		jMenuFile.addSeparator();
		jMenuFile.add(jMenuFileExit);
		jMenuFile.addSeparator();
		contentPane.add(statusBar, null);
		contentPane.add(inPanel1);
		contentPane.add(inPanel2);

		addKeyListener(listener);
		timeThread = new TimeThread(inPanel1);
		timeThread2 = new TimeThread(inPanel2);
		timeThread.start();
		timeThread2.start();

	}

	void jMenuFileExit_actionPerformed(ActionEvent actionEvent) {
		System.exit(0);
	}

	void jMenuStart_actionPerformed(ActionEvent actionEvent) {
		if (TimeThread.isPaused) {
			TimeThread.isPlaying = true;
			TimeThread.isPaused = false;
			jMenuItem1.setEnabled(false);
			jMenuItem2.setEnabled(true);
			jMenuItem3.setEnabled(true);
			return;
		}
		inPanel1.level = 0;
		inPanel1.score = 0;
		inPanel1.lines = 0;
		inPanel1.winner = -1;

		inPanel2.level = 0;
		inPanel2.score = 0;
		inPanel2.lines = 0;
		inPanel2.winner = -1;

		inPanel1.renascence();
		inPanel1.runFirst();
		inPanel1.repaint();
		TimeThread.isPlaying = true;
		inPanel2.renascence();
		inPanel2.runFirst();
		inPanel2.repaint();

		jMenuItem1.setEnabled(false);
		jMenuItem2.setEnabled(true);
		jMenuItem3.setEnabled(true);

	}

	void jMenuPause_actionPerformed(ActionEvent actionEvent) {

		TimeThread.isPlaying = false;
		TimeThread.isPaused = true;

		jMenuItem1.setEnabled(true);
		jMenuItem2.setEnabled(false);

	}

	protected void jMenuStop_actionPerformed(ActionEvent actionEvent) {

		TimeThread.isPaused = false;
		TimeThread.isPlaying = false;

		jMenuItem2.setEnabled(false);
		JtetrisFrame.jMenuItem1.setEnabled(true);
		jMenuItem2.setEnabled(false);
		JtetrisFrame.jMenuItem3.setEnabled(false);

	}

	public void jMenuShake_actionPerformed(ActionEvent actionEvent) {
		this.willFrameShake = !this.willFrameShake;

	}

	public InPanel getinPanel2() {
		return inPanel2;
	}

	public InPanel getinPanel1() {
		return inPanel1;
	}

	public void windowClosing(WindowEvent we) {
		if (JOptionPane.showConfirmDialog(this, "Do you love this game?", "Thanks for Playing",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			System.exit(0);
		} else {
			;
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
					GroupMove.left(inPanel1.curGrid, inPanel1.current);
					inPanel1.repaint();
					break;
				case KeyEvent.VK_D:
					GroupMove.right(inPanel1.curGrid, inPanel1.current);
					inPanel1.repaint();
					break;
				case KeyEvent.VK_S:
					if (!GroupMove.down(inPanel1.curGrid, inPanel1.current)) {
						inPanel1.delLines();
						if (inPanel1.transfer()) {
							inPanel1.getNextBlockGroup();
						}
					}
					inPanel1.repaint();
					break;
				case KeyEvent.VK_W:
					GroupMove.rotate(inPanel1.curGrid, inPanel1.current);
					inPanel1.repaint();
					break;
				case KeyEvent.VK_G: // 一键到底

					while (GroupMove.down(inPanel1.curGrid, inPanel1.current)) {
					}
					inPanel1.delLines();
					if (inPanel1.transfer()) {
						inPanel1.getNextBlockGroup();
					}
					GroupMove.up(inPanel1.curGrid, inPanel1.current);
					inPanel1.repaint();

					break;

				// //////////////////////////////PlayerⅡ
				case KeyEvent.VK_LEFT:
					GroupMove.left(inPanel2.curGrid, inPanel2.current);
					inPanel2.repaint();
					break;
				case KeyEvent.VK_RIGHT:
					GroupMove.right(inPanel2.curGrid, inPanel2.current);
					inPanel2.repaint();
					break;
				case KeyEvent.VK_DOWN:
					if (!GroupMove.down(inPanel2.curGrid, inPanel2.current)) {
						inPanel2.delLines();
						if (inPanel2.transfer()) {
							inPanel2.getNextBlockGroup();
						}
					}
					inPanel2.repaint();
					break;
				case KeyEvent.VK_UP:
					GroupMove.rotate(inPanel2.curGrid, inPanel2.current);
					inPanel2.repaint();
					break;
				case KeyEvent.VK_END:

					while (GroupMove.down(inPanel2.curGrid, inPanel2.current)) {
					}
					inPanel2.delLines();
					if (inPanel2.transfer()) {
						inPanel2.getNextBlockGroup();
					}
					GroupMove.up(inPanel2.curGrid, inPanel2.current);
					inPanel2.repaint();

					break;

				case KeyEvent.VK_H: // 清除当前方块
					GroupMove.before(inPanel1.curGrid, inPanel1.current);
					inPanel1.transfer();
					inPanel1.getNextBlockGroup();
					inPanel1.repaint();
					inPanel1.score -= 50;
					break;

				case KeyEvent.VK_PAGE_DOWN:
					GroupMove.before(inPanel2.curGrid, inPanel2.current);
					inPanel2.transfer();
					inPanel2.getNextBlockGroup();
					inPanel2.repaint();
					inPanel2.score -= 50;
					break;
				}
			}
		}
	}

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

class JtetrisFrame_jMenuShake_ActionAdapter implements ActionListener {
	JtetrisFrame adaptee;

	protected JtetrisFrame_jMenuShake_ActionAdapter(JtetrisFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent actionEvent) {
		adaptee.jMenuShake_actionPerformed(actionEvent);
	}

}
