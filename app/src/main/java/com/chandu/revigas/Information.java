package com.chandu.revigas;

import java.io.Serializable;

/**
 * Created by KTirumalsetty on 11/11/2016.
 */

public class Information implements Serializable {

   /* "date_of_alert": "2017-11-08",
            "estado": "ABIERTO",
            "poliza": "1",
            "date_of_revision": "2016-10-21",
            "direction": "CASA DEL PAVO",
            "ID": "1",
            "obra": "A0000095",
            "IDUSER": "11"*/

    public String date_of_alert;
    public String estado;
    public String poliza;
    public String date_of_revision;
    public String direction;
    public String ID;
    public String obra;
    public String IDUSER;

    public Information() {
    }

    public String getDate_of_alert() {
        return date_of_alert;
    }

    public void setDate_of_alert(String date_of_alert) {
        this.date_of_alert = date_of_alert;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPoliza() {
        return poliza;
    }

    public void setPoliza(String poliza) {
        this.poliza = poliza;
    }

    public String getDate_of_revision() {
        return date_of_revision;
    }

    public void setDate_of_revision(String date_of_revision) {
        this.date_of_revision = date_of_revision;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getObra() {
        return obra;
    }

    public void setObra(String obra) {
        this.obra = obra;
    }

    public String getIDUSER() {
        return IDUSER;
    }

    public void setIDUSER(String IDUSER) {
        this.IDUSER = IDUSER;
    }




}
