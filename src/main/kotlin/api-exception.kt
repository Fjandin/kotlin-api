private fun getMessageFromStatus(status: Int): String {
    when (status) {
        400 -> return "Bad Request"
        401 -> return "Unauthorized"
        403 -> return "Forbidden"
    }
    return "Internal Server Error"
}

data class ApiExceptionResponse(
    val status: Int,
    val message: String,
    val meta: HashMap<String, Any?>?
)

class ApiException: RuntimeException {
    var status: Int = 500
    var meta: HashMap<String, Any?>? = null

    constructor(status: Int, message: String, meta: HashMap<String, Any?>): super(message) {
        this.status = status
        this.meta = meta
    }

    constructor(status: Int, message: String): super(message) {
        this.status = status
        this.meta = meta
    }

    constructor(status: Int): super(getMessageFromStatus(status)) {
        this.status = status
    }

    constructor(status: Int, meta: HashMap<String, Any?>): super(getMessageFromStatus(status)) {
        this.status = status
        this.meta = meta
    }

    constructor(meta: HashMap<String, Any?>): super("Error") {
        this.status = 500
        this.meta = meta
    }

    fun getResponse(): ApiExceptionResponse {
        if (this.message != null) {
            return ApiExceptionResponse(this.status, this.message, this.meta)
        } else {
            return ApiExceptionResponse(this.status, "Undefined", this.meta)
        }

    }
}