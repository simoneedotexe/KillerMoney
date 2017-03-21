package net.diecode.killermoney.compatibility.actionbar;

import net.diecode.killermoney.interfaces.ActionBar;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ActionBar_v1_8_R3 implements ActionBar {

    @Override
    public void sendActionBarMessage(Player player, String message) {
        CraftPlayer p = (CraftPlayer) player;
        IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte)2);

        p.getHandle().playerConnection.sendPacket(ppoc);
    }
}
