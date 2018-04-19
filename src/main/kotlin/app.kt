import io.javalin.Javalin

fun main(args: Array<String>) {
    val app = Javalin.create().apply {
        port(1337)
    }.start()

    app.before("/*") { ctx ->
        val authorization: String? = ctx.header("Authorization")
        if (authorization != "1234" && authorization != "5678") {
            var errors: HashMap<String, Any?> = HashMap()
            errors["foo"] = "bar"
            errors["nothing"] = null
            throw ApiException(401, errors)
        }
    }

    app.exception(ApiException::class.java, { e, ctx ->
        ctx.status(e.status).json(e.getResponse())
    })


    app.get("/") {ctx ->
        ctx.json("Hello")
    }

}
