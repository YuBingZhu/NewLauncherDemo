package com.launcher.demo.addview;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by panbing on 2018/7/26.
 */

public class AddModel implements Serializable {

    public String itemTitle;
    public int itemType;
    public List<String> itemChilds = new ArrayList<>();

    public AddModel(String itemTitle, int itemType) {
        this.itemTitle = itemTitle;
        this.itemType = itemType;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public List<String> getItemChilds() {
        return itemChilds;
    }

    public void setItemChilds(List<String> itemChilds) {
        this.itemChilds = itemChilds;
    }

    @Override
    public String toString() {
        return "AddModel{" +
                "itemTitle='" + itemTitle + '\'' +
                ", itemType=" + itemType +
                ", itemChilds=" + itemChilds +
                '}';
    }
}
