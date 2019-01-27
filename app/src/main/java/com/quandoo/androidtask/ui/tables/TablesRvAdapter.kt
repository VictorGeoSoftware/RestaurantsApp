package com.quandoo.androidtask.ui.tables

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.quandoo.androidtask.R
import com.quandoo.androidtask.data.models.Table
import com.quandoo.androidtask.utils.Logger
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.table_cell.view.*

class TablesRvAdapter (
        private val tables: List<Table>,
        private val clickListener: TableClickListener) : RecyclerView.Adapter<TablesRvAdapter.TableViewHolder>(), Logger {


    interface TableClickListener {
        fun onTableItemClick(clickedTable: Table)
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): TableViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.table_cell, viewGroup, false)
        return TableViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: TableViewHolder, i: Int) {
        val table = this.tables[i]
        viewHolder.bind(table, clickListener)
    }

    override fun getItemCount(): Int {
        return tables.size
    }



    class TableViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(table: Table, clickListener: TableClickListener) = with(itemView) {
            tableId.text = table.id.toString()

            //TODO : Set name and color depending on reservation status
            if (table.reservedBy != null) {
                reservingCustomerName.text = table.reservedBy
                reservingCustomerName.setTextColor(Color.RED)

                //load reserving user image
                table.avatarImageReserve?.run {
                    // TODO :: avatarImageReserver -> check when TablesActivity refactoring!!
                    Picasso.get().load(table.avatarImageReserve).into(avatarImageView)
                    avatarImageView.visibility = View.VISIBLE
                }

            } else {
                reservingCustomerName.text = context.getText(R.string.free)
                reservingCustomerName.setTextColor(Color.GREEN)
                avatarImageView.visibility = View.INVISIBLE
            }


            tableImageView.setImageResource(getTableShapeImageResourceId(table.shape!!))
            itemView.setOnClickListener { v -> clickListener.onTableItemClick(table) }
        }

        private fun getTableShapeImageResourceId(tableShape: String): Int {
            return when (tableShape) {
                "circle" -> R.drawable.ic_circle
                "square" -> R.drawable.ic_square
                else -> R.drawable.ic_rectangle
            }
        }
    }
}