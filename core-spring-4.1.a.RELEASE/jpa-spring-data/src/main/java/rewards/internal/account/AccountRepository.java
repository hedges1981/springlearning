package rewards.internal.account;


import org.springframework.data.repository.Repository;

/**
 * Loads account aggregates. Called by the reward network to find and
 * reconstitute Account entities from an external form such as a set of RDMS
 * rows.
 * 
 * Objects returned by this repository are guaranteed to be fully initialized
 * and ready to use.
 */

//	DONE-04:  Alter this interface to extend a Spring Data Interface.
//	Define a method that will look up an Account by the provided creditCardNumber.
//NOTE: extending the Repository interface does?
public interface AccountRepository extends Repository<Account,Long> {

      public Account findByCreditCardNumber( String creditCardNumber );
}