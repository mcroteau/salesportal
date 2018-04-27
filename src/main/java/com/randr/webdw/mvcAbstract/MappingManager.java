/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

package com.randr.webdw.mvcAbstract;

import java.util.Hashtable;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2000</p>
 * <p>Company: </p>
 *
 * @author unascribed
 * @version 1.0
 */
public class MappingManager {
    private static Hashtable detailMappingsContainer = new Hashtable();

    /**
     * Method declaration
     *
     * @param detailClass
     * @param mappingHashtable
     * @see
     */
    public static void addDetailClassMapping(Class detailClass, Hashtable mappingHashtable) {
        detailMappingsContainer.put(detailClass, mappingHashtable);
    }

    /**
     * Method declaration
     *
     * @param detailClass
     * @return Hashtable
     * @see
     */
    public static Hashtable getDetailClassMapping(Class detailClass) {
        return (Hashtable) detailMappingsContainer.get(detailClass);
    }

}


/*--- formatting done in "Sun Java Convention" style on 01-28-2003 ---*/

