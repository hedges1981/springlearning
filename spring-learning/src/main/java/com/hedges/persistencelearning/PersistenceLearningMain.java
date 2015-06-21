package com.hedges.persistencelearning;

import com.hedges.persistencelearning.hibernateorm.AnEntity;
import com.hedges.persistencelearning.hibernateorm.AnEntityService;
import com.hedges.persistencelearning.jdbc.JDBCPersistenceBean;
import com.hedges.persistencelearning.jdbc.TestTable;
import com.hedges.springlearning.U;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by rowland-hall on 17/02/15
 */
public class PersistenceLearningMain
{
    public static void main( String[] args )
    {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("persistenceLearningContext.xml");

        testTheJdbcStuff( context );

        testTheHibernateOrmStuff( context);
    }

    private static void testTheHibernateOrmStuff( AbstractApplicationContext context )
    {
        AnEntityService anEntityService = ( AnEntityService ) context.getBean( "anEntityService" );

        AnEntity anEntity = new AnEntity();
        anEntity.setName( "name" );
        anEntity.setValue( "value" );

        anEntityService.saveAnEntiry( anEntity );

        List<AnEntity> anEntities = anEntityService.getAnEntityByName( "name" );

        U.print(anEntities);
    }

    private static void testTheJdbcStuff( AbstractApplicationContext context )
    {
        //testing out the jdbc stuff:
        JDBCPersistenceBean jdbcPersistenceBean = (JDBCPersistenceBean)context.getBean( JDBCPersistenceBean.class );

        try
        {
            jdbcPersistenceBean.createTestTableRow( 1,"name1", "value1" );
        }
        catch(Exception e )
        {
            //not fussed, is probably because we have already created the row previously.
        }

        TestTable testTable = jdbcPersistenceBean.findTestTableById( 1 );

        U.print( testTable );
    }
}
