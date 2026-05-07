package com.danielsleiman.memoryorbs.data.local

import androidx.room.*
import com.danielsleiman.memoryorbs.data.model.MemoryOrb
import kotlinx.coroutines.flow.Flow

@Dao
interface MemoryOrbDao {
    @Query("SELECT * FROM memory_orbs ORDER BY createdAt DESC")
    fun getAllOrbs(): Flow<List<MemoryOrb>>

    @Query("SELECT * FROM memory_orbs WHERE TRIM(COALESCE(contextLabel, '')) = TRIM(:contextLabel) ORDER BY createdAt DESC")
    fun getOrbsByContextLabel(contextLabel: String): Flow<List<MemoryOrb>>

    @Query("SELECT * FROM memory_orbs WHERE TRIM(COALESCE(contextLabel, '')) = TRIM(:contextLabel) ORDER BY createdAt DESC, id DESC LIMIT 1")
    suspend fun getFirstOrbByContextLabel(contextLabel: String): MemoryOrb?

    @Query("SELECT * FROM memory_orbs WHERE id = :id")
    suspend fun getOrbById(id: Int): MemoryOrb?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrb(orb: MemoryOrb)

    @Update
    suspend fun updateOrb(orb: MemoryOrb)

    @Delete
    suspend fun deleteOrb(orb: MemoryOrb)
}
