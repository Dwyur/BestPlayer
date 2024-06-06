package me.dwyur.listener

import me.dwyur.gui.BestMenu
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class InventoryListener : Listener {

    @EventHandler
    fun onClick(e: InventoryClickEvent) {
        e.isCancelled = true

        e.clickedInventory?.holder?.let { holder ->
            if (holder is BestMenu) holder.onClick(e)
        }
    }
}