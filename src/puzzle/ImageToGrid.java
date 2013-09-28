package puzzle;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ImageToGrid {

	private final int blockSizeX;
	private final int blockSizeY;

	private List<BufferedImage> images = new ArrayList<BufferedImage>();

	public ImageToGrid(BufferedImage image, int xBlock, int yBlock) {

		int width = image.getWidth();
		int height = image.getHeight();
		
		boolean changedSize = false;
		while (width > 700 || height > 700) {
			changedSize = true;
			width = (int) ((int)width * 0.8);
			height = (int) ((int)height * 0.8);
		}
		if (changedSize) {
			Image newImage = image.getScaledInstance(width,height, Image.SCALE_SMOOTH);
			BufferedImage buffered = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			buffered.getGraphics().drawImage(newImage, 0, 0 , null);
			image = buffered;
		}
		
		
		

		this.blockSizeX = width / xBlock;
		this.blockSizeY = height / yBlock;
		

		for (int i = 0; i < yBlock; i++) {
			for (int j = 0; j < xBlock; j++) {
				this.images.add(image.getSubimage(this.blockSizeX * j,
						this.blockSizeY * i, this.blockSizeX, this.blockSizeY));
			}
		}
		
	}
	
	
	public BufferedImage getImage(int i) {
		return images.get(i);
	}
	
	
	public List<BufferedImage> getImages() {
		return images;
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
	
}
