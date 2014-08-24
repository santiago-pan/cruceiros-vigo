package com.nadisam.cruceirosvigo;

import java.net.URL;
import java.util.Collections;
import java.util.Vector;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CruceirosActivity extends ActionBarActivity implements ActionBar.OnNavigationListener
{

    public String                     webContent       = "";
    public Vector<Cruceiro>           cruceiros        = new Vector<Cruceiro>();
    public Vector<Cruceiro>           cruceirosSlora   = new Vector<Cruceiro>();
    public Vector<Cruceiro>           cruceirosLlegada = new Vector<Cruceiro>();
    private ListView                  cruceirosListView;
    private EfficientAdapterCruceiros adapter;
    private ProgressDialog            pd;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.cruceiro_list);

        DownloadFilesTask task = new DownloadFilesTask();
        try
        {
            task.execute(new URL("http://google.es"));
            pd = ProgressDialog.show(this, "", getResources().getString(R.string.text_loading), true);
        }
        catch (Exception e)
        {
            pd.dismiss();
        }

        showCruceirosListView();
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId)
    {
        // mSelected.setText("Selected: " + mLocations[itemPosition]);
        Log.d("Cruceiro", "Selected: " + itemId);
        if (1 == itemId)
        {
            Collections.sort(cruceirosSlora);
            cruceiros = cruceirosSlora;
        }
        else
        {
            cruceiros = cruceirosLlegada;
        }
        adapter.notifyDataSetChanged();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle presses on the action bar items
        switch (item.getItemId())
        {
        case R.id.action_arrival:
            cruceiros = cruceirosLlegada;
            adapter.notifyDataSetChanged();
            return true;
        case R.id.action_slora:
            Collections.sort(cruceirosSlora);
            cruceiros = cruceirosSlora;
            adapter.notifyDataSetChanged();
            return true;
        }
        return false;
    }

    private class DownloadFilesTask extends AsyncTask<URL, Integer, Long>
    {
        protected Long doInBackground(URL... urls)
        {
            int count = urls.length;
            long totalSize = 0;

            String urlLlegada = "http://www.apvigo.com/control.php?sph=a_iap=1110&p_rpp=1";
            // String url =
            // "http://www.apvigo.com/control.php?sph=a_lst_nrt=106%25%25a_lst_npa=1%25%25a_lst_cod=6%25%25a_lst_sod=DESC%25%25a_iap=1110";
            WebFetcher fetcher;
            try
            {
                fetcher = new WebFetcher(urlLlegada);
                // fetcher.getPageHeader();
                webContent = fetcher.getPageContent();

                // Process web
                processWebContent(webContent);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return totalSize;
        }

        protected void onProgressUpdate(Integer... progress)
        {}

        protected void onPostExecute(Long result)
        {
            adapter.notifyDataSetChanged();
            pd.dismiss();
        }
    }

    public void processWebContent(String web)
    {
        // Get beginning of boats information
        String table = "";
        String initDelimiter = "<tr class='fila1'";
        table = web.substring(web.indexOf(initDelimiter));

        // Split by row
        String columnSplit = "<tr class='fila";
        String[] columns = table.split(columnSplit);
        int numColumns = columns.length;

        // Split each row by column
        String rowSplit = "class=\"negro\">";
        for (int col = 0; col < numColumns; col++)
        {
            String[] rows = columns[col].split(rowSplit);

            // Get ship info
            if (rows.length == 9)
            {
                // Create Cruceiro
                Cruceiro c = new Cruceiro();
                int cnt = 1;
                c.setLlegada(rows[cnt].substring(0, rows[cnt].indexOf("</span>")));
                cnt++;
                c.setBuque(rows[cnt].substring(0, rows[cnt].indexOf("</span>")));
                cnt++;
                c.setSlora(Integer.valueOf(rows[cnt].substring(0, rows[cnt].indexOf("</span>"))));
                cnt++;
                c.setHoraLlegada(rows[cnt].substring(0, rows[cnt].indexOf("</span>")));
                cnt++;
                c.setHoraSalida(rows[cnt].substring(0, rows[cnt].indexOf("</span>")));
                cnt++;
                c.setProcedencia(rows[cnt].substring(0, rows[cnt].indexOf("</span>")));
                cnt++;
                c.setDestino(rows[cnt].substring(0, rows[cnt].indexOf("</span>")));

                cruceirosSlora.add(c);
                cruceirosLlegada.add(c);

            }
        }

        // By default sort by llegada
        cruceiros = cruceirosLlegada;
    }

    private class EfficientAdapterCruceiros extends BaseAdapter
    {
        private LayoutInflater mInflater;
        private Bitmap         mIcon;

        public EfficientAdapterCruceiros(Context context)
        {
            // Cache the LayoutInflate to avoid asking for a new one each time.
            mInflater = LayoutInflater.from(context);

            // Icons bound to the rows.
            mIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_ship);
        }

        /**
         * The number of items in the list is determined by the number of speeches in our array.
         * 
         * @see android.widget.ListAdapter#getCount()
         */
        public int getCount()
        {
            if (null != cruceiros)
            {
                return cruceiros.size();
            }
            else
            {
                return 0;
            }
        }

        /**
         * Since the data comes from an array, just returning the index is sufficient to get at the data. If we were using a more complex data structure, we would return whatever object represents one row in the list.
         * 
         * @see android.widget.ListAdapter#getItem(int)
         */
        public Object getItem(int position)
        {
            return position;
        }

        /**
         * Use the array index as a unique id.
         * 
         * @see android.widget.ListAdapter#getItemId(int)
         */
        public long getItemId(int position)
        {
            return position;
        }

        /**
         * Make a view to hold each row.
         * 
         * @see android.widget.ListAdapter#getView(int, android.view.View, android.view.ViewGroup)
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ViewHolder holder;

            if (convertView == null)
            {
                convertView = mInflater.inflate(R.layout.list_item2, null);

                // Creates a ViewHolder and store references to the two children
                // views we want to bind data to.
                holder = new ViewHolder();
                holder.llegada = (TextView) convertView.findViewById(R.id.item_llegada);
                holder.textName = (TextView) convertView.findViewById(R.id.item_name);
                holder.textSlora = (TextView) convertView.findViewById(R.id.item_slora);
                holder.horaLlegada = (TextView) convertView.findViewById(R.id.item_hora_llegada);
                holder.horaSalida = (TextView) convertView.findViewById(R.id.item_hora_salida);
                holder.icon = (ImageView) convertView.findViewById(R.id.item_image);

                convertView.setTag(holder);
            }
            else
            {
                // Get the ViewHolder back to get fast access to the TextView
                // and the ImageView.
                holder = (ViewHolder) convertView.getTag();
            }

            // Get crucero
            if (null != cruceiros)
            {
                Cruceiro c = cruceiros.get(position);

                if (c != null)
                {
                    // Bind the data efficiently with the holder.
                    try
                    {

                        holder.llegada.setText(c.getLlegada());
                        holder.textName.setText(c.getBuque());
                        holder.textSlora.setText(getResources().getString(R.string.text_slora) + ": " + String.valueOf(c.getSlora()) + " m");
                        holder.horaLlegada.setText(getResources().getString(R.string.text_arrival) + ": " + c.getHoraLlegada());
                        holder.horaSalida.setText(getResources().getString(R.string.text_departure) + ": " + c.getHoraSalida());
                        holder.icon.setImageBitmap(mIcon);
                    }
                    catch (Exception e)
                    {

                    }
                }
            }
            return convertView;
        }

        class ViewHolder
        {
            TextView  llegada;
            TextView  textName;
            TextView  textSlora;
            TextView  horaLlegada;
            TextView  horaSalida;
            ImageView icon;
        }
    }

    private void showCruceirosListView()
    {
        // Create efficient adapter
        adapter = new EfficientAdapterCruceiros(this);

        // Get list view
        this.cruceirosListView = (ListView) findViewById(R.id.list_cruceiros);

        // Creates list view
        this.cruceirosListView.setAdapter(adapter);
        this.cruceirosListView.setCacheColorHint(0x000000);
        // Animate the view
        this.cruceirosListView.setTextFilterEnabled(true);

        this.cruceirosListView.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                // TODO
            }
        });
    }
}
