package puzzle;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class puzzle implements KeyListener {

	List<MyButton> buttons = new ArrayList<MyButton>();
	private final JFrame frame = new JFrame("Puzzle");
	private JLabel timeLabel = new JLabel();
	private JLabel pointsLabel = new JLabel();
	private int moves = 0;	
	private ImageToGrid imageGrid;
	private Object[] currentPosition;
	private Object[] endPosition;
	private int emptySpace;	
	private int x,y;
	private boolean win = false;

	private void initPosition() {
		List<Integer> tempList = new ArrayList<Integer>();
		for (int i = 0; i <= this.x*this.y-1; i++) {
			tempList.add(i);
		} // x*y-1 - empty
		this.endPosition = tempList.toArray();
		Collections.shuffle(tempList);
		this.currentPosition = tempList.toArray();
		tempList.clear();
		for (int i = 0; i <= this.x*this.y-1; i++) {
			if ((int) this.currentPosition[i] == this.x*this.y-1) {
				emptySpace = i;
				break;
			}
		}

	}

	private int getIdByButton(int keyId) {

		int e = this.emptySpace;
		
		
		// Down
		if (keyId == 40) {
			if (e >= this.x) {
				return e - this.x;
			}
		}

		// Right
		if (keyId == 39) {
			if (e % this.x != 0) {
				return e - 1;
			}
		}

		// Up
		if (keyId == 38) {
			if (e <= this.x*this.y-this.y-1) {
				return e + this.x;
			}
		}

		//Left
		if (keyId == 37) {
			if (e % this.x != this.x-1) {
				return e + 1;
			}
		}

		return -1;
	}

	private int getButtonPosById(int id) {
		for (int i = 0; i <= this.x * this.y - 1; i++) {
			if ((int)currentPosition[i] == id) {
				return i;
			}
		}
		return -1;
	}

	private void swapButtons(int id1, int id2) {
		//System.out.println(currentPosition[id1] + " " + currentPosition[id2]);
		Object temp = this.currentPosition[id1];
		this.currentPosition[id1] = this.currentPosition[id2];
		this.currentPosition[id2] = temp;
		this.emptySpace = id1;
	}

	private void moveButton(int j) {
		int buttonId = j;
		int emptyId = this.emptySpace;

		// Up
		if (buttonId <= this.x * (this.y - 1) - 1) {
			if (buttonId + this.x == emptyId) {
				this.swapButtons(buttonId, emptyId);
				this.updateMovesLabel();
			}
		} 
		
		// Down
		if (buttonId >= this.x - 1) {
			if (buttonId - this.x == emptyId) {
				this.swapButtons(buttonId, emptyId);
				this.updateMovesLabel();
			}
		}
		
		// Left
		if (buttonId % this.x != this.x - 1) {
			if (buttonId + 1 == emptyId && buttonId % this.x != this.x - 1) {
				this.swapButtons(buttonId, emptyId);
				this.updateMovesLabel();
			}
		}
		
		// Right
		if (buttonId % this.x != 0) {
			if (buttonId - 1 == emptyId && buttonId % this.x != 0) {
				this.swapButtons(buttonId, emptyId);
				this.updateMovesLabel();

			}
		}

		updateGui();

		boolean win = true;
		for (int i = 0; i <= this.x*this.y-1; i++) {
			if (!currentPosition[i].equals(endPosition[i])) {
				win = false;
			}
		}
		if (win) {
			this.timer.stop();
			JOptionPane.showMessageDialog(null, this.timeLabel.getText() + "\n" + this.pointsLabel.getText());		
			
		}

	}

	private void updateMovesLabel() {
		this.moves++;
		pointsLabel.setText("Ejimai: " + this.moves);

	}

	private void updateGui() {
		for (int i = 0; i <= this.x*this.y-1; i++) {

			MyButton button = buttons.get(i);
			//button.setId((int) currentPosition[i]);
			//button.repaintImage(imageGrid.getImage((int) currentPosition[i]));
			button.setId((int) currentPosition[i]);
			button.repaintImage(imageGrid.getImage((int) currentPosition[i]));	
			
			if ((int)currentPosition[i] == this.x*this.y-1) {	
				button.setIcon(null);
				button.setBackground(Color.WHITE);
			} else {

			}

		}
	}
	
	private void showSolved() {
		for (int i = 0; i <= this.x*this.y-1; i++) {
			MyButton button = buttons.get(i);
			button.repaintImage(imageGrid.getImage((int) endPosition[i]));
		}
	}

	
		
	Timer timer = new Timer(10, new UpdateTime());
	
	private class UpdateTime implements ActionListener {
		private int time = 0;
		public void actionPerformed(ActionEvent e) {
			String sTime = this.time / 100 + ":" + this.time % 100 + "";
			this.time++;
			timeLabel.setText("Laikas: " + sTime);
		}
	}
	
	
	
	private void setGui(BufferedImage image, int x, int y) {
		frame.setTitle("Slide Puzzle");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridBagLayout());
		//frame.setResizable(false);
		
		this.imageGrid = new ImageToGrid(image, x, y);		
		frame.setSize(imageGrid.getBlockSizeX() * this.x, imageGrid.getBlockSizeY() * this.y+10);
		this.timeLabel.setText("Laikas: 0");
		this.pointsLabel.setText("Ejimai: 0");

		for (int i = 0; i <= x*y-1; i++) {
			MyButton button = new MyButton();
			//System.out.println(i);
			if ((int)this.currentPosition[i] != x*y-1) {
				button.setIcon(new ImageIcon(imageGrid.getImage((int)this.currentPosition[i])));
			} else {
				button.setBackground(Color.WHITE);
			}
			button.setId((int) this.currentPosition[i]);
			button.setPreferredSize(imageGrid.getBlockSize());
//			button.addActionListener(new ActionListener() {
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					MyButton button = (MyButton) e.getSource();
////					System.out.println(button.getId());
//					moveButton(getButtonPosById(button.getId()));
//
//				}
//			});
			button.addKeyListener(this);
			this.buttons.add(button);
		}

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 0, 0);

		for (int i = 0; i <= x*y-1; i++) {
			frame.add(this.buttons.get(i), c);
			c.gridx++;
			if (i % x == x-1) {
				c.gridy++;
				c.gridx = 0;
			}
		}
		c.gridx = 0;
		c.gridy = this.y;
		c.gridwidth = 40;
		c.anchor = GridBagConstraints.SOUTHWEST;
		frame.add(timeLabel, c);
		c.gridy++;
		frame.add(pointsLabel, c);
		
		
		MyButton hintButton = new MyButton();
		
		hintButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {		
			}

			@Override
			public void mousePressed(MouseEvent e) {				
			}

			@Override
			public void mouseReleased(MouseEvent e) {				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				showSolved();
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				updateGui();
				
			}
			
		});
		
		hintButton.setText("?");
		hintButton.setBackground(Color.WHITE);
		hintButton.setEnabled(false);
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = this.x-1;
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.SOUTHEAST;
		frame.add(hintButton, c);		
		frame.pack();
		frame.setVisible(true);
	}

	public puzzle(BufferedImage image, int x, int y) {
		this.x = x;
		this.y = y;
		initPosition();
		timer.start();
		setGui(image,x,y);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int i = this.getIdByButton(e.getKeyCode());
		if (i != -1) {
			this.moveButton(i);
		}
	}

	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.out.print("Neteisingas parametrø skaièius");
		}
		try {
			//String file = JOptionPane.showInputDialog(null, "Enter file name (Same directory as .jar file)");
			String file = args[0];
			BufferedImage image = ImageIO.read(new File(file));
			//String size = JOptionPane.showInputDialog(null, "Enter the size of board");
			int s = Integer.parseInt(args[1]);
			puzzle p = new puzzle(image, s, s);			
		} catch(IOException e) {
			//JOptionPane.showMessageDialog(null, "File not found");
			System.out.println("File not found");
		} catch(NumberFormatException e) {
			//JOptionPane.showMessageDialog(null, "That's not an integer!");
			System.out.println("Enter integer");
		} catch (Exception e){
		
		}
		
	}

}


