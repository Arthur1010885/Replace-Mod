package com.autoreplace.client.config;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

/**
 * Client-side runtime config and selection state for AutoReplace.
 */
public final class AutoReplaceConfig {
	public static boolean enabled = false;
	public static boolean selectionMode = false;
	public static boolean onlySameBlock = true;
	public static boolean dropItems = true;

	public static @Nullable BlockPos pos1 = null;
	public static @Nullable BlockPos pos2 = null;

	/** Prevent recursive break storms while mass-breaking. */
	public static boolean isMassBreaking = false;

	private AutoReplaceConfig() {
	}

	public static void clearSelection() {
		pos1 = null;
		pos2 = null;
	}

	public static boolean hasCompleteSelection() {
		return pos1 != null && pos2 != null;
	}

	public static boolean isInsideSelection(BlockPos pos) {
		if (!hasCompleteSelection()) {
			return false;
		}
		int minX = Math.min(pos1.getX(), pos2.getX());
		int minY = Math.min(pos1.getY(), pos2.getY());
		int minZ = Math.min(pos1.getZ(), pos2.getZ());
		int maxX = Math.max(pos1.getX(), pos2.getX());
		int maxY = Math.max(pos1.getY(), pos2.getY());
		int maxZ = Math.max(pos1.getZ(), pos2.getZ());
		return pos.getX() >= minX && pos.getX() <= maxX
				&& pos.getY() >= minY && pos.getY() <= maxY
				&& pos.getZ() >= minZ && pos.getZ() <= maxZ;
	}

	public static int getSelectedVolume() {
		if (!hasCompleteSelection()) {
			return 0;
		}
		int dx = Math.abs(pos1.getX() - pos2.getX()) + 1;
		int dy = Math.abs(pos1.getY() - pos2.getY()) + 1;
		int dz = Math.abs(pos1.getZ() - pos2.getZ()) + 1;
		return dx * dy * dz;
	}

	public static BlockPos getMin() {
		return new BlockPos(
				Math.min(pos1.getX(), pos2.getX()),
				Math.min(pos1.getY(), pos2.getY()),
				Math.min(pos1.getZ(), pos2.getZ())
		);
	}

	public static BlockPos getMax() {
		return new BlockPos(
				Math.max(pos1.getX(), pos2.getX()),
				Math.max(pos1.getY(), pos2.getY()),
				Math.max(pos1.getZ(), pos2.getZ())
		);
	}

	public static boolean matchesFilter(BlockState broken, BlockState candidate) {
		if (!onlySameBlock) {
			return !candidate.isAir();
		}
		Block brokenBlock = broken.getBlock();
		return candidate.getBlock() == brokenBlock;
	}
}
