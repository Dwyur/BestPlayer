package me.dwyur

import com.j256.ormlite.dao.Dao
import com.j256.ormlite.dao.DaoManager
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource
import com.j256.ormlite.table.TableUtils
import me.dwyur.gui.BestMenu
import me.dwyur.listener.InventoryListener
import me.dwyur.listener.JoinListener
import me.dwyur.listener.NPCListener
import me.dwyur.models.BestPlayer
import me.dwyur.service.BestPlayerService
import me.dwyur.service.VaultService
import net.milkbowl.vault.economy.Economy
import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    private var econ: Economy? = null
    private var connectionSource: JdbcPooledConnectionSource? = null

    val vaultService: VaultService
        get() = VaultService(econ!!)

    val menu: BestMenu
        get() = BestMenu(bestPlayerService)

    private val bestPlayerService: BestPlayerService
        get() = BestPlayerService(getDao(), this)

    override fun onEnable() {
        setupEconomy()
        saveDefaultConfig()
        initConnection()

        server.pluginManager.registerEvents(InventoryListener(), this)
        server.pluginManager.registerEvents(NPCListener(this), this)
        server.pluginManager.registerEvents(JoinListener(bestPlayerService, vaultService), this)
    }

    override fun onDisable() {
        HandlerList.unregisterAll(InventoryListener())
    }

    private fun setupEconomy() {
        val rsp = server.servicesManager.getRegistration(Economy::class.java) ?: return
        econ = rsp.provider
    }

    private fun initConnection() {
        val config = this.config

        connectionSource = JdbcPooledConnectionSource(
            "jdbc:mysql://${config.getString("database.address")}:${config.getInt("database.port")}/${config.getString("database.databaseName")}" +
                    "?useSSL=false&serverTimezone=UTC"
        ).apply {
            setUsername(config.getString("database.username"))
            setPassword(config.getString("database.password"))
            setMaxConnectionsFree(5)
        }

        TableUtils.createTableIfNotExists(connectionSource, BestPlayer::class.java)
        DaoManager.createDao(connectionSource, BestPlayer::class.java)
    }

    private inline fun <reified D : Dao<T, *>, T> getDao(): D = DaoManager.lookupDao(connectionSource, D::class.java)
}