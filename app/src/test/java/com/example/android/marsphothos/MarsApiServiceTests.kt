package com.example.android.marsphothos

import com.example.android.marsphotos.network.MarsApiService
import com.example.android.marsphotos.network.MarsPhoto
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MarsApiServiceTests : BaseTest() {

    //PROBAMOS DIRECTAMENTE EL API DEL SERVICIO
    private lateinit var service: MarsApiService

    @Before
    fun setup(){
        //URL A INTERCEPTAR POR MOCKWEBSERVER
        val url = this.mockWebServer.url("/")

        //SE CREA UN OBJETO DEL TIPO DE LA INTERFAZ USADA POR RETROFIT COMO API
        service = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(
                //SE TRANSFORMA LA DATA
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add( KotlinJsonAdapterFactory() )
                        .build()
                )
            )
            .build()
            .create(MarsApiService::class.java)

    }

    @Test
    fun api_service(){
        this.enqueue("mars_photos.json")
        runBlocking {
            val apiResponse: List<MarsPhoto> = service.getPhotos()

            //SE VERIFICA QUE LA LLAMADA AL API NO SEA NULL
            assertNotNull(apiResponse)

            //COMO EL SERVICIO RETORNA UNA LISTA VERIFICAMOS QUE NO ESTE VACIA
            assertTrue("The list was empty", apiResponse.isNotEmpty())

            //VERICAMOS QUE ALGUNOS DATOS DE LA LISTA SEAN CORRECTOS
            assertEquals("The Ids did not match", "424905", apiResponse[0].id)

        }
    }

}