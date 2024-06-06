package me.dwyur.gui

import me.dwyur.service.BestPlayerService
import me.dwyur.utility.ItemBuilder
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class BestMenu(private val bestPlayerService: BestPlayerService) : InventoryHolder {

    private val inventory = Bukkit.createInventory(this, 27, Component.text("Лучший игрок"))

    override fun getInventory(): Inventory = inventory

    init {
      update()
    }

    fun update() {
        inventory.addItem(when (val bestPlayer = bestPlayerService.getBestPlayer()) {
            null -> ItemBuilder(Material.STICK)
                .setDisplayName("Лучшего игрока еще нет!")
                .setLore(listOf("Стань первым", "Это твой шанс!"))
            else -> ItemBuilder(Material.STICK)
                .setDisplayName("Лучший игрок: ${bestPlayer.playerName}")
                .setLore(listOf("Хочешь также?", "Плати!"))
        }.build())

        inventory.setItem(3, ItemBuilder(Material.EMERALD).setDisplayName("Пополнить на 1").build())
        inventory.setItem(4, ItemBuilder(Material.EMERALD).setDisplayName("Пополнить на 10").build())
        inventory.setItem(5, ItemBuilder(Material.EMERALD).setDisplayName("Пополнить на 50").build())
    }

    fun onClick(event: InventoryClickEvent) {
        val player = event.whoClicked as? Player ?: return
        bestPlayerService.buyBestPlayer(player, when (event.slot) {
            3 -> 1.0
            4 -> 10.0
            5 -> 50.0
            else -> return
        })
    }
}