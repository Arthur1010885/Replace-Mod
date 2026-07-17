package com.autoreplace.client;

import com.autoreplace.client.config.AutoReplaceConfig;
import net.fabricmc.fabric.api.event.client.player.ClientPlayerBlockBreakEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

/**
 * When enabled, breaking one block inside the selection breaks all matching blocks in the area.
 */
public final class BreakHandler {
	/** Safety cap so huge selections don't freeze the client. */
	private static final int MAX_BLOCKS_PER_BREAK = 4096;

	private BreakHandler() {
	}

	public static void register() {
		ClientPlayerBlockBreakEvents.AFTER.register(BreakHandler::onBlockBroken);
	}

	private static void onBlockBroken(ClientLevel level, LocalPlayer player, BlockPos pos, BlockState state) {
		if (!AutoReplaceConfig.enabled) {
			return;
		}
		if (AutoReplaceConfig.isMassBreaking) {
			return;
		}
		if (!AutoReplaceConfig.hasCompleteSelection()) {
			return;
		}
		if (!AutoReplaceConfig.isInsideSelection(pos)) {
			return;
		}
		if (state.isAir()) {
			return;
		}

		Minecraft client = Minecraft.getInstance();
		MultiPlayerGameMode gameMode = client.gameMode;
		if (gameMode == null) {
			return;
		}

		BlockPos min = AutoReplaceConfig.getMin();
		BlockPos max = AutoReplaceConfig.getMax();

		List<BlockPos> toBreak = new ArrayList<>();
		for (int x = min.getX(); x <= max.getX(); x++) {
			for (int y = min.getY(); y <= max.getY(); y++) {
				for (int z = min.getZ(); z <= max.getZ(); z++) {
					BlockPos target = new BlockPos(x, y, z);
					if (target.equals(pos)) {
						continue;
					}
					BlockState targetState = level.getBlockState(target);
					if (targetState.isAir()) {
						continue;
					}
					if (!AutoReplaceConfig.matchesFilter(state, targetState)) {
						continue;
					}
					// Skip unbreakable blocks (bedrock, barriers, etc.)
					if (targetState.getDestroySpeed(level, target) < 0) {
						continue;
					}
					toBreak.add(target);
					if (toBreak.size() >= MAX_BLOCKS_PER_BREAK) {
						break;
					}
				}
				if (toBreak.size() >= MAX_BLOCKS_PER_BREAK) {
					break;
				}
			}
			if (toBreak.size() >= MAX_BLOCKS_PER_BREAK) {
				break;
			}
		}

		if (toBreak.isEmpty()) {
			return;
		}

		player.sendOverlayMessage(
				Component.translatable("autoreplace.status.breaking", String.valueOf(toBreak.size()))
		);

		AutoReplaceConfig.isMassBreaking = true;
		int broken = 0;
		try {
			for (BlockPos target : toBreak) {
				// Re-check in case state changed
				BlockState current = level.getBlockState(target);
				if (current.isAir()) {
					continue;
				}
				if (!AutoReplaceConfig.matchesFilter(state, current)) {
					continue;
				}
				if (gameMode.destroyBlock(target)) {
					broken++;
				}
			}
		} finally {
			AutoReplaceConfig.isMassBreaking = false;
		}

		player.sendOverlayMessage(
				Component.translatable("autoreplace.status.done", String.valueOf(broken))
		);
	}
}
