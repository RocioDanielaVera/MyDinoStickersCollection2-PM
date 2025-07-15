package com.example.mydinostickerscollection2.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mydinostickerscollection2.data.Sticker
import com.example.mydinostickerscollection2.databinding.ItemStickerBinding
import com.squareup.picasso.Picasso

class StickersAdapter(
    private val stickers: List<Sticker>,
    private val selectStickerClickLister: (Sticker) -> Unit
) : RecyclerView.Adapter<StickersAdapter.StickersViewHolder>() {

    class StickersViewHolder(val binding: ItemStickerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StickersViewHolder {
        val clientBinding =
            ItemStickerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StickersViewHolder(clientBinding)
    }

    override fun onBindViewHolder(holder: StickersViewHolder, position: Int) {
        val sticker = stickers[position]
        holder.binding.tvStickerDescription.text = sticker.description
        holder.binding.tvStickerName.text = sticker.name
        Picasso.get().load(sticker.picture).into(holder.binding.ivStickerImage)
        holder.binding.tvStickerExtraData.text = buildString { append("${sticker.height}M                  ${sticker.weight} \n ALTURA            PESO") }
        holder.binding.stickerCard.setOnClickListener {
            selectStickerClickLister(sticker)
        }
    }

    override fun getItemCount(): Int {
        return stickers.size
    }
}