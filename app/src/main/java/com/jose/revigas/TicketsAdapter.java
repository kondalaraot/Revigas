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

public class TicketsAdapter extends RecyclerView.Adapter<TicketsAdapter.MyViewHolder> {

    private List<Ticket> mTickets;
    private Context mContext;

    DateFormat fromFormat = new SimpleDateFormat("yyyy-MM-dd");

    SimpleDateFormat toDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIvEmail;
//        private TextView mIdValue;
//        private TextView mIdUserValue;
        private TextView mNumberValue;
        private TextView mDirectionValue;
        private TextView mEstadoValue;
        private TextView mTipoValue;
        private TextView mFechaValue;
        private TextView mObservValue;




        public MyViewHolder(View view) {
            super(view);
//            view.setOnClickListener(this);
            mIvEmail = (ImageView)view.findViewById( R.id.iv_email );
//            mIdValue = (TextView)view.findViewById( R.id.id_value );
//            mIdUserValue = (TextView)view.findViewById( R.id.id_user_value );
            mNumberValue = (TextView)view.findViewById( R.id.number_value );
            mDirectionValue = (TextView)view.findViewById( R.id.direction_value );
            mEstadoValue = (TextView)view.findViewById( R.id.estado_value );
            mTipoValue = (TextView)view.findViewById( R.id.tipo_value );
            mFechaValue = (TextView)view.findViewById( R.id.fecha_value );
            mObservValue = (TextView)view.findViewById( R.id.observ_value );
        }

    }

    public TicketsAdapter(Context context,List<Ticket> ticketsList) {
        this.mContext = context;
        this.mTickets = ticketsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tickets_list_row, viewGroup, false);
        MyViewHolder itemViewHolder = new MyViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Ticket ticket = mTickets.get(position);
//        holder.mIdValue.setText(ticket.getID());
//        holder.mIdUserValue.setText(ticket.getIDUSER());
        holder.mNumberValue.setText(ticket.getNumber());
        holder.mDirectionValue.setText(ticket.getDirection());
        holder.mEstadoValue.setText(ticket.getESTADO());
        holder.mTipoValue.setText(ticket.getTipo());
        try {
            Date dateFromat = fromFormat.parse(ticket.getFecha());
            holder.mFechaValue.setText(toDateFormat.format(dateFromat));
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.mObservValue.setText(ticket.getObservacion());
        holder.mIvEmail.setTag(ticket);
        if(ticket.getESTADO().equalsIgnoreCase("ABIERTO")){
            holder.mEstadoValue.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        }else{
            holder.mEstadoValue.setTextColor(ContextCompat.getColor(mContext, R.color.red));

        }

        holder.mIvEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Position "+position);

                Ticket ticket = mTickets.get(position);
                String jsonEmailBody = new Gson().toJson(ticket);

                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{Constants.TO_EMAIL});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, Constants.EMAIL_SUBJECT);
                Date dateFromat = null;
                String fetchaDateFormatted ="";
                try {
                    dateFromat = fromFormat.parse(ticket.getFecha());
                    fetchaDateFormatted = toDateFormat.format(dateFromat);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                emailIntent.putExtra(
                        Intent.EXTRA_TEXT,
                        Html.fromHtml(new StringBuilder()
                               /* .append("<p><b>ID : </b>"+ ticket.getID()+"</p>")
                                .append("<p><b>ID User : </b>"+ ticket.getIDUSER()+"</p>")*/
                                .append("<p><b>Número : </b>"+ ticket.getNumber()+"</p>")
                                .append("<p><b>DIRECCIÓN : </b>"+ ticket.getDirection()+"</p>")
                                .append("<p><b>ESTADO : </b>"+ ticket.getESTADO()+"</p>")
                                .append("<p><b>Tipo : </b>"+ ticket.getTipo()+"</p>")
                                .append("<p><b>Fecha : </b>"+ fetchaDateFormatted+"</p>")
                                .append("<p><b>Observación : </b>"+ ticket.getObservacion()+"</p>")
                               /* .append("<a>http://www.google.com</a>")
                                .append("<small><p>More content</p></small>")*/
                                .toString())
                );
/* Send it off to the Activity-Chooser */
                mContext.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mTickets.size();
    }
}
