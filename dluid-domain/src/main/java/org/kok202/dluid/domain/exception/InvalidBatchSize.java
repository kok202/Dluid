package org.kok202.dluid.domain.exception;

public class InvalidBatchSize extends RuntimeException {

    private int totalSize;

    public InvalidBatchSize(int totalSize){
        super("Batch size must be under than total size.");
        this.totalSize = totalSize;
    }

    public int getRecommendedSize(){
        if(totalSize <= 4)
            return totalSize;
        else if(totalSize <= 32)
            return 4;
        else if(totalSize <= 64)
            return 16;
        else if(totalSize <= 256)
            return 32;
        else if(totalSize <= 1024)
            return 64;
        else
            return 128;
    }

}
