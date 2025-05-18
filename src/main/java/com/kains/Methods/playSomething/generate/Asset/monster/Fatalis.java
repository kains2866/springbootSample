package com.kains.Methods.playSomething.generate.Asset.monster;
import com.kains.Methods.playSomething.generate.Asset.Asset;
import com.kains.Methods.playSomething.generate.motion.FatalisMotion.FatalisMotions;
import com.kains.Methods.playSomething.generate.motion.Motion;
import com.kains.Methods.playSomething.position.Position;
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
public class Fatalis extends Asset {
    public static final String FATALIS_NAME = "fatalis";
    public static final String FATALIS_ASSET = "fatalis";
    public static final String FATALIS_TYPE = "monster";

    private final Motion motion = new FatalisMotions();
    private Position position;

    // 默认构造器：使用默认值 + 默认位置
    public Fatalis() {
        this(FATALIS_NAME, FATALIS_ASSET, FATALIS_TYPE, new Position(0, 0));
    }

    // 接收 Position 构造器
    public Fatalis(Position position) {
        this(FATALIS_NAME, FATALIS_ASSET, FATALIS_TYPE, position);
    }

    // 全参构造器
    public Fatalis(String name, String asset, String type, Position position) {
        super(name, asset, type);
        this.position = position;
    }

    public void move(double x, double y) {
        position.move(x, y);
    }

    public void move(double x, double y, double z) {
        position.move(x, y, z);
    }

    @Override
    public String getType() {
        return FATALIS_TYPE;
    }

    @Override
    public void use() {
        motion.run();
    }

    @Override
    public String toString() {
        return "Fatalis{" +
                "name='" + getName() + '\'' +
                ", asset='" + getAsset() + '\'' +
                ", type='" + getType() + '\'' +
                ", position=" + position +
                '}';
    }
}