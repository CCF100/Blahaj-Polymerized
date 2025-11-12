package hibi.blahaj.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import hibi.blahaj.Blahaj;
import hibi.blahaj.TrinketsHelper;

import java.util.List;

@Mixin(targets = "net.minecraft.server.network.ServerPlayerEntity$1")
public class ArmorHeadSlotMixin {
    @Final
    @Shadow
    ServerPlayerEntity field_29182;

    @ModifyVariable(method = "updateSlot", at = @At("HEAD"), argsOnly = true)
    private ItemStack modifyHeadSlotItem(ItemStack stack, ScreenHandler handler, int slot) {
        ItemStack hatItem = TrinketsHelper.getHatItem(field_29182);
        if (handler instanceof PlayerScreenHandler &&
                (hatItem != ItemStack.EMPTY && slot == 5)) {
            return hatItem;
        }
        return stack;
    }

    @Inject(method = "updateState", at = @At(value = "TAIL"))
    void modifyHeadSlotItem(ScreenHandler handler, List<ItemStack> stacks, ItemStack cursorStack, int[] properties, CallbackInfo ci) {
        if (Blahaj.DEV_ENV) {
            Blahaj.LOGGER.info("modifyHeadSlotItem (ArmorHeadSlotMixin) Mixin called");
        }
        ItemStack itemStack = TrinketsHelper.getHatItem(field_29182);
        if (handler instanceof PlayerScreenHandler &&
                (itemStack != ItemStack.EMPTY)) {
            this.field_29182.networkHandler
                    .sendPacket(new ScreenHandlerSlotUpdateS2CPacket(handler.syncId, handler.nextRevision(), 5,
                            itemStack));
        }
    }

}
