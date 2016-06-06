package utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Map where the size is limited.
 *
 * If an entry is added that would take it above the size limit, the eldest entry is removed.
 *
 * Would have thought that a simple thing like this would exist in a 3rd party library, but apparently not at the
 * time of writing.
 *
 * Created by rowland-hall on 22/04/15
 */
public class SizeLimitedHashMap<K, V> extends LinkedHashMap<K, V>
{
    private int maxNumEntries;

    public SizeLimitedHashMap( int maxNumEntries )
    {
        this.maxNumEntries = maxNumEntries;
    }

    @Override
    protected boolean removeEldestEntry( Map.Entry<K, V> eldest )
    {
        return size() > maxNumEntries;
    }
}
