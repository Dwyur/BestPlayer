package me.dwyur.models.dao

import com.j256.ormlite.dao.Dao
import me.dwyur.models.BestPlayer
import java.util.*

interface BestPlayerDao : Dao<BestPlayer, Long> {
    fun save(bestPlayer: BestPlayer): BestPlayer
    fun findById(id: Long): Optional<BestPlayer>
}
