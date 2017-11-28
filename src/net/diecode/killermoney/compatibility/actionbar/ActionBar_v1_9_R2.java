package net.diecode.killermoney.compatibility.actionbar;

import net.diecode.killermoney.interfaces.IActionBar;
import net.minecraft.server.v1_9_R2.IChatBaseComponent;
import net.minecraft.server.v1_9_R2.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ActionBar_v1_9_R2 implements IActionBar {

    @Override
    public void sendActionBarMessage(Player player, String message) {
        CraftPlayer p = (CraftPlayer) player;
        IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte)2);

        p.getHandle().playerConnection.sendPacket(ppoc);
    }
}
