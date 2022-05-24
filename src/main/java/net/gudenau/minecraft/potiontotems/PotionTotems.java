package net.gudenau.minecraft.potiontotems;

import net.fabricmc.api.ModInitializer;
import net.gudenau.minecraft.potiontotems.recipe.PotionTotemRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;

public class PotionTotems implements ModInitializer {
	public static final String MOD_ID = "potion_totems";
	
	@Override
	public void onInitialize() {
		RecipeSerializers.init();
	}
	
	public static @NotNull Identifier identify(@NotNull String path) {
		return new Identifier(MOD_ID, path);
	}
	
	public static final class RecipeSerializers {
		public static final SpecialRecipeSerializer<PotionTotemRecipe> POTION_TOTEM = new SpecialRecipeSerializer<>(PotionTotemRecipe::new);
		
		private static void init() {
			register("potion_totem", POTION_TOTEM);
		}
		
		private static void register(String id, RecipeSerializer<?> serializer) {
			Registry.register(Registry.RECIPE_SERIALIZER, identify(id), serializer);
		}
	}
}
