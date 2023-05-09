package com.ru.kirmcr.lab5chor

import java.util.UUID

data class User (
     val id: UUID= UUID.randomUUID(),
     var login: String = "",
     var password: String =""
        )