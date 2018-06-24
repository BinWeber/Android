package top.binweber.lampsay;

/**
 * Created by wangb on 2017/12/27.
 */

public class Contacts {

    private String name;

    private String address;

    private String time;

    private int imageId;

    public Contacts(String name, String address, int imageId, String time) {
        this.name = name;
        this.address = address;
        this.imageId = imageId;
        this.time = time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public int getImageId() {
        return imageId;
    }

    public String getAddress() {
        return address;
    }


}
