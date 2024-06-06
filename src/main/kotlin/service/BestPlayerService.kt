package me.dwyur.service

import me.dwyur.Main
import me.dwyur.models.BestPlayer
import me.dwyur.models.dao.BestPlayerDao
import net.citizensnpcs.api.CitizensAPI
import org.bukkit.Bukkit
import org.bukkit.Effect
import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player


class BestPlayerService(
    private val bestPlayerDao: BestPlayerDao,
    private val plugin: Main
) {

    private var bestPlayer: BestPlayer? = bestPlayerDao.findById(0).orElse(null)
    private val npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "bestplayer")

    init {
        createNPC()
        startUpdaters()
    }

    fun getBestPlayer(): BestPlayer? = bestPlayer

    fun isBestPlayer(player: Player) = bestPlayer?.playerName?.equals(player.name, ignoreCase = true) ?: false

    fun buyBestPlayer(player: Player, buyCost: Double) {
        bestPlayer = bestPlayer?.copy(playerName = player.name, buyCost = buyCost) ?: BestPlayer(0, player.name, buyCost)
        bestPlayerDao.save(bestPlayer!!)

        plugin.vaultService.takeMoney(player, buyCost)

        player.world.playSound(player.location, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1f, 1f)
        player.world.playEffect(player.location, Effect.FIREWORK_SHOOT, 30)

        updateNPC()
        plugin.menu.update()

        plugin.logger.info("Новый лучший игрок проекта! ${player.name}")
    }

    private fun updateNPC() {
        npc.despawn()
        createNPC()
    }

    private fun createNPC() {
        npc.spawn((plugin.config.getString("location") ?:
        throw NullPointerException("The string along the path \"location\" is null!")).toLocation())

        npc.shouldRemoveFromTabList().not()
    }

    private fun startUpdaters() {
        Bukkit.getScheduler().runTaskTimer(plugin, Runnable {
            npc.setSneaking(!npc.entity.isSneaking)
        }, 0L, 40L)
    }

    private fun String.toLocation(): Location? {
        val locData = plugin.config.getString("location")?.split(", ") ?: return null
        val world = Bukkit.getWorld(locData[0]) ?: return null
        return Location(world, locData[1].toDouble(), locData[2].toDouble(), locData[3].toDouble(), locData[4].toFloat(), locData[5].toFloat())
    }
}