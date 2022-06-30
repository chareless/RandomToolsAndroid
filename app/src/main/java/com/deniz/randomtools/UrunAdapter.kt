package com.deniz.randomtools

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deniz.randomtools.databinding.RecyclerRowBinding

class UrunAdapter(val urunList : ArrayList<Listem>) : RecyclerView.Adapter<UrunAdapter.UrunHolder>() {

    class UrunHolder(val binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UrunHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UrunHolder(binding)
    }

    override fun onBindViewHolder(holder: UrunHolder, position: Int) {
        holder.binding.recyclerViewisimTextView.text=urunList.get(position).isim
        holder.binding.recyclerViewfiyatTextView.text="Birim Fiyatı : " + (urunList.get(position).fiyat.toDouble()).toString()
        holder.binding.recyclerViewadetTextView.text=urunList.get(position).adet + " adet"
    }

    override fun getItemCount(): Int {
        return urunList.size
    }
}