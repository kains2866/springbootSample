package com.kains.Methods.playSomething.position;

import com.kains.Methods.playSomething.generate.Asset.Asset;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author kains
 * @Date 2025/05/2025/5/18
 * @Description:
 */
@Data
public class BaseScene extends Asset {

    private List<BaseScene> innerScenes = null;

    // 添加无参构造方法，调用父类构造方法
    public BaseScene() {
        super("baseScene", "scene", "scene");
    }

    // 修改带参数的构造方法，确保父类构造方法被正确调用
    public BaseScene(String name, List<BaseScene> innerScenes) {
        super(name, "scene", "scene");
        this.innerScenes = innerScenes;
    }

    @Override
    public String getType() {
        return "BaseScene";
    }

    @Override
    public void use() {
        // TODO: 2025/5/18
    }

    public String toString() {
        return "BaseScene{" +
                "name='" + getName() + '\'' +
                ", asset='" + getAsset() + '\'' +
                ", type='" + getType() + '\'' +
                ", innerScenes=" + innerScenes +
                '}';
    }
}