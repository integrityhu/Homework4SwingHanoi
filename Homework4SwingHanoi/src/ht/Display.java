package ht;

import java.awt.Color;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.RoundRectangle2D;
import java.util.Random;
/*
 * created by: Ifj. Dravecz Tibor
 */
@SuppressWarnings("serial")
public class Display extends javax.swing.JPanel implements MouseListener,
		MouseMotionListener {

	private int diskWidth = hanoiTower.diskWidth;
	private int diskHeight = diskWidth;
	private int towerNumber;
	private int diskDown;
	private Color[] table = new Color[hanoiTower.towersMaxSize + 1];
	private int counter = hanoiTower.towersMaxSize;
	private hanoiTower game;

	@SuppressWarnings("deprecation")
	public Display(int size, hanoiTower hanoiTower) {
		super();
		this.towerNumber = 3;
		this.diskDown = size;
		addMouseListener(this);
		addMouseMotionListener(this);
		this.game = hanoiTower;
		javax.swing.JFrame frame = new javax.swing.JFrame("Hanoi Tower");
		frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		int totalWidth = diskWidth * towerNumber * size + diskWidth * 2;
		int totalHeight = diskHeight * diskDown;
		frame.setSize(totalWidth + 50, totalHeight + 50);
		this.setBackground(java.awt.Color.gray);
		this.setPreferredSize(new java.awt.Dimension(totalWidth + 10,
				totalHeight + 10));
		java.awt.Container container = frame.getContentPane();
		container.setBackground(java.awt.Color.gray);
		container.setLayout(new java.awt.FlowLayout());
		container.add(this);
		frame.show();
	}
	
	public void paintComponent(java.awt.Graphics origG) {
		int diskSize;
		int shift; 
		int setTower;
		int x;
		int y;
		super.paintComponent(origG);
		java.awt.Graphics2D g = (java.awt.Graphics2D) origG;
		final int margin = 1;
		for (int tower = 0; tower < towerNumber; tower++) {
			
			setTower = diskWidth * tower * (diskDown + 1);
			
			for (int disk = 0; disk < diskDown; disk++) {
				
				diskSize = game.diskSize(tower, (diskDown - 1) - disk);
				shift = diskWidth * (diskDown - diskSize) / 2;
				x = setTower + shift + margin;
				y = disk * diskHeight + margin;
				
				Shape diskOutline = new RoundRectangle2D.Float(x, y, diskWidth
						* diskSize, diskHeight, diskHeight / 2, diskHeight / 2);
				g.setColor(Color.black);
				g.draw(diskOutline);
				Shape diskShape = new RoundRectangle2D.Float(x + 1, y + 1,
						diskWidth * diskSize - 1, diskHeight - 1,
						(diskHeight) / 2, (diskHeight) / 2);
				g.setColor(getRandomColor(game.diskSize(tower, (diskDown - 1)
						- disk)));
				g.fill(diskShape);
			}
		}
	}
	

	// véletlen szerüen válasz egy színt és azt letárolja egy adott koronghoz
	private Color getRandomColor(int diskSize) {
		Random rn = new Random();
		Color rnd = new Color(rn.nextInt(255), rn.nextInt(255), rn.nextInt(255));
		if (counter != 0) {
			table[counter] = rnd;
			counter--;
			return rnd;
		} else {
			return table[diskSize];
		}
	}

	public void mouseDragged(MouseEvent arg0) {
	}

	public void mouseMoved(MouseEvent arg0) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
		game.Stop();
	}

	public void mouseExited(MouseEvent e) {
		game.Start();
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}
}
