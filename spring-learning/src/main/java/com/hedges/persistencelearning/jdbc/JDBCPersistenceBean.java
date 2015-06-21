package com.hedges.persistencelearning.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

/**
 * Created by rowland-hall on 11/02/15
 */
@Service
public class JDBCPersistenceBean
{
    String insertSql = "insert into test_table (id, name, value)"+
            "values(:id,:name,:value)";

    String findOneSql ="select id, name, value from test_table where id= :id";

    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    @Autowired
    private TestTableRowMapper testTableRowMapper;

    public void createTestTableRow( int id, String name, String value )
    {
        SqlParameterSource parameterSource = new MapSqlParameterSource( ).addValue("id",id).addValue( "name", name ).addValue( "value", value );

        jdbcOperations.update( insertSql, parameterSource );
    }

    public TestTable findTestTableById( int id )
    {
        SqlParameterSource parameterSource = new MapSqlParameterSource( "id", id );
        return jdbcOperations.queryForObject( findOneSql, parameterSource, testTableRowMapper );

    }

    public void setJdbcOperations( NamedParameterJdbcOperations jdbcOperations )
    {
        this.jdbcOperations = jdbcOperations;
    }

    public void setTestTableRowMapper( TestTableRowMapper testTableRowMapper )
    {
        this.testTableRowMapper = testTableRowMapper;
    }



}
