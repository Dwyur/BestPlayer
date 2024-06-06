package me.dwyur.listener

import me.dwyur.service.BestPlayerService
import me.dwyur.service.VaultService
import net.kyori.adventure.text.Component
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class JoinListener(
    private val bestPlayerService: BestPlayerService,
    private val vaultService: VaultService
) : Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player

        with(bestPlayerService) {
            if (isBestPlayer(player)) {
                player.sendMessage(Component.text("Вы по-прежнему лучший игрок сервера! Поздравляем!"))
            }

            val bestPlayer = getBestPlayer()

            if (vaultService.hasMoney(player, bestPlayer?.buyCost?.plus(1) ?: 0)) {
                player.sendMessage(Component.text("У вас хватает монет для того, чтобы стать лучшим игроком сервера!"))
                player.sendMessage(Component.text("Это ваш шанс, чтобы забрать этот титул!"))
            }
        }
    }
}