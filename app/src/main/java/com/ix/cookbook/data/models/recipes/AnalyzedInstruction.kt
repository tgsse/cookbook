package com.ix.cookbook.data.models.recipes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnalyzedInstruction(
    @SerialName("name")
    val name: String,
    @SerialName("steps")
    val steps: List<Step>,
)
