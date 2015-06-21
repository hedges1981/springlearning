package com.hedges.persistencelearning.hibernateorm;

import javax.persistence.*;

/**
 * Created by rowland-hall on 18/02/15
 *
 * Create table for this is: CREATE TABLE `an_entity` (
 `entity_id` int(11) NOT NULL AUTO_INCREMENT,
 `name` varchar(45) NOT NULL,
 `value` varchar(45) DEFAULT NULL,
 PRIMARY KEY (`entity_id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1
 */
@Entity
@Table( name="an_entity")
@NamedQuery(name=AnEntity.GET_AN_ENTITY_BY_NAME, query="SELECT e FROM AnEntity e where e.name LIKE :name")
public class AnEntity

{
    public static final String GET_AN_ENTITY_BY_NAME = "getAnEntityByName";

    private Integer id;
    private String name;
    private String value;

    @Id
    @Column( name="entity_id")
    @GeneratedValue(strategy = GenerationType.AUTO )
    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue( String value )
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return "AnEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
