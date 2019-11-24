package org.kok202.deepblock.application.adapter.file;

import lombok.Getter;
import lombok.Setter;
import org.kok202.deepblock.application.adapter.PopUpExtension;
import org.kok202.deepblock.application.content.popup.ImageManagementPopUpController;
import org.kok202.deepblock.application.singleton.AppPropertiesSingleton;
import org.kok202.deepblock.domain.stream.NumericRecordSet;
import org.kok202.deepblock.domain.stream.image.ImageColorScale;
import org.kok202.deepblock.domain.stream.image.ImageWriter;

import java.io.File;

public abstract class ExtendedImageSaver {
    private String directoryPath;
    @Getter
    private PopUpExtension popUpExtension;
    @Setter
    private String imageName = "";
    @Setter
    private int imageWidth = 1;
    @Setter
    private int imageHeight = 1;
    @Setter
    private ImageColorScale imageColorScale = ImageColorScale.GRAY;

    public void popUpWindow(File file) {
        try{
            directoryPath = file.getPath();
            popUpExtension = new PopUpExtension();
            popUpExtension
                    .setTitle(AppPropertiesSingleton.getInstance().get("frame.file.save.image.title"))
                    .setWidth(400)
                    .setHeight(200)
                    .setPopUpSceneRoot(new ImageManagementPopUpController(this).createView())
                    .setCallbackAfterLoad(this::save)
                    .show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void save(){
        for(int index = 0; index < getNumericRecordSet().getRecords().size(); index++){
            ImageWriter imageWriter = new ImageWriter();
            imageWriter.setWidth(imageWidth);
            imageWriter.setHeight(imageHeight);
            imageWriter.setImageColorScale(imageColorScale);
            imageWriter.setPixel(getNumericRecordSet().getRecordAsArray(index));
            imageWriter.write(directoryPath + "/" + imageName + " (" + (index+1) + ").png");
        }
    }

    public void closePopUpExtensionWithLoad() {
        popUpExtension.load();
    }

    public void closePopUpExtension() {
        popUpExtension.cancel();
    }

    protected abstract NumericRecordSet getNumericRecordSet();
}
