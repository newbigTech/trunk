package com.uniware.driver.domain;

/**
 * Created by ayue on 2017/7/31.
 */

public class DriverResult {
    private String address;
    private double lat;
    private double lon;
    private int type;
    private String description;

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getAddress() {
      return address;
    }

    public void setAddress(String address) {
      this.address = address;
    }

    public double getLat() {
      return lat;
    }

    public void setLat(double lat) {
      this.lat = lat;
    }

    public double getLon() {
      return lon;
    }

    public void setLon(double lon) {
      this.lon = lon;
    }

    public int getType() {
      return type;
    }

    public void setType(int type) {
      this.type = type;
    }

  @Override public String toString() {
    return "DriverResult{" +
        "address='" + address + '\'' +
        ", lat=" + lat +
        ", lon=" + lon +
        ", type=" + type +
        ", description='" + description + '\'' +
        '}';
  }
}
