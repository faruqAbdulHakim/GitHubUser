package com.faruqabdulhakim.githubuser.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorites")
@Parcelize
data class Favorite(
    @PrimaryKey(autoGenerate = false)
    @field:ColumnInfo(name = "username")
    val username: String = "",

    @field:ColumnInfo(name = "avatarUrl")
    val avatarUrl: String = ""
) : Parcelable