package com.hedges.persistencelearning2.jdbc;

import com.hedges.persistencelearning2.model.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by rowland-hall on 22/07/15
 */
public class PersonRowMapper implements RowMapper<Person>
{
    @Override
    public Person mapRow( ResultSet resultSet, int i ) throws SQLException
    {
        Person person = new Person();

        //************NOTE!!! need to be careful with this thing that the column order is as expected, it is specified by the list of columns
        //in the query, so you must always have them in the same order in the queries used.
        person.setId( resultSet.getInt( 1 ) );
        person.setName( resultSet.getString( 2 ));
        person.setDateOfBirth( resultSet.getDate( 3 ));
        person.setSex( resultSet.getString( 4 ) );
        person.setCarMake( resultSet.getString( 5 ) );

        return person;
    }
}
