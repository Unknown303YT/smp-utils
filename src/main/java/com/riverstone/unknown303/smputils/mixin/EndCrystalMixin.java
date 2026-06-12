package com.riverstone.unknown303.smputils.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EndCrystal.class)
public abstract class EndCrystalMixin {
    @Inject(method = "hurtClient(Lnet/minecraft/world/damagesource/DamageSource;)Z", at = @At("HEAD"), cancellable = true)
    private void smp_utils$preventClientCrystalExplosion(final DamageSource source, CallbackInfoReturnable<Boolean> callback) {
        callback.setReturnValue(false);
        callback.cancel();
    }

    @Inject(method = "hurtServer(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;F)Z", at = @At("HEAD"), cancellable = true)
    private void smp_utils$preventServerCrystalExplosion(final ServerLevel level, final DamageSource source, final float damage, CallbackInfoReturnable<Boolean> callback) {
        EndCrystal self = (EndCrystal) (Object) this;

        if (!self.isRemoved()) {
            self.remove(Entity.RemovalReason.KILLED);
            this.onDestroyedBy(level, source);
        }

        callback.setReturnValue(false);
        callback.cancel();
    }

    @Shadow
    protected abstract void onDestroyedBy(final ServerLevel level, final DamageSource source);
}
