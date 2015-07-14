/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hedges.springlearning.mvc.securitylearning;

import org.springframework.security.access.annotation.Secured;


public class BeanWithMethodLevelSecurity 
{
    @Secured("ROLE_user")
    public void canOnlyBeDoneByUser()
    {
        
    }
    
    @Secured("ROLE_admin")
    public void adminOnlyViaAnnotation()
    {
        
    }
    
    //No annotation or XML driven security protectio on this one, it is secured by the
    //global-method-security declaration in the security-context.xml 
    public void adminOnlyViaGlobalDeclaration()
    {
        
    }
    
}
