package data.network.alameda10

import com.google.gson.annotations.SerializedName


data class VisualHighlight(

    @SerializedName("isUrgentVisualHighlight") var isUrgentVisualHighlight: Boolean? = null,
    @SerializedName("isVisualHighlight") var isVisualHighlight: Boolean? = null,
    @SerializedName("highLightComments") var highLightComments: ArrayList<String> = arrayListOf()

)