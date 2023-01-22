package com.deniz.randomtools

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deniz.randomtools.databinding.RecyclerRowSifreBinding

class SifreAdapter(val sifreList : List<Sifre>) : RecyclerView.Adapter<SifreAdapter.SifreHolder>() {

    class SifreHolder(val recyclerRowBinding: RecyclerRowSifreBinding) : RecyclerView.ViewHolder(recyclerRowBinding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SifreHolder {
        val recyclerRowBinding = RecyclerRowSifreBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SifreHolder(recyclerRowBinding)
    }

    override fun onBindViewHolder(holder: SifreHolder, position: Int) {
        holder.recyclerRowBinding.recyclerViewTextView.text=sifreList.get(position).baslik
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context,SifreKaydetActivity::class.java)
            intent.putExtra("new",sifreList.get(position))
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return sifreList.size
    }

}