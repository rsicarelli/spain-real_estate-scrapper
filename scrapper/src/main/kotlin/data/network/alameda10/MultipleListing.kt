package data.network.alameda10

import com.google.gson.annotations.SerializedName


data class MultipleListing(

    @SerializedName("listing") var listing: ArrayList<String>

)