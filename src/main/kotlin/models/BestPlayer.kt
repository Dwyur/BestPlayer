package me.dwyur.models

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import models.dao.BestPlayerDaoImpl

@DatabaseTable(tableName = "BestPlayer", daoClass = BestPlayerDaoImpl::class)
data class BestPlayer(

    @DatabaseField(generatedId = true)
    var id: Long = 0,

    @DatabaseField
    var playerName: String,

    @DatabaseField
    var buyCost: Double = 0.0
)
