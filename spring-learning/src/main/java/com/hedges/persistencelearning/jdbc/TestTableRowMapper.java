package com.hedges.persistencelearning.jdbc;

import com.hedges.persistencelearning.jdbc.TestTable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by rowland-hall on 17/02/15
 */
@Component
public class TestTableRowMapper implements RowMapper<TestTable>
{

    @Override
    public TestTable mapRow( ResultSet resultSet, int i ) throws SQLException
    {
        TestTable testTable = new TestTable();
        testTable.setId( resultSet.getInt( 1 ) ); //note how the columns start at 1.
        testTable.setName( resultSet.getString( 2 ) );
        testTable.setValue( resultSet.getString( 3 ) );

        return testTable;
    }
}
