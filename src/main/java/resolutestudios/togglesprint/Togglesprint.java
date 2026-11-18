package resolutestudios.togglesprint;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Togglesprint implements ClientModInitializer {
	public static final String MOD_ID = "togglesprint";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final KeyMapping toggleKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
			"key.togglesprint.toggle",
			InputConstants.KEY_LCONTROL,
			new KeyMapping.Category(Identifier.tryParse("togglesprint:togglesprint"))
	));

	@Override
	public void onInitializeClient() {
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (toggleKey.consumeClick()) {
				SprintManager.toggle();
			}
		});

		LOGGER.info("[Togglesprint] Has been initialized!");
	}
}
