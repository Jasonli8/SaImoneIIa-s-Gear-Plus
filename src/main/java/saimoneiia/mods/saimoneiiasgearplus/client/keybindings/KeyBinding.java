package saimoneiia.mods.saimoneiiasgearplus.client.keybindings;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY_BATTLE = "key.category.saimoneiiasgearplus.battle";
    public static final String KEY_TOGGLE_BATTLE_MODE = "key.saimoneiiasgearplus.toggleBattleMode";
    public static final KeyMapping TOGGLE_BATTLE_MODE_KEY = new KeyMapping(KEY_TOGGLE_BATTLE_MODE, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, KEY_CATEGORY_BATTLE);
}
