package com.example.data

import kotlinx.coroutines.flow.Flow

class ProfileRepository(private val profileDao: ProfileDao) {
    val allProfiles: Flow<List<SensitivityProfile>> = profileDao.getAllProfiles()

    suspend fun insertProfile(profile: SensitivityProfile) {
        profileDao.insertProfile(profile)
    }

    suspend fun deleteProfile(profile: SensitivityProfile) {
        profileDao.deleteProfile(profile)
    }

    suspend fun deleteProfileById(id: Int) {
        profileDao.deleteProfileById(id)
    }
}
