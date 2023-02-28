package com.example.android.marsphotos.overview


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.marsphotos.databinding.GridViewItemBinding
import com.example.android.marsphotos.network.MarsPhoto

class PhotoGridAdapter :
    ListAdapter<MarsPhoto, PhotoGridAdapter.MarsPhotoViewHolder>(DiffCallback) {

    inner class MarsPhotoViewHolder(
        private var binding: GridViewItemBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: MarsPhoto){

            binding.photo = item

            //FUERZA LA ACTUALIZACIÓN DE LA UI EN TIEMPO REAL DESPUES QUE SE HAYA
            //ACTUALIZADO DATOS EN UN LIVEDATA
            binding.executePendingBindings()

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotoGridAdapter.MarsPhotoViewHolder {
        //SE INFLA EL DISEÑO DE LOS ITEMS A DIBUJAR
        val view = GridViewItemBinding.inflate(LayoutInflater.from(parent.context))
        return MarsPhotoViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MarsPhotoViewHolder,
        position: Int
    ) {
        val marsPhoto = getItem( position )
        holder.bind( marsPhoto )
    }

    //MarsPhoto es el objeto genérico que se va a comparar
    companion object DiffCallback : DiffUtil.ItemCallback<MarsPhoto>() {

        //DOS OBJETOS REPRESENTAN EL MISMO ELEMENTO
        //DiffUtil usa este método para determinar si el objeto MarsPhoto nuevo
        //es el mismo que el objeto MarsPhoto anterior
        override fun areItemsTheSame(oldItem: MarsPhoto, newItem: MarsPhoto): Boolean {
            return oldItem.id == newItem.id
        }

        //VERIFICA SI SOS ELEMENTOS CONTIENEN LOS MISMOS DATOS, ESTOS SON DATOS QUE
        //SABEMOS PUEDEN DIFERENCIAR LOS ELEMENTOS
        override fun areContentsTheSame(oldItem: MarsPhoto, newItem: MarsPhoto): Boolean {
            return oldItem.imgSrcUrl == newItem.imgSrcUrl
        }

    }


}