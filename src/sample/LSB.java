package sample;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class LSB {

    static BufferedImage merge(BufferedImage coverImage, BufferedImage hiddenImage) {
        if (!checkImages(1,1,1,1)) return null;

        BufferedImage stegoImage = coverImage;

        for(int i = 0; i < coverImage.getWidth(); i++) {
            for(int j = 0; j < coverImage.getHeight(); j++) {
                int coverPixelValue = coverImage.getRGB(i, j);
                ArrayList<Integer> coverPixelValues = getDecimalPixelValues(coverPixelValue);

                int hiddenPixelValue = hiddenImage.getRGB(i, j);
                ArrayList<Integer> hiddenPixelValues = getDecimalPixelValues(hiddenPixelValue);

                int stegoPixelValue = modifyPixelValue(coverPixelValues, hiddenPixelValues);

                stegoImage.setRGB(i, j, stegoPixelValue);
            }
        }

        return stegoImage;
    }

    /*
    public static ArrayList<BufferedImage> unmerge(BufferedImage stegoImage, int hiddenImageWidth, int hiddenImageHeight) {

    }
    */

    private static ArrayList<Integer> getDecimalPixelValues(int argbValue) {
        ArrayList<Integer> decimalPixelValues = new ArrayList<>();

        for(int i = 24; i >= 0; i-=8) {
            decimalPixelValues.add((argbValue>>i) & 0xff);
        }

        return decimalPixelValues;
    }

    private static int toRgb(ArrayList<Integer> argbValues) {
        int a = argbValues.get(0);
        int r = argbValues.get(1);
        int g = argbValues.get(2);
        int b = argbValues.get(3);

        return (a<<24) | (r<<16) | (g<<8) | b;
    }

    private static ArrayList<String> toBinaryValues(ArrayList<Integer> argbValues) {
        ArrayList<String> binaryPixelValues = new ArrayList<>();

        for(Integer value : argbValues) {
            binaryPixelValues.add(String.format("%8s", Integer.toBinaryString(value)).replace(' ', '0'));
            //binaryPixelValues.add(Integer.toBinaryString(value));
        }

        return binaryPixelValues;
    }

    private static ArrayList<Integer> toDecimalValues(ArrayList<String> argbValues) {
        ArrayList<Integer> decimalPixelValues = new ArrayList<>();

        for(String value : argbValues) {
            decimalPixelValues.add(Integer.parseInt(value, 2));
        }

        return decimalPixelValues;
    }

    private static int modifyPixelValue(ArrayList<Integer> coverArgbValues, ArrayList<Integer> hiddenArgbValues) {
        ArrayList<String> coverArgbBinaryValues = toBinaryValues(coverArgbValues);
        ArrayList<String> hiddenArgbBinaryValues = toBinaryValues(hiddenArgbValues);
        ArrayList<String> modifiedArgbBinaryValues = new ArrayList<>();

        for(int i = 0; i < 4; i++) {
            String modPixelValue = coverArgbBinaryValues.get(i).substring(0, 4) + hiddenArgbBinaryValues.get(i).substring(0, 4);
            modifiedArgbBinaryValues.add(modPixelValue);
        }

        return toRgb(toDecimalValues(modifiedArgbBinaryValues));
    }

    private static boolean checkImages(int cWidth, int cHeight, int hWidth, int hHeight) {
        return true;
    }
}
