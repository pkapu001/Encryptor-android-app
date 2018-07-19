package encriptor.dragon.encriptor;


import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

public class recycerAdapter extends RecyclerView.Adapter<recycerAdapter.history_holder>
{

    //private Cursor cursor;
    ArrayList <data> dataArrayList;
    history history;

    Context ctx;

    public recycerAdapter(ArrayList<data> dataArrayList , Context ctx)
    {
        //this.cursor = c;
        this.dataArrayList = dataArrayList;
        this.ctx = ctx;
        history =(history)ctx ;
       // Log.d("hay ..... : " , "CURSER SET ");

    }

    @Override
    public history_holder onCreateViewHolder( ViewGroup parent, int viewType) {
       // Log.d("hay ..... : " , "HISTORY HOLDER RETURNING 111");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item_view,parent,false);
        history_holder h_h = new history_holder(view, history);
       // Log.d("hay ..... : " , "HISTORY HOLDER RETURNING ");
        return h_h;
    }

    @Override
    public void onBindViewHolder( history_holder holder, int position)
    {

        /*cursor.moveToPosition(cursor.getCount() - position -1);

        String ot = cursor.getString(cursor.getColumnIndex(Econtract.Econtract_entry.org_text_h));
        String mt = cursor.getString(cursor.getColumnIndex(Econtract.Econtract_entry.mod_text_h));
        String et = cursor.getString(cursor.getColumnIndex(Econtract.Econtract_entry.e_path_h));*
        Log.d("hay ..... : " , ot + "\n\n" + et + "\n\n " +mt);

        holder.org_text_v.setText(ot);
        holder.mod_text_v.setText(mt);
        holder.e_path_v.setText(et);*/

        final data b = dataArrayList.get(position);

        holder.org_text_v.setText(dataArrayList.get(position).getOrg_text());
        holder.mod_text_v.setText(dataArrayList.get(position).getMod_text());
        holder.e_path_v.setText(dataArrayList.get(position).getPath_text());
        holder.id_v.setText(dataArrayList.get(position).getTime());
        holder.itemcheck.setOnCheckedChangeListener(null);
        holder.itemcheck.setChecked(b.isSelected());
        holder.itemcheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                b.setSelected(isChecked);

            }
        });

        if(!history.inaction)
        {
            holder.itemcheck.setVisibility(View.GONE);
        }else
        {
            holder.itemcheck.setVisibility(View.VISIBLE);

        }


    }

    @Override
    public int getItemCount() {
        //return cursor.getCount();
        return dataArrayList.size();
    }




    public static class history_holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView org_text_v , mod_text_v , e_path_v ,id_v;
        CheckBox itemcheck ;
        CardView cardView ;
        history history;

        public history_holder(View itemView , history history  ) {

            super(itemView);
            org_text_v = itemView.findViewById(R.id.h_org_text);
            mod_text_v = itemView.findViewById(R.id.h_mod_text);
            e_path_v = itemView.findViewById(R.id.h_e_path);
            id_v= itemView.findViewById(R.id.id_h);
            itemcheck = itemView.findViewById(R.id.itemcheckbox);



            this.history= history;
            cardView =(CardView) itemView.findViewById(R.id.history_cardview);
            cardView.setOnLongClickListener(history);
            itemcheck.setOnClickListener(this);

        }
        


        @Override
        public void onClick(View v) {

            history.prepareselection(v,getAdapterPosition());
        }
    }

    public  void update_adapter (ArrayList<data> list)
    {
        for (data d: list)
        {
            dataArrayList.remove(d);
        }
        notifyDataSetChanged();
    }
}
