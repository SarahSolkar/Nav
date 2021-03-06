package com.example.sarah.nav;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Data> Mlist;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
    void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
    public FoodAdapter(Context mContext, ArrayList<Data> mlist) {
        this.mContext = mContext;
        Mlist = mlist;
        Log.d("mList", ""+Mlist);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflator = LayoutInflater.from(mContext);
        View view =  layoutInflator.inflate(R.layout.rv_food_items, parent ,false);
        ViewHolder viewHolder = new ViewHolder(view, mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Data foodItem  =  Mlist.get(position);
        ImageView image = holder.food_image;

        TextView name, price_order,rest_order;
        name = holder.food_name;
        rest_order = holder.rest_name;
        price_order = holder.food_price;

        String restaurant_name, category, del,imgname,rid;
        getIp ip = new getIp();
        del = ip.getIp();
        
        restaurant_name = foodItem.getRestaurant_name();
        category = foodItem.getCategory();
        rid = foodItem.getRid();
        imgname = foodItem.getImgname();

        //Log.d("restaurant name is",""+restaurant_name);
        //Log.d("category is",""+category);
        //Log.d("image name is",""+imgname);

        String loc = ""+del+":8080/images/"+rid+"/"+category+"/"+imgname;
        //loc  = loc.replace('\\', '/');
        //Log.d("loc",loc);

        Picasso.get().load(""+loc).centerCrop().fit().error(R.drawable.ic_launcher_background).into(image, new Callback() {
            @Override
            public void onSuccess() {
                Log.d("success","LOADED");
            }

            @Override
            public void onError(Exception e) {
                Log.d("error",""+e);
            }
        });

        rest_order.setText(restaurant_name);
        name.setText(category);
        price_order.setText("₹"+foodItem.getPrice());
    }


    @Override
    public int getItemCount() {
        return Mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView food_image;
        TextView food_name, food_price, rest_name;
        Button food_order;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView) ;
            food_image = itemView.findViewById(R.id.food_image);
            food_name = itemView.findViewById(R.id.food_name);
            food_price = itemView.findViewById(R.id.food_price);
            food_order =  itemView.findViewById(R.id.food_order);
            rest_name = itemView.findViewById(R.id.rest_name);

            food_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("On cart click", "Cart Clicked");
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }

            });
        }
    }
}
