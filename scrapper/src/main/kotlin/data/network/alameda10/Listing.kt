package data.network.alameda10

import com.google.gson.annotations.SerializedName


data class Listing(
    @SerializedName("id") var id: Int,
    @SerializedName("comments") var comments: Comments,
    @SerializedName("multimedias") var multimedias: Multimedias,
    @SerializedName("prices") var prices: Prices,
    @SerializedName("property") var property: Property,
    @SerializedName("creation") var creation: Long,
    @SerializedName("IsRent") var IsRent: Boolean,
    @SerializedName("IsInactive") var IsInactive: Boolean,
    @SerializedName("OperationName") var OperationName: String,
)

data class Comments(
    @SerializedName("adComments") var adComments: ArrayList<AdComments>
)

data class AdComments(
    @SerializedName("propertyComment") var propertyComment: String?,
    @SerializedName("language") var language: Int?
)


data class Multimedias(
    @SerializedName("pictures") var pictures: ArrayList<Pictures>,
    @SerializedName("professionalVideos") var professionalVideos: ArrayList<String>,
    @SerializedName("videos") var videos: ArrayList<Videos>,
    @SerializedName("hasHomestaging") var hasHomestaging: Boolean?,
    @SerializedName("MainPicture") var MainPicture: String?,
    @SerializedName("CountPictures") var CountPictures: Int?
)

data class Pictures(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("masterPath") var masterPath: String? = null,
    @SerializedName("masterName") var masterName: String? = null,
    @SerializedName("heightPixels") var heightPixels: Int? = null,
    @SerializedName("widthPixels") var widthPixels: Int? = null,
    @SerializedName("masterIsThumbnail") var masterIsThumbnail: Boolean? = null,
    @SerializedName("position") var position: Int? = null,
    @SerializedName("aspectRatioId") var aspectRatioId: Int? = null,
    @SerializedName("rotationDegree") var rotationDegree: Int? = null,
    @SerializedName("multimediaTag") var multimediaTag: String? = null
)

data class Videos(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("masterPath") var masterPath: String? = null,
    @SerializedName("masterName") var masterName: String? = null,
    @SerializedName("heightPixels") var heightPixels: Int? = null,
    @SerializedName("widthPixels") var widthPixels: Int? = null,
    @SerializedName("masterIsThumbnail") var masterIsThumbnail: Boolean? = null,
    @SerializedName("position") var position: Int? = null,
    @SerializedName("aspectRatioId") var aspectRatioId: Int? = null,
    @SerializedName("rotationDegree") var rotationDegree: Int? = null,
    @SerializedName("multimediaTag") var multimediaTag: String? = null
)