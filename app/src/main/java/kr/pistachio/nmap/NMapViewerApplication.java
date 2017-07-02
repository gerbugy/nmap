package kr.pistachio.nmap;

import android.app.Application;

public class NMapViewerApplication extends Application {

	private static NMapViewerApplication instance;

	public static NMapViewerApplication getInstance() {
		return instance;
	}

	@Override
	public void onCreate() {

		super.onCreate();

		instance = this;
	}
}
