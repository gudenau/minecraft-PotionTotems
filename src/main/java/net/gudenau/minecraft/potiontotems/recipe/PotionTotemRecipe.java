package net.gudenau.minecraft.potiontotems.recipe;

import net.gudenau.minecraft.potiontotems.PotionTotems;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmithingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public final class PotionTotemRecipe extends SmithingRecipe {
    private final Identifier identifier;
    
    public PotionTotemRecipe(Identifier identifier) {
        super(identifier, null, null, null);
        this.identifier = identifier;
    }
    
    @Override
    public boolean matches(Inventory inventory, World world) {
        // return this.base.test(inventory.getStack(0)) && this.addition.test(inventory.getStack(1));
        var base = inventory.getStack(0);
        if(!base.isOf(Items.TOTEM_OF_UNDYING)) {
            return false;
        }
        if(!PotionUtil.getPotionEffects(base).isEmpty()) {
            return false;
        }
        
        var addition = inventory.getStack(1);
        if(PotionUtil.getPotionEffects(addition).isEmpty()) {
            return false;
        }
        
        return true;
    }
    
    @Override
    public ItemStack craft(Inventory inventory) {
        var stack = inventory.getStack(0).copy();
        PotionUtil.setCustomPotionEffects(stack, PotionUtil.getPotionEffects(inventory.getStack(1)));
        return stack;
    }
    
    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }
    
    @Override
    public ItemStack getOutput() {
        return new ItemStack(Items.TOTEM_OF_UNDYING);
    }
    
    @Override
    public Identifier getId() {
        return identifier;
    }
    
    @Override
    public RecipeSerializer<?> getSerializer() {
        return PotionTotems.RecipeSerializers.POTION_TOTEM;
    }
    
    @Override
    public RecipeType<?> getType() {
        return RecipeType.SMITHING;
    }
    
    @Override
    public boolean isEmpty() {
        return false;
    }
}
