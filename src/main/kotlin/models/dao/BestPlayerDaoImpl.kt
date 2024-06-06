package models.dao

import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.support.ConnectionSource
import me.dwyur.models.BestPlayer
import me.dwyur.models.dao.BestPlayerDao
import java.util.*

open class BestPlayerDaoImpl protected constructor(connectionSource: ConnectionSource?, dataClass: Class<BestPlayer?>?) :
    BaseDaoImpl<BestPlayer, Long>(connectionSource, dataClass), BestPlayerDao {

    override fun save(bestPlayer: BestPlayer): BestPlayer {
        createOrUpdate(bestPlayer)
        return bestPlayer
    }

    override fun findById(id: Long): Optional<BestPlayer> {
        return Optional.ofNullable(queryForId(id))
    }
}
