package io.wax911.sample.data.model.container

import com.google.gson.annotations.SerializedName

data class Aniticipated<M>(
    val list_count: Int,
    @SerializedName(
        value = "show",
        alternate = ["movie"]
    )
    val result: M
)