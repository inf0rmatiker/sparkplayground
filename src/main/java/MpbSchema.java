import java.io.Serializable;

public final class MpbSchema implements Serializable {

    private Integer CELL_ID, YEAR, MPB, ELEVATION, ASPECT, STEMS, BP0, BP1, BP2, BP3, BP0red, BP1red, BP2red, BP3red, BP0man, BP1man, BP2man, BP3man;
    private Double LATITUDE, LONGITUDE, SLOPE, NORTHERNESS, EASTERNESS, T_MAX, T_MIN_SUMMER, T_MIN_WINTER, DEGREE_DAYS,
                    COLD_TOLDERANCE, RELATIVE_HUMIDITY, SMI, WIND_SPEED, PEAK_EMERGENCE, PINE_COVER, PINE_AGE, DIST_TO_SOUTH_BORDER;

    public Integer getCELL_ID() {
        return CELL_ID;
    }

    public Integer getYEAR() {
        return YEAR;
    }

    public Integer getMPB() {
        return MPB;
    }

    public Integer getELEVATION() {
        return ELEVATION;
    }

    public Integer getASPECT() {
        return ASPECT;
    }

    public Integer getSTEMS() {
        return STEMS;
    }

    public Integer getBP0() {
        return BP0;
    }

    public Integer getBP1() {
        return BP1;
    }

    public Integer getBP2() {
        return BP2;
    }

    public Integer getBP3() {
        return BP3;
    }

    public Integer getBP0red() {
        return BP0red;
    }

    public Integer getBP1red() {
        return BP1red;
    }

    public Integer getBP2red() {
        return BP2red;
    }

    public Integer getBP3red() {
        return BP3red;
    }

    public Integer getBP0man() {
        return BP0man;
    }

    public Integer getBP1man() {
        return BP1man;
    }

    public Integer getBP2man() {
        return BP2man;
    }

    public Integer getBP3man() {
        return BP3man;
    }

    public Double getLATITUDE() {
        return LATITUDE;
    }

    public Double getLONGITUDE() {
        return LONGITUDE;
    }

    public Double getSLOPE() {
        return SLOPE;
    }

    public Double getNORTHERNESS() {
        return NORTHERNESS;
    }

    public Double getEASTERNESS() {
        return EASTERNESS;
    }

    public Double getT_MAX() {
        return T_MAX;
    }

    public Double getT_MIN_SUMMER() {
        return T_MIN_SUMMER;
    }

    public Double getT_MIN_WINTER() {
        return T_MIN_WINTER;
    }

    public Double getDEGREE_DAYS() {
        return DEGREE_DAYS;
    }

    public Double getCOLD_TOLDERANCE() {
        return COLD_TOLDERANCE;
    }

    public Double getRELATIVE_HUMIDITY() {
        return RELATIVE_HUMIDITY;
    }

    public Double getSMI() {
        return SMI;
    }

    public Double getWIND_SPEED() {
        return WIND_SPEED;
    }

    public Double getPEAK_EMERGENCE() {
        return PEAK_EMERGENCE;
    }

    public Double getPINE_COVER() {
        return PINE_COVER;
    }

    public Double getPINE_AGE() {
        return PINE_AGE;
    }

    public Double getDIST_TO_SOUTH_BORDER() {
        return DIST_TO_SOUTH_BORDER;
    }

    public void setCELL_ID(Integer CELL_ID) {
        this.CELL_ID = CELL_ID;
    }

    public void setYEAR(Integer YEAR) {
        this.YEAR = YEAR;
    }

    public void setMPB(Integer MPB) {
        this.MPB = MPB;
    }

    public void setELEVATION(Integer ELEVATION) {
        this.ELEVATION = ELEVATION;
    }

    public void setASPECT(Integer ASPECT) {
        this.ASPECT = ASPECT;
    }

    public void setSTEMS(Integer STEMS) {
        this.STEMS = STEMS;
    }

    public void setBP0(Integer BP0) {
        this.BP0 = BP0;
    }

    public void setBP1(Integer BP1) {
        this.BP1 = BP1;
    }

    public void setBP2(Integer BP2) {
        this.BP2 = BP2;
    }

    public void setBP3(Integer BP3) {
        this.BP3 = BP3;
    }

    public void setBP0red(Integer BP0red) {
        this.BP0red = BP0red;
    }

    public void setBP1red(Integer BP1red) {
        this.BP1red = BP1red;
    }

    public void setBP2red(Integer BP2red) {
        this.BP2red = BP2red;
    }

    public void setBP3red(Integer BP3red) {
        this.BP3red = BP3red;
    }

    public void setBP0man(Integer BP0man) {
        this.BP0man = BP0man;
    }

    public void setBP1man(Integer BP1man) {
        this.BP1man = BP1man;
    }

    public void setBP2man(Integer BP2man) {
        this.BP2man = BP2man;
    }

    public void setBP3man(Integer BP3man) {
        this.BP3man = BP3man;
    }

    public void setLATITUDE(Double LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    public void setLONGITUDE(Double LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }

    public void setSLOPE(Double SLOPE) {
        this.SLOPE = SLOPE;
    }

    public void setNORTHERNESS(Double NORTHERNESS) {
        this.NORTHERNESS = NORTHERNESS;
    }

    public void setEASTERNESS(Double EASTERNESS) {
        this.EASTERNESS = EASTERNESS;
    }

    public void setT_MAX(Double t_MAX) {
        T_MAX = t_MAX;
    }

    public void setT_MIN_SUMMER(Double t_MIN_SUMMER) {
        T_MIN_SUMMER = t_MIN_SUMMER;
    }

    public void setT_MIN_WINTER(Double t_MIN_WINTER) {
        T_MIN_WINTER = t_MIN_WINTER;
    }

    public void setDEGREE_DAYS(Double DEGREE_DAYS) {
        this.DEGREE_DAYS = DEGREE_DAYS;
    }

    public void setCOLD_TOLDERANCE(Double COLD_TOLDERANCE) {
        this.COLD_TOLDERANCE = COLD_TOLDERANCE;
    }

    public void setRELATIVE_HUMIDITY(Double RELATIVE_HUMIDITY) {
        this.RELATIVE_HUMIDITY = RELATIVE_HUMIDITY;
    }

    public void setSMI(Double SMI) {
        this.SMI = SMI;
    }

    public void setWIND_SPEED(Double WIND_SPEED) {
        this.WIND_SPEED = WIND_SPEED;
    }

    public void setPEAK_EMERGENCE(Double PEAK_EMERGENCE) {
        this.PEAK_EMERGENCE = PEAK_EMERGENCE;
    }

    public void setPINE_COVER(Double PINE_COVER) {
        this.PINE_COVER = PINE_COVER;
    }

    public void setPINE_AGE(Double PINE_AGE) {
        this.PINE_AGE = PINE_AGE;
    }

    public void setDIST_TO_SOUTH_BORDER(Double DIST_TO_SOUTH_BORDER) {
        this.DIST_TO_SOUTH_BORDER = DIST_TO_SOUTH_BORDER;
    }
}
