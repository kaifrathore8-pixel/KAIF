package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {
    @Query("SELECT * FROM sensitivity_profiles ORDER BY timestamp DESC")
    fun getAllProfiles(): Flow<List<SensitivityProfile>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: SensitivityProfile)

    @Delete
    suspend fun deleteProfile(profile: SensitivityProfile)

    @Query("DELETE FROM sensitivity_profiles WHERE id = :id")
    suspend fun deleteProfileById(id: Int)
}
