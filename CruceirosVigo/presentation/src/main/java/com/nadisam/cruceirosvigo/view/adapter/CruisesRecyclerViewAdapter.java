package com.nadisam.cruceirosvigo.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nadisam.cruceirosvigo.R;
import com.nadisam.cruceirosvigo.domain.Cruise;
import com.nadisam.cruceirosvigo.model.CruiseModel;
import com.nadisam.cruceirosvigo.view.activity.SortMenuOptionListener;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class CruisesRecyclerViewAdapter extends RecyclerView.Adapter<CruisesRecyclerViewAdapter.ViewHolder>

{
    private List<CruiseModel> mValues;
    private String length;
    private String length_m;
    private String arrive_time;
    private String departure_time;
    private Context context;

    public CruisesRecyclerViewAdapter(Context context, List<CruiseModel> cruiseCollection)
    {
        mValues = cruiseCollection;
        length = context.getString(R.string.cruise_length);
        arrive_time = context.getString(R.string.cruise_arrive_time);
        departure_time = context.getString(R.string.cruise_departure_time);
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_cruises,
                                                                     parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        final CruiseModel cruiseModel = this.mValues.get(position);

        length = context.getResources().getString(R.string.cruise_length, cruiseModel.getLength());
        arrive_time = context.getString(R.string.cruise_arrive_time, cruiseModel.getArriveTime());
        departure_time = context.getString(R.string.cruise_departure_time, cruiseModel.getDepartureTime());

        holder.mLength.setText(length);
        holder.mName.setText(cruiseModel.getCruise());

        holder.mArrive.setText(cruiseModel.getArrive());
        holder.mArriveTime.setText(arrive_time);
        holder.mDepartureTime.setText(departure_time);
    }

    @Override
    public int getItemCount()
    {
        return mValues.size();
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    public void setCruisesCollection(List<CruiseModel> cruiseCollection)
    {
        this.validateCruisesCollection(cruiseCollection);
        this.mValues = cruiseCollection;
        this.notifyDataSetChanged();
    }

    public void sortListBy(int type) {
        if (type == SortMenuOptionListener.SORT_BY_DATE) {
            this.sortByDate();
        }
        if (type == SortMenuOptionListener.SORT_BY_LENGTH) {
            this.sortByLength();
        }
    }

    public void sortByDate()
    {
        Collections.sort(this.mValues, dateComparator);
        this.notifyDataSetChanged();
    }

    public void sortByLength()
    {
        Collections.sort(this.mValues, lengthComparator);
        this.notifyDataSetChanged();
    }

    private void validateCruisesCollection(Collection<CruiseModel> cruiseCollection)
    {
        if (cruiseCollection == null)
        {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public final View mView;

        public final TextView mArrive;
        public final TextView mName;
        public final TextView mLength;
        public final TextView mArriveTime;
        public final TextView mDepartureTime;

        //public CruiseModel mItem;

        public ViewHolder(View view)
        {
            super(view);
            mView = view;

            mArrive         = (TextView) view.findViewById(R.id.cruise_arrive);
            mName           = (TextView) view.findViewById(R.id.cruise_name);
            mLength         = (TextView) view.findViewById(R.id.cruise_length);
            mArriveTime     = (TextView) view.findViewById(R.id.cruise_arrive_time);
            mDepartureTime  = (TextView) view.findViewById(R.id.cruise_departure_time);
        }

        @Override
        public String toString()
        {
            return super.toString();
        }
    }

    // Create length and date comparators
    private Comparator<CruiseModel> lengthComparator = (o1, o2) -> {
        if (o1.getLength() > o2.getLength())
        {
            return -1;
        }
        else if (o1.getLength() < o2.getLength())
        {
            return 1;
        }
        else
        {
            return 0;
        }
    };


    private String myFormatString = "dd/MM/yyyy";
    private SimpleDateFormat df = new SimpleDateFormat(myFormatString);

    private Comparator<CruiseModel> dateComparator = (o1, o2) -> {

        try
        {
            Date d1 = df.parse(o1.getArrive());
            Date d2 = df.parse(o2.getArrive());

            if (d1.before(d2))
            {
                return 1;
            }
            else if (d2.before(d1))
            {
                return -1;
            }
            else
            {
                return 0;
            }
        }
        catch (Exception e)
        {
            return 0;
        }
    };
}
