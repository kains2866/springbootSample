package com.kains.Methods.playSomething.position;

import lombok.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author kains
 * @Date 2025/05/2025/5/18
 * @Description:
 */
@Getter
@Setter
@ToString
public class Position {
    private double x;
    private double y;
    private double z;

    public Position() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }
    public Position(double x, double y) {
        this.x = x;
        this.y = y;
        this.z = 0;
    }
    public Position(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public void move(double x, double y) {
        this.x += x;
        this.y += y;
    }

    public void move(double x, double y,  double z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }
}
