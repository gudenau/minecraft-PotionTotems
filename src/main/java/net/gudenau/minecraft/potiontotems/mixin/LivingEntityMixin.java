package net.gudenau.minecraft.potiontotems.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.PotionUtil;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	@Shadow public abstract void readCustomDataFromNbt(NbtCompound nbt);
	
	@Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect);
	
	@SuppressWarnings("ConstantConditions")
	private LivingEntityMixin() {
		super(null, null);
	}
	
	@SuppressWarnings("ConstantConditions")
	@Inject(
		method = "tryUseTotem",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/LivingEntity;addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;)Z",
			ordinal = 0
		),
		cancellable = true,
		locals = LocalCapture.CAPTURE_FAILHARD
	)
	private void tryUseTotem(DamageSource source, CallbackInfoReturnable<Boolean> cir, @NotNull ItemStack totemStack) {
		if(!((Object)this instanceof PlayerEntity player)) {
			return;
		}
		
		var effects = PotionUtil.getPotionEffects(totemStack);
		if(effects.isEmpty()) {
			return;
		}
		
		for (var effect : effects) {
			if (effect.getEffectType().isInstant()) {
				effect.getEffectType().applyInstantEffect(player, player, player, effect.getAmplifier(), 1);
			}else {
				addStatusEffect(new StatusEffectInstance(effect));
			}
		}
		
		world.sendEntityStatus(this, (byte)35);
		
		cir.setReturnValue(true);
	}
}
