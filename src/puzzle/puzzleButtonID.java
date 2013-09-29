package slidePuzzleV2;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.Icon;

public class puzzleButtonID extends puzzleButton {

	private int id;

	public puzzleButtonID(BufferedImage image) {
		super(image);
		this.id = -1;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setIcon(Icon icon) {
		if (icon == null) {
			this.button.setBackground(Color.WHITE);
		}
		this.button.setIcon(icon);
	}

}
