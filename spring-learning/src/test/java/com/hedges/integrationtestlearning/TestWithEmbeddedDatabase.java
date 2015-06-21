package com.hedges.integrationtestlearning;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by rowland-hall on 29/01/15
 */
@RunWith( SpringJUnit4ClassRunner.class)
//note the inherit locations on this, default is true, ones declared here are loaded after the superclass ones.
@ContextConfiguration(locations={"classpath:embedded-dataSource-context.xml","classpath:embedded-datasource-jpa-context.xml","classpath:testService-context.xml"} )
@Transactional
//this means that spring will start a txn for each test method, could also annotate at method level as well.
public class TestWithEmbeddedDatabase
{
    @Autowired
    TestService testService;

    @Autowired
    TestTableDAO testTableDAO;

    @Test
    public void aTest()
    {
        testService.setTestTableDao( testTableDAO );

        TestTable testTable = new TestTable();

        testService.saveTestTable(1, "nam","val");

        TestTable testTable1 = testService.findTestTable( 1 );

        System.out.println( testTable1 );
    }

}
