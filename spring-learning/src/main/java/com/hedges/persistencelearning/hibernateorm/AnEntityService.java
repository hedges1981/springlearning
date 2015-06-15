package com.hedges.persistencelearning.hibernateorm;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by rowland-hall on 19/02/15
 */
@Service
@Transactional
public class AnEntityService
{
    @Autowired
    private SessionFactory sessionFactory;

    public void saveAnEntiry( AnEntity e )
    {
         getSession().save( e );
    }

    public List<AnEntity> getAnEntityByName( String name )
    {
        return getSession().getNamedQuery( AnEntity.GET_AN_ENTITY_BY_NAME ).setString( "name", name ).list();
    }

    private Session getSession()
    {
        return sessionFactory.getCurrentSession();
    }
}
