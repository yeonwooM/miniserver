package xyz.xynsia.miniserver;

import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scoreboard.*;
import java.util.Random;

public class PluginListener implements Listener {
    public PluginListener() {
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        Location respawnLocation = player.getBedSpawnLocation();
        if (respawnLocation == null) {
            respawnLocation = this.generateRandomLocation();
            event.setRespawnLocation(respawnLocation);
        }

    }

    private Location generateRandomLocation() {
        Random random = new Random();
        int x = random.nextInt(5001) - 5000;
        int z = random.nextInt(5001) - 5000;
        World world = Bukkit.getServer().getWorld("world");
        int y = world.getHighestBlockYAt(x, z);
        return new Location(world, x, y, z);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player.getName());
        this.createBoard(e.getPlayer());
        if (offlinePlayer.hasPlayedBefore()) {
            e.setJoinMessage("§7[§a+§7]§a " + player.getName());
        } else {
            e.setJoinMessage("§7[§a+§7]§a " + player.getName() + " || 첫 방문이에요!");
        }
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        this.createBoard(e.getPlayer());
        e.setQuitMessage("§7[§4-§7]§4 " + player.getName());
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (!Bukkit.getOnlinePlayers().isEmpty()) {

            for (Player player : Bukkit.getOnlinePlayers()) {
                this.createBoard(player);
            }
        }

    }

    public void createBoard(Player player) {
        Location location = player.getLocation();
        Biome biome = location.getBlock().getBiome();
        String formattedX = String.format("%.1f", location.getX());
        String formattedY = String.format("%.1f", location.getY());
        String formattedZ = String.format("%.1f", location.getZ());
        int tick = player.getStatistic(Statistic.PLAY_ONE_MINUTE);
        long playtimeMinutes = tick / 20 / 60;
        long playtimeHours = playtimeMinutes / 60;
        playtimeMinutes %= 60;
        String PlayTime = playtimeHours + "시간 " + playtimeMinutes + "분";
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective obj = board.registerNewObjective("info", "dummy", ChatColor.translateAlternateColorCodes('&', "&a&l<< &2&l XYNSIA &a&l>>"));
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score score1 = obj.getScore(ChatColor.AQUA + "Online Players : " + ChatColor.BLUE + Bukkit.getOnlinePlayers().size());
        score1.setScore(5);
        Score score2 = obj.getScore(ChatColor.AQUA + "Location : " + ChatColor.WHITE + formattedX + ", " + formattedY + ", " + formattedZ);
        score2.setScore(4);
        Score score3 = obj.getScore(ChatColor.AQUA + "Biome : " + ChatColor.WHITE + biome);
        score3.setScore(3);
        Score score4 = obj.getScore(ChatColor.AQUA + "PlayTime : " + ChatColor.RED + PlayTime);
        score4.setScore(2);
        Score score5 = obj.getScore(ChatColor.AQUA + "Death : " + ChatColor.BLUE + player.getStatistic(Statistic.DEATHS));
        score5.setScore(1);
        player.setScoreboard(board);
    }
}
