package hibi.blahaj.block;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import java.util.*;

import static hibi.blahaj.Blahaj.*;

public class BlahajBlocks {

	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.createBlocks(MOD_ID);
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(MOD_ID);

	public static DeferredHolder<Block, CuddlyBlock> GRAY_SHARK_BLOCK = BLOCKS.register("gray_shark", () -> new CuddlyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LIGHT_GRAY_WOOL).noOcclusion()));
	public static DeferredHolder<Item, CuddlyItem> GRAY_SHARK_ITEM = ITEMS.register("gray_shark", () -> new CuddlyItem(GRAY_SHARK_BLOCK.get(), new Item.Properties().stacksTo(1).attributes(CuddlyItem.createAttributeModifiers()), "block.blahaj.gray_shark.tooltip"));
	public static DeferredHolder<Block, CuddlyBlock> BLAHAJ_BLOCK = BLOCKS.register("blue_shark", () -> new CuddlyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CYAN_WOOL)));
	public static DeferredHolder<Item, CuddlyItem> BLAHAJ_ITEM = ITEMS.register("blue_shark", () -> new CuddlyItem(BLAHAJ_BLOCK.get(), new Item.Properties().stacksTo(1).attributes(CuddlyItem.createAttributeModifiers()), "block.blahaj.blue_shark.tooltip"));
	public static DeferredHolder<Block, CuddlyBlock> BLAVINGAD_BLOCK = BLOCKS.register("blue_whale", () -> new CuddlyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BLUE_WOOL)));
	public static DeferredHolder<Item, CuddlyItem> BLAVINGAD_ITEM = ITEMS.register("blue_whale", () -> new CuddlyItem(BLAVINGAD_BLOCK.get(), new Item.Properties().stacksTo(1).attributes(CuddlyItem.createAttributeModifiers()), "block.blahaj.blue_whale.tooltip"));
	public static DeferredHolder<Block, CuddlyBlock> BREAD_BLOCK = BLOCKS.register("bread", () -> new CuddlyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ORANGE_WOOL)));
	public static DeferredHolder<Item, CuddlyItem> BREAD_ITEM = ITEMS.register("bread", () -> new CuddlyItem(BREAD_BLOCK.get(), new Item.Properties().stacksTo(1).attributes(CuddlyItem.createAttributeModifiers()), null));
	public static DeferredHolder<Block, CuddlyBlock> BROWN_BEAR_BLOCK = BLOCKS.register("brown_bear", () -> new CuddlyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_WOOL)));
	public static DeferredHolder<Item, CuddlyItem> BROWN_BEAR_ITEM = ITEMS.register("brown_bear", () -> new CuddlyItem(BROWN_BEAR_BLOCK.get(), new Item.Properties().stacksTo(1).attributes(CuddlyItem.createAttributeModifiers()), "block.blahaj.brown_bear.tooltip"));

	public static final List<String> PRIDE_NAMES = List.of(
		"ace", "agender", "aro", "aroace", "bi", "demiboy", "demigirl",
		"demi_r", "demi_s", "enby", "gay", "genderfluid", "genderqueer", "greyrose",
		"grey_r", "grey_s", "intersex", "lesbian", "pan", "poly", "pride", "trans");

	public static List<DeferredHolder<Block, CuddlyBlock>> PRIDE_BLOCKS = new ArrayList<>();
	public static List<DeferredHolder<Item, CuddlyItem>> PRIDE_ITEMS = new ArrayList<>();

	static {
		for (String name : PRIDE_NAMES) {
			DeferredHolder<Block, CuddlyBlock> block = BLOCKS.register(name + "_shark", () -> new CuddlyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_WOOL)));
			DeferredHolder<Item, CuddlyItem> item = ITEMS.register(name + "_shark", () -> new CuddlyItem(block.get(), new Item.Properties().stacksTo(1).attributes(CuddlyItem.createAttributeModifiers()), "block.blahaj.blue_shark.tooltip"));

			PRIDE_BLOCKS.add(block);
			PRIDE_ITEMS.add(item);
		}
	}

	public static void register(IEventBus eventBus) {
		eventBus.addListener(BuildCreativeModeTabContentsEvent.class, event -> {
			if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
				event.accept(new ItemStack(GRAY_SHARK_ITEM));
				event.accept(new ItemStack(BLAHAJ_ITEM));
				event.accept(new ItemStack(BLAVINGAD_ITEM));
				event.accept(new ItemStack(BREAD_ITEM));
				event.accept(new ItemStack(BROWN_BEAR_ITEM));

				for (DeferredHolder<Item, CuddlyItem> item : PRIDE_ITEMS) {
					event.accept(new ItemStack(item));
				}
			}
		});

		BLOCKS.register(eventBus);
		ITEMS.register(eventBus);

		eventBus.addListener(FMLClientSetupEvent.class, e -> e.enqueueWork(() -> {
			ItemBlockRenderTypes.setRenderLayer(GRAY_SHARK_BLOCK.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(BLAHAJ_BLOCK.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(BLAVINGAD_BLOCK.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(BREAD_BLOCK.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(BROWN_BEAR_BLOCK.get(), RenderType.cutout());

			for (DeferredHolder<Block, CuddlyBlock> block : PRIDE_BLOCKS) {
				ItemBlockRenderTypes.setRenderLayer(block.get(), RenderType.cutout());
			}
		}));
	}

}
