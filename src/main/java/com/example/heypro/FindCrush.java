package com.example.heypro;

public class FindCrush {

public String myself , FULLNAME , Status;


    public FindCrush(){}
    public FindCrush(String myself, String FULLNAME, String status) {
        this.myself = myself;
        this.FULLNAME = FULLNAME;
        Status = status;
    }

    public String getMyself() {
        return myself;
    }

    public String getFULLNAME() {
        return FULLNAME;
    }

    public String getStatus() {
        return Status;
    }
}
