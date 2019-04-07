package edu.css.unit11_weather_async;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText etLoc;
    EditText etTemp;
    EditText etWind;
    EditText etVis;

    AsyncDownloadXML AsyncWeatherDownloader = new AsyncDownloadXML();

    /**
     * On creation of app
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up our edit text variables
        etLoc =  (EditText) findViewById(R.id.textLoc);
        etTemp =  (EditText) findViewById(R.id.textTemp);
        etWind =  (EditText) findViewById(R.id.textWind);
        etVis =  (EditText) findViewById(R.id.textVis);
    }

    /**
     * On button click, get weather from OpenWeatherMapAPI and set fields to content
     * @param v current view
     * @throws XmlPullParserException XML has parsing error
     * @throws URISyntaxException URI syntax is incorrect
     * @throws IOException Input/Output problem
     */
    public void btnClick (View v) throws XmlPullParserException, URISyntaxException, IOException {
        // Download the weather asynchronously
        String zip = etLoc.getText().toString();
        if(!zip.isEmpty()) {
            //AsyncWeatherDownloader.execute(this);

            //I added this to prevent an IllegalStateException that's caused by running the same task more than
            //once. Is there a reason we don't do this? Possibly for speed?
            if(AsyncWeatherDownloader.getStatus() == AsyncTask.Status.FINISHED) {
                AsyncWeatherDownloader = new AsyncDownloadXML();
                AsyncWeatherDownloader.execute(this);
            }
            else {
                AsyncWeatherDownloader.execute(this);
            }
        }
        else {
            setStatus("Zip Code cannot be empty!");
        }
    }

    /**
     * Set temp field to String parameter
     * @param newTemp temperature to set field
     */
    public void setTemp(String newTemp) {
        etTemp.setText(newTemp);
    }

    /**
     * Set wind field to String parameter
     * @param newWind wind to set field
     */
    public void setWind(String newWind) {
        etWind.setText(newWind);
    }

    public void setVisibility(String newVis){
        etVis.setText(newVis);
    }

    /**
     * Get locations from etLoc field
     * @return String location
     */
    public String getLocation() {
        return etLoc.getText().toString();
    }

    /**
     * Toast status of String parameter
     * @param newStatus status to toast to user
     */
    public void setStatus(String newStatus) {
        Toast toast=Toast.makeText(getApplicationContext(), newStatus,Toast.LENGTH_LONG );
        toast.show();
    }

}
