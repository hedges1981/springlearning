package com.hedges.jpalearning.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by rowland-hall on 19/10/15
 */
@Entity
@Table(name="print_queue")
public class PrintQueue
{
    @Id
    @Column(name="name")
    private String name;

    @OneToMany(targetEntity =PrintJob.class,mappedBy = "printQueue", cascade = CascadeType.ALL)
    @OrderColumn(name="print_order")
    private List<PrintJob> printJobs;

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public List<PrintJob> getPrintJobs()
    {
        return printJobs;
    }

    public void setPrintJobs( List<PrintJob> printJobs )
    {
        this.printJobs = printJobs;
    }

}
