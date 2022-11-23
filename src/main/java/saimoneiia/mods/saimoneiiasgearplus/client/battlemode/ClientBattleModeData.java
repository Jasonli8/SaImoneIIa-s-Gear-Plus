package saimoneiia.mods.saimoneiiasgearplus.client.battlemode;

import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientBattleModeData {
    private static boolean isBattleMode = false;
    private static final Minecraft minecraft = Minecraft.getInstance();

    public static void set(boolean isSet) { isBattleMode = isSet; }

    public static void toggle() {
        isBattleMode = !isBattleMode;
        if (isBattleMode) {
            minecraft.options.setCameraType(CameraType.THIRD_PERSON_BACK);
            minecraft.player.removeVehicle();
        } else {
            minecraft.options.setCameraType(CameraType.FIRST_PERSON);
        }
    }

    public static boolean get()  { return isBattleMode; }


}
