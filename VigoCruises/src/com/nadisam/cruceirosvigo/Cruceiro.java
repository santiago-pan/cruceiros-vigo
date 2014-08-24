package com.nadisam.cruceirosvigo;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;

public class Cruceiro implements Comparable<Cruceiro>
{
    private String llegada;
    private String buque;
    private int    slora;
    private String horaLlegada;
    private String horaSalida;
    private String procedencia;
    private String destino;
    private String consignatario;
    private String picture;
    private String other;

    public Cruceiro()
    {}

    public String getLlegada()
    {
        return llegada;
    }

    public void setLlegada(String llegada)
    {
        this.llegada = llegada;
    }

    public String getBuque()
    {
        return buque;
    }

    public void setBuque(String buque)
    {
        this.buque = buque;
    }

    public int getSlora()
    {
        return slora;
    }

    public void setSlora(int slora)
    {
        this.slora = slora;
    }

    public String getHoraLlegada()
    {
        return horaLlegada;
    }

    public void setHoraLlegada(String horaLlegada)
    {
        this.horaLlegada = horaLlegada;
    }

    public String getHoraSalida()
    {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida)
    {
        this.horaSalida = horaSalida;
    }

    public String getProcedencia()
    {
        return procedencia;
    }

    public void setProcedencia(String procedencia)
    {
        this.procedencia = procedencia;
    }

    public String getDestino()
    {
        return destino;
    }

    public void setDestino(String destino)
    {
        this.destino = destino;
    }

    public String getConsignatario()
    {
        return consignatario;
    }

    public void setConsignatario(String consignatario)
    {
        this.consignatario = consignatario;
    }

    public String getPicture()
    {
        return picture;
    }

    public void setPicture(String picture)
    {
        this.picture = picture;
    }

    public String getOther()
    {
        return other;
    }

    public void setOther(String other)
    {
        this.other = other;
    }

    @Override
    /**
     * Compare cruisers slora
     * returns -1,  c less than this
     * return 0, c == than this
     * return 1 c greater than this
     *   
     */
    public int compareTo(Cruceiro c)
    {
        int slora = c.getSlora();

        if (slora < this.slora)
        {
            return -1;
        }
        else if (slora > this.slora)
        {
            return 1;
        }
        // If they have the same slora we sort by arival data
        else
        {
            String arrival = c.getLlegada();

            String myFormatString = "dd/MM/yyyy"; // for example
            SimpleDateFormat df = new SimpleDateFormat(myFormatString);
            try
            {
                Date date1 = df.parse(arrival);
                Date date2 = df.parse(this.llegada);
                if (date1.before(date2))
                {
                    return -1;
                }
                else if (date2.before(date1))
                {
                    return 1;
                }
                else
                {
                    return 0;
                }
            }
            catch (Exception e)
            {
                Log.d("Cruceiro", "Date comparision");
            }

            return 0;
        }
    }
}
