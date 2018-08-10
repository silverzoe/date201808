package date201808;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame {

	/**
	 * 主界面
	 */
	public static void main(String[] args) {
		Frame fr = new Frame();
		fr.show();
	}

	private void show() {
		JFrame frame = new JFrame();
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(3);
		frame.setResizable(false);
		
		JPanel north = new JPanel();
		JButton button1 = new JButton("训练");
		JButton button2 = new JButton("识别");
		north.setBackground(Color.yellow);
		north.add(button1);
		north.add(button2);
		Integer[] choice = {0,1,2,3,4,5,6,7,8,9};
		JComboBox<Integer> box = new JComboBox<Integer>(choice);
		north.add(box);
		JButton button3 = new JButton("清除缓存");
		north.add(button3);
		frame.add(north, BorderLayout.NORTH);
		
		frame.setVisible(true);
		
		Graphics g = frame.getGraphics();
		int width = 800;
		int height = 600;
		int length = 15;
		int[][] size = new int[height/length][width/length];
		
		Listener lis = new Listener(g, size);
		frame.addMouseListener(lis);
		frame.addMouseMotionListener(lis);
		button1.addActionListener(lis);
		button2.addActionListener(lis);
		button3.addActionListener(lis);
		box.addActionListener(lis);
	}

}
