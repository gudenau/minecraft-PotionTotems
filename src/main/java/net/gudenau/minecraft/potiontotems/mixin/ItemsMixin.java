package net.gudenau.minecraft.potiontotems.mixin;

import net.gudenau.minecraft.potiontotems.item.PotionTotemItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Debug(export = true)
@Mixin(Items.class)
public abstract class ItemsMixin {
    @Shadow private static Item register(String id, Item item){return item;}
    
    /*
           L1010
        LINENUMBER 1213 L1010
        LDC "totem_of_undying"
        NEW net/minecraft/item/Item
        DUP
        NEW net/minecraft/item/Item$Settings
        DUP
        INVOKESPECIAL net/minecraft/item/Item$Settings.<init> ()V
        ICONST_1
        INVOKEVIRTUAL net/minecraft/item/Item$Settings.maxCount (I)Lnet/minecraft/item/Item$Settings;
        GETSTATIC net/minecraft/item/ItemGroup.COMBAT : Lnet/minecraft/item/ItemGroup;
        INVOKEVIRTUAL net/minecraft/item/Item$Settings.group (Lnet/minecraft/item/ItemGroup;)Lnet/minecraft/item/Item$Settings;
        GETSTATIC net/minecraft/util/Rarity.UNCOMMON : Lnet/minecraft/util/Rarity;
        INVOKEVIRTUAL net/minecraft/item/Item$Settings.rarity (Lnet/minecraft/util/Rarity;)Lnet/minecraft/item/Item$Settings;
        INVOKESPECIAL net/minecraft/item/Item.<init> (Lnet/minecraft/item/Item$Settings;)V
        INVOKESTATIC net/minecraft/item/Items.register (Ljava/lang/String;Lnet/minecraft/item/Item;)Lnet/minecraft/item/Item;
        PUTSTATIC net/minecraft/item/Items.TOTEM_OF_UNDYING : Lnet/minecraft/item/Item;
        
       L1011
        LINENUMBER 1213 L1011
        LDC "shulker_shell"
        NEW net/minecraft/item/Item
         */
    @Redirect(
        method = "<clinit>",
        at = @At(
            value = "NEW",
            target = "net/minecraft/item/Item"
        ),
        slice = @Slice(
            from = @At(
                value = "CONSTANT",
                args = "stringValue=totem_of_undying"
            ),
            to = @At(
                value = "CONSTANT",
                args = "stringValue=shulker_shell"
            )
        )
    )
    private static Item staticInit(Item.Settings settings) {
        return new PotionTotemItem(settings);
    }
}
