package rewards.internal.restaurant;

import common.money.MonetaryAmount;
import common.money.Percentage;
import rewards.Dining;
import rewards.internal.account.Account;

import javax.persistence.*;

/**
 * A restaurant establishment in the network. Like AppleBee's.
 * 
 * Restaurants calculate how much benefit may be awarded to an account for
 * dining based on a availability policy and a benefit percentage.
 */
// DONE-03: Map this class using JPA Annotations

/*
create table T_RESTAURANT (ID integer identity primary key, MERCHANT_NUMBER varchar(10) not null, NAME varchar(80) not null, BENEFIT_PERCENTAGE decimal(5,2) not null, BENEFIT_AVAILABILITY_POLICY varchar(1) not null, unique(MERCHANT_NUMBER));

 */
@Entity
@Table(name="T_RESTAURANT")
public class Restaurant {

    @Id
    @Column(name="ID")
	private Long entityId;
    @Column(name="MERCHANT_NUMBER")
	private String number;
    @Column(name="NAME")
	private String name;
    @Embedded
    //NOTE: this @AttributeOverride tells it to use the value of col BENEFIT_PERCENTAGE to set the value property of the
    //Percentage object.
    @AttributeOverride(name="value",column=@Column(name="BENEFIT_PERCENTAGE"))
	private Percentage benefitPercentage;


	// DO NOT map this field. For now it is always set to AlwaysAvailable.
	// The bonus section later will redo this mapping.
	@Transient
	private BenefitAvailabilityPolicy benefitAvailabilityPolicy = AlwaysAvailable.INSTANCE;

	public Restaurant() {
		//Needed by the JPA spec
        int x=0;
	}

	/**
	 * Creates a new restaurant.
	 * 
	 * @param number
	 *            the restaurant's merchant number
	 * @param name
	 *            the name of the restaurant
	 */
	public Restaurant(String number, String name) {
		this.number = number;
		this.name = name;
	}

	/**
	 * Sets the percentage benefit to be awarded for eligible dining
	 * transactions.
	 * 
	 * @param benefitPercentage
	 *            the benefit percentage
	 */
	public void setBenefitPercentage(Percentage benefitPercentage) {
		this.benefitPercentage = benefitPercentage;
	}

	/**
	 * Sets the policy that determines if a dining by an account at this
	 * restaurant is eligible for benefit.
	 * 
	 * @param benefitAvailabilityPolicy
	 *            the benefit availability policy
	 */
	public void setBenefitAvailabilityPolicy(
			BenefitAvailabilityPolicy benefitAvailabilityPolicy) {
		this.benefitAvailabilityPolicy = benefitAvailabilityPolicy;
	}

	/**
	 * Returns the name of this restaurant.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the merchant number of this restaurant.
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * Returns this restaurant's benefit percentage.
	 */
	public Percentage getBenefitPercentage() {
		return benefitPercentage;
	}

	/**
	 * Returns this restaurant's benefit availability policy.
	 */
	public BenefitAvailabilityPolicy getBenefitAvailabilityPolicy() {
		return benefitAvailabilityPolicy;
	}

	/**
	 * Returns the id for this restaurant.
	 */
	public Long getEntityId() {
		return entityId;
	}

	/**
	 * Calculate the benefit eligible to this account for dining at this
	 * restaurant.
	 * 
	 * @param account
	 *            the account that dined at this restaurant
	 * @param dining
	 *            a dining event that occurred
	 * @return the benefit amount eligible for reward
	 */
	public MonetaryAmount calculateBenefitFor(Account account, Dining dining) {
		if (benefitAvailabilityPolicy.isBenefitAvailableFor(account, dining)) {
			return dining.getAmount().multiplyBy(benefitPercentage);
		} else {
			return MonetaryAmount.zero();
		}
	}

	public String toString() {
		return "Number = '" + number + "', name = '" + name
				+ "', benefitPercentage = " + benefitPercentage
				+ ", benefitAvailabilityPolicy = " + benefitAvailabilityPolicy;
	}

	// Internal methods for JPA only - hence they are protected.
	/**
	 * Sets this restaurant's benefit availability policy from the code stored
	 * in the underlying column. This is a database specific accessor using the
	 * JPA 2 @Access annotation.
	 */
	protected void setDbBenefitAvailabilityPolicy(String policyCode) {
		if ("A".equals(policyCode)) {
			benefitAvailabilityPolicy = AlwaysAvailable.INSTANCE;
		} else if ("N".equals(policyCode)) {
			benefitAvailabilityPolicy = NeverAvailable.INSTANCE;
		} else {
			throw new IllegalArgumentException("Not a supported policy code "
					+ policyCode);
		}
	}

	/**
	 * Returns this restaurant's benefit availability policy code for storage in
	 * the underlying column. This is a database specific accessor using the JPA
	 * 2 @Access annotation.
	 */
	protected String getDbBenefitAvailabilityPolicy() {
		if (benefitAvailabilityPolicy == AlwaysAvailable.INSTANCE) {
			return "A";
		} else if (benefitAvailabilityPolicy == NeverAvailable.INSTANCE) {
			return "N";
		} else {
			throw new IllegalArgumentException("No policy code for "
					+ benefitAvailabilityPolicy.getClass());
		}
	}
}