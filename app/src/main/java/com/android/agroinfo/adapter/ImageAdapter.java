package com.android.agroinfo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.android.agroinfo.R;
import com.android.agroinfo.activity.ItemDetailActivity;
import com.bumptech.glide.Glide;
import com.android.agroinfo.model.Images;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends ArrayAdapter<Images> implements Filterable {

    private final Context context;
    private final List<Images> imagesList;
    private final List<Images> filteredList;

    public ImageAdapter(Context context, List<Images> imagesList) {
        super(context, R.layout.item_image, new ArrayList<>(imagesList));
        this.context = context;
        this.imagesList = imagesList;
        this.filteredList = new ArrayList<>(imagesList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
        }

        Images currentImage = filteredList.get(position);

        ImageView imageView = convertView.findViewById(R.id.image_card);
        TextView textView = convertView.findViewById(R.id.image_text);

        textView.setText(currentImage.getName());
        Glide.with(context).load(currentImage.getImageUrl()).into(imageView);

        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ItemDetailActivity.class);
            intent.putExtra("NAME", currentImage.getName());
            intent.putExtra("DESCRIPTION", currentImage.getDescription());
            context.startActivity(intent);
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public Images getItem(int position) {
        return filteredList.get(position);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<Images> suggestions = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    suggestions.addAll(imagesList);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (Images image : imagesList) {
                        if (image.getName().toLowerCase().contains(filterPattern)) {
                            suggestions.add(image);
                        }
                    }
                }

                results.values = suggestions;
                results.count = suggestions.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList.clear();
                filteredList.addAll((List<Images>) results.values);
                notifyDataSetChanged();
            }
        };
    }

    public void addMoreItems(List<Images> newImages) {
        imagesList.addAll(newImages);
        filteredList.addAll(newImages);
        notifyDataSetChanged();
    }
}