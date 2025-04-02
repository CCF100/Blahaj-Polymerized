package hibi.blahaj;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import hibi.blahaj.block.BlahajBlocks;
import hibi.blahaj.sound.BlahajSoundEvents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;

@Mod(Blahaj.MOD_ID)
public class Blahaj {

	public static final String MOD_ID = "blahaj";
	public static final Logger LOGGER = LoggerFactory.getLogger("Blahaj");

	public Blahaj(IEventBus modEventBus, ModContainer modContainer) {
		BlahajDataComponentTypes.register(modEventBus);
		BlahajBlocks.register(modEventBus);
		BlahajSoundEvents.init();

		NeoForge.EVENT_BUS.addListener(this::onTradesLoad);
		NeoForge.EVENT_BUS.addListener(this::onLootTableLoad);
	}

	private void onLootTableLoad(LootTableLoadEvent event) {
		ResourceLocation key = event.getName();
		LootTable table = event.getTable();
		if(key.equals(BuiltInLootTables.STRONGHOLD_CROSSING.location()) || key.equals(BuiltInLootTables.STRONGHOLD_CORRIDOR.location())) {
			LootPool.Builder pb = LootPool.lootPool()
					.add(LootItem.lootTableItem(BlahajBlocks.GRAY_SHARK_ITEM.get()).setWeight(5))
					.add(LootItem.lootTableItem(Items.AIR).setWeight(100));
			table.addPool(pb.build());
		} else if(key.equals(BuiltInLootTables.VILLAGE_PLAINS_HOUSE.location())) {
			LootPool.Builder pb = LootPool.lootPool()
					.add(LootItem.lootTableItem(BlahajBlocks.GRAY_SHARK_ITEM.get()))
					.add(LootItem.lootTableItem(Items.AIR).setWeight(43));
			table.addPool(pb.build());
		} else if(key.equals(BuiltInLootTables.VILLAGE_TAIGA_HOUSE.location()) || key.equals(BuiltInLootTables.VILLAGE_SNOWY_HOUSE.location())) {
			LootPool.Builder pb = LootPool.lootPool()
					.add(LootItem.lootTableItem(BlahajBlocks.GRAY_SHARK_ITEM.get()).setWeight(5))
					.add(LootItem.lootTableItem(Items.AIR).setWeight(54));
			table.addPool(pb.build());
		} else if(key.equals(BuiltInLootTables.FLETCHER_GIFT.location())
				|| key.equals(BuiltInLootTables.BUTCHER_GIFT.location())
				|| key.equals(BuiltInLootTables.LEATHERWORKER_GIFT.location())) {

			LootPool.Builder pb = LootPool.lootPool()
					.add(LootItem.lootTableItem(BlahajBlocks.BROWN_BEAR_ITEM.get()).setWeight(5))
					.add(LootItem.lootTableItem(Items.AIR).setWeight(25));
			table.addPool(pb.build());
		}
	}

	private void onTradesLoad(VillagerTradesEvent event) {
		if(event.getType() == VillagerProfession.SHEPHERD) {
			event.getTrades().computeIfAbsent(5, k -> NonNullList.create()).add((entity, random) -> new MerchantOffer(new ItemCost(Items.EMERALD, 15), new ItemStack(BlahajBlocks.GRAY_SHARK_ITEM), 2, 30, 0.1f));
		}
	}

}
