package com.autoreplace.client.gui;

import com.autoreplace.client.config.AutoReplaceConfig;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/**
 * Simple config menu opened with Z.
 */
public class AutoReplaceScreen extends Screen {
	private static final int BUTTON_WIDTH = 180;
	private static final int BUTTON_HEIGHT = 20;
	private static final int SPACING = 24;

	public AutoReplaceScreen() {
		super(Component.translatable("autoreplace.menu.title"));
	}

	@Override
	protected void init() {
		int centerX = this.width / 2;
		int startY = this.height / 2 - 70;

		this.addRenderableWidget(Button.builder(
				enableLabel(),
				btn -> {
					AutoReplaceConfig.enabled = !AutoReplaceConfig.enabled;
					btn.setMessage(enableLabel());
					if (this.minecraft != null && this.minecraft.player != null) {
						this.minecraft.player.sendOverlayMessage(
								Component.translatable(AutoReplaceConfig.enabled
										? "autoreplace.status.enabled"
										: "autoreplace.status.disabled")
						);
					}
				}
		).bounds(centerX - BUTTON_WIDTH / 2, startY, BUTTON_WIDTH, BUTTON_HEIGHT).build());

		this.addRenderableWidget(Button.builder(
				selectLabel(),
				btn -> {
					AutoReplaceConfig.selectionMode = !AutoReplaceConfig.selectionMode;
					btn.setMessage(selectLabel());
					if (this.minecraft != null && this.minecraft.player != null) {
						this.minecraft.player.sendOverlayMessage(
								Component.translatable(AutoReplaceConfig.selectionMode
										? "autoreplace.status.select_mode_on"
										: "autoreplace.status.select_mode_off")
						);
					}
				}
		).bounds(centerX - BUTTON_WIDTH / 2, startY + SPACING, BUTTON_WIDTH, BUTTON_HEIGHT).build());

		this.addRenderableWidget(Button.builder(
				onlySameLabel(),
				btn -> {
					AutoReplaceConfig.onlySameBlock = !AutoReplaceConfig.onlySameBlock;
					btn.setMessage(onlySameLabel());
				}
		).bounds(centerX - BUTTON_WIDTH / 2, startY + SPACING * 2, BUTTON_WIDTH, BUTTON_HEIGHT).build());

		this.addRenderableWidget(Button.builder(
				dropItemsLabel(),
				btn -> {
					AutoReplaceConfig.dropItems = !AutoReplaceConfig.dropItems;
					btn.setMessage(dropItemsLabel());
				}
		).bounds(centerX - BUTTON_WIDTH / 2, startY + SPACING * 3, BUTTON_WIDTH, BUTTON_HEIGHT).build());

		this.addRenderableWidget(Button.builder(
				Component.translatable("autoreplace.menu.clear"),
				btn -> {
					AutoReplaceConfig.clearSelection();
					if (this.minecraft != null && this.minecraft.player != null) {
						this.minecraft.player.sendOverlayMessage(
								Component.translatable("autoreplace.status.cleared")
						);
					}
				}
		).bounds(centerX - BUTTON_WIDTH / 2, startY + SPACING * 4, BUTTON_WIDTH, BUTTON_HEIGHT).build());

		this.addRenderableWidget(Button.builder(
				Component.translatable("autoreplace.menu.close"),
				btn -> this.onClose()
		).bounds(centerX - BUTTON_WIDTH / 2, startY + SPACING * 5, BUTTON_WIDTH, BUTTON_HEIGHT).build());
	}

	private static Component enableLabel() {
		return Component.translatable(AutoReplaceConfig.enabled
				? "autoreplace.menu.enabled"
				: "autoreplace.menu.disabled");
	}

	private static Component selectLabel() {
		return Component.translatable("autoreplace.menu.select")
				.append(Component.literal(AutoReplaceConfig.selectionMode ? " [ON]" : " [OFF]"));
	}

	private static Component onlySameLabel() {
		return Component.translatable(AutoReplaceConfig.onlySameBlock
				? "autoreplace.menu.only_same"
				: "autoreplace.menu.only_same_off");
	}

	private static Component dropItemsLabel() {
		return Component.translatable(AutoReplaceConfig.dropItems
				? "autoreplace.menu.drop_items"
				: "autoreplace.menu.drop_items_off");
	}

	@Override
	public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
		super.extractRenderState(graphics, mouseX, mouseY, partialTick);

		graphics.centeredText(this.font, this.title, this.width / 2, this.height / 2 - 95, 0xFFFFFF);

		String selectionInfo;
		if (AutoReplaceConfig.hasCompleteSelection()) {
			selectionInfo = "Area: " + AutoReplaceConfig.getSelectedVolume() + " blocos";
		} else if (AutoReplaceConfig.pos1 != null) {
			selectionInfo = "Pos1: " + formatPos(AutoReplaceConfig.pos1) + " | clique o 2o canto";
		} else {
			selectionInfo = "Nenhuma area selecionada";
		}
		graphics.centeredText(this.font, selectionInfo, this.width / 2, this.height / 2 + 80, 0xAAAAAA);

		String status = "Status: " + (AutoReplaceConfig.enabled ? "ATIVO" : "INATIVO")
				+ " | Selecao: " + (AutoReplaceConfig.selectionMode ? "ON" : "OFF");
		graphics.centeredText(this.font, status, this.width / 2, this.height / 2 + 94, 0x55FF55);
	}

	private static String formatPos(net.minecraft.core.BlockPos pos) {
		return pos.getX() + ", " + pos.getY() + ", " + pos.getZ();
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}
}
