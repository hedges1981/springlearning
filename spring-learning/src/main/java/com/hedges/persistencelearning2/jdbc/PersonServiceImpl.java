package com.hedges.persistencelearning2.jdbc;

import com.hedges.persistencelearning2.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    private static final String FIND_BY_CAR_MAKE_SQL="select id,name,dob,sex,car_make from person where car_make=:car_make";

    private static final String FIND_ONE_BY_ID_SQL=
            "select person_id,name,sex,dob,car_make from person where person_id = :person_id";

    private static final String UPDATE_SQL=
            "update person set name=:name,sex=:sex,dob=:dob,car_make=:car_make where person_id=:person_id";

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
    public List<Person> getPersonsByCarMake( String carMake )
    {
        SqlParameterSource parameterSource = new MapSqlParameterSource(  )
                .addValue( "car_make", carMake );

        return jdbcTemplate.query( FIND_BY_CAR_MAKE_SQL, parameterSource, personRowMapper );
    }

    @Override
    public Person getPerson( int id )
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
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
}
