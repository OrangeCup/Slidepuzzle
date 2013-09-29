package slidePuzzleV2;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class puzzleGUI {

	public static String title = "Title";
	public static int maxWindowHeight = 400;
	public static int maxWindowWidth = 400;
	
	
	private imageGrid grid;
	private List<puzzleButtonID> button = new ArrayList<puzzleButtonID>();
	private int size;
	private final JFrame frame = new JFrame();
	private ActionListener listener;

	public void addButtons(Object[] position, int empty) {

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 0, 0);
		Dimension d = grid.getBlockSize();

		for (int i = 0; i < Math.pow(size, 2); i++) {
			puzzleButtonID b;
			if ((int) position[i] == Math.pow(size, 2) - 1) {
				b = new puzzleButtonID(null);
			} else {
				b = new puzzleButtonID(grid.getImage((int) position[i]));
			}
			b.setId((int) position[i]);
			b.setDimension(d);
			this.button.add(b);
			this.addToFrame(b, c);
			this.addListener(b);
			c.gridx++;
			if (i % size == size - 1) {
				c.gridy++;
				c.gridx = 0;
			}
		}
	}

	private void addToFrame(puzzleButtonID b, GridBagConstraints c) {
		this.frame.add(b.getJButton(), c);
	}

	private void addListener(puzzleButtonID b) {
		b.getJButton().addActionListener(this.listener);
	}

	public void initGUI() {
		frame.setResizable(false);
		frame.setTitle(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridBagLayout());
	}

	public puzzleGUI(BufferedImage image, int i, ActionListener a) {
		this.listener = a;
		this.grid = new imageGrid(image, i, maxWindowHeight, maxWindowWidth, 0.9);
		this.size = i;
	}

	public void setVisible(boolean b) {
		this.frame.pack();
		this.frame.setVisible(b);

	}

	public int findButtonPosition(JButton b) {
		for (int i = 0; i < Math.pow(size, 2); i++) {
			if (button.get(i).getJButton() == b) {
				return i;
			}
		}
		return -1;
	}

	public int findButtonId(int position) {
		return button.get(position).getId();
	}

	public void updateButtons(Object[] position, int emptySpace) {
		for (int i = 0; i < Math.pow(this.size, 2); i++) {
			puzzleButtonID b = button.get(i);
			if ((int) position[i] == Math.pow(this.size, 2) - 1) {
				b.setIcon(null);
			} else {
				b.setIcon(new ImageIcon(grid.getImage((int) position[i])));
			}
			b.setId((int) position[i]);			
		}
		
	}

}
