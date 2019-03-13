package trd.ams.dto;

/**
 * Created by hanu on 17-11-2017.
 */

public class Measure {

    private String measureName;
    private String measureValue;

    private String requiredRange;
    private String previousValues;
    private String lowerLimit;
    private String upperLimit;

    public String getMeasureName() {
        return measureName;
    }

    public void setMeasureName(String  measureName) {
        this.measureName =  measureName;
    }


    public String getMeasureValue() {
        return measureValue;
    }

    public void setMeasureValue(String  measureValue) {
        this.measureValue =  measureValue;
    }

    public String getRequiredRange() {
        return requiredRange;
    }

    public void setRequiredRange(String  requiredRange) {
        this.requiredRange =  requiredRange;
    }

    public String getPreviousValues() {
        return previousValues;
    }

    public void setPreviousValues(String  previousValues) {
        this.previousValues = previousValues;
    }

    public String getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(String  lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public String getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(String  upperLimit) {
        this.upperLimit = upperLimit;
    }



}
