package saimoneiia.mods.saimoneiiasgearplus.client.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.tuple.Pair;
import saimoneiia.mods.saimoneiiasgearplus.client.core.RenderHelper;

import java.util.*;

public class MitoRetraceRenderer {
    public static final MitoRetraceRenderer INSTANCE = new MitoRetraceRenderer();

    private static final double LIFETIME_AFTER_LAST_LINE = 200;
    private Timestamp refreshTimestamp = Timestamp.ZERO;
    private final Random random = new Random();
    private final List<RetraceEmitter> retraceEmitters = new LinkedList<>();

    public static void onWorldRenderLast(Camera camera, float partialTicks, PoseStack ps, RenderBuffers buffers) {
        ps.pushPose();
        // here we translate based on the inverse position of the client viewing camera to get back to 0, 0, 0
        Vec3 camVec = camera.getPosition();
        ps.translate(-camVec.x, -camVec.y, -camVec.z);
        MitoRetraceRenderer.INSTANCE.render(partialTicks, ps, buffers);

        ps.popPose();
    }

    public void render(float partialTicks, PoseStack matrixStack, RenderBuffers buffers) {
        Matrix4f matrix = matrixStack.last().pose();
        Timestamp timestamp = new Timestamp(Minecraft.getInstance().level.getGameTime(), partialTicks);
        var bufferSource = buffers.bufferSource();
        VertexConsumer buffer = bufferSource.getBuffer(RenderHelper.MITO_RETRACE);

        for (Iterator<RetraceEmitter> iter = retraceEmitters.iterator(); iter.hasNext();) {
            RetraceEmitter emitter = iter.next();
            emitter.renderTick(timestamp, matrix, buffer);
            if (emitter.shouldRemove(timestamp)) {
                iter.remove();
            }
        }
        bufferSource.endBatch(RenderHelper.MITO_RETRACE);

    }

    public void add(Level level, long seed, Vec3 origin, float partialTicks) {
        // add retrace emitter to list to be rendered
        // each emitter should have lifespan and method to spawn it
        if (!level.isClientSide) {
            return;
        }
        for (int i = 0; i < 20; i++) { // make 1 lines
            // TODO: randomize start and ends
            Random random = new Random();
            double xOffset = random.nextDouble(1, 10 + 1) - 5;
            double zOffset = random.nextDouble(1, 10 + 1) - 5;
            Vec3 start = origin.add(xOffset,3,zOffset);
            xOffset = random.nextDouble(1, 10 + 1) - 5;
            zOffset = random.nextDouble(1, 10 + 1) - 5;
            System.out.println("x: "+xOffset+" z: "+zOffset);
            Vec3 end = origin.add(xOffset,-1,zOffset);
            Timestamp makeTimeStamp = new Timestamp(level.getGameTime(), partialTicks);
            var emitter = new RetraceEmitter(new MitoRetraceOptions(start, end), makeTimeStamp);
            retraceEmitters.add(emitter);
        }
    }

    public class RetraceEmitter {

        private final Vec3 lineStart;
        private final Vec3 lineEnd;
        private final int lifeSpan;

        private Timestamp createTime;
        private MitoRetraceOptions options;

        public RetraceEmitter(MitoRetraceOptions options, Timestamp createTime) {
            this.lineStart = options.start;
            this.lineEnd = options.end;
            this.lifeSpan = options.lifespan;
            this.createTime = createTime;
            this.options = options;
        }

        public void renderTick(Timestamp timestamp, Matrix4f matrix, VertexConsumer buffer) {
            buffer.vertex(matrix, (float) lineStart.x, (float) lineStart.y, (float) lineStart.z)
                    .color(0, 80, 80, 200)
                    .endVertex();
            buffer.vertex(matrix, (float) lineEnd.x, (float) lineEnd.y, (float) lineEnd.z)
                    .color(0, 80, 80, 200)
                    .endVertex();
        }

        public boolean shouldRemove(Timestamp currTime) {
            return currTime.ticks > createTime.ticks + lifeSpan;
        }
    }

    private static class Timestamp {

        public static final Timestamp ZERO = new Timestamp(0, 0);
        private final long ticks;
        private final float partial;

        public Timestamp(long ticks, float partial) {
            this.ticks = ticks;
            this.partial = partial;
        }

        public Timestamp subtract(Timestamp other) {
            long newTicks = ticks - other.ticks;
            float newPartial = partial - other.partial;
            if (newPartial < 0) {
                newPartial += 1;
                newTicks -= 1;
            }
            return new Timestamp(newTicks, newPartial);
        }

        public float value() {
            return ticks + partial;
        }

        public boolean isPassed(Timestamp prev, double duration) {
            long ticksPassed = ticks - prev.ticks;
            if (ticksPassed > duration) {
                return true;
            }
            duration -= ticksPassed;
            if (duration >= 1) {
                return false;
            }
            return (partial - prev.partial) >= duration;
        }
    }
}
