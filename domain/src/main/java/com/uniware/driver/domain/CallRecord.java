package com.uniware.driver.domain;

/**
 * Created by ayue on 2017/3/29.
 */

public class CallRecord {
  /** 业务ID或订单号 */
  private Integer id;

  private Integer bizId;
  /** 业务类型 0：即时召车；1：预约召车；2：车辆指派 */
  private Integer bizType;
  /** 用车地点 */
  private String userLocation;
  /** 目的地点 */
  private String destLocation;
  /** 用车参照 */
  private String userRefer;
  /** 目的参照 */
  private String destRefer;
  /** 乘客位置经度 */
  private double userLon;
  /** 乘客位置纬度 */
  private double userLat;
  /** 目的地位置经度 */
  private double destLon;
  /** 目的地位置纬度 */
  private double destLat;

  /** 呼入电话 */
  private String callerTel;

  /** 乘客ID */
  private String passengerId;
  /** 乘客电话号码 */
  private String passengerTel;
  /** 乘客姓名 */
  private String passengerName;
  /** 乘客人数 */
  private String passengerNum;
  /** 乘客性别 */
  private String passengerGender;
  /** 业务描述:乘客需求 */
  private String passengerAttr;
  /** 乘客类型 */
  private String passengerType;

  private String description;

  /** 电召服务费 */
  private double callFee;
  /** 选车范围，距离用车地点的距离，单位公里 */
  private Integer distence;
  /** 该订单当前推送范围，单位公里*/
  //private int notifyRange;

  /** 录入人名称 */
  private String userId;
  /** 录入人名称 */
  private String userName;

  /** 该订单的创建时间 */
 // private int createTime;
  /** 用车时间 */
  private long useTime;
  /** 订单有效开始时间 */
  //private int enableStartTime;
  /** 订单有效结束时间 */
  //private int enableEndTime;
  /** 发送时间 */
 // private int sendTime;

  /** 发单类型（全部发送/按常用地址发送） */
  private Integer sendType;
  /** 发送的isuId */
  private String sendIsu;
  /** 发送的手机号 */
  private String sendPhone;

  /** 用车数量 */
  private Integer needCount;

  /** 96106订单编号 */
  private String idCode;

  // 以下是赛格协议
  /** 中心号 */
  private String centerCode;
  /** 坐席号 */
  private String clientCode;

  /** 订单来源*/
  private String source;
  /** 订单详情*/

  ///** 该订单按圈车规则，通知到的目标司机*/
  //private List<String> driverNotifyList;
  //
  ///** 该订单按圈车规则，通知到的目标Isu*/
  //private List<String> isuNotifyList;
  /** 备注*/
  private String remark;

  private Integer sendNum;

  private Integer notFinish;

  private Integer status;

  /** 该订单被司机App、Isu、其他平台接单*/
  private int takenType;

  /** 该订单由driverTel抢单并确认*/
  private String driverTel;

  /** 司机名称*/
  private String driverName;

  /** 该订单由isuId抢单并确认*/
  private String isuId;

  /** 车牌号码*/
  private String plateNum;

  /** 抢单时间*/
 // private int takenTime;

  /** 完成时间*/
 // private int completeTime;

  /** 机构*/
  private String  company;

  /** 分司*/
  private String department;

  /** 抢单结果*/
  private Integer qdResult;

  /** 备注*/
  private String explain;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getBizId() {
    return bizId;
  }

  public void setBizId(Integer bizId) {
    this.bizId = bizId;
  }

  public Integer getBizType() {
    return bizType;
  }

  public void setBizType(Integer bizType) {
    this.bizType = bizType;
  }

  public String getUserLocation() {
    return userLocation;
  }

  public void setUserLocation(String userLocation) {
    this.userLocation = userLocation;
  }

  public String getDestLocation() {
    return destLocation;
  }

  public void setDestLocation(String destLocation) {
    this.destLocation = destLocation;
  }

  public String getUserRefer() {
    return userRefer;
  }

  public void setUserRefer(String userRefer) {
    this.userRefer = userRefer;
  }

  public String getDestRefer() {
    return destRefer;
  }

  public void setDestRefer(String destRefer) {
    this.destRefer = destRefer;
  }

  public double getUserLon() {
    return userLon;
  }

  public void setUserLon(double userLon) {
    this.userLon = userLon;
  }

  public double getUserLat() {
    return userLat;
  }

  public void setUserLat(double userLat) {
    this.userLat = userLat;
  }

  public double getDestLon() {
    return destLon;
  }

  public void setDestLon(double destLon) {
    this.destLon = destLon;
  }

  public double getDestLat() {
    return destLat;
  }

  public void setDestLat(double destLat) {
    this.destLat = destLat;
  }

  public String getCallerTel() {
    return callerTel;
  }

  public void setCallerTel(String callerTel) {
    this.callerTel = callerTel;
  }

  public String getPassengerId() {
    return passengerId;
  }

  public void setPassengerId(String passengerId) {
    this.passengerId = passengerId;
  }

  public String getPassengerTel() {
    return passengerTel;
  }

  public void setPassengerTel(String passengerTel) {
    this.passengerTel = passengerTel;
  }

  public String getPassengerName() {
    return passengerName;
  }

  public void setPassengerName(String passengerName) {
    this.passengerName = passengerName;
  }

  public String getPassengerNum() {
    return passengerNum;
  }

  public void setPassengerNum(String passengerNum) {
    this.passengerNum = passengerNum;
  }

  public String getPassengerGender() {
    return passengerGender;
  }

  public void setPassengerGender(String passengerGender) {
    this.passengerGender = passengerGender;
  }

  public String getPassengerAttr() {
    return passengerAttr;
  }

  public void setPassengerAttr(String passengerAttr) {
    this.passengerAttr = passengerAttr;
  }

  public String getPassengerType() {
    return passengerType;
  }

  public void setPassengerType(String passengerType) {
    this.passengerType = passengerType;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public double getCallFee() {
    return callFee;
  }

  public void setCallFee(double callFee) {
    this.callFee = callFee;
  }

  public Integer getDistence() {
    return distence;
  }

  public void setDistence(Integer distence) {
    this.distence = distence;
  }

  //public int getNotifyRange() {
  //  return notifyRange;
  //}
  //
  //public void setNotifyRange(int notifyRange) {
  //  this.notifyRange = notifyRange;
  //}

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  //public int getCreateTime() {
  //  return createTime;
  //}
  //
  //public void setCreateTime(int createTime) {
  //  this.createTime = createTime;
  //}

  public long getUseTime() {
    return useTime;
  }

  public void setUseTime(int useTime) {
    this.useTime = useTime;
  }

  //public int getEnableStartTime() {
  //  return enableStartTime;
  //}
  //
  //public void setEnableStartTime(int enableStartTime) {
  //  this.enableStartTime = enableStartTime;
  //}
  //
  //public int getEnableEndTime() {
  //  return enableEndTime;
  //}
  //
  //public void setEnableEndTime(int enableEndTime) {
  //  this.enableEndTime = enableEndTime;
  //}
  //
  //public int getSendTime() {
  //  return sendTime;
  //}
  //
  //public void setSendTime(int sendTime) {
  //  this.sendTime = sendTime;
  //}

  public Integer getSendType() {
    return sendType;
  }

  public void setSendType(Integer sendType) {
    this.sendType = sendType;
  }

  public String getSendIsu() {
    return sendIsu;
  }

  public void setSendIsu(String sendIsu) {
    this.sendIsu = sendIsu;
  }

  public String getSendPhone() {
    return sendPhone;
  }

  public void setSendPhone(String sendPhone) {
    this.sendPhone = sendPhone;
  }

  public Integer getNeedCount() {
    return needCount;
  }

  public void setNeedCount(Integer needCount) {
    this.needCount = needCount;
  }

  public String getIdCode() {
    return idCode;
  }

  public void setIdCode(String idCode) {
    this.idCode = idCode;
  }

  public String getCenterCode() {
    return centerCode;
  }

  public void setCenterCode(String centerCode) {
    this.centerCode = centerCode;
  }

  public String getClientCode() {
    return clientCode;
  }

  public void setClientCode(String clientCode) {
    this.clientCode = clientCode;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Integer getSendNum() {
    return sendNum;
  }

  public void setSendNum(Integer sendNum) {
    this.sendNum = sendNum;
  }

  public Integer getNotFinish() {
    return notFinish;
  }

  public void setNotFinish(Integer notFinish) {
    this.notFinish = notFinish;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public int getTakenType() {
    return takenType;
  }

  public void setTakenType(int takenType) {
    this.takenType = takenType;
  }

  public String getDriverTel() {
    return driverTel;
  }

  public void setDriverTel(String driverTel) {
    this.driverTel = driverTel;
  }

  public String getDriverName() {
    return driverName;
  }

  public void setDriverName(String driverName) {
    this.driverName = driverName;
  }

  public String getIsuId() {
    return isuId;
  }

  public void setIsuId(String isuId) {
    this.isuId = isuId;
  }

  public String getPlateNum() {
    return plateNum;
  }

  public void setPlateNum(String plateNum) {
    this.plateNum = plateNum;
  }

  //public int getTakenTime() {
  //  return takenTime;
  //}
  //
  //public void setTakenTime(int takenTime) {
  //  this.takenTime = takenTime;
  //}
  //
  //public int getCompleteTime() {
  //  return completeTime;
  //}
  //
  //public void setCompleteTime(int completeTime) {
  //  this.completeTime = completeTime;
  //}

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public Integer getQdResult() {
    return qdResult;
  }

  public void setQdResult(Integer qdResult) {
    this.qdResult = qdResult;
  }

  public String getExplain() {
    return explain;
  }

  public void setExplain(String explain) {
    this.explain = explain;
  }

  @Override public String toString() {
    return "CallRecord{" +
        "id=" + id +
        ", bizId=" + bizId +
        ", bizType=" + bizType +
        ", userLocation='" + userLocation + '\'' +
        ", destLocation='" + destLocation + '\'' +
        ", userRefer='" + userRefer + '\'' +
        ", destRefer='" + destRefer + '\'' +
        ", userLon=" + userLon +
        ", userLat=" + userLat +
        ", destLon=" + destLon +
        ", destLat=" + destLat +
        ", callerTel='" + callerTel + '\'' +
        ", passengerId='" + passengerId + '\'' +
        ", passengerTel='" + passengerTel + '\'' +
        ", passengerName='" + passengerName + '\'' +
        ", passengerNum='" + passengerNum + '\'' +
        ", passengerGender='" + passengerGender + '\'' +
        ", passengerAttr='" + passengerAttr + '\'' +
        ", passengerType='" + passengerType + '\'' +
        ", description='" + description + '\'' +
        ", callFee=" + callFee +
        ", distence=" + distence +
       // ", notifyRange=" + notifyRange +
        ", userId='" + userId + '\'' +
        ", userName='" + userName + '\'' +
       // ", createTime=" + createTime +
        ", useTime=" + useTime +
       // ", enableStartTime=" + enableStartTime +
       // ", enableEndTime=" + enableEndTime +
       // ", sendTime=" + sendTime +
        ", sendType=" + sendType +
        ", sendIsu='" + sendIsu + '\'' +
        ", sendPhone='" + sendPhone + '\'' +
        ", needCount=" + needCount +
        ", idCode='" + idCode + '\'' +
        ", centerCode='" + centerCode + '\'' +
        ", clientCode='" + clientCode + '\'' +
        ", source='" + source + '\'' +
        ", remark='" + remark + '\'' +
        ", sendNum=" + sendNum +
        ", notFinish=" + notFinish +
        ", status=" + status +
        ", takenType=" + takenType +
        ", driverTel='" + driverTel + '\'' +
        ", driverName='" + driverName + '\'' +
        ", isuId='" + isuId + '\'' +
        ", plateNum='" + plateNum + '\'' +
       // ", takenTime=" + takenTime +
       // ", completeTime=" + completeTime +
        ", company='" + company + '\'' +
        ", department='" + department + '\'' +
        ", qdResult=" + qdResult +
        ", explain='" + explain + '\'' +
        '}';
  }
}
