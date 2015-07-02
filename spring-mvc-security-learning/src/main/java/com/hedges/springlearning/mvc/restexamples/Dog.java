package com.hedges.springlearning.mvc.restexamples;

/**
 * Created by rowland-hall on 26/06/15
 */
public class Dog
{
    private String name;

    private String age;

    private String breed;

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getAge()
    {
        return age;
    }

    public void setAge( String age )
    {
        this.age = age;
    }

    public String getBreed()
    {
        return breed;
    }

    public void setBreed( String breed )
    {
        this.breed = breed;
    }

    @Override
    public String toString()
    {
        return "Dog{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", breed='" + breed + '\'' +
                '}';
    }
}
