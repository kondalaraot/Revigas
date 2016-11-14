package com.chandu.revigas;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by KTirumalsetty on 10/13/2016.
 */

public class TicketsAdapter extends RecyclerView.Adapter<TicketsAdapter.MyViewHolder> {

    private List<Ticket> mTickets;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIvEmail;
        private TextView mIdValue;
        private TextView mIdUserValue;
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
            mIdValue = (TextView)view.findViewById( R.id.id_value );
            mIdUserValue = (TextView)view.findViewById( R.id.id_user_value );
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
        holder.mIdValue.setText(ticket.getID());
        holder.mIdUserValue.setText(ticket.getIDUSER());
        holder.mNumberValue.setText(ticket.getNumber());
        holder.mDirectionValue.setText(ticket.getDirection());
        holder.mEstadoValue.setText(ticket.getESTADO());
        holder.mTipoValue.setText(ticket.getTipo());
        holder.mFechaValue.setText(ticket.getFecha());
        holder.mObservValue.setText(ticket.getObservacion());
        holder.mIvEmail.setTag(ticket);

        holder.mIvEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Position "+position);
                 /* Ticket ticket = mTickets.get(position);
                Intent intent = new Intent(TicketsActivity.this,InformationActivity.class);
                intent.putExtra("KEY_TICKET_OBJ",ticket);
                startActivity(intent);*/
                Ticket ticket = mTickets.get(position);
                String jsonEmailBody = new Gson().toJson(ticket);
                /* Create the Intent */
                String htmlBody = "<html> <body> " +
                        "<font color='#ff33b5e5'>ID </font>" +ticket.getID()+"</br>"+
                        "<font color='#ff33b5e5'>ID User </font>" +ticket.getIDUSER()+"</br>"+
                        "<font color='#ff33b5e5'>Number </font>" +ticket.getNumber()+"</br>"+
                        "<font color='#ff33b5e5'>Direction </font>" +ticket.getDirection()+"</br>"+
                        "<font color='#ff33b5e5'>ESTADO </font>" +ticket.getESTADO()+"</br>"+
                        "<font color='#ff33b5e5'>Tipo </font>" +ticket.getTipo()+"</br>"+
                        "<font color='#ff33b5e5'>Fecha </font>" +ticket.getFecha()+"</br>"+
                        "<font color='#ff33b5e5'>Observation </font>" +ticket.getObservacion()+"</br>"+
                        "</body></html>";
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
/* Fill it with Data */
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{Constants.TO_EMAIL});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, Constants.EMAIL_SUBJECT);
//                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml(htmlBody));
                emailIntent.putExtra(
                        Intent.EXTRA_TEXT,
                        Html.fromHtml(new StringBuilder()
                                .append("<p><b>ID : </b>"+ ticket.getID()+"</p>")
                                .append("<p><b>ID User : </b>"+ ticket.getIDUSER()+"</p>")
                                .append("<p><b>Number : </b>"+ ticket.getNumber()+"</p>")
                                .append("<p><b>Direction : </b>"+ ticket.getDirection()+"</p>")
                                .append("<p><b>ESTADO : </b>"+ ticket.getESTADO()+"</p>")
                                .append("<p><b>Tipo : </b>"+ ticket.getTipo()+"</p>")
                                .append("<p><b>Fecha : </b>"+ ticket.getFecha()+"</p>")
                                .append("<p><b>Observation : </b>"+ ticket.getObservacion()+"</p>")
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
