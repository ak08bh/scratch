package com.example.scratchcardactivity;

public class ScratchCardItemList
{
String amount;
Boolean scratchedStatus;

    public ScratchCardItemList(String amount,Boolean scratchedStatus)
    {
        this.amount = amount;
        this.scratchedStatus=scratchedStatus;
    }

    public Boolean getScratchedStatus() {
        return scratchedStatus;
    }

    public String getAmount() {
        return amount;
    }

}
