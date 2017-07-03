package kr.pistachio.nmap.ui;

import android.os.Bundle;
import android.view.View;

import com.nhn.android.maps.overlay.NMapPOIdata;

import kr.pistachio.nmap.Constants;
import kr.pistachio.nmap.R;
import kr.pistachio.nmap.nmap.NMapActivityParent;
import kr.pistachio.nmap.nmap.NMapPOIflagType;
import kr.pistachio.nmap.rest.NMap;
import kr.pistachio.nmap.rest.NMapAPI;
import kr.pistachio.nmap.util.GeoTrans;
import kr.pistachio.nmap.util.GeoTransPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends NMapActivityParent {

    private View.OnClickListener mOnSearchClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            search(view.getTag().toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setMapView(R.id.mapView);
        setSearchButtons(R.id.btn_location1, R.id.btn_location2);
    }

    private void setSearchButtons(int... ids) {
        for (int id : ids) {
            findViewById(id).setOnClickListener(mOnSearchClickListener);
        }
    }

    private void search(String keyword) {
        String query = getMyLocationName() + " " + keyword;
        NMapAPI.retrofit.create(NMapAPI.class).geocode(Constants.CLIENT_ID, Constants.CLIENT_SECRET, query).enqueue(new Callback<NMap>() {
            @Override
            public void onResponse(Call<NMap> call, Response<NMap> response) {
                showSearchResults(response.body());
            }

            @Override
            public void onFailure(Call<NMap> call, Throwable t) {

            }
        });
    }

    private void showSearchResults(NMap nMap) {
        NMapPOIdata poiData = new NMapPOIdata(nMap.getDisplay(), mMapViewerResourceProvider);
        poiData.beginPOIdata(nMap.getDisplay());
        for (NMap.Item item : nMap.getItems()) {
            GeoTransPoint katec = new GeoTransPoint(item.getMapx(), item.getMapy());
            GeoTransPoint geo = GeoTrans.convert(GeoTrans.KATEC, GeoTrans.GEO, katec);
            poiData.addPOIitem(geo.getX(), geo.getY(), item.getTitle(), NMapPOIflagType.PIN, 0);
        }
        poiData.endPOIdata();

        clearOverlays();
        createPOIdataOverlay(poiData);
    }
}
