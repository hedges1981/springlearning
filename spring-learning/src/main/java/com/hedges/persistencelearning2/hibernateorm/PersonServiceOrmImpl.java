package com.hedges.persistencelearning2.hibernateorm;

import com.hedges.persistencelearning2.model.Person;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by rowland-hall on 24/07/15
 */
@Service
@Transactional   //THIS indicates that all public methods are Transactional.Note that the Default propagation is: required.
public class PersonServiceOrmImpl implements PersonServiceOrm
{
    @Autowired
    private SessionFactory sessionFactory;

    public void createPerson( Person p )
    {
        sessionFactory.getCurrentSession().save( p );
    }

    @Transactional( readOnly = true ) //read only will prevent Hibernate from flushing the session.
    public Person getPersonById( int id )
    {
        return (Person)sessionFactory.getCurrentSession().get( Person.class, id ) ;
    }

    @Override
    @Transactional( timeout = 3 ) //timeout measured in seconds.
    /*
    NOTE that the sleeping does not cause it to time out, so presumably the time out relates to the actual time to commit?
     */
    public void createPersonTimesOut( Person p )
    {
        sessionFactory.getCurrentSession().save( p );
        try
        {
            Thread.currentThread().sleep( 6000 );
        }
        catch ( InterruptedException e )
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    public void createPersonExceptionCausesRollback( Person p )
    {
        sessionFactory.getCurrentSession().save( p );
        throw new RuntimeException(  );
    }

    @Override
    @Transactional( rollbackFor = RollbackForException.class )
    public void createPersonDemosRollbackFor( Person p )
    {
        if ( p.getName().equals( "throw RollbackForException" ) )
        {
            throw new RollbackForException();
        }
        else
        {
            throw new RuntimeException(  );
        }
    }
}
