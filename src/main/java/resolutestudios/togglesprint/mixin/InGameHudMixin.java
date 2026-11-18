package resolutestudios.togglesprint.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import resolutestudios.togglesprint.SprintManager;

@Mixin(Hud.class)
public class InGameHudMixin {
	@Inject(at = @At("TAIL"), method = "extractPlayerHealth")
	private void renderOverlay(GuiGraphicsExtractor context, CallbackInfo info) {
		int x = 2;
		int y = 2;

		String key = SprintManager.toggleActive ? "text.togglesprint.enabled" : "text.togglesprint.disabled";
		context.text(Minecraft.getInstance().font, Component.translatable(key), x, y, 0xFFFFFFFF, true);
	}
}
