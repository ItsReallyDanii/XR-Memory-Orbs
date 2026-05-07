package com.danielsleiman.memoryorbs.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.danielsleiman.memoryorbs.data.local.AppDatabase
import com.danielsleiman.memoryorbs.data.model.MemoryOrb
import com.danielsleiman.memoryorbs.data.repository.MemoryOrbRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MemoryOrbViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MemoryOrbRepository
    val allOrbs: StateFlow<List<MemoryOrb>>

    init {
        val orbDao = AppDatabase.getDatabase(application).memoryOrbDao()
        repository = MemoryOrbRepository(orbDao)
        allOrbs = repository.allOrbs.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    fun insertOrb(orb: MemoryOrb) = viewModelScope.launch {
        repository.insertOrb(orb)
    }

    fun updateOrb(orb: MemoryOrb) = viewModelScope.launch {
        repository.updateOrb(orb)
    }

    fun deleteOrb(orb: MemoryOrb) = viewModelScope.launch {
        repository.deleteOrb(orb)
    }

    fun getOrbsByContextLabel(contextLabel: String): Flow<List<MemoryOrb>> {
        return repository.getOrbsByContextLabel(contextLabel)
    }

    suspend fun getOrbById(id: Int): MemoryOrb? {
        return repository.getOrbById(id)
    }
}
