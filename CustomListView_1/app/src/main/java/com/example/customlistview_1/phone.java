package com.example.customlistview_1;

public class phone {
    private String namephone;
    private int imagephone;
    // đại diện các đối tượng
    public phone(String namephone, int imagephone) {
        this.namephone = namephone;
        this.imagephone = imagephone;
    }

    public String getNamephone() {
        return namephone;
    }

    public int getImagephone() {
        return imagephone;
    }

    public void setNamephone(String namephone) {
        this.namephone = namephone;
    }

    public void setImagephone(int imagephone) {
        this.imagephone = imagephone;
    }
}
