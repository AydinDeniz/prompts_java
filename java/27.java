import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

// Image Processing Utilities
class ImageProcessor {

    public BufferedImage loadImage(String filePath) throws IOException {
        return ImageIO.read(new File(filePath));
    }

    public void saveImage(BufferedImage image, String format, String filePath) throws IOException {
        ImageIO.write(image, format, new File(filePath));
    }

    public BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(resultingImage, 0, 0, null);
        g2d.dispose();
        return outputImage;
    }

    public BufferedImage convertToGrayscale(BufferedImage originalImage) {
        BufferedImage grayscaleImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(),
                BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g2d = grayscaleImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, null);
        g2d.dispose();
        return grayscaleImage;
    }

    public BufferedImage detectEdges(BufferedImage originalImage) {
        BufferedImage edgeImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        for (int y = 1; y < originalImage.getHeight() - 1; y++) {
            for (int x = 1; x < originalImage.getWidth() - 1; x++) {
                int color = getEdgeDetectionColor(originalImage, x, y);
                edgeImage.setRGB(x, y, color);
            }
        }
        return edgeImage;
    }

    private int getEdgeDetectionColor(BufferedImage img, int x, int y) {
        int xWeight = -1 * getGray(img.getRGB(x - 1, y - 1)) + 1 * getGray(img.getRGB(x + 1, y - 1))
                    - 2 * getGray(img.getRGB(x - 1, y)) + 2 * getGray(img.getRGB(x + 1, y))
                    - 1 * getGray(img.getRGB(x - 1, y + 1)) + 1 * getGray(img.getRGB(x + 1, y + 1));

        int yWeight = -1 * getGray(img.getRGB(x - 1, y - 1)) - 2 * getGray(img.getRGB(x, y - 1))
                    - 1 * getGray(img.getRGB(x + 1, y - 1)) + 1 * getGray(img.getRGB(x - 1, y + 1))
                    + 2 * getGray(img.getRGB(x, y + 1)) + 1 * getGray(img.getRGB(x + 1, y + 1));

        int mag = (int) Math.sqrt(xWeight * xWeight + yWeight * yWeight);
        return new Color(mag, mag, mag).getRGB();
    }

    private int getGray(int rgb) {
        Color color = new Color(rgb);
        return (color.getRed() + color.getGreen() + color.getBlue()) / 3;
    }

}

// Machine Learning Model Simulation
class ImageClassifier {
    
    private Random random;

    public ImageClassifier() {
        this.random = new Random();
    }

    public String classifyImage(BufferedImage image) {
        // Simulate a classification result
        String[] possibleResults = {"Cat", "Dog", "Tree", "Car"};
        return possibleResults[random.nextInt(possibleResults.length)];
    }
}

// Image Processing and Classification Application
public class ImageProcessingApp {

    public static void main(String[] args) {
        ImageProcessor processor = new ImageProcessor();
        ImageClassifier classifier = new ImageClassifier();
        
        try {
            BufferedImage image = processor.loadImage("input.jpg");
            BufferedImage grayImage = processor.convertToGrayscale(image);
            processor.saveImage(grayImage, "jpg", "output_gray.jpg");
            
            BufferedImage edgeImage = processor.detectEdges(image);
            processor.saveImage(edgeImage, "jpg", "output_edges.jpg");

            // Resizing and then classifying the image
            BufferedImage resizedImage = processor.resizeImage(image, 100, 100);
            String classification = classifier.classifyImage(resizedImage);
            
            System.out.println("Image classified as: " + classification);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}