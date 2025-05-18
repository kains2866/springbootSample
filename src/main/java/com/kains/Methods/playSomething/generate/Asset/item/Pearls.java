package com.kains.Methods.playSomething.generate.Asset.item;

import com.kains.Methods.playSomething.generate.Asset.Asset;
import lombok.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author kains
 * @Date 2025/05/2025/5/18
 * @Description:
 */
@Builder
@Data
@ToString
public class Pearls extends Asset {
    private final static String name = "Pearls";
    private final static String asset = "Pearls";
    private final static String type = "item";
    public Pearls() {
        super(name, asset, type);
    }
    @Override
    public void use() {
        //装配
        System.out.println("Pearls: assemble");
    }
    @Override
    public String toString() {
        return "Pearls{" +
                "name='" + name + '\'' +
                ", asset='" + asset + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
