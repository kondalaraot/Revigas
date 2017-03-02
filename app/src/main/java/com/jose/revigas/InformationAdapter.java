package com.jose.revigas;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by KTirumalsetty on 10/13/2016.
 */

public class InformationAdapter extends RecyclerView.Adapter<InformationAdapter.MyViewHolder> {

    private List<Information> mInformations;
    private Context mContext;

    DateFormat fromFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat toDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIvEmail;
//        private TextView mIdValue;
//        private TextView mIdUserValue;
        private TextView mPolizaValue;
        private TextView mEstadoValue;
        private TextView mObraValue;
        private TextView mDateOfrevValue;
        private TextView mDirectionValue;
        private TextView mDateOfAlertValue;


        public MyViewHolder(View view) {
            super(view);
//            view.setOnClickListener(this);
            mIvEmail = (ImageView)view.findViewById( R.id.iv_email );

//            mIdValue = (TextView)view.findViewById( R.id.id_value );
//            mIdUserValue = (TextView)view.findViewById( R.id.id_user_value );
            mPolizaValue = (TextView)view.findViewById( R.id.poliza_value );
            mEstadoValue = (TextView)view.findViewById( R.id.estado_value );
            mObraValue = (TextView)view.findViewById( R.id.obra_value );
            mDateOfrevValue = (TextView)view.findViewById( R.id.dateOfrev_value );
            mDirectionValue = (TextView)view.findViewById( R.id.direction_value );
            mDateOfAlertValue = (TextView)view.findViewById( R.id.date_of_alert_value );
        }

    }

    public InformationAdapter(Context context,List<Information> informationsList) {
        this.mContext = context;
        this.mInformations = informationsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.informations_list_row, viewGroup, false);
        MyViewHolder itemViewHolder = new MyViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Information information = mInformations.get(position);
//        holder.mIdUserValue.setText(information.getIDUSER());
//        holder.mIdValue.setText(information.getID());
        holder.mPolizaValue.setText(information.getPoliza());
        holder.mEstadoValue.setText(information.getEstado());
        holder.mObraValue.setText(information.getObra());

        try {
            Date dateFromat = fromFormat.parse(information.getDate_of_revision());
            holder.mDateOfrevValue.setText(toDateFormat.format(dateFromat));
        } catch (Exception e) {
            e.printStackTrace();
        }

//        holder.mDateOfrevValue.setText(information.getDate_of_revision());
        holder.mDirectionValue.setText(information.getDirection());

        try {
            Date dateFromat = fromFormat.parse(information.getDate_of_alert());
            holder.mDateOfAlertValue.setText(toDateFormat.format(dateFromat));
        } catch (Exception e) {
            e.printStackTrace();
        }

//        holder.mDateOfAlertValue.setText(information.getDate_of_alert());

        if(information.getEstado().equalsIgnoreCase("Abierto") || information.getEstado().equalsIgnoreCase("ABIERTO")){
            holder.mEstadoValue.setTextColor(ContextCompat.getColor(mContext, android.R.color.holo_green_light));
        }else {
            holder.mEstadoValue.setTextColor(ContextCompat.getColor(mContext, R.color.red));

        }

        holder.mIvEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Position "+position);

                Information info = mInformations.get(position);
                String jsonEmailBody = new Gson().toJson(info);

                String htmlBody = "<html> <body> " +
                        "<font color='#ff33b5e5'>ID </font>" +info.getID()+"</br>"+
                        "<font color='#ff33b5e5'>ID User </font>" +info.getIDUSER()+"</br>"+
                        "<font color='#ff33b5e5'>Poliza </font>" +info.getPoliza()+"</br>"+
                        "<font color='#ff33b5e5'>Estado </font>" +info.getEstado()+"</br>"+
                        "<font color='#ff33b5e5'>Obra </font>" +info.getObra()+"</br>"+
                        "<font color='#ff33b5e5'>Date of revision </font>" +info.getDate_of_revision()+"</br>"+
                        "<font color='#ff33b5e5'>Direction </font>" +info.getDirection()+"</br>"+
                        "<font color='#ff33b5e5'>Date of Alert </font>" +info.getDate_of_alert()+"</br>"+
                        "</body></html>";
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{Constants.TO_EMAIL});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, Constants.EMAIL_SUBJECT);
//                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml(htmlBody));

                Date dateFromat = null;
                String dateOfRevFormatted ="";
                String getDate_of_alertFormatted ="";
                try {
                    dateFromat = fromFormat.parse(info.getDate_of_revision());
                    dateOfRevFormatted = toDateFormat.format(dateFromat);
                    dateFromat = fromFormat.parse(info.getDate_of_alert());
                    getDate_of_alertFormatted = toDateFormat.format(dateFromat);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                emailIntent.putExtra(
                        Intent.EXTRA_TEXT,
                        Html.fromHtml(new StringBuilder()
                               /* .append("<p><b>ID : </b>"+ info.getID()+"</p>")
                                .append("<p><b>ID User : </b>"+ info.getIDUSER()+"</p>")*/
                                .append("<p><b>Poliza : </b>"+ info.getPoliza()+"</p>")
                                .append("<p><b>Estado : </b>"+ info.getEstado()+"</p>")
                                .append("<p><b>Obra : </b>"+ info.getObra()+"</p>")
                                .append("<p><b>Fecha de revision : </b>"+ dateOfRevFormatted+"</p>")
                                .append("<p><b>Dirección : </b>"+ info.getDirection()+"</p>")
                                .append("<p><b>Fecha Aviso : </b>"+ getDate_of_alertFormatted+"</p>")
                               /* .append("<a>http://www.google.com</a>")
                                .append("<small><p>More content</p></small>")*/
                                .toString()));

                mContext.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            }
        });


    }

    @Override
    public int getItemCount() {
        return mInformations.size();
    }
}
