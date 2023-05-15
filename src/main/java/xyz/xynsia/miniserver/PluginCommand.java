package xyz.xynsia.miniserver;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PluginCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player)commandSender;
        if (s.equalsIgnoreCase("mt")) {
            String message = "관리자채팅 " + player.getDisplayName() + ": ";
            String[] var1 = strings;
            int var2 = strings.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                String chatText = var1[var3];
                message = message + chatText + " ";
            }

            for (Player MTplayer : Bukkit.getOnlinePlayers()) {
                if (MTplayer.isOp()) {
                    MTplayer.sendMessage(message);
                }
            }
        }

        if (s.equalsIgnoreCase("heal")) {
            player.setHealth(20.0);
            player.setFoodLevel(20);
        }

        return true;
    }
}
