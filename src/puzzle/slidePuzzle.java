package slidePuzzleV2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;

public class slidePuzzle {

	private static final int UP = 0;
	private static final int DOWN = 2;
	private static final int RIGHT = 1;
	private static final int LEFT = 3;

	private final puzzleGUI gui;
	private Object[] endPosition;
	private Object[] currentPosition;
	private int emptySpace;
	private int size;

	private int canMove(int position) {

		// Down
		if (position <= Math.pow(this.size, 2) - 1) {
			if (position + this.size == emptySpace) {
				return DOWN;
			}
		}

		// Up
		if (position >= this.size - 1) {
			if (position - this.size == emptySpace) {
				return UP;
			}
		}

		// Right
		if (position % this.size != this.size - 1) {
			if (position + 1 == emptySpace
					&& position % this.size != this.size - 1) {
				return RIGHT;
			}
		}

		// Left
		if (position % this.size != 0) {
			if (position - 1 == emptySpace && position % this.size != 0) {
				return LEFT;
			}
		}

		return -1;
	}

	private void swapPositions(int i, int j) {
		int temp = (int) this.currentPosition[i];
		this.currentPosition[i] = this.currentPosition[j];
		this.currentPosition[j] = temp;
		this.emptySpace = i;
	}

	ActionListener listener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			int position = gui.findButtonPosition(b);
			int moveDirection = canMove(position);
			if (moveDirection == -1) {
				return;
			}
			switch (moveDirection) {
			case (UP):
				swapPositions(position, position - size);
				break;
			case (DOWN):
				swapPositions(position, position + size);
				break;
			case (LEFT):
				swapPositions(position, position - 1);
				break;
			case (RIGHT):
				swapPositions(position, position + 1);
			}

			gui.updateButtons(currentPosition, emptySpace);

			if (isVictory()) {
				System.out.print("Congratulations! You won: NOTHING");
			}

		}

	};

	private boolean isVictory() {
		for (int i = 0; i < this.size * this.size; i++) {
			if (currentPosition[i] != endPosition[i]) {
				return false;
			}
		}
		return true;
	}

	private void initPositions() {
		List<Integer> tempList = new ArrayList<Integer>();
		for (int i = 0; i <= Math.pow(this.size, 2) - 1; i++) {
			tempList.add(i);
		}
		this.endPosition = tempList.toArray();
		Collections.shuffle(tempList);
		this.currentPosition = tempList.toArray();
		tempList.clear();
		for (int i = 0; i < Math.pow(this.size, 2); i++) {
			if ((int) this.currentPosition[i] == Math.pow(this.size, 2) - 1) {
				emptySpace = i;
				break;
			}
		}
	}

	public slidePuzzle(BufferedImage image, int i) {
		this.gui = new puzzleGUI(image, i, this.listener);
		this.size = i;
		this.initPositions();
		gui.initGUI();
		gui.addButtons(currentPosition, emptySpace);
	}

	public void startGame() {
		gui.setVisible(true);

	}

}
