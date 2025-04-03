package hibi.blahaj;

import com.mojang.serialization.*;
import net.minecraft.component.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import org.jetbrains.annotations.*;

import java.util.function.*;

public class OwnerComponent implements TooltipAppender {

	public static final Codec<OwnerComponent> CODEC = TextCodecs.CODEC.xmap(OwnerComponent::new, OwnerComponent::getOwnerName);
	public static final PacketCodec<RegistryByteBuf, OwnerComponent> PACKET_CODEC = TextCodecs.REGISTRY_PACKET_CODEC.xmap(OwnerComponent::new, OwnerComponent::getOwnerName);

	final Text ownerName;

	public OwnerComponent(Text ownerName) {
		this.ownerName = ownerName;
	}

	private Text getOwnerName() {
		return this.ownerName;
	}

	@Override
	public void appendTooltip(Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type, ComponentsAccess components) {
		@Nullable OwnerComponent owner = components.get(BlahajDataComponentTypes.OWNER);
		if (owner != null) {
			@Nullable Text customName = components.get(DataComponentTypes.CUSTOM_NAME);
			if (customName == null) {
				textConsumer.accept(Text.translatable("tooltip.blahaj.owner.craft", owner.getOwnerName()).formatted(Formatting.GRAY));
			} else {
				textConsumer.accept(Text.translatable("tooltip.blahaj.owner.rename", customName, owner.getOwnerName()).formatted(Formatting.GRAY));
			}
		}
	}

}
