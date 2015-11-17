package com.hedges.jpalearning.model;

import javax.persistence.*;

/**
 * Created by rowland-hall on 16/11/15
 */
//@Entity
@Table( name="quality_project")
public class QualityProject extends Project
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private int id;

    @Column( name="quality_project_notes")
    private String qualityProjectNotes;

    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public String getQualityProjectNotes()
    {
        return qualityProjectNotes;
    }

    public void setQualityProjectNotes( String qualityProjectNotes )
    {
        this.qualityProjectNotes = qualityProjectNotes;
    }
}
