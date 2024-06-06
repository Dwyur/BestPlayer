package me.dwyur.utility

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

class ItemBuilder(material: Material) {
    private val itemStack = ItemStack(material)
    private val meta = itemStack.itemMeta

    fun setAmount(amount: Int): ItemBuilder {
        itemStack.amount = amount
        return this
    }

    fun setDisplayName(displayName: String): ItemBuilder {
        meta.displayName(Component.text(displayName))
        return this
    }

    fun addFlags(flag: ItemFlag): ItemBuilder {
        meta.addItemFlags(flag)
        return this
    }

    fun addEnchantment(enchantment: Enchantment, level: Int): ItemBuilder {
        meta.addEnchant(enchantment, level, false)
        return this
    }

    fun setLore(lore: List<String>): ItemBuilder {
        meta.lore(lore.map { Component.text(it) })
        return this
    }

    fun setCustomModelData(modelData: Int): ItemBuilder {
        meta.setCustomModelData(modelData)
        return this
    }

    fun build(): ItemStack {
        itemStack.itemMeta = meta
        return itemStack
    }
}
