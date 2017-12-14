package com.uniware.driver.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import javax.inject.Inject;

/**
 * Created by SJ on 15/11/21.
 */
public class DriverLocation {
  private String bestProvider = LocationManager.GPS_PROVIDER;
  private LocationManager locationManager;
  private Location defaultLocation;
  private Context context;

  @Inject
  public DriverLocation(Context context){
    this.context=context;
    locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    defaultLocation = new Location(bestProvider);
    defaultLocation.setLatitude(39.913340);
    defaultLocation.setLongitude(116.384502 );
  }

  public Location getLocation(){
    SharedPreferences sp=context.getSharedPreferences("userYJ",0);
    Location location = new Location(bestProvider);
    location.setLatitude(Double.parseDouble(sp.getString("lat","0")));
    location.setLongitude(Double.parseDouble(sp.getString("lon","0")));
    return location == null ? defaultLocation : location;
  }
  public int getAddressId(){
    SharedPreferences sp=context.getSharedPreferences("userYJ",0);
    return sp.getInt("addressId",0);
  }
  public double getAddressLat(){
    SharedPreferences sp=context.getSharedPreferences("userYJ",0);
    Double d=Double.parseDouble(sp.getString("addressLat","0"));
    return d;
  }
  public double getAddressLon(){
    SharedPreferences sp=context.getSharedPreferences("userYJ",0);
    Double d=Double.parseDouble(sp.getString("addressLon","0"));
    return d;
  }

  public int getFlag(){
    SharedPreferences sp=context.getSharedPreferences("userYJ",0);
    return sp.getInt("taxi_flag", 0);
  }
}
