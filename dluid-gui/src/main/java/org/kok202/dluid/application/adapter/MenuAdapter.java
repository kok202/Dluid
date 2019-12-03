package org.kok202.dluid.application.adapter;

import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import lombok.Data;

import java.util.ArrayList;
import java.util.function.Consumer;

@Data
public class MenuAdapter<T> {
    private MenuButton MenuButton;
    private Consumer<T> menuItemChangedListener;
    private ArrayList<MenuAdapterItem<T>> menuAdapterItems;

    public MenuAdapter(MenuButton MenuButton) {
        this.MenuButton = MenuButton;
        this.menuAdapterItems = new ArrayList<>();
    }

    public void addMenuItem(String menuItemName, T settingType){
        MenuItem menuItem = new MenuItem(menuItemName);
        menuItem.setOnAction(event -> {
            menuItemChangedListener.accept(settingType);
            MenuButton.setText(menuItemName);
        });
        MenuButton.getItems().add(menuItem);
        menuAdapterItems.add(new MenuAdapterItem<>(menuItem,settingType));
    }

    public void setDefaultMenuItem(){
        setDefaultMenuItem(0);
    }

    public void setDefaultMenuItem(T settingType){
        for(int i = 0; i< menuAdapterItems.size(); i++){
            if(settingType == menuAdapterItems.get(i).getSettingType()){
                setDefaultMenuItem(i);
                return;
            }
        }
        setDefaultMenuItem();
    }

    public void setDefaultMenuItem(int index){
        MenuButton.setText(menuAdapterItems.get(index).getMenuItem().getText());
        menuItemChangedListener.accept(menuAdapterItems.get(index).getSettingType());
    }
}

