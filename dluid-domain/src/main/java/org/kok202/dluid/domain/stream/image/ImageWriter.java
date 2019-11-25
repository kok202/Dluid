package org.kok202.dluid.domain.stream.image;

import lombok.Data;
import org.kok202.dluid.domain.exception.InvalidImagePropertyException;
import org.kok202.dluid.domain.stream.NumericConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

@Data
public class ImageWriter {
    private int width = -1;
    private int height = -1;
    private ImageColorScale imageColorScale = null;

    private int[] pixel;
    private int[] pixelRed;
    private int[] pixelGreen;
    private int[] pixelBlue;

    private final String fileFormat = "png";
    private final int RGB_SIZE = 3;
    private final int ARGB_SIZE = 4;

    public void write(String filePath){
        validate();
        normalize();
        switch (imageColorScale){
            case GRAY:
                writeAsGray(filePath);
                break;
            case RGB:
                writeAsRGB(filePath);
                break;
        }
    }

    // values = RGB Array
    // ex) [0,0](255)  |  [1,0](32)  |  [2,0](12)
    // ex) [0,1](255)  |  [1,1](32)  |  [2,1](12)
    // ex) [0,2](255)  |  [1,2](32)  |  [2,2](12)
    private void writeAsGray(String filePath){
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster writableRaster = image.getRaster();
        writableRaster.setSamples(0, 0, width, height, 0, pixel);
        writeImage(filePath, image);
    }

    // values = RGB Array
    // ex) [0,0](255, 0, 0)  |  [1,0](127, 0, 0)  |  [2,0](50, 0, 0)
    // ex) [0,1](0, 255, 0)  |  [1,1](0, 127, 0)  |  [2,1](0, 50, 0)
    // ex) [0,2](0, 0, 255)  |  [1,2](0, 0, 127)  |  [2,2](0, 0, 50)
    private void writeAsRGB(String filePath){
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        WritableRaster writableRaster = image.getRaster();
        writableRaster.setSamples(0, 0, width, height, 0, pixelRed);
        writableRaster.setSamples(0, 0, width, height, 1, pixelGreen);
        writableRaster.setSamples(0, 0, width, height, 2, pixelBlue);
        writeImage(filePath, image);
    }

    private void writeImage(String filePath, BufferedImage image){
        try {
            File file = new File(filePath);
            ImageIO.write(image, fileFormat, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void validate(){
        if(width <= 0)
            throw new InvalidImagePropertyException();
        if(height <= 0)
            throw new InvalidImagePropertyException();
        if(pixel == null)
            throw new InvalidImagePropertyException();
        if(imageColorScale == null)
            throw new InvalidImagePropertyException();
    }

    private void normalize(){
        if(pixelRed == null || pixelGreen == null || pixelBlue == null)
            extractRGBMask();
    }

    private void extractRGBMask(){
        pixelRed = extractRGBMask(0, pixel);
        pixelGreen = extractRGBMask(1, pixel);
        pixelBlue = extractRGBMask(2, pixel);
    }

    private int[] extractRGBMask(int colorOffset, int... values){
        int[] extractedValues = new int[values.length/RGB_SIZE];
        for(int i = 0; i < extractedValues.length; i++) {
            extractedValues[i] = values[i * RGB_SIZE + colorOffset];
        }
        return extractedValues;
    }

    public void setPixel(int[] pixel){
        this.pixel = pixel;
    }

    public void setPixel(double[] pixel){
        this.pixel = NumericConverter.denormalize(0,255, pixel);
    }

    public void setPixelRed(int[] pixel){
        this.pixelRed = pixel;
    }

    public void setPixelRed(double[] pixel){
        this.pixelRed = NumericConverter.denormalize(0,255, pixel);
    }

    public void setPixelGreen(int[] pixel){
        this.pixelGreen = pixel;
    }

    public void setPixelGreen(double[] pixel){
        this.pixelGreen = NumericConverter.denormalize(0,255, pixel);
    }

    public void setPixelBlue(int[] pixel){
        this.pixelBlue = pixel;
    }

    public void setPixelBlue(double[] pixel){
        this.pixelBlue = NumericConverter.denormalize(0,255, pixel);
    }
}
