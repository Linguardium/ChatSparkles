package mod.linguardium.chatsparkles.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatListenerHud;
import net.minecraft.network.MessageType;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ChatListenerHud.class)
public class ChatHud {
    @Shadow
    MinecraftClient client;
    @Inject(at = @At("HEAD"), method = "onChatMessage")
    private void addToHud(MessageType messageType, Text message, CallbackInfo info) {
        System.out.println(message.toString());
        if (message instanceof TranslatableText) {
            if (((TranslatableText) message).getArgs().length == 2) {
                Object arg0 = ((TranslatableText) message).getArgs()[0];
                Object arg1 = ((TranslatableText) message).getArgs()[1];
                if (arg0 instanceof LiteralText && arg1 instanceof String) {
                    String speakerName = ((LiteralText)arg0).asString();
                    String msg = ((String)arg1).toLowerCase();
                    if (!speakerName.equals(client.getSession().getUsername())) {
                        if (msg.contains("woot!")) {
                            client.player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                        }
                    }
                }
            }
        }
    }

}
