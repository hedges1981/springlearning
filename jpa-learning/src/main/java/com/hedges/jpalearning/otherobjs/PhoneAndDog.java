package com.hedges.jpalearning.otherobjs;

/**
 * Created by rowland-hall on 10/11/15
 */
public class PhoneAndDog
{
    String phoneNum;
    String dogName;

    public PhoneAndDog( String phoneNum, String dogName )
    {
        this.phoneNum = phoneNum;
        this.dogName = dogName;
    }

    public String getPhoneNum()
    {
        return phoneNum;
    }

    public void setPhoneNum( String phoneNum )
    {
        this.phoneNum = phoneNum;
    }

    public String getDogName()
    {
        return dogName;
    }

    public void setDogName( String dogName )
    {
        this.dogName = dogName;
    }

    @Override
    public String toString()
    {
        return "PhoneAndDog{" +
                "phoneNum='" + phoneNum + '\'' +
                ", dogName='" + dogName + '\'' +
                '}';
    }
}
