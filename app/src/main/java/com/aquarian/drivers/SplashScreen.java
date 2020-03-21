package com.aquarian.drivers;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import android.view.Window;
import android.view.WindowManager;

import com.aquarian.drivers.ui.login.Login;
import com.aquarian.drivers.util.GetData;
import com.aquarian.drivers.util.GlobalVariables;
import com.aquarian.drivers.util.SaveData;

import org.json.JSONException;
import org.json.JSONObject;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary));

        //Call the Login Screen Activity
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                SaveData sd = new SaveData();
                sd.read("Driver",SplashScreen.this);
                String response = sd.content;
                if(response != null)
                {
                    new GetData(SplashScreen.this).execute("http://soc-web-liv-82.napier.ac.uk/api/jobs", "jobsFile");
                    parseData(response);
                }
                else
                {
                    Intent intent = new Intent(SplashScreen.this, Login.class);
                    startActivity(intent);
                }
                finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 3000);
    }

    public void parseData(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
                ((GlobalVariables) this.getApplication()).setDriverID(jsonObject.getString("DriverID"));
                ((GlobalVariables) this.getApplication()).setDriverFirstname(jsonObject.getString("FirstName"));
                ((GlobalVariables) this.getApplication()).setDriverLastConnection(jsonObject.getString("LastConnected"));
                ((GlobalVariables) this.getApplication()).setVehicleID(jsonObject.getString("VehicleID"));
                new GetData(SplashScreen.this).execute("http://soc-web-liv-82.napier.ac.uk/api/vehicles/" + jsonObject.getString("VehicleID"), "vehicle");
                if (((GlobalVariables) this.getApplication()).getDriverFirstname() != null)
                {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}