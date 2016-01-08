package rewards.internal.restaurant;


import org.springframework.data.repository.Repository;

/**
 * Loads restaurant aggregates. Called by the reward network to find and reconstitute Restaurant entities from an
 * external form such as a set of RDMS rows.
 * 
 * Objects returned by this repository are guaranteed to be fully-initialized and ready to use.
 */

//	DONE-05: Alter this interface to extend a Spring Data Interface.
//	Define a method that will look up a Restaurant by a provided merchant number.
public interface RestaurantRepository extends Repository<Restaurant, Long>
{
    //NOTE: this is very magic, the Spring will create a concrete class of this inteface, because the method is called:
    //findByNumber, and there is a property called number on the restaurant class, it will automatically create a method to
    //find a restauant by its number.
    Restaurant findByNumber(String merchantNumber);
}
