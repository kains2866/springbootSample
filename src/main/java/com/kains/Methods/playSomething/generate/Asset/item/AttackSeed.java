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
public class AttackSeed extends Asset {
    private final static String name = "AttackSeed";
    private final static String asset = "AttackSeed";
    private final static String type = "item";
    public AttackSeed() {
        super(name,  asset, type);
    }
    @Override
    public void use() {
        System.out.println("eat attackSeed");
    }
    public String toString(){
        return "AttackSeed{" +
                "name='" + name + '\'' +
                ", asset='" + asset + '\'' +
                ", type='" + type + '\'' +
                '}' ;
    }

}
