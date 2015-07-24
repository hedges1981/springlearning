package com.hedges.persistencelearning2.hibernateorm;

import com.hedges.persistencelearning2.model.Person;

/**
 * Created by rowland-hall on 24/07/15
 */
public interface PersonServiceOrm
{
    void createPerson( Person p );
    Person getPersonById( int id );
    void createPersonTimesOut( Person p );
    void createPersonExceptionCausesRollback( Person p );
    void createPersonDemosRollbackFor( Person p );
}
