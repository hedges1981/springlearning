/**
 * Created by rowland-hall on 23/05/16
 */
public class TestO
{
    public static void main( String[] args )
    {
        int n =6;

        for( int i = 1; i<=6; i-- )
        {
            System.out.println( getStringLengthN( i, "#" ));
        }
    }

    static String getStringLengthN(int n, String charachter )
    {
        StringBuilder sb = new StringBuilder();

        for( int i=0; i<n; i++)
        {
            sb.append( charachter );
        }

        return sb.toString();
    }
}
