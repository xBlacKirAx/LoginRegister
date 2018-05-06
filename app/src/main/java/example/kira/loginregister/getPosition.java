package example.kira.loginregister;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.util.List;

public class getPosition extends Activity {

    private LocationManager locationManager;
    private double[] position = {0,0};
    private boolean flag = false;

    public void onCreate() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            initPermission();
            return;
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        String provider = null;

        System.out.println("-------------------onCreate()------------------------------");
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
            System.out.println("-------------------GPS success------------------------------");
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
            System.out.println("-------------------network success------------------------------");
        } else {
            Toast.makeText(this, "Please open your GPS service or network service", Toast.LENGTH_SHORT).show();
            return;
        }
        locationManager.requestLocationUpdates(provider, 3000, 0, gpsLocationListener);
        Intent intent = new Intent();
        intent.putExtra("langtitude",position[0]);
        intent.putExtra("longtitude",position[1]);
        setResult(10010,intent);
        finish();

    } //end onCreate()


    private void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Check GPS and Network permission
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //Ask for opening the permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                flag = true;
            }
        } else {
            flag = true;
        }
    }

    private final LocationListener gpsLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            updateLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
            updateLocation(null);
        }
    };

    private Location updateLocation(Location location){
        if(location!=null) {
            position[0] = location.getLatitude();
            position[1] = location.getLongitude();
        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(),"Please wait for getting position.....",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
        return location;
    }



}

