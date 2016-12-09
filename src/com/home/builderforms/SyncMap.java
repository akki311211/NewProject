/*

 * @(#)CollectionModel.java        1.00 20/01/2001

 *

 * Copyright (c) Webrizon eSolutions Limited.

 * B-31 Sector-5, Noida 201 301,India.

 * All rights reserved.

 *

 */



package com.home.builderforms;



import java.io.Serializable;

import java.util.Iterator;

import java.util.HashMap;

import java.util.Set;

import java.util.Collection;




/**

 * This class represents the model data for the collection data.

 *

 * @version 1.0 Date :20th January 2001

 * @author 	Ashish

 */



public class SyncMap implements Serializable {



    public HashMap collection;



    private static final int CONSTRUCTOR= 0;

    private static final int COPY 		= 1;

    private static final int ADD 		= 2;

    private static final int ADDALL 	= 3;

    private static final int REMOVE 	= 4;

    private static final int GETALL 	= 5;

    private static final int GETONE 	= 6;

    private static final int GETLIKEONE = 7;

    private static final int GETSIZE 	= 8;

    private static final int CLEAR 		= 9;

    private static final int KEYSET		= 10;

    private static final int CONTAINSKEY

            = 11;

    public SyncMap(HashMap collection) {

        methodCaller(CONSTRUCTOR,collection,null);

    }

    public void copy(SyncMap other) {

        methodCaller(COPY,other.collection,null);

    }



    public HashMap getCollection()

    {

        return collection;

    }



    private synchronized  Object methodCaller(int methodType,Object param1, Object param2)

    {

        switch (methodType)

        {

            case CONSTRUCTOR:

            {

                collection = (HashMap)param1;

                break;

            }

            case COPY:

            {

                if (collection!=null)

                {

                    HashMap map = (HashMap)param1;

                    collection.clear();

                    collection.putAll(map);

                }

                break;

            }

            case ADD:

            {

                return collection.put(param1,param2);

            }



            case ADDALL:

            {

                collection.putAll((HashMap)param1);

            }

            case REMOVE:

            {

                collection.remove(param1);

                break;

            }

            case GETALL:

            {

                if (collection != null)

                {

                    HashMap collectionCopy = (HashMap)collection.clone();

                    return collectionCopy.values().iterator();

                }

                break;



            }

            case GETONE:

            {

                if (collection !=null)

                {

                    return collection.get(param1);

                }

                break;

            }

            case GETLIKEONE:

            {

                if (collection !=null)

                {

                    Iterator it = collection.keySet().iterator();

                    boolean found = false;

                    String key = null;

                    while (it.hasNext())

                    {

                        key = it.next().toString();

                        if (param1.toString().indexOf(key) != -1)

                        {

                            found = true;

                            break;

                        }

                    }

                    if (found) return collection.get(key);

                }

                break;

            }

            case GETSIZE:

            {

                int size = (collection==null) ? -1: collection.size();

                return new Integer(size);

            }

            case CLEAR:

            {

                collection.clear();

                return null;

            }

            case KEYSET:

            {

                if (collection != null)

                {

                    HashMap collectionCopy = (HashMap)collection.clone();

                    return collectionCopy.keySet().iterator();

                }

                break;

            }

            case CONTAINSKEY:

            {

                if(collection != null && collection.containsKey(param1))

                {

                    return new Boolean(true);

                }else{

                    return new Boolean(false);

                }

            }

        }

        return null;

    }



    public int getCollectionSize()

    {

        return ((Integer)methodCaller(GETSIZE,null,null)).intValue();

    }



    /** @return an iterator over the whole collection. */

    public Iterator getAll() {

        return (Iterator)methodCaller(GETALL,null,null);

    }



    public Iterator getKeySet() {

        return (Iterator)methodCaller(KEYSET,null,null);

    }



    public Object getOne(String id) {

        return methodCaller(GETONE,id,null);

    }



    public Object getLikeOne(String id){

        return methodCaller(GETLIKEONE,id,null);

    }



    public Object put(Object key, Object value){



        return methodCaller(ADD,key,value);

    }



    public Object get(Object key){



        return methodCaller(GETONE,key, null);

    }



    public void putAll(HashMap map){



        methodCaller(ADDALL,map,null);

    }



    public void remove(Object key){

        methodCaller(REMOVE,key,null);

    }



    public void clear(){

        methodCaller(CLEAR, null, null);

    }

    public boolean containsKey(Object obj){

        return ((Boolean)methodCaller(CONTAINSKEY, obj, null)).booleanValue();

    }

}