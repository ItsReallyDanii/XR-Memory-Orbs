package com.danielsleiman.memoryorbs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memory_orbs")
data class MemoryOrb(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val bodyText: String,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val tags: String = "", // Simplified for MVP: comma-separated string
    val privacyState: String = "LOCAL_ONLY",
    val mediaUri: String? = null,
    val contextLabel: String? = null,
    val recallHint: String? = null
)
