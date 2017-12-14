package com.uniware.driver.domain;

import java.util.List;

/**
 * Created by ayue on 2017/7/27.
 */

public class AddressResult extends NetBiz{

  private List<AddressBean> driverAppAddressList;

  public List<AddressBean> getDriverAppAddressList() {
    return driverAppAddressList;
  }

  public void setDriverAppAddressList(List<AddressBean> driverAppAddressList) {
    this.driverAppAddressList = driverAppAddressList;
  }

  public static class AddressBean {
    /**
     * address : 柏彦大厦
     * description : 公司
     * id : 1
     * lat : 39.991821
     * lon : 116.35605
     */

    private String address;
    private String description;
    private int id;
    private double lat;
    private double lon;

    public String getAddress() {
      return address;
    }

    public void setAddress(String address) {
      this.address = address;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
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

    @Override public String toString() {
      return "AddressBean{" +
          "address='" + address + '\'' +
          ", description='" + description + '\'' +
          ", id=" + id +
          ", lat=" + lat +
          ", lon=" + lon +
          '}';
    }
  }

  @Override public String toString() {
    return "AddressResult{" +
        "driverAppAddressList=" + driverAppAddressList +
        '}';
  }
}
