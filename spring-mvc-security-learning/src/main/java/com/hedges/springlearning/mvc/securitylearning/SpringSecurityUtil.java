package com.hedges.springlearning.mvc.securitylearning;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by rowland-hall on 20/07/15
 */
public class SpringSecurityUtil
{
    public static List<String> getUserRoles()
    {
        Collection<? extends GrantedAuthority> authorities =  SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        List<String> retList = new ArrayList<String>();

        for( GrantedAuthority sga : authorities )
        {
            retList.add( sga.getAuthority() );
        }

        return retList;
    }
}
