package android.rafid.gstore.ViewHolder;

import android.rafid.gstore.Interface.ItemClickListener;
import android.rafid.gstore.R;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Alucard on 2017-12-09.
 */

public class AllGamesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView allgames_name;
    public ImageView allgames_image;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public AllGamesViewHolder(View itemView) {
        super(itemView);

        allgames_name = (TextView) itemView.findViewById(R.id.allgames_name);
        allgames_image = (ImageView) itemView.findViewById(R.id.allgames_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}