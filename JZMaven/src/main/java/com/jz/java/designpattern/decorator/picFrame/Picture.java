package com.jz.java.designpattern.decorator.picFrame;

public class Picture {
    int id;

    IPictureFrame frame;

    public Picture(int id) {
        this.id = id;
    }

    public void setFrame(IPictureFrame frame) {
        this.frame = frame;
    }

    public void paint() {
        System.out.println("Paint the Picture : " + id);

        if (frame != null) {
            frame.paintFrame();
        }
    }

}
