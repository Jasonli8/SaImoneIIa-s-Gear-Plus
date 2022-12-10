package saimoneiia.mods.saimoneiiasgearplus.client.particle;

public enum EffectType {
    MITO_RETRACE_EFFECT(-1);

    // If -1, then variable length and the number of arguments is also sent over the network
    public final int argCount;

    EffectType(int argCount) {
        this.argCount = argCount;
    }
}
