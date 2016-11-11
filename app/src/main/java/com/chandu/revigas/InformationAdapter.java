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

public class InformationAdapter extends RecyclerView.Adapter<InformationAdapter.MyViewHolder> {

    private List<Information> mInformations;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvInformationName;
        private TextView mTvAdmType;
        private TextView mTvRoomNo;
        private TextView mTvFacilityName;
        private TextView mTvAdmReason;
        private TextView mTvAdmiDate;

        public MyViewHolder(View view) {
            super(view);
//            view.setOnClickListener(this);
            mTvInformationName = (TextView)view.findViewById( R.id.tv_patient_name );
            mTvAdmType = (TextView)view.findViewById( R.id.tv_adm_type );
            mTvRoomNo = (TextView)view.findViewById( R.id.tv_room_no );
            mTvFacilityName = (TextView)view.findViewById( R.id.tv_facility_name );
            mTvAdmReason = (TextView)view.findViewById( R.id.tv_adm_reason );
            mTvAdmiDate = (TextView)view.findViewById( R.id.tv_admi_date );
        }

    }

    public InformationAdapter(List<Information> informationsList) {
        this.mInformations = informationsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.informations_list_row, viewGroup, false);
        MyViewHolder itemViewHolder = new MyViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Information information = mInformations.get(position);
        holder.mTvInformationName.setText("DATE "+information.getDate_of_alert());
        holder.mTvAdmiDate.setText("estado "+information.getEstado());
        holder.mTvAdmReason.setText("poliza "+information.getPoliza());
        holder.mTvRoomNo.setText("Revision "+information.getDate_of_revision());
        holder.mTvFacilityName.setText("Direction "+information.getDirection());
        holder.mTvAdmType.setText("Obra"+information.getObra());

    }

    @Override
    public int getItemCount() {
        return mInformations.size();
    }
}
