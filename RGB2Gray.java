/***********************************************************************************************************************
 * @file  RGB2Gray.java
 * @brief This turns photos into gray based on a scale
 * @author Heba Beshai
 * @date   Appril 6, 2016
 ***********************************************************************************************************************/
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RGB2Gray {

    // Variable declarations
    public static void main(String[] args) throws IOException {
        BufferedImage inputImage, outImage; // reference for the input and output images
        int[][] red, green, blue;  // array names for the red, green, and blue image parts
        int[][] gray;  // array name for the gray scale image part
        // Read the image from a file specified by the user
        inputImage = readImageFromFile();
        red = getChannel(inputImage, "red");// Obtain the red, green, and blue channels using method getChannel()
        green = getChannel(inputImage, "green");
        blue = getChannel(inputImage, "blue");
        // Convert the rgb parts to gray scale
        gray = convertToGray(inputImage.getWidth(), inputImage.getHeight(), red, green, blue);
        // Write the output image to file: image.jpg
        outImage = arrayToBufferedImage(gray);
        ImageIO.write(outImage, "jpg", new File("image.jpg"));
    }


    // Convert the gray scale part back into an image using method arrayToBufferedImage()
    public static BufferedImage arrayToBufferedImage(int[][] gray) {
        BufferedImage outImage = new BufferedImage(gray.length, gray[0].length,
                BufferedImage.TYPE_BYTE_GRAY);
        for (int i = 0; i < gray.length; i++) {
            for (int j = 0; j < gray[i].length; j++) {
                int value = gray[i][j] << 16 | gray[i][j] << 8 | gray[i][j];
                outImage.setRGB(i, j, value);
            }
        }

        return outImage;
    }



    // Read the image from a file specified by the user

              /*  BufferedImage inputImage, outImage; // reference for the input and output images
                int[][] red, green, blue; // array names for the red, green, and blue image parts
                int[][] gray; // array name for the gray scale image part
                inputImage = readImageFromFile(); //method used
                red = getChannel(inputImage, "red");
                green = getChannel(inputImage, "green");
                blue = getChannel(inputImage, "blue");
                connectToGray(inputImage.getWidth(),inputImage.getHeight(), red, green, blue);
                getChannel(inputImage,"red");*/



    // Obtain the red, green, and blue channels using method getChannel()
    public static int[][] getChannel(BufferedImage img, String channel){
        int[][] table = new int[img.getWidth()][img.getHeight()];
        int flag, shift;
        if(channel.toLowerCase().equals("red")){
            flag = 16711680;
            shift = 16;
        } else if (channel.toLowerCase().equals("green")){
            flag = 65280;
            shift = 8;
        } else if (channel.toLowerCase().equals("blue")) {
            flag = 255;
            shift = 0;
        } else {
            return null;
        }

        for (int i = 0; i < img.getWidth(); i++){
            for (int j = 0; j < img.getHeight(); j++){
                table[i][j] = (img.getRGB(i, j) & flag) >> shift;
            }
        }
        return table;
    }


    // readImageFromFile() uses JFileChooser to help the user select an image and then
// returns the image as a BufferedImage
// ----------------------------------------------------------------------------------
    public static BufferedImage readImageFromFile() {
        BufferedImage img = null;
        final JFileChooser fileChooser = new JFileChooser();
        File selectedFile = null;
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
        }
        try {
            img = ImageIO.read(selectedFile);
        } catch (IOException e) {
        }
        return img;
    }

    // convertToGray() uses width, height, and 2-D arrays red, green, and blue to produce
// a new 2-D array of corresponding gray scale values for each pixel
// ----------------------------------------------------------------------------------
    public static int[][] convertToGray(int w, int h, int[][] red, int[][] green,
                                        int[][] blue) {
        int [][]gray = new int [w][h];
        for( w = 0; w<gray.length;w++) {
            for (h = 0; h < gray[0].length; h++) {


                gray[w][h] =(int) ((0.2126 * red[w][h]) +( 0.7152 * green[w][h]) + (0.0722 * blue[w][h]) );
                //two for nested loops
            }
        }

        return gray;
    }
}