package hibi.blahaj.block;

import hibi.blahaj.*;
import net.minecraft.block.*;
import net.minecraft.component.type.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import eu.pb4.factorytools.api.item.FactoryBlockItem;
import eu.pb4.polymer.core.api.item.PolymerItemUtils;
import net.minecraft.item.tooltip.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import eu.pb4.polymer.core.api.block.PolymerBlock;
import org.jetbrains.annotations.*;

import java.util.function.*;

public class CuddlyItem extends FactoryBlockItem {

	private final Text tooltip;

	public <T extends Block & PolymerBlock> CuddlyItem(Block block, Settings settings, String tooltip) {
		super((T) block, settings);
		this.tooltip = tooltip == null ? null : Text.translatable(tooltip).formatted(Formatting.GRAY);
		PolymerItemUtils.enableStonecutterFix();
	}

	@Override
	public void onCraftByPlayer(ItemStack stack, World world, PlayerEntity player) {
		super.onCraftByPlayer(stack, world, player);
		if (player != null) { // compensate for auto-crafter mods
			stack.set(BlahajDataComponentTypes.OWNER, player.getName());
		}
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
		super.appendTooltip(stack, context, displayComponent, textConsumer, type);

		if (this.tooltip != null) {
			textConsumer.accept(this.tooltip);
		}

		@Nullable
		Text ownerName = stack.get(BlahajDataComponentTypes.OWNER);
		// Text ownerName = Text.of("Default");
		if (ownerName != null) {
			@Nullable
			Text customName = stack.get(DataComponentTypes.CUSTOM_NAME);
			if (customName == null) {
				tooltip.add(Text.translatable("tooltip.blahaj.owner.craft", ownerName).formatted(Formatting.GRAY));
			} else {
				tooltip.add(Text.translatable("tooltip.blahaj.owner.rename", customName, ownerName)
						.formatted(Formatting.GRAY));
			}
		}
	}

	public static final Identifier MINING_SPEED_MODIFIER_ID = Identifier.of(Blahaj.MOD_ID, "base_attack_damage");

	public static AttributeModifiersComponent createAttributeModifiers() {
		return AttributeModifiersComponent.builder()
				.add(EntityAttributes.BLOCK_BREAK_SPEED,
						new EntityAttributeModifier(MINING_SPEED_MODIFIER_ID, -3.0,
								EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
						AttributeModifierSlot.MAINHAND)
				.add(EntityAttributes.ATTACK_DAMAGE,
						new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, -2.0,
								EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
						AttributeModifierSlot.MAINHAND)
				.build();
	}

}
