package slidePuzzleV2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class puzzleButton {

	protected final JButton button;
	private Dimension d;

	public puzzleButton(BufferedImage image) {
		if (image == null) {
			this.button = new JButton();
			this.button.setBackground(Color.WHITE);
		} else {
			this.button = new JButton(new ImageIcon(image));
		}

	}

	public JButton getJButton() {
		return button;
	}

	public Dimension getDimension() {
		return d;
	}

	public void setDimension(Dimension d) {
		this.button.setPreferredSize(d);
		this.d = d;
	}

	public void setIcon(Icon icon) {
		this.button.setIcon(icon);
	}

}
