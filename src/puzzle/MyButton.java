package puzzle;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MyButton extends JButton {

	private static int id;

	public MyButton() {
		super();
	}
	
	public MyButton(String string, ImageIcon imageIcon) {
		super(string, imageIcon);
	}

	public void setId(int id) {
		id = id;
	}

	public int getId() {
		return id;
	}

	public void repaintImage(BufferedImage image) {
		if (image == null) {
			this.setBackground(Color.WHITE);
			this.setIcon(null);
		} else {
			this.setIcon(new ImageIcon(image));	
		}
		
		
	}



}