package saimoneiia.mods.saimoneiiasgearplus.client.particle;

import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Random;

public class MitoRetraceOptions {
    public final Vec3 start;
    public final Vec3 end;
    public final int lifespan = 200;

    public float lineWidth = 0.05F;

    public MitoRetraceOptions(Vec3 start, Vec3 end) {
        this.start = start;
        this.end = end;
    }
}
