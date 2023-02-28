package com.example.android.marsphothos

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import java.io.InputStream
import java.io.InputStreamReader

//CLASE QUE CREA UN SERVIDOR FICTICIO Y LEE LA DATA MOCKEADA EN EL ARCHIVO JSON
open class BaseTest {

    val mockWebServer = MockWebServer()

    //ANALIZA LOS DATOS DEL ARCHIVO mars_photos.json
    fun enqueue(fileName: String) {
        //SE OBTIENE EL ARCHIVO
        val inputStream: InputStream = javaClass.classLoader!!.getResourceAsStream(fileName)

        val buffer = inputStream.source().buffer()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(buffer.readString(Charsets.UTF_8))
        )
    }
}