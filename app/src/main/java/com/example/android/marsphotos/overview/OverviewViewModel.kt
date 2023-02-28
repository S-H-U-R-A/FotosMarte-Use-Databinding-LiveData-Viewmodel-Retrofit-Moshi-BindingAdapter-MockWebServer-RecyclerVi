package com.example.android.marsphotos.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.marsphotos.network.MarsApi
import com.example.android.marsphotos.network.MarsPhoto
import kotlinx.coroutines.launch

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */

enum class MarsApiStatus{
    LOADING,
    ERROR,
    DONE
}

class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    //El MutableLiveData interno que almacena el estado de la solicitud más reciente
    private val _status = MutableLiveData<MarsApiStatus>()

    // The external immutable LiveData for the request status
    //El LiveData inmutable externo para el estado de la solicitud
    val status: LiveData<MarsApiStatus> = _status

    private val _photos = MutableLiveData< List<MarsPhoto> >()
    val photos: LiveData< List<MarsPhoto> > = _photos

    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     * Llame a getMarsPhotos() en init para que podamos mostrar el estado inmediatamente.
     */
    init {
        getMarsPhotos()
    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     * Obtiene información de fotos de Marte del servicio Mars API Retrofit y actualiza la
     * [MarsPhoto] [List] [LiveData].
     */
    private fun getMarsPhotos() {
        viewModelScope.launch {
            //SE MUESTRA EL ESTADO DE CARGA
            _status.value = MarsApiStatus.LOADING
            try {
                _photos.value = MarsApi.retrofitService.getPhotos()
                //ESTADO DE SUCCESS
                _status.value =MarsApiStatus.DONE
            } catch (e: Exception) {
                //ESTADO DE ERROR
                _status.value = MarsApiStatus.ERROR
                _photos.value = listOf()
            }

        }
    }
}
