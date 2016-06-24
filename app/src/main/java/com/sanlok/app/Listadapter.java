package com.sanlok.app;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AnalogClock;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
//import org.apache.commons.lang3.text.WordUtils;
import java.lang.Object;

/**
 * Created by Anip Mehta on 10/4/2015.
 */
public class Listadapter extends BaseAdapter {

    String[] name,date,time,teacher;
    Context context;
    LayoutInflater layoutInflater;

   // DateFormat df5 = new SimpleDateFormat("E, MMM dd yyyy");
    public  Listadapter(String[] n,String[] d,String[] t,String[] e,Context context){
        this.context = context;
            layoutInflater = LayoutInflater.from(context);
         name=n;

         date=d;
        time=t;
        teacher=e;


    }

    @Override
    public int getCount() {
        return name.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //LayoutInflater inflater = getLayoutInflater();
        convertView= layoutInflater.inflate(R.layout.custom_list_view, null);
        TextView na=(TextView)convertView.findViewById(R.id.textView);
        TextView da=(TextView)convertView.findViewById(R.id.date);
       // ImageView ac=(ImageView)convertView.findViewById(R.id.im1);
        TextView ti=(TextView)convertView.findViewById(R.id.time);
        //TextView te=(TextView)convertView.findViewById(R.id.teacher);
        String mo=null;


       DateFormat inputDF  = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Date date1 = null;
        try {
            date1 = inputDF.parse(date[position]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.setTime(date1);
        int month = cal.get(Calendar.MONTH);
        month=month+1;
        String mm=String.valueOf(month);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);
        System.out.println(month + " - " + day + " - " + year);
        if(month==1)
            mo="Jan";
        if(mm.contains("02"))
            mo = "Feb";
        if(mm.contains("03"))
            mo="Mar";
        if(mm.contains("04"))
            mo="Apr";
        if(mm.contains("05"))
            mo="May";
        if(mm.contains("06"))
            mo="Jun";
        if(mm.contains("07"))
            mo="Jul";
        if(mm.contains("08"))
            mo="Aug";
        if(mm.contains("09"))
            mo="Sep";
        if(month==10)
            mo="Oct";
        if(mm.equals("11"))
            mo="Nov";
        if(mm.equals("12"))
            mo="Dec";

        na.setText((name[position].substring(0,1).toUpperCase()+name[position].substring(1)));
        da.setText(day+" "+mo+","+year);
        String [] hourminsec=time[position].split(":");
        int hour=Integer.parseInt(hourminsec[0]);
        int min=Integer.parseInt(hourminsec[1]);
        int sec=Integer.parseInt(hourminsec[2]);
        Log.i("hell","hours"+hour+"min"+min+"sec"+sec );
        int [] prgmImages={R.drawable.fnine,R.drawable.twelve};
        if(time[position].contains("09:00:00"))
            ti.setText("09:00 am");
        else if(time[position].contains("12:00:00"))

            ti.setText("12:00 noon");
        //te.setText("Teacher:"+teacher[position].substring(0,1).toUpperCase()+teacher[position].substring(1));
       // View row;
       // row = inflater.inflate(R.layout.custom_list_view, parent, false);
        return convertView;
    }
}
