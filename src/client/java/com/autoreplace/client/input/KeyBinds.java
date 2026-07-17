package com.autoreplace.client.input;

import com.autoreplace.AutoReplace;
import com.autoreplace.client.gui.AutoReplaceScreen;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public final class KeyBinds {
	private static KeyMapping openMenuKey;
	private static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(
			Identifier.fromNamespaceAndPath(AutoReplace.MOD_ID, "main")
	);

	private KeyBinds() {
	}

	public static void register() {
		openMenuKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
				"key.autoreplace.open_menu",
				InputConstants.Type.KEYSYM,
				GLFW.GLFW_KEY_Z,
				CATEGORY
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (openMenuKey.consumeClick()) {
				if (client.player != null) {
					client.gui.setScreen(new AutoReplaceScreen());
				}
			}
		});
	}
}
