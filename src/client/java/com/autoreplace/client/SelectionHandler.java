package com.autoreplace.client;

import com.autoreplace.client.config.AutoReplaceConfig;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

/**
 * Handles Litematica-style corner selection with right-click.
 */
public final class SelectionHandler {
	private SelectionHandler() {
	}

	public static void register() {
		UseBlockCallback.EVENT.register(SelectionHandler::onUseBlock);
	}

	private static InteractionResult onUseBlock(Player player, Level level, InteractionHand hand, BlockHitResult hit) {
		if (!level.isClientSide() || hand != InteractionHand.MAIN_HAND) {
			return InteractionResult.PASS;
		}
		if (!AutoReplaceConfig.selectionMode) {
			return InteractionResult.PASS;
		}
		if (player.isSpectator()) {
			return InteractionResult.PASS;
		}

		BlockPos pos = hit.getBlockPos();

		if (AutoReplaceConfig.pos1 == null || AutoReplaceConfig.pos2 != null) {
			// Start a new selection
			AutoReplaceConfig.pos1 = pos.immutable();
			AutoReplaceConfig.pos2 = null;
			player.sendOverlayMessage(
					Component.translatable("autoreplace.status.pos1", format(pos))
			);
		} else {
			// Set second corner
			AutoReplaceConfig.pos2 = pos.immutable();
			int volume = AutoReplaceConfig.getSelectedVolume();
			player.sendOverlayMessage(
					Component.translatable("autoreplace.status.pos2", format(pos), String.valueOf(volume))
			);
			// Exit selection mode after completing the box
			AutoReplaceConfig.selectionMode = false;
		}

		// Consume so we don't place/use the held item while selecting
		return InteractionResult.SUCCESS;
	}

	private static String format(BlockPos pos) {
		return pos.getX() + ", " + pos.getY() + ", " + pos.getZ();
	}
}
