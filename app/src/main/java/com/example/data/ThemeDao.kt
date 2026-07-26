package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface ThemeDao {
    @Query("SELECT * FROM theme_presets")
    fun getAllThemes(): Flow<List<ThemePreset>>

    @Query("SELECT * FROM theme_presets WHERE isApplied = 1 LIMIT 1")
    fun getActiveTheme(): Flow<ThemePreset?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertThemes(themes: List<ThemePreset>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTheme(theme: ThemePreset)

    @Query("UPDATE theme_presets SET isApplied = 0")
    suspend fun clearActiveThemes()

    @Query("UPDATE theme_presets SET isApplied = 1 WHERE id = :themeId")
    suspend fun setActiveTheme(themeId: String)

    @Transaction
    suspend fun applyTheme(themeId: String) {
        clearActiveThemes()
        setActiveTheme(themeId)
    }

    @Query("UPDATE theme_presets SET dynamicIslandEnabled = :enabled WHERE isApplied = 1")
    suspend fun setDynamicIslandEnabled(enabled: Boolean)

    @Query("UPDATE theme_presets SET iconShapeRadiusDp = :radius WHERE isApplied = 1")
    suspend fun setIconShapeRadius(radius: Int)
}
