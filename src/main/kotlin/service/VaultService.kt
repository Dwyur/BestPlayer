package me.dwyur.service

import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class VaultService(private var economy: Economy) {

    fun takeMoney(player: Player, sum: Double) {
        economy.withdrawPlayer(Bukkit.getOfflinePlayer(player.name), sum)
    }

    fun getBalance(player: Player): Double {
        return economy.getBalance(Bukkit.getOfflinePlayer(player.name))
    }

    fun hasMoney(player: Player, moneyCount: Number): Boolean {
        return getBalance(player) >= moneyCount.toDouble()
    }
}