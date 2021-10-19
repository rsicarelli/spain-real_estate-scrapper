package domain.entity

data class User(override val _id: String, val email: String, val hashedPass: ByteArray) : Model {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (_id != other._id) return false
        if (email != other.email) return false
        if (!hashedPass.contentEquals(other.hashedPass)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = _id.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + hashedPass.contentHashCode()
        return result
    }
}