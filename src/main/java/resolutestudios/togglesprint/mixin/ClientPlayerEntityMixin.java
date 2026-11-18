package resolutestudios.togglesprint.mixin;

import net.minecraft.client.player.ClientInput;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import resolutestudios.togglesprint.SprintManager;
import net.minecraft.world.effect.MobEffects;

@Mixin(LocalPlayer.class)
public abstract class ClientPlayerEntityMixin {
	@Shadow
	public ClientInput input;

	@Inject(at = @At("HEAD"), method = "aiStep")
	private void onTickMovement(CallbackInfo info) {
		if (SprintManager.toggleActive) {
			LocalPlayer player = (LocalPlayer) (Object) this;

			// Anticheat Compatibility & Realistic Behavior:
			// We mimic vanilla "Hold Sprint" behavior but only when it is safe to do so.
			// This prevents the mod from forcing a sprint state while hitting a wall or taking damage,
			// which would otherwise cause a "packet storm" (rapid start/stop packets) flagged by anticheats.

			boolean isMovingForward = this.input.hasForwardImpulse();
			boolean canSprint = player.getFoodData().getFoodLevel() > 6 || player.getAbilities().instabuild;
			boolean isNotObstructed = !player.horizontalCollision;
			boolean isNotDamaged = player.hurtTime <= 0;
			boolean isNotSneaking = !player.isShiftKeyDown();
			boolean isNotBlind = !player.hasEffect(MobEffects.BLINDNESS);

			if (isMovingForward && canSprint && isNotObstructed && isNotDamaged && isNotSneaking && isNotBlind) {
                // Testing sprint field (if sprinting failed)
				player.setSprinting(true);
			}
		}
	}
}
