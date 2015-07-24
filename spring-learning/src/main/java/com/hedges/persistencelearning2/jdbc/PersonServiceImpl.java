package com.hedges.persistencelearning2.jdbc;

import com.hedges.persistencelearning2.model.Person;
import com.hedges.springlearning.U;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by rowland-hall on 22/07/15
 */
@Service
public class PersonServiceImpl implements PersonService
{
    private static final String CREATE_SQL =
            "insert into person (name,sex,dob,car_make) values (:name,:sex,:dob,:car_make)";

    private static final String FIND_ALL_SQL=
            "select id,name,dob,sex,car_make from person";

    private static final String FIND_ALL_SQL_WITH_ERROR=
            "select id,name,dob,sex,car_make from persons";

    private static final String FIND_BY_CAR_MAKE_SQL="select id,name,dob,sex,car_make from person where car_make=:car_make";

    private static final String FIND_ONE_BY_ID_SQL=
            "select id,name,dob,sex,car_make from person where id = :id";

    private static final String UPDATE_SQL=
            "update person set name=:name,sex=:sex,dob=:dob,car_make=:car_make where id=:id";

    private static final String DELETE_SQL=
            "delete from person where person_id=:person_id";

    @Autowired
    private NamedParameterJdbcOperations jdbcTemplate;

    private PersonRowMapper personRowMapper = new PersonRowMapper();


    @Override
    public void CreatePerson( Person person )
    {
        SqlParameterSource parameterSource = new MapSqlParameterSource(  )
                .addValue( "name",person.getName() )
                .addValue( "sex",person.getSex() )
                .addValue( "dob",person.getDateOfBirth() )
                .addValue( "car_make",person.getCarMake() );

        //********NOTE: the use of the keyholder enables us to make use of the MySql auto increment functionality.
        //NO need to specify the ID here, MySQL creates it, then returns it through the keyholder.
        KeyHolder keyHolder= new GeneratedKeyHolder(  );

        jdbcTemplate.update( CREATE_SQL,parameterSource,keyHolder );

        person.setId( keyHolder.getKey().intValue() );
    }

    @Override
    public List<Person> getAllPersons()
    {
        return jdbcTemplate.query( FIND_ALL_SQL, personRowMapper );
    }

    @Override
    public void processAllPersonsWithRowCallBackHandler()
    {
        RowCallbackHandler rowCallBackHandler = new RowCallbackHandler()
        {
            @Override
            public void processRow( ResultSet rs ) throws SQLException
            {
                U.print( "RowcallBackHandler processing row:"+rs );
            }
        };

        jdbcTemplate.query( FIND_ALL_SQL, rowCallBackHandler );
    }

    @Override
    public String getCarMakeByIdDemosResultSetExtractor( int id )
    {
        ResultSetExtractor<String> resultSetExtractor = new ResultSetExtractor<String>()
        {
            @Override
            public String extractData( ResultSet rs ) throws SQLException, DataAccessException
            {
                return rs.getString( 4 );
            }
        };

        SqlParameterSource parameterSource = new MapSqlParameterSource(  )
                .addValue( "id", id );

        return jdbcTemplate.query( FIND_ONE_BY_ID_SQL, parameterSource, resultSetExtractor );
    }

    @Override
    public List<Person> getPersonsByCarMake( String carMake )
    {
        SqlParameterSource parameterSource = new MapSqlParameterSource(  )
                .addValue( "car_make", carMake );

        return jdbcTemplate.query( FIND_BY_CAR_MAKE_SQL, parameterSource, personRowMapper );
    }

    @Override
    public Person getPerson( int id )
    {
        SqlParameterSource parameterSource = new MapSqlParameterSource(  )
                .addValue( "id", id );
        //NOTE the use of queryForObject, similar to .query in this case, but enforces a single value.
        return jdbcTemplate.queryForObject( FIND_ONE_BY_ID_SQL, parameterSource, personRowMapper );
    }

    @Override
    public Map<String, Object> getPersonAsAMap( int id )
    {
        SqlParameterSource parameterSource = new MapSqlParameterSource(  )
                .addValue( "id", id );

        return jdbcTemplate.queryForMap( FIND_ONE_BY_ID_SQL,parameterSource );
    }

    @Override
    public void updatePerson( Person person )
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deletePerson( int id )
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void runQueryThrowsException()
    {
        jdbcTemplate.query( FIND_ALL_SQL_WITH_ERROR, personRowMapper );
    }
}
