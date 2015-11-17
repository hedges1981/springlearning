package com.hedges.jpalearning.model;

import javax.persistence.*;

/**
 * Created by rowland-hall on 16/11/15
 */
//@Entity
@Table( name="design_project")
public class DesignProject extends Project
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private int id;

    @Column( name="design_notes")
    private String designNotes;

    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public String getDesignNotes()
    {
        return designNotes;
    }

    public void setDesignNotes( String designNotes )
    {
        this.designNotes = designNotes;
    }
}
