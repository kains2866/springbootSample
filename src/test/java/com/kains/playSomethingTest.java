package com.kains;

import com.kains.Methods.playSomething.generate.Asset.Asset;
import com.kains.Methods.playSomething.generate.Asset.item.AttackSeed;
import com.kains.Methods.playSomething.generate.Asset.item.Pearls;
import com.kains.Methods.playSomething.generate.Asset.monster.Fatalis;
import com.kains.Methods.playSomething.position.BaseScene;
import com.kains.Methods.playSomething.position.BaseStage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author kains
 * @Date 2025/05/2025/5/18
 * @Description:
 */
public class playSomethingTest {
    public static void main(String[] args) {
        BaseStage baseStage = new BaseStage("MainStage",iniAsset());
        System.out.println(baseStage.check());
        System.out.println(baseStage.toString());

        Fatalis fatalis = (Fatalis) baseStage.getAssets().stream().filter(asset -> asset.getName().equals("fatalis")).findFirst().get();
        fatalis.move(1,1);
        System.out.println(fatalis.toString());
    }


    public static List<Asset> iniAsset() {
        List<Asset> assets = new ArrayList<>();
        //baseScene
        List<BaseScene> innerScenes = Arrays.asList(
                new BaseScene("insideRoom1",null),
                new BaseScene("insideRoom2",null),
                new BaseScene("outsideDoor",null)
        );
        BaseScene baseScene = new BaseScene("MainScene",innerScenes );

        //monster
        Fatalis fatalis = new Fatalis();

        //item
        Pearls pearls = new Pearls();
        AttackSeed attackSeed = new AttackSeed();

        assets.add(baseScene);
        assets.add(fatalis);
        assets.add(pearls);
        assets.add(attackSeed);

        return assets;
    }
}
