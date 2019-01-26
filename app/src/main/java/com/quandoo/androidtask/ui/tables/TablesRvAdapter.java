package com.quandoo.androidtask.ui.tables;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.quandoo.androidtask.data.models.Customer;
import com.quandoo.androidtask.utils.Logger;
import com.quandoo.androidtask.R;
import com.quandoo.androidtask.data.models.Table;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TablesRvAdapter extends RecyclerView.Adapter<TablesRvAdapter.TableViewHolder> implements Logger {


    interface TableClickListener {
        void onTableItemClick(Table clickedTable);
    }

    private final List<Table> tables;
    private final TableClickListener clickLstnr;

    TablesRvAdapter(final List<Table> tables, final @NonNull TableClickListener clickLstnr) {
        this.tables = tables;
        this.clickLstnr = clickLstnr;
    }


    @NonNull
    @Override
    public TableViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.table_cell, viewGroup, false);
        return new TableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TableViewHolder viewHolder, int i) {
        Table table = this.tables.get(i);

        viewHolder.tableId.setText("" + table.getId());

        //TODO : Set name and color depending on reservation status
        if (table.getReservedBy() != null) {
            viewHolder.reservingCustomerName.setText(table.getReservedBy());
            viewHolder.reservingCustomerName.setTextColor(Color.RED);

            //load reserving user image
            Picasso.get().load(findUserImage(table.getReservedBy())).into(viewHolder.avatarImage);
            viewHolder.avatarImage.setVisibility(View.VISIBLE);

        } else {
            viewHolder.reservingCustomerName.setText("Free");
            viewHolder.reservingCustomerName.setTextColor(Color.GREEN);
            viewHolder.avatarImage.setVisibility(View.INVISIBLE);
        }


        viewHolder.tableImage.setImageResource(getTableShapeImageResourceId(table.getShape()));
        viewHolder.itemView.setOnClickListener(v -> clickLstnr.onTableItemClick(table));
    }

    private String findUserImage(String userFirstNameLastName) {
        for (Customer customer : TablesActivity.customers) {
            String fullName = customer.getFirstName() + " " + customer.getLastName();
            if (fullName.equals(userFirstNameLastName)) {
                return customer.getImageUrl();
            }
        }
        return null;
    }

    private int getTableShapeImageResourceId(String tableShape) {
        switch (tableShape) {
            case "circle":
                return R.drawable.ic_circle;
            case "square":
                return R.drawable.ic_square;
            default:
                return R.drawable.ic_rectangle;
        }

    }

    @Override
    public int getItemCount() {
        return tables.size();
    }

    public static class TableViewHolder extends RecyclerView.ViewHolder {

        final TextView tableId;
        final TextView reservingCustomerName;
        final ImageView tableImage;
        final ImageView avatarImage;

        TableViewHolder(View itemView) {
            super(itemView);

            tableId = itemView.findViewById(R.id.tableId);
            reservingCustomerName = itemView.findViewById(R.id.reservingCustomerName);
            tableImage = itemView.findViewById(R.id.tableImageView);
            avatarImage = itemView.findViewById(R.id.avatarImageView);
        }
    }
}