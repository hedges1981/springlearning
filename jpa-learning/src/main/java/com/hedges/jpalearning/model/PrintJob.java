package com.hedges.jpalearning.model;

import javax.persistence.*;

/**
 * Created by rowland-hall on 19/10/15
 */
@Entity
@Table(name="print_job")
public class PrintJob
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="print_queue")
    private PrintQueue printQueue;


    @Column(name = "print_order")
    private int printOrder;

    public int getPrintOrder()
    {
        return printOrder;
    }

    public void setPrintOrder( int printOrder )
    {
        this.printOrder = printOrder;
    }

    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public PrintQueue getPrintQueue()
    {
        return printQueue;
    }

    public void setPrintQueue( PrintQueue printQueue )
    {
        this.printQueue = printQueue;
    }


}
