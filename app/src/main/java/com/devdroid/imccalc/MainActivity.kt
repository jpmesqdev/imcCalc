package com.devdroid.imccalc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class MainActivity : AppCompatActivity() {

    lateinit var rv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dataSet = listOf<MenuButton>(
            MenuButton("IMC", R.drawable.body),
            MenuButton("TMB", R.drawable.clock),
        )

        rv = findViewById(R.id.main_rv)
        rv.adapter = MainAdapter(dataSet)
        rv.layoutManager = GridLayoutManager(this, 2)
    }

    private inner class MainAdapter(val dataSet: List<MenuButton>) :
        Adapter<MainAdapter.MainHolder>() {
        private inner class MainHolder(view: View) : ViewHolder(view) {
            fun bind(currentMenu: MenuButton) {
                itemView.findViewById<TextView>(R.id.rv_title).text = currentMenu.title
                itemView.findViewById<ImageView>(R.id.rv_icon).setImageResource(currentMenu.icon)
                itemView.setOnClickListener {
                    when (currentMenu.title) {
                        "IMC" -> {
                            val intent = Intent(this@MainActivity, ImcActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
            val view = layoutInflater.inflate(R.layout.rv_cell, parent, false)
            return MainHolder(view)
        }

        override fun onBindViewHolder(holder: MainHolder, position: Int) {
            val currentMenu = dataSet[position]
            holder.bind(currentMenu)
        }

        override fun getItemCount(): Int {
            return dataSet.size
        }
    }
}