package com.example.android.marsphotos.overview

import android.content.res.Resources.NotFoundException
import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.android.marsphotos.R
import com.example.android.marsphotos.network.MarsPhoto

//SE CREA EL ATRIBUTO imageUrl PARA LOS VIEW DE TIPO IMAGEVIEW
@BindingAdapter("imageUrl")
fun bindImage(
    imgView: ImageView,
    imgUrl: String?
){
    imgUrl?.let {

        //SE CONSTRUYE UN OBJETO URI A PARTIR DEL STRING DE LA URL
        val imgUri = it.toUri()
            .buildUpon()
            .scheme("https")
            .build()

        //load ES UN MÉTODO DISPONIBLE POR COIL
        imgView.load(imgUri){
            //MIENTRAS CARGA
            placeholder(R.drawable.loading_animation)
            //SI NO SE PUDO CARGAR
            error(R.drawable.ic_broken_image)
        }

    }
}

//SE CREA UN ATRIBUTO listData A RECYCLERVIEW
@BindingAdapter("listData")
fun bindRecyclerView(
    recyclerView: RecyclerView,
    data: List<MarsPhoto>?
){
    //SE OBTIENE EL ADPATADOR ASIGNADO AL RECYCLERVIEW
    //EN ESTE CASO LO CASTEAMOS AL TIPO DE ADAPTADOR QUE NECESITAMOS
    val adapter = recyclerView.adapter as PhotoGridAdapter
    //LE ENVIAMOS LA LISTA DE DATA QUE ESPERA EL ADAPTADOR PARA TRABAJAR
    adapter.submitList(data)
}

@BindingAdapter("marsApiStatus")
fun bindStatus(
    statusImageView: ImageView,
    status: MarsApiStatus?
){
    when(status){
        MarsApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        MarsApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
        MarsApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }else -> {
            throw NotFoundException("No existe esta opción")
        }
    }
}