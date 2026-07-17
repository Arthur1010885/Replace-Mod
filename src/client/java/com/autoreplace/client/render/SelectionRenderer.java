package com.autoreplace.client.render;

import com.autoreplace.client.config.AutoReplaceConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.gizmos.GizmoStyle;
import net.minecraft.gizmos.Gizmos;
import net.minecraft.world.phys.AABB;

/**
 * Draws a Litematica-like selection box outline in the world using the Gizmos API.
 */
public final class SelectionRenderer {
	// ARGB cyan stroke
	private static final int COLOR_ENABLED = 0xFF33E5FF;
	private static final int COLOR_DISABLED = 0x9933E5FF;
	// Semi-transparent fill
	private static final int FILL_ENABLED = 0x3333E5FF;
	private static final int FILL_DISABLED = 0x2233E5FF;

	private SelectionRenderer() {
	}

	public static void register() {
		// Per-tick gizmos are collected by LevelExtractor and drawn each frame.
		ClientTickEvents.END_CLIENT_TICK.register(client -> render(client));
	}

	private static void render(Minecraft client) {
		if (AutoReplaceConfig.pos1 == null) {
			return;
		}
		if (client.level == null || client.player == null) {
			return;
		}

		BlockPos p1 = AutoReplaceConfig.pos1;
		BlockPos p2 = AutoReplaceConfig.pos2 != null ? AutoReplaceConfig.pos2 : p1;

		int minX = Math.min(p1.getX(), p2.getX());
		int minY = Math.min(p1.getY(), p2.getY());
		int minZ = Math.min(p1.getZ(), p2.getZ());
		int maxX = Math.max(p1.getX(), p2.getX()) + 1;
		int maxY = Math.max(p1.getY(), p2.getY()) + 1;
		int maxZ = Math.max(p1.getZ(), p2.getZ()) + 1;

		AABB box = new AABB(minX, minY, minZ, maxX, maxY, maxZ);

		int stroke = AutoReplaceConfig.enabled ? COLOR_ENABLED : COLOR_DISABLED;
		int fill = AutoReplaceConfig.enabled ? FILL_ENABLED : FILL_DISABLED;
		GizmoStyle style = GizmoStyle.strokeAndFill(stroke, 2.0f, fill);

		try (var ignored = client.collectPerTickGizmos()) {
			Gizmos.cuboid(box, style, true);
		}
	}
}
