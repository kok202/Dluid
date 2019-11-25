package org.kok202.dluid.application.adapter;

import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import lombok.Data;

import java.util.ArrayList;
import java.util.function.Consumer;

@Data
public class SplitMenuAdapter<T> {
    private SplitMenuButton splitMenuButton;
    private Consumer<T> menuItemChangedListener;
    private ArrayList<SplitMenuItem<T>> splitMenuItems;

    public SplitMenuAdapter(SplitMenuButton splitMenuButton) {
        this.splitMenuButton = splitMenuButton;
        this.splitMenuItems = new ArrayList<>();
    }

    public void addMenuItem(String menuItemName, T settingType){
        MenuItem menuItem = new MenuItem(menuItemName);
        menuItem.setOnAction(event -> {
            menuItemChangedListener.accept(settingType);
            splitMenuButton.setText(menuItemName);
        });
        splitMenuButton.getItems().add(menuItem);
        splitMenuItems.add(new SplitMenuItem<>(menuItem,settingType));
    }

    public void setDefaultMenuItem(){
        setDefaultMenuItem(0);
    }

    public void setDefaultMenuItem(T settingType){
        for(int i=0; i<splitMenuItems.size(); i++){
            if(settingType == splitMenuItems.get(i).getSettingType()){
                setDefaultMenuItem(i);
                return;
            }
        }
        setDefaultMenuItem();
    }

    public void setDefaultMenuItem(int index){
        splitMenuButton.setText(splitMenuItems.get(index).getMenuItem().getText());
        menuItemChangedListener.accept(splitMenuItems.get(index).getSettingType());
    }
}

