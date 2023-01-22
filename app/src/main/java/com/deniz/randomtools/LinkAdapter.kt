package com.deniz.randomtools

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.deniz.randomtools.databinding.RecyclerRowLinkBinding

class LinkAdapter(val linkList : List<Link>) : RecyclerView.Adapter<LinkAdapter.LinkHolder>() {

    class LinkHolder(val recyclerRowBinding: RecyclerRowLinkBinding) : RecyclerView.ViewHolder(recyclerRowBinding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinkHolder {
        val recyclerRowBinding = RecyclerRowLinkBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LinkHolder(recyclerRowBinding)
    }

    override fun onBindViewHolder(holder: LinkHolder, position: Int) {
        holder.recyclerRowBinding.recyclerViewTextView.text=linkList.get(position).baslik
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context,LinkKaydetActivity::class.java)
            intent.putExtra("new",linkList.get(position))
            holder.itemView.context.startActivity(intent)
        }
        holder.itemView.setOnLongClickListener{
            val clipboard = getSystemService(holder.itemView.context, ClipboardManager::class.java)
            val clip = ClipData.newPlainText("randomtools",linkList[position].link)
            clipboard?.setPrimaryClip(clip)
            Toast.makeText(it.context.applicationContext,"'${linkList.get(position).baslik}' linki kopyalandı.",Toast.LENGTH_SHORT).show()
            true
        }
    }

    override fun getItemCount(): Int {
        return linkList.size
    }

}