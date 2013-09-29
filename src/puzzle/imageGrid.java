package slidePuzzleV2;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class imageGrid {

	private final List<BufferedImage> imageList = new ArrayList<BufferedImage>();
	private int blockSizeX;
	private int blockSizeY;

	public imageGrid(BufferedImage image, int i, int maxWindowX, int maxWindowY, double ratio) {
		int width = image.getWidth();
		int height = image.getHeight();

		boolean changedSize = false;
		while (width > maxWindowX || height > maxWindowY) {
			changedSize = true;
			width = (int) ((int) width * ratio);
			height = (int) ((int) height * ratio);
		}
		if (changedSize) {
			Image newImage = image.getScaledInstance(width, height,
					Image.SCALE_SMOOTH);
			BufferedImage buffered = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			buffered.getGraphics().drawImage(newImage, 0, 0, null);
			image = buffered;
		}

		this.blockSizeX = width / i;
		this.blockSizeY = height / i;

		for (int y = 0; y < i; y++) {
			for (int x = 0; x < i; x++) {
				this.imageList.add(image.getSubimage(this.blockSizeX * x,
						this.blockSizeY * y, this.blockSizeX, this.blockSizeY));
			}
		}
	}

	public int getBlockSizeX() {
		return this.blockSizeX;
	}

	public int getBlockSizeY() {
		return this.blockSizeY;
	}
	
	public Dimension getBlockSize() {
		return new Dimension(this.blockSizeX, this.blockSizeY);
	}

	public BufferedImage getImage(int i) {
		if (i < 0 || i > imageList.size()) {
			return null;
		}
		return imageList.get(i);
	}

}
