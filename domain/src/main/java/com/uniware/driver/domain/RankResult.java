package com.uniware.driver.domain;

import java.util.List;

/**
 * Created by ayue on 2017/12/2.
 */

public class RankResult extends NetBiz {

  private List<RankingListBean> rankingList;

  public List<RankingListBean> getRankingList() {
    return rankingList;
  }

  public void setRankingList(List<RankingListBean> rankingList) {
    this.rankingList = rankingList;
  }

  public static class RankingListBean {
    /**
     * company : 金建出租
     * countNum : 30
     * department : 02分公司
     * id : 0
     * ranking : 1
     */

    private String company;
    private int countNum;
    private String department;
    private int id;
    private int ranking;
    private String plateNum;

    public String getPlateNum() {
      return plateNum;
    }

    public void setPlateNum(String plateNum) {
      this.plateNum = plateNum;
    }

    public String getCompany() {
      return company;
    }

    public void setCompany(String company) {
      this.company = company;
    }

    public int getCountNum() {
      return countNum;
    }

    public void setCountNum(int countNum) {
      this.countNum = countNum;
    }

    public String getDepartment() {
      return department;
    }

    public void setDepartment(String department) {
      this.department = department;
    }

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public int getRanking() {
      return ranking;
    }

    public void setRanking(int ranking) {
      this.ranking = ranking;
    }

    @Override public String toString() {
      return "RankingListBean{" +
          "company='" + company + '\'' +
          ", countNum=" + countNum +
          ", department='" + department + '\'' +
          ", id=" + id +
          ", ranking=" + ranking +
          ", plateNum=" + plateNum +
          '}';
    }
  }

  @Override public String toString() {
    return "RankResult{" +
        "rankingList=" + rankingList +
        '}';
  }
}
