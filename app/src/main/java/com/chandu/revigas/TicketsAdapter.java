package com.chandu.revigas;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by KTirumalsetty on 10/13/2016.
 */

public class TicketsAdapter extends RecyclerView.Adapter<TicketsAdapter.MyViewHolder> {

    private List<Ticket> mTickets;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvTicketName;
        private TextView mTvAdmType;
        private TextView mTvRoomNo;
        private TextView mTvFacilityName;
        private TextView mTvAdmReason;
        private TextView mTvAdmiDate;

        public MyViewHolder(View view) {
            super(view);
//            view.setOnClickListener(this);
            mTvTicketName = (TextView)view.findViewById( R.id.tv_patient_name );
            mTvAdmType = (TextView)view.findViewById( R.id.tv_adm_type );
            mTvRoomNo = (TextView)view.findViewById( R.id.tv_room_no );
            mTvFacilityName = (TextView)view.findViewById( R.id.tv_facility_name );
            mTvAdmReason = (TextView)view.findViewById( R.id.tv_adm_reason );
            mTvAdmiDate = (TextView)view.findViewById( R.id.tv_admi_date );
        }

    }

    public TicketsAdapter(List<Ticket> ticketsList) {
        this.mTickets = ticketsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tickets_list_row, viewGroup, false);
        MyViewHolder itemViewHolder = new MyViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Ticket ticket = mTickets.get(position);
        holder.mTvTicketName.setText("ESTADO "+ticket.getESTADO());
        holder.mTvAdmiDate.setText("Fecha "+ticket.getDirection());
        holder.mTvAdmReason.setText("Observacion "+ticket.getObservacion());
        holder.mTvRoomNo.setText("Number #"+ticket.getNumber());
        holder.mTvFacilityName.setText("Direction "+ticket.getDirection());
        holder.mTvAdmType.setText("IDUSER"+ticket.getIDUSER());

    }

    @Override
    public int getItemCount() {
        return mTickets.size();
    }
}
