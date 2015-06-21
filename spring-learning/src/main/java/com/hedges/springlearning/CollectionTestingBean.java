package com.hedges.springlearning;

import java.util.*;

/**
 * Created by rowland-hall on 29/10/14
 */
public class CollectionTestingBean
{
    private List injectedList;

    private Set injectedSet;

    private Map injectedMap;

    private LinkedList linkedList;

    public List getBeanList()
    {
        return beanList;
    }

    public void setBeanList( List beanList )
    {
        this.beanList = beanList;
    }

    private List beanList;

    public LinkedList getLinkedList()
    {
        return linkedList;
    }

    public void setLinkedList( LinkedList linkedList )
    {
        this.linkedList = linkedList;
    }

    public Map getInjectedMap()
    {
        return injectedMap;
    }

    public void setInjectedMap( Map injectedMap )
    {
        this.injectedMap = injectedMap;
    }

    public Set getInjectedSet()
    {
        return injectedSet;
    }

    public void setInjectedSet( Set injectedSet )
    {
        this.injectedSet = injectedSet;
    }

    public void setInjectedList( List injectedList )
    {
        this.injectedList = injectedList;
    }

    public List getInjectedList()
    {
        return injectedList;
    }
}
