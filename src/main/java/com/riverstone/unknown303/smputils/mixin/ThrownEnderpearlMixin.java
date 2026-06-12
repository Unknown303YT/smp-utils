package com.riverstone.unknown303.smputils.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownEnderpearl;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ThrownEnderpearl.class)
public abstract class ThrownEnderpearlMixin {
    /**
     * @author Unknown_303
     * @reason Get rid of pearl's teleportation.
     */
    @Inject(method = "onHitEntity(Lnet/minecraft/world/phys/EntityHitResult;)V",
        at = @At("HEAD"), cancellable = true)
    private void smp_utils$cancelEnderpearlHitDamage(final EntityHitResult hitResult, CallbackInfo callback) {
        callback.cancel();
    }

    @Inject(method = "isAllowedToTeleportOwner(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/level/Level;)Z",
        at = @At("HEAD"), cancellable = true)
    private static void smp_utils$cancelEnderpearlTeleport(final Entity owner, final Level newLevel, CallbackInfoReturnable<Boolean> callback) {
        callback.setReturnValue(false);
        callback.cancel();
    }
}
