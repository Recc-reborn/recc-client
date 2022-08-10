package com.recc.recc_client.models.responses

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Type

data class CreateToken(
    @SerializedName("password") val password: String = "",
    @SerializedName("email") val email: String = "",
    @SerializedName("device_name") val deviceName: String = ""
)

class Token {
    var token: String = ""
    var message: String = ""

    inner class Deserializer: JsonDeserializer<Token> {
        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): Token {
            var res = Token()
            json?.asJsonObject?.let { jsonObject ->
                // Success
                if (jsonObject.has("token")) {
                    res.token = jsonObject.getAsJsonPrimitive("token").asString
                // Failure
                } else {
                    res.message = jsonObject.getAsJsonPrimitive("message").asString
                }
            }
            return res
        }
    }
}