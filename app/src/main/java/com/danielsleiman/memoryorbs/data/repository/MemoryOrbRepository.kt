package com.danielsleiman.memoryorbs.data.repository

import com.danielsleiman.memoryorbs.data.local.MemoryOrbDao
import com.danielsleiman.memoryorbs.data.model.MemoryOrb
import kotlinx.coroutines.flow.Flow

class MemoryOrbRepository(private val memoryOrbDao: MemoryOrbDao) {
    val allOrbs: Flow<List<MemoryOrb>> = memoryOrbDao.getAllOrbs()

    fun getOrbsByContextLabel(contextLabel: String): Flow<List<MemoryOrb>> {
        return memoryOrbDao.getOrbsByContextLabel(contextLabel)
    }

    suspend fun getFirstOrbByContextLabel(contextLabel: String): MemoryOrb? {
        return memoryOrbDao.getFirstOrbByContextLabel(contextLabel)
    }

    suspend fun getOrbById(id: Int): MemoryOrb? {
        return memoryOrbDao.getOrbById(id)
    }

    suspend fun insertOrb(orb: MemoryOrb) {
        memoryOrbDao.insertOrb(orb)
    }

    suspend fun updateOrb(orb: MemoryOrb) {
        memoryOrbDao.updateOrb(orb)
    }

    suspend fun deleteOrb(orb: MemoryOrb) {
        memoryOrbDao.deleteOrb(orb)
    }
}
