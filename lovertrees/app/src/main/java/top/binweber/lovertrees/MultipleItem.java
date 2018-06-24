package top.binweber.lovertrees;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by BinWe on 2018/3/19.
 */

public class MultipleItem implements MultiItemEntity {

    public static final int TYPE_0 = 0; // banner
    public static final int TYPE_1 = 1; // divider
    public static final int TYPE_2 = 2; // special
    public static final int TYPE_3 = 3; // goods

    public static final int TYPE_SPAN_SIZE_5 = 5;
    public static final int TYPE_SPAN_SIZE_10 = 10;
    public static final int TYPE_SPAN_SIZE_20 = 20;

    private int itemType;
    private int spanSize;
    private int image;
    private int background;
    private String title;
    private String price;
    private String type;
    private List<String> list;

    public MultipleItem(int itemTyep, List<String> list, int spanSize) {
        this.itemType = itemTyep;
        this.list = list;
        this.spanSize = spanSize;
    }

    public MultipleItem(int itemType ,int spanSize) {
        this.itemType = itemType;
        this.spanSize = spanSize;
    }

    public MultipleItem(int itemType, int image, String title, int spanSize) {
        this.itemType = itemType;
        this.image = image;
        this.title = title;
        this.spanSize = spanSize;
    }

    public MultipleItem(int itemType, int background, int image, String title, int spanSize) {
        this.itemType = itemType;
        this.background = background;
        this.image = image;
        this.title = title;
        this.spanSize = spanSize;
    }

    public MultipleItem(int itemType, int image, String title, String price, String type, int spanSize) {
        this.itemType = itemType;
        this.image = image;
        this.title = title;
        this.price = price;
        this.type = type;
        this.spanSize = spanSize;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public int getSpanSize() {
        return spanSize;
    }

    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public int getItemType() {
        return itemType;
    }
}
