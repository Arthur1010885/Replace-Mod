package com.autoreplace.client;

import com.autoreplace.AutoReplace;
import com.autoreplace.client.input.KeyBinds;
import com.autoreplace.client.render.SelectionRenderer;
import net.fabricmc.api.ClientModInitializer;

public class AutoReplaceClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		KeyBinds.register();
		SelectionHandler.register();
		BreakHandler.register();
		SelectionRenderer.register();
		AutoReplace.LOGGER.info("AutoReplace client ready! Press Z to open the menu.");
	}
}
