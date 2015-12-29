package ht;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JSlider;

/*
 * created by: Ifj. Dravecz Tibor
 */
public class hanoiTower {

	static final int towersMaxSize = 30;
	static final int diskWidth = 10;

	static int sleepTime = 10;

	static final long startTime = System.nanoTime();
	static long endTime;
	static int[][] tower = new int[3][towersMaxSize];
	static final int mainTower = 0;
	static final int helpTower = 1;
	static final int destinationTower = 2;
	static int counter = 0;
	static int towerWithDiskOne = 0;
	static boolean stop = false;

	private static Display viewer;

	public hanoiTower() {
		viewer = new Display(towersMaxSize, this);
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		JFrame frame = new JFrame("Speed Set");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 100);
		Container content = frame.getContentPane();
		content.setBackground(Color.white);

		JSlider sliderSpeed = new JSlider();
		sliderSpeed.setBorder(BorderFactory
				.createTitledBorder("0 is no sleep time and 100 is stop"));
		sliderSpeed.setMajorTickSpacing(10);
		sliderSpeed.setMinorTickSpacing(1);
		sliderSpeed.setPaintTicks(true);
		sliderSpeed.setPaintLabels(true);
		content.add(sliderSpeed, BorderLayout.CENTER);
		frame.setVisible(true);

		hanoiTower hT = new hanoiTower();
		setFirstTower();
		drawAll();
		// checkFinish() leellenőrzi a 3. torony állapotát
		while (checkFinish()) {
			sleepTime = sliderSpeed.getValue() * 10;
			while (sleepTime == 1000 || stop) {
				sleepTime = sliderSpeed.getValue() * 10;
				Thread.yield();
			}
			// ez az algoritmus aszerint változik hogy mekkora a torony mérete
			if (towersMaxSize % 2 == 1)
				if (towerWithDiskOne == 0)
					towerWithDiskOne = 2;
				else
					towerWithDiskOne--;
			else if (towerWithDiskOne == 2)
				towerWithDiskOne = 0;
			else
				towerWithDiskOne++;
			// mozgatja az első paraméterben megadott torony felső korongját a
			// másodikra
			// getOne() megadja az 1-es korong helyét
			getANDsetValue(getOne(), towerWithDiskOne);
			drawAll();
			counter++;
			// mozgatja a a legkisebb (nem a 1-es) korongot az egyetlen
			// lehetséges toronyra;
			moveSecond();
			drawAll();
			counter++;
		}
		endTime = System.nanoTime();
		System.out.println("Time:			" + (endTime - startTime) + "ns");
		System.out.println("Sleep time:	" + sleepTime + "ms");
		System.out.println("Steps:			" + counter);
	}

	private static void drawAll() {
		viewer.repaint();
		try {
			Thread.sleep(sleepTime);
		} catch (Exception e) {
		}
	}

	private static void moveSecond() {
		switch (towerWithDiskOne) {
		case 0:
			if (getTop(helpTower) > getTop(destinationTower))
				getANDsetValue(destinationTower, helpTower);
			else
				getANDsetValue(helpTower, destinationTower);
			break;
		case 1:
			if (getTop(mainTower) > getTop(destinationTower))
				getANDsetValue(destinationTower, mainTower);
			else
				getANDsetValue(mainTower, destinationTower);
			break;
		case 2:
			if (getTop(mainTower) > getTop(helpTower))
				getANDsetValue(helpTower, mainTower);
			else
				getANDsetValue(mainTower, helpTower);
			break;
		}
	}

	private static void getANDsetValue(int getTower, int setTower) {
		int save = 0;
		for (int i = towersMaxSize - 1; i >= 0; i--)
			if (tower[getTower][i] != 0) {
				save = tower[getTower][i];
				tower[getTower][i] = 0;
				break;
			}
		for (int i = 0; i < towersMaxSize; i++)
			if (tower[setTower][i] == 0) {
				tower[setTower][i] = save;
				break;
			}
	}

	private static int getOne() {
		if (getTop(mainTower) == 1)
			return mainTower;
		else if (getTop(helpTower) == 1)
			return helpTower;
		else
			return destinationTower;
	}

	private static int getTop(int towerNumber) {
		for (int i = towersMaxSize - 1; i >= 0; i--)
			if (tower[towerNumber][i] != 0)
				return tower[towerNumber][i];
		return towersMaxSize;
	}

	private static boolean checkFinish() {
		boolean rtn = true;
		for (int i = 0; i < towersMaxSize; i++)
			if (tower[destinationTower][i] == towersMaxSize - i)
				rtn = false;
			else
				rtn = true;
		return rtn;
	}

	private static void setFirstTower() {
		for (int i = 0; i < towersMaxSize; i++)
			tower[mainTower][i] = towersMaxSize - i;
	}

	public int diskSize(int towerNumber, int disk) {
		return tower[towerNumber][disk];
	}

	public void Stop() {
		stop = true;
	}

	public void Start() {
		stop = false;
	}
}
