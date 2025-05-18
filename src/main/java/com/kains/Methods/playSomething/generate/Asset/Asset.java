package com.kains.Methods.playSomething.generate.Asset;

import lombok.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author kains
 * @Date 2025/05/2025/5/18
 * @Description: 基本的资产
 */

@Getter
public abstract class Asset {
    private String name = "Asset";
    private String asset;
    private String type;

    public Asset() {
        this.asset = "Asset";
        this.type = "Asset";
        this.name = "Asset";
    }
    public Asset(String asset, String type) {
        this.asset = asset;
        this.type = type;
    }
    public Asset(String name,String asset, String type) {
        this.name = name;
        this.asset = asset;
        this.type = type;
    }

    public String getType(){
        return "Asset";
    }
    public abstract void use();
}
