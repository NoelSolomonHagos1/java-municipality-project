package org.gpiste.opendata;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private TextView txtPopulationData;
    private TextView txtWeatherData;

    private TextView txtWorkData;
    private TextView txtRateData;
    private EditText editTextLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        txtPopulationData = findViewById(R.id.txtPopulation);
        txtWeatherData = findViewById(R.id.txtWeather);
        editTextLocation = findViewById(R.id.txtEditlocation);
        txtWorkData = findViewById(R.id.txtWork);
        txtRateData = findViewById(R.id.txtRate);
    }

    public void onFindBtnClick (View view) {
        Log.d("LUT", "Nappula toimii");
        Context context = this;

        MunicipalityDataRetriever mr = new MunicipalityDataRetriever();
        WeatherDataRetriever wr = new WeatherDataRetriever();
        WorkDataRetriever wdr = new WorkDataRetriever();
        EmploymentDataRetriever edr = new EmploymentDataRetriever();


        String location = editTextLocation.getText().toString();

        ExecutorService service = Executors.newSingleThreadExecutor();

        service.execute(new Runnable() {
            @Override
            public void run() {
                ArrayList<MunicipalityData> populationData = mr.getData(context, location);
                ArrayList<EmploymentRateData> rateData = edr.getData(context, location);
                WeatherData weatherData = wr.getWeatherData(location);
                ArrayList<WorkData> workData = wdr.getData(context, location);

                if(populationData == null) {
                    return;
                }

                if(rateData == null) {
                    return;
                }

                if(workData == null) {
                    return;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String s = "";
                        String k = "";
                        String o = "";
                        for(MunicipalityData data : populationData) {
                            s = s + data.getYear() + ": " + data.getPopulation() + "\n";

                        }

                        for(EmploymentRateData data : rateData) {
                            k = k + data.getYear() + ": " + data.getPercentage() + "%" + "\n";
                        }

                        for(WorkData data : workData) {
                            o = o + data.getYear() + ": " + data.getPercentage() + "%" + "\n";
                        }


                        txtPopulationData.setText(s);
                        txtRateData.setText(k);
                        txtWorkData.setText(o);

                        txtWeatherData.setText(
                                weatherData.getName() + "\n" +
                                "Sää nyt: " + weatherData.getMain() + " (" + weatherData.getDescription() +") \n" +
                                "Lämpötila: " + weatherData.getTemperature() + " K\n" +
                                "Tuulennopeus: " + weatherData.getWindSpeed() + " m/s\n"
                        );

                    }
                });

                Log.d("LUT", "Data haettu");
            }
        });

    }

}