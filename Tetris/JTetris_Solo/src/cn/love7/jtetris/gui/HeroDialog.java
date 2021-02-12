package cn.love7.jtetris.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cn.love7.jtetris.game.Player;
import cn.love7.jtetris.game.Top10;

public class HeroDialog extends JDialog {
	private static final long serialVersionUID = 6870011882052524907L;
	String newHeroName = "";
	int score = 0;
	Top10 top10 = new Top10();

	JPanel panel1 = new JPanel();
	JLabel jLabel1 = new JLabel();
	JTextField jTextField1 = new JTextField();
	JLabel jLabel2 = new JLabel();
	JTextArea jTextArea1 = new JTextArea();
	JButton jButton1 = new JButton();
	JButton jButton2 = new JButton();
	BorderLayout borderLayout1 = new BorderLayout();

	public HeroDialog(JFrame owner, String title, boolean modal) {
		super(owner, title, modal);
		try {
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			jbInit();
			pack();
		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

	public HeroDialog() {
		this(new JFrame(), "Hero", false);

	}

	private void jbInit() throws Exception {
		panel1.setLayout(null);
		this.getContentPane().setLayout(borderLayout1);
		panel1.setBackground(SystemColor.control);
		jLabel2.setFont(new java.awt.Font("宋体", Font.BOLD, 18));
		jLabel2.setText("Top10");
		jLabel2.setBounds(new Rectangle(141, 80, 56, 28));

		// 排行榜区
		jTextArea1.setBounds(new Rectangle(52, 114, 260, 283));
		jTextArea1.setEditable(false);
		top10.read();
		jTextArea1.setText(top10.toString());

		jButton1.setBounds(new Rectangle(193, 409, 107, 35));
		jButton1.setText("离开");
		jButton1.addActionListener(new Hero_jButton1_actionAdapter(this));
		jButton2.setBounds(new Rectangle(34, 409, 107, 35));
		jButton2.setText("确定");
		jButton2.addActionListener(new Hero_jButton2_actionAdapter(this));
		jLabel1.setFont(new java.awt.Font("宋体", Font.BOLD, 14));
		jLabel1.setText("请输入您的姓名");
		jLabel1.setBounds(new Rectangle(28, 42, 138, 36));

		jTextField1.setBackground(SystemColor.inactiveCaptionText);
		jTextField1.setBorder(BorderFactory.createLoweredBevelBorder());
		// 输入姓名区
		jTextField1.setBounds(new Rectangle(160, 45, 174, 27));
		jTextField1.addActionListener(new Hero_jTextField1_actionAdapter(this));

		panel1.add(jLabel1);
		panel1.add(jTextField1);
		panel1.add(jTextArea1);
		panel1.add(jLabel2);
		panel1.add(jButton2);
		panel1.add(jButton1);
		this.getContentPane().add(panel1, java.awt.BorderLayout.CENTER);

		setModal(true);
		setAlwaysOnTop(true);
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
		setResizable(false);
	}

	public void jButton2_actionPerformed(ActionEvent e) {
		newHeroName = jTextField1.getText();
		if (newHeroName.equals("")) {
			newHeroName = "无名氏";
		}

		// 纪录一次以后清空输入区并不再允许输入
		jTextField1.setEditable(false);
		jButton2.setEnabled(false);
		jTextField1.setText("");

		// 显示更新后的纪录 必须在这里实现
		top10.read();
		Player newHero = new Player(newHeroName, score);
		top10.joinTop10(newHero);
		top10.write();
		this.jTextArea1.setText(top10.toString());
	}

	public void jButton1_actionPerformed(ActionEvent e) {
		setVisible(false);
		// AutoDownThread.isPlaying=true;
		// AutoDownThread.isPaused=false;
	}

	public void jTextField1_actionPerformed(ActionEvent e) {
		jButton2.doClick();
		jButton1.requestFocus();
	}
}

class Hero_jTextField1_actionAdapter implements ActionListener {
	private HeroDialog adaptee;

	Hero_jTextField1_actionAdapter(HeroDialog adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jTextField1_actionPerformed(e);
	}
}

class Hero_jButton1_actionAdapter implements ActionListener {
	private HeroDialog adaptee;

	Hero_jButton1_actionAdapter(HeroDialog adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jButton1_actionPerformed(e);
	}
}

class Hero_jButton2_actionAdapter implements ActionListener {
	private HeroDialog adaptee;

	Hero_jButton2_actionAdapter(HeroDialog adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jButton2_actionPerformed(e);
	}
}
