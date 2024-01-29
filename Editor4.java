import java.awt.Color;

public class Editor4 {
    public static void main (String[] args) {
		String source = args[0];
        Color[][] targetImage;
		int n = Integer.parseInt(args[2]);
		Color[][] sourceImage = Runigram.read(source);
        targetImage = (Runigram.grayScaled(sourceImage));
		Runigram.setCanvas(sourceImage);
		Runigram.morph(sourceImage, targetImage, n);
	}
}
