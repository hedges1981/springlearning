package com.hedges.jpalearning.advancedormchapter.model;

/**
 * Created by rowland-hall on 17/02/16
 */
public abstract class CachedEntity
{
    private long createTime = System.currentTimeMillis();

    public long getCacheAgeMs()
    {
        return System.currentTimeMillis() - createTime;
    }

}