package net.diecode.killermoney.compatibility.actionbar;

import net.diecode.killermoney.interfaces.IActionBar;
import net.minecraft.server.v1_13_R1.ChatMessageType;
import net.minecraft.server.v1_13_R1.IChatBaseComponent;
import net.minecraft.server.v1_13_R1.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_13_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ActionBar_v1_13_R1 implements IActionBar {

    @Override
    public void sendActionBarMessage(Player player, String message) {
        CraftPlayer p = (CraftPlayer) player;
        IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, ChatMessageType.GAME_INFO);

        p.getHandle().playerConnection.sendPacket(ppoc);
    }
}
