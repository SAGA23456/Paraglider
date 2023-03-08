package tictim.paraglider.contents;

import com.mojang.serialization.Codec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tictim.paraglider.contents.block.GoddessStatueBlock;
import tictim.paraglider.contents.block.HornedStatueBlock;
import tictim.paraglider.contents.item.AntiVesselItem;
import tictim.paraglider.contents.item.EssenceItem;
import tictim.paraglider.contents.item.HeartContainerItem;
import tictim.paraglider.contents.item.ParagliderItem;
import tictim.paraglider.contents.item.SpiritOrbItem;
import tictim.paraglider.contents.item.StaminaVesselItem;
import tictim.paraglider.contents.loot.ParagliderLoot;
import tictim.paraglider.contents.loot.SpiritOrbLoot;
import tictim.paraglider.contents.loot.VesselLoot;
import tictim.paraglider.contents.recipe.CosmeticRecipe;
import tictim.paraglider.contents.recipe.bargain.SimpleStatueBargain;
import tictim.paraglider.contents.recipe.bargain.StatueBargain;
import tictim.paraglider.contents.recipe.bargain.StatueBargainContainer;
import tictim.paraglider.contents.worldgen.NetherHornedStatue;
import tictim.paraglider.contents.worldgen.TarreyTownGoddessStatue;
import tictim.paraglider.contents.worldgen.UndergroundHornedStatue;

import java.util.ArrayList;
import java.util.List;

import static net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD;
import static tictim.paraglider.ParagliderMod.MODID;

@EventBusSubscriber(modid = MODID, bus = MOD)
public final class Contents{
	private Contents(){}

	public static CreativeModeTab GROUP;

	@SubscribeEvent
	public static void onCreativeModeTabRegister(CreativeModeTabEvent.Register event){
		GROUP = event.registerCreativeModeTab(new ResourceLocation(MODID, MODID), b -> b
				.icon(() -> new ItemStack(PARAGLIDER.get()))
				.title(Component.translatable("itemGroup."+MODID))
				.displayItems((features, out, hasOp) -> {
					out.accept(PARAGLIDER.get());
					out.accept(DEKU_LEAF.get());
					out.accept(HEART_CONTAINER.get());
					out.accept(STAMINA_VESSEL.get());
					out.accept(SPIRIT_ORB.get());
					out.accept(ANTI_VESSEL.get());
					out.accept(ESSENCE.get());
					out.accept(GODDESS_STATUE.get());
					out.accept(KAKARIKO_GODDESS_STATUE.get());
					out.accept(GORON_GODDESS_STATUE.get());
					out.accept(RITO_GODDESS_STATUE.get());
					out.accept(HORNED_STATUE.get());
				}));
	}

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
	public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MODID);
	public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);
	public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOTS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MODID);
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MODID);
	public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, MODID);
	public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, MODID);
	public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registries.STRUCTURE_TYPE, MODID);
	public static final DeferredRegister<StructurePieceType> PIECES = DeferredRegister.create(Registries.STRUCTURE_PIECE, MODID);

	public static final RegistryObject<RecipeType<StatueBargain>> STATUE_BARGAIN_RECIPE_TYPE = RECIPE_TYPES.register("statue_bargain", () -> RecipeType.simple(new ResourceLocation(MODID, "statue_bargain")));

	private static BlockBehaviour.Properties statueProperties(){
		return Block.Properties.of(Material.STONE)
				.sound(SoundType.STONE)
				.requiresCorrectToolForDrops()
				.strength(1.5f, 100f)
				.noOcclusion();
	}

	public static final RegistryObject<Block> GODDESS_STATUE = BLOCKS.register("goddess_statue",
			() -> new GoddessStatueBlock(statueProperties()));
	public static final RegistryObject<Block> KAKARIKO_GODDESS_STATUE = BLOCKS.register("kakariko_goddess_statue",
			() -> new GoddessStatueBlock(statueProperties(),
					Component.translatable("tooltip.paraglider.kakariko_goddess_statue.0")
							.setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY))));
	public static final RegistryObject<Block> GORON_GODDESS_STATUE = BLOCKS.register("goron_goddess_statue",
			() -> new GoddessStatueBlock(statueProperties().lightLevel(value -> 15),
					Component.translatable("tooltip.paraglider.goron_goddess_statue.0")
							.setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY))));
	public static final RegistryObject<Block> RITO_GODDESS_STATUE = BLOCKS.register("rito_goddess_statue",
			() -> new GoddessStatueBlock(statueProperties(),
					Component.translatable("tooltip.paraglider.rito_goddess_statue.0")
							.setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY))));
	public static final RegistryObject<Block> HORNED_STATUE = BLOCKS.register("horned_statue",
			() -> new HornedStatueBlock(statueProperties()));

	public static final RegistryObject<ParagliderItem> PARAGLIDER = ITEMS.register("paraglider", () -> new ParagliderItem(0xA65955));
	public static final RegistryObject<ParagliderItem> DEKU_LEAF = ITEMS.register("deku_leaf", () -> new ParagliderItem(0x3FB53F));
	public static final RegistryObject<Item> HEART_CONTAINER = ITEMS.register("heart_container", HeartContainerItem::new);
	public static final RegistryObject<Item> STAMINA_VESSEL = ITEMS.register("stamina_vessel", StaminaVesselItem::new);
	public static final RegistryObject<Item> SPIRIT_ORB = ITEMS.register("spirit_orb", () -> new SpiritOrbItem(new Item.Properties().rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> ANTI_VESSEL = ITEMS.register("anti_vessel", () -> new AntiVesselItem(new Item.Properties().rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> ESSENCE = ITEMS.register("essence", () -> new EssenceItem(new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<BlockItem> GODDESS_STATUE_ITEM = ITEMS.register("goddess_statue", () -> new BlockItem(GODDESS_STATUE.get(),
			new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<BlockItem> KAKARIKO_GODDESS_STATUE_ITEM = ITEMS.register("kakariko_goddess_statue", () -> new BlockItem(KAKARIKO_GODDESS_STATUE.get(),
			new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<BlockItem> GORON_GODDESS_STATUE_ITEM = ITEMS.register("goron_goddess_statue", () -> new BlockItem(GORON_GODDESS_STATUE.get(),
			new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<BlockItem> RITO_GODDESS_STATUE_ITEM = ITEMS.register("rito_goddess_statue", () -> new BlockItem(RITO_GODDESS_STATUE.get(),
			new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<BlockItem> HORNED_STATUE_ITEM = ITEMS.register("horned_statue", () -> new BlockItem(HORNED_STATUE.get(),
			new Item.Properties().rarity(Rarity.EPIC)));

	public static final RegistryObject<MobEffect> EXHAUSTED = EFFECTS.register("exhausted", () -> new MobEffect(MobEffectCategory.HARMFUL, 5926017){
		@Override public List<ItemStack> getCurativeItems(){
			return new ArrayList<>();
		}
	}.addAttributeModifier(Attributes.MOVEMENT_SPEED, "65ed2ca4-ceb3-4521-8552-73006dcba58d", -0.30, AttributeModifier.Operation.MULTIPLY_TOTAL)); // Slowness color

	public static final RegistryObject<CosmeticRecipe.Serializer> COSMETIC_RECIPE = RECIPE_SERIALIZERS.register("cosmetic", CosmeticRecipe.Serializer::new);
	public static final RegistryObject<SimpleStatueBargain.Serializer> STATUE_BARGAIN_RECIPE = RECIPE_SERIALIZERS.register("statue_bargain", SimpleStatueBargain.Serializer::new);

	public static final RegistryObject<MenuType<StatueBargainContainer>> GODDESS_STATUE_CONTAINER = MENUS.register(
			"goddess_statue", () -> new MenuType<>(ModContainers::goddessStatue));
	public static final RegistryObject<MenuType<StatueBargainContainer>> HORNED_STATUE_CONTAINER = MENUS.register(
			"horned_statue", () -> new MenuType<>(ModContainers::hornedStatue));

	public static final RegistryObject<Codec<ParagliderLoot>> PARAGLIDER_LOOT = LOOTS.register("paraglider", () -> ParagliderLoot.CODEC);
	public static final RegistryObject<Codec<SpiritOrbLoot>> SPIRIT_ORB_LOOT = LOOTS.register("spirit_orb", () -> SpiritOrbLoot.CODEC);
	public static final RegistryObject<Codec<VesselLoot>> VESSEL_LOOT = LOOTS.register("vessel", () -> VesselLoot.CODEC);

	public static final RegistryObject<Attribute> MAX_STAMINA = ATTRIBUTES.register("max_stamina", () -> new RangedAttribute("max_stamina", 0, 0, Double.MAX_VALUE).setSyncable(true));

	public static final RegistryObject<StructureType<UndergroundHornedStatue>> UNDERGROUND_HORNED_STATUE = structureType("underground_horned_statue", UndergroundHornedStatue.CODEC);
	public static final RegistryObject<StructureType<NetherHornedStatue>> NETHER_HORNED_STATUE = structureType("nether_horned_statue", NetherHornedStatue.CODEC);
	public static final RegistryObject<StructureType<TarreyTownGoddessStatue>> TARREY_TOWN_GODDESS_STATUE = structureType("tarrey_town_goddess_statue", TarreyTownGoddessStatue.CODEC);

	private static <T extends Structure> RegistryObject<StructureType<T>> structureType(String id, Codec<T> codec){
		return STRUCTURE_TYPES.register(id, () -> (StructureType<T>)(() -> codec));
	}

	public static final RegistryObject<StructurePieceType> TARREY_TOWN_GODDESS_STATUE_PIECE = PIECES.register("tarrey_town_goddess_statue", TarreyTownGoddessStatue::pieceType);
	public static final RegistryObject<StructurePieceType> NETHER_HORNED_STATUE_PIECE = PIECES.register("nether_horned_statue", NetherHornedStatue::pieceType);
	public static final RegistryObject<StructurePieceType> UNDERGROUND_HORNED_STATUE_PIECE = PIECES.register("underground_horned_statue", UndergroundHornedStatue::pieceType);

	public static void registerEventHandlers(IEventBus eventBus){
		BLOCKS.register(eventBus);
		ITEMS.register(eventBus);
		EFFECTS.register(eventBus);
		MENUS.register(eventBus);
		LOOTS.register(eventBus);
		RECIPE_SERIALIZERS.register(eventBus);
		ATTRIBUTES.register(eventBus);
		RECIPE_TYPES.register(eventBus);
		STRUCTURE_TYPES.register(eventBus);
		PIECES.register(eventBus);
	}
}
