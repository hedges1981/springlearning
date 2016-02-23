package com.hedges.jpalearning.advancedchapters.listeners;

import com.hedges.jpalearning.U;

import javax.persistence.PrePersist;

/**
 * Created by rowland-hall on 23/02/16
 *
 * NOTE: how the methods on this class take Object as a param,so it can be applied to any entity. You could also use an interface,
 * See e.g. the NamedEntity example on book page: 330-331.
 */
public class GeneralEntityListener
{
    @PrePersist
    public void validateString( Object obj )
    {
        U.print( "in pre persist of GeneralEntityListener with entity "+obj );
    }
}
