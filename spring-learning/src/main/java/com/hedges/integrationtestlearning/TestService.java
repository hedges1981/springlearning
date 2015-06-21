package com.hedges.integrationtestlearning;

import org.springframework.transaction.annotation.Transactional;

/**
 * Created by rowland-hall on 29/01/15
 */
public class TestService
{
    //@Autowired
    private TestTableDAO testTableDao;

    @Transactional
    public void saveTestTable( int id, String name, String value )
    {
        TestTable t = new TestTable();

        t.setId( id );
        t.setName( name );
        t.setValue( value );

        testTableDao.saveAndFlush( t );
    }

    public TestTableDAO getTestTableDao()
    {
        return testTableDao;
    }

    public void throwAnException()
    {
        throw new RuntimeException(  );

    }

    public void setTestTableDao( TestTableDAO testTableDao )
    {
        this.testTableDao = testTableDao;
    }

    public TestTable findTestTable( int id )
    {
        return testTableDao.findOne( id );
    }
}
