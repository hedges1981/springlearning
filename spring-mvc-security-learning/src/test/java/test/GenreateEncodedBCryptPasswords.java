
package test;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class GenreateEncodedBCryptPasswords {
    
 @Test
 public void ddd()
 {
     BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
     System.out.println(encoder.encode("password"));
 }
    
}
