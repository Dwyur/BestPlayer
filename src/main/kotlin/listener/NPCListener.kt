package me.dwyur.listener

import me.dwyur.Main
import net.citizensnpcs.api.event.NPCClickEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class NPCListener(private var plugin: Main) : Listener {

    @EventHandler
    fun onClick(event: NPCClickEvent) {
        event.clicker.openInventory(plugin.menu.inventory)
    }
}