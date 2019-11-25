package org.kok202.dluid.ai.util;

public class ConvolutionCalculatorUtil {
    public static int getConv1DOutputSize(int inputSize, int kernelSize, int stride, int padding){
        return (int)((inputSize + 2d * padding - kernelSize) / stride + 1);
    }

    public static int[] getConv2DOutputSize(int[] inputSize, int[] kernelSize, int[] stride, int[] padding){
        return new int[]{
                (getConv1DOutputSize(inputSize[0], kernelSize[0], stride[0], padding[0])),
                (getConv1DOutputSize(inputSize[1], kernelSize[1], stride[1], padding[1])),
        };
    }

    public static int getDeconv1DOutputSize(int inputSize, int kernelSize, int stride, int padding){
        return (inputSize - 1) * stride + kernelSize - 2 * padding;
    }

    public static int[] getDeconv2DOutputSize(int[] inputSize, int[] kernelSize, int[] stride, int[] padding){
        return new int[]{
                (getDeconv1DOutputSize(inputSize[0], kernelSize[0], stride[0], padding[0])),
                (getDeconv1DOutputSize(inputSize[1], kernelSize[1], stride[1], padding[1])),
        };
    }
}
