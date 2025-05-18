package com.kains.Methods.playSomething.position;

import com.kains.Methods.playSomething.generate.Asset.Asset;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @author kains
 * @Date 2025/05/2025/5/18
 * @Description:
 */
@Builder
@Data
public class BaseStage {
    private String name;
    private List<Asset> assets;

    public BaseStage(String name, List<Asset> assets) {
        this.name = name;
        this.assets = assets;
    }
    public BaseStage(){
        this.name = "BaseStage";
        this.assets = List.of(new BaseScene("baseScene",null));
    }

    public boolean check(){
        if (this.name==null||this.assets==null||this.assets.size()==0){
            return false;
        }
        if ( this.assets.stream()
                .filter(asset -> "BaseScene".equals(asset.getType()))
                .count() != 1) {
            System.out.println("无BaseScene或有多个BaseScene");
            return false;
        }
        return true;
    }

    public String toString(){
        List<String> assetsStrings = this.assets.stream().map(Object::toString)
                .collect(Collectors.toList());
        return "BaseStage{" +
                "name='" + name + '\'' +
                ", assets=" + assetsStrings +
                '}';
    }
}
