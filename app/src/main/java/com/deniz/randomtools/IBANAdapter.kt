package com.deniz.randomtools

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.deniz.randomtools.databinding.RecyclerRowIbanBinding

class IBANAdapter(val ibanList : List<IBAN>) : RecyclerView.Adapter<IBANAdapter.IBANHolder>() {

    class IBANHolder(val recyclerRowBinding: RecyclerRowIbanBinding) : RecyclerView.ViewHolder(recyclerRowBinding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IBANHolder {
        val recyclerRowBinding = RecyclerRowIbanBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return IBANHolder(recyclerRowBinding)
    }

    override fun onBindViewHolder(holder: IBANHolder, position: Int) {
        holder.recyclerRowBinding.recyclerViewTextView.text=ibanList.get(position).baslik
        holder.recyclerRowBinding.recyclerViewTextIBANView.text=ibanList.get(position).iban
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context,IBANKaydetActivity::class.java)
            intent.putExtra("new",ibanList.get(position))
            holder.itemView.context.startActivity(intent)
        }
        holder.itemView.setOnLongClickListener{
            val clipboard = getSystemService(holder.itemView.context, ClipboardManager::class.java)
            val clip = ClipData.newPlainText("randomtools",ibanList[position].iban)
            clipboard?.setPrimaryClip(clip)
            Toast.makeText(it.context.applicationContext,"IBAN numarası '${ibanList.get(position).baslik}' kopyalandı.",Toast.LENGTH_SHORT).show()
            true
        }
    }

    override fun getItemCount(): Int {
        return ibanList.size
    }

}