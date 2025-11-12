package hibi.blahaj.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import hibi.blahaj.Blahaj;
import hibi.blahaj.TrinketsHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.PickItemFromBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PickItemFromEntityC2SPacket;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
    @Shadow
    public ServerPlayerEntity player;

    @Inject(method = "onPickItemFromEntity", at = @At(value = "TAIL", target = "Lnet/minecraft/advancement/criterion/Criteria;INVENTORY_CHANGED:Lnet/minecraft/advancement/criterion/InventoryChangedCriterion;"))
    void modifyHeadSlotItem(PickItemFromEntityC2SPacket packet, CallbackInfo ci) {
                if (Blahaj.DEV_ENV) {
                    Blahaj.LOGGER.info("modifyHeadSlotItem (ServerPlayNetworkHandler) Mixin called");
                }
        ScreenHandler handler = this.player.currentScreenHandler;
        ItemStack hatStack = TrinketsHelper.getHatItem(this.player);
        if (hatStack != ItemStack.EMPTY) {
            this.player.networkHandler.sendPacket(
                    new ScreenHandlerSlotUpdateS2CPacket(handler.syncId, handler.nextRevision(), 5, hatStack));
        }
    }

    @Inject(method = "onClickSlot", at = @At(value = "TAIL", target = "Lnet/minecraft/advancement/criterion/Criteria;INVENTORY_CHANGED:Lnet/minecraft/advancement/criterion/InventoryChangedCriterion;"))
    void modifyHeadSlotItem2(ClickSlotC2SPacket packet, CallbackInfo ci) {
        if (Blahaj.DEV_ENV) {
            Blahaj.LOGGER.info("modifyHeadSlotItem2 (ServerPlayNetworkHandler) Mixin called");
        }
        ScreenHandler handler = this.player.currentScreenHandler;
        ItemStack hatStack = TrinketsHelper.getHatItem(this.player);
        if (hatStack != ItemStack.EMPTY && packet.slot()  == 5) {
            this.player.networkHandler.sendPacket(
                    new ScreenHandlerSlotUpdateS2CPacket(handler.syncId, handler.nextRevision(), 5, hatStack));
        }
    }
}
