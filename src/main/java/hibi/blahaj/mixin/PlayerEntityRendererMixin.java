package hibi.blahaj.mixin;

import hibi.blahaj.block.*;
import net.minecraft.client.network.*;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {
	@Inject(
		method = "getArmPose(Lnet/minecraft/client/network/AbstractClientPlayerEntity;Lnet/minecraft/util/Arm;)Lnet/minecraft/client/render/entity/model/BipedEntityModel$ArmPose;",
		at = @At("TAIL"),
		cancellable = true
	)
	private static void blahaj$cuddle(AbstractClientPlayerEntity player, Arm arm, CallbackInfoReturnable<BipedEntityModel.ArmPose> cir) {
		// CROSSBOW_CHARGE only renders properly in offhand, soooooo
		ItemStack stack = player.getStackInHand(arm == Arm.LEFT ? Hand.OFF_HAND : Hand.MAIN_HAND);
		if (stack.getItem() instanceof CuddlyItem) {
			cir.setReturnValue(BipedEntityModel.ArmPose.CROSSBOW_CHARGE);
			cir.cancel();
		}
	}
}
