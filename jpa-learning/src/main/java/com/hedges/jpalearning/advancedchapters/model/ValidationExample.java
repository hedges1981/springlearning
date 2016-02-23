package com.hedges.jpalearning.advancedchapters.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by rowland-hall on 23/02/16
 *
 * NOTE: this class demos a small subset of the available validation annotations, see book p.337 for the full set as defined in JSR330.
 */
@Entity
@Table( name="validation_example")
public class ValidationExample
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;

    @NotNull( message = "this string must not be null" ) //NOTE how you can set a message on this, e.g. spring uses this to generate a user message
    //TODO: how else is the message used?
    private String notNullString;

    @Size( max=4 )
    private String lengthLessThan5String;

    @Past //can also have @Future, note that a null date passes both the @Past and @Future validations.
    private Date dateInThePast;

    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public String getNotNullString()
    {
        return notNullString;
    }

    public void setNotNullString( String notNullString )
    {
        this.notNullString = notNullString;
    }

    public String getLengthLessThan5String()
    {
        return lengthLessThan5String;
    }

    public void setLengthLessThan5String( String lengthLessThan5String )
    {
        this.lengthLessThan5String = lengthLessThan5String;
    }

    public Date getDateInThePast()
    {
        return dateInThePast;
    }

    public void setDateInThePast( Date dateInThePast )
    {
        this.dateInThePast = dateInThePast;
    }
}
