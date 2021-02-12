package cn.love7.jtetris.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;

public class Jtetris {
	boolean packFrame = false;

	/**
	 * Construct and show the application.
	 */
	public Jtetris() {
		JtetrisFrame frame = new JtetrisFrame();
		if (packFrame) {
			frame.pack();
		} else {
			frame.validate();
		}

		// Center the window
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame.getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		frame.setUndecorated(true);
		frame.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);

		ImageIcon ico = new ImageIcon(cn.love7.jtetris.gui.Jtetris.class.getResource("imgs/Tetris.jpg"));
		// 使用自己的图标
		frame.setIconImage(ico.getImage());

		frame.setVisible(true);
		frame.setResizable(false);
	}

	/**
	 * Application entry point.
	 * 
	 * @param love7
	 *            String[]
	 */
	public static void main(String[] love7) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Jtetris();
			}
		});
	}
}
