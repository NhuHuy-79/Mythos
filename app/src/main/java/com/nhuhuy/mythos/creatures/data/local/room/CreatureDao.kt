package com.nhuhuy.mythos.creatures.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CreatureDao {
    @Query("SELECT * FROM creatures ORDER BY name ASC")
    suspend fun getAll(): List<CreatureEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(creatures: List<CreatureEntity>)

    @Query("SELECT * FROM creatures WHERE id = :id")
    suspend fun getCreatureById(id: Long): CreatureEntity
}