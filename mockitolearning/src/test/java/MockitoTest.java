import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by rowland-hall on 22/01/15
 * <p/>
 * Follows mockito user guide:
 * <p/>
 * https://docs.google.com/document/d/15mJ2Qrldx-J14ubTEnBj7nYN2FB8ap7xOn8GRAi24_A/edit?pli=1
 */
public class MockitoTest
{
    /**
     * *NOTE this bit demos standard way of setting up mocks and a test object injected with them:***
     */
    @Mock
    AnotherClass anotherClassMOck;
    @InjectMocks
    AClass aClassInjectedWithMocks = new AClass();

    @Before //need this to get the mocks set up. Can also set-up the mocking behaviour in this method.
    public void initMocks()
    {
        MockitoAnnotations.initMocks( this );
    }

    void p( Object o )
    {
        System.out.println( o );
    }

    @Test
    public void _1letsVerifySomeBehaviour()
    {
        List mockedList = mock( List.class );

        //using mock object
        mockedList.add( "one" );
        mockedList.clear();

        //verifies that mockedList.add("one") was called:
        verify( mockedList ).add( "one" );

        //verifies that mockedList.clear() was called:
        verify( mockedList ).clear();

        //this will cause the test to fail, as the method was not called with this param:
        //verify(mockedList).add("gfhghghg");

        //resets the mock:
        reset( mockedList );

        mockedList.add( "one" );
        mockedList.add( "one" );

        //verifies that it was called 2 times.
        verify( mockedList, times( 2 ) ).add( "one" );
    }

    @Test
    public void _2howAboutSomeStubbing()
    {
        LinkedList mockedList = mock( LinkedList.class );

        //stubbing: mocking up return values from calls:
        when( mockedList.get( 0 ) ).thenReturn( "0" );
        when( mockedList.get( 1 ) ).thenThrow( new RuntimeException() );

        //will give 0
        p( mockedList.get( 0 ) );

        //will throw exception:
        try
        {
            mockedList.get( 1 );
        }
        catch ( Exception e )
        {
            p( e );
        }

        //this will give null, as we have not mocked this call:
        p( mockedList.get( 33 ) );


        when( mockedList.get( 0 ) ).thenReturn( "value has been changed" );
        //will give the new value:
        p( mockedList.get( 0 ) );
    }

    @Test
    public void _3argumentMatchers()
    {
        LinkedList mockedList = mock( LinkedList.class );

        //use of the anyInt() argument matcher:
        when( mockedList.get( anyInt() ) ).thenReturn( "Any int argment matcher used" );

        //will return the same thing for any int argument
        assertEquals( mockedList.get( 1 ), "Any int argment matcher used" );
        assertEquals( mockedList.get( 1343434 ), "Any int argment matcher used" );

        //Use of a custom argument matcher, to get a yes/no decision as to whether or not an argument triggers something:
        Map mockedMap = mock( HashMap.class );
        when( mockedMap.get( argThat( new IsStringBeginningWithS() ) ) ).thenReturn( "String beginning with S" );

        assertEquals( "String beginning with S", mockedMap.get( "sssss" ) );      //these arguments match the IsStringBeginningWithS matcher,so mockito returns the designated thing.
        assertEquals( "String beginning with S", mockedMap.get( "Sdfdfdfdfdfd" ) );
        assertTrue( mockedMap.get( "asas" ) == null );   //doesn't match the matcher, so returns null.

        //Use of argumentMatcher for multiple parameters, if one param is an ArgumentMatcher, they all have to be.
        AClass mockedClass = mock( AClass.class );
        when( mockedClass.doSomething( anyString(), anyString(), eq( "String 3" ) ) ).thenReturn( "hello" );//note the use of the eq(..) argument matcher, that allows for a specific value to be put in.

        assertEquals( mockedClass.doSomething( "anyOldthing", "anyOldthing", "String 3" ), "hello" );
        assertNull( mockedClass.doSomething( "anyOldthing", "anyOldthing", "String 4" ) ); //doesn't match the matchers, so returns null.
    }

    @Test
    public void argumentCaptors()
    {
        /*
        Use e.g. this to capture the argument passed to a method call for inspection.

        ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);
   verify(mock).doSomething(argument.capture());
   assertEquals("John", argument.getValue().getName());
         */

    }

    /**
     * U can use mockito to test that some stuff occurred in a certain order.
     */
    @Test
    public void testInvocationOrder()
    {
        AClass aClass = mock( AClass.class );
        InOrder inOrder = inOrder( aClass );

        //call some stuff in an order:
        aClass.doSomething( "","","" ) ;
        aClass.doSomethingElse();

        //test will fail here if the order is different to what actually happened:
        inOrder.verify( aClass ).doSomething( "","","" );
        inOrder.verify( aClass ).doSomethingElse();
    }

    private class IsStringBeginningWithS extends ArgumentMatcher<String>
    {
        @Override
        public boolean matches( Object argument )
        {
            return argument != null
                    && argument instanceof String
                    && ( ( ( String ) argument ).startsWith( "s" ) || ( ( String ) argument ).startsWith( "S" ) );
        }
    }


    private class AClass
    {
        private AnotherClass anotherClass;

        private void setAnotherClass( AnotherClass anotherClass )
        {
            this.anotherClass = anotherClass;
        }

        public String doSomething( String s1, String s2, String s3 )
        {
            return "aString";
        }

        public void doSomethingElse()
        {

        }
    }

    private class AnotherClass
    {

    }


}
