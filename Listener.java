package date201808;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Listener extends MouseAdapter implements ActionListener {

	Graphics g;
	String text, lasttext;
	String c;
	int countout = 1, countin = 0;
	int width = 800, height = 600, length = 15;
	int[][] size;
	int x0 = 0, y0 = 70, x1, y1;
	HashMap<Integer, Integer> hash = new HashMap<>();
	int kk = 0;
	int[] k = new int[150];
	int count = 0;
	int min;
	File folder = new File("E:\\编程\\蓝杰学习\\silverzoey\\src\\date201808\\data");


	public Listener(Graphics g, int[][] size) {
		this.g = g;
		this.size = size;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for (int i = 0; i < width / length; i++) {
			for (int j = 0; j < height / length; j++) {
				g.setColor(Color.black);
				g.fillRect(x0 + i * length, y0 + j * length, length, length);
			}
		}
		for (int i = 0; i < height / length; i++) {
			for (int j = 0; j < width / length; j++)
				size[i][j] = 0;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		kk = 0;
		if (lasttext.equals("训练")) {
			// 写入文档
			String pathname, filename, s;
			countout = countout + 1;
			filename = Integer.toString(countout);
			pathname = "E:\\编程\\蓝杰学习\\silverzoey\\src\\date201808\\data\\" + c
					+ filename + ".txt";
			File file = new File(pathname);
			try {
				OutputStream out = new FileOutputStream(file);
				for (int i = 0; i < height / length; i++) {
					for (int j = 0; j < width / length; j++) {
						s = Integer.toString(size[i][j]);
						out.write(s.getBytes());
					}
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if (lasttext.equals("识别")) {
			int a;
			for (int m = 0; m < 10; m++) {
				String cc = Integer.toString(m);
				String pathname, filename;
				// 每个数字都查一遍
				for (int n = 1; n < 15; n++) {
					kk = 0;
					filename = Integer.toString(n);
					pathname = "E:\\编程\\蓝杰学习\\silverzoey\\src\\date201808\\data\\"
							+ cc + filename + ".txt";
					File file = new File(pathname);
					try {
						InputStream in = new FileInputStream(file);
						for (int i = 0; i < height / length; i++) {
							for (int j = 0; j < width / length; j++) {
								try {
									a = in.read();
									// 计算差值，ascll码48
									kk += Math.abs(size[i][j] - a + 48);
//									 System.out.print(a-48);
								} catch (IOException e1) {
									e1.printStackTrace();
								}

							}
							// System.out.print("\n");
						}
					} catch (FileNotFoundException e1) {
//						e1.printStackTrace();
					}
					k[count++] = kk;
					hash.put(kk, m);
				}

			}
			// kk的最小值
			min = 1000;
			for (int l = 0; l < count; l++) {
				if (k[l] != 0 && k[l] < min)
					min = k[l];
			}
			System.out.println("\n" + hash.get(min));
			shuchu();
		}
		count = 0;
	}

	private void shuchu() {
		JFrame f = new JFrame();
		f.setSize(200, 180);
		f.setLocationRelativeTo(null);
//		f.setDefaultCloseOperation(3);
		f.setResizable(false);
		int number = hash.get(min);
		ImageIcon tp = new ImageIcon(
				"E:\\编程\\蓝杰学习\\silverzoey\\src\\date201808\\picture\\"
						+ Integer.toString(number) + ".jpg");
		JLabel l = new JLabel();
		l.setIcon(tp);
		f.add(l);
		f.setVisible(true);
	}

	public void mouseDragged(MouseEvent e) {
		x1 = e.getX();
		y1 = e.getY();
		g.setColor(Color.white);
		g.fillRect(x1, y1, length, length);
		if (x1 > x0 && x1 < x0 + width && y1 > y0 && y1 < y0 + height) {
			size[(y1 - y0) / length][(x1 - x0) / length] = 1;
			size[(y1 - y0) / length + 1][(x1 - x0) / length + 1] = 1;
		}

	}

	public void actionPerformed(ActionEvent e) {
		// 鼠标动作监听
		text = e.getActionCommand();
		if (text.equals("训练")) {
			System.out.println("训练");
			lasttext = text;

		}
		if (text.equals("识别")) {
			System.out.println("识别");
			lasttext = text;
		}

		if (text.equals("comboBoxChanged")) {
			JComboBox<Integer> box = (JComboBox<Integer>) e.getSource();
			c = box.getSelectedItem().toString();
			System.out.println(c);
			countout=0;
		}
		if (text.equals("清除缓存")) {
			clear(folder);
		}
	}

	private void clear(File folder) {
		File[] files = folder.listFiles();
		if(files!=null){
			for(File f:files){
				if(f.isDirectory())
					clear(f);
				else
					f.delete();
			}
			System.out.println("清除缓存完毕！");
		}
	}
}
