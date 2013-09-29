package slidePuzzleV2;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;

public class game {

	public static void main(String[] args) throws Exception {

		if (args.length != 2) {
			System.out.println("Blogas parametrø skaèius");
		} else {
			try {
				String file = args[0];
				BufferedImage image = ImageIO.read(new File(file));
				String sSize = args[1];
				int size = Integer.parseInt(sSize);
				puzzleGUI.maxWindowHeight = 300;
				puzzleGUI.maxWindowWidth = 300;
				puzzleGUI.title = "Slide puzzle";
				slidePuzzle puzzle = new slidePuzzle(image, size);
				puzzle.startGame();
				
			} catch (IIOException e) {
				System.out.println("Neradau paveiksliuko");
			} catch (NumberFormatException e) {
				System.out.println("Áveskite sveikà skaièiø");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Ávyko nenumatyta klaida");
			}
		}

	}

}
