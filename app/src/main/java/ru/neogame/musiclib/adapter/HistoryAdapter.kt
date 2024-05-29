package ru.neogame.musiclib.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.persistableBundleOf
import androidx.recyclerview.widget.RecyclerView
import ru.neogame.musiclib.databinding.HistoryRowBinding

class HistoryAdapter(private  val hashData: List<HashMap<String, String>>) :
    RecyclerView.Adapter<HistoryAdapter.MyViewHolder>() {



        class MyViewHolder (private val binding: HistoryRowBinding) : RecyclerView.ViewHolder(binding.root){

            fun bindData(dat : String, nam : String) {

                binding.dateInRow.text = dat
                binding.titleInRow.text = nam
            }

        }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): HistoryAdapter.MyViewHolder {

        val binding = HistoryRowBinding.inflate(LayoutInflater.from(p0.context),p0,false)
        return MyViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return hashData.size
    }

    override fun onBindViewHolder(p0: HistoryAdapter.MyViewHolder, p1: Int) {
        p0.bindData(hashData.elementAt(p1)["d"].toString(), hashData.elementAt(p1)["n"].toString())
    }
}