package kr.pistachio.nmap;

import android.os.Bundle;

public class MainActivity extends NaverMapActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setMapView(R.id.mapView);
    }
}
