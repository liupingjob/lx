package com.example.lx.util;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class StreamUtils {

    /**
     * 序列化,List
     */
    public static <T> boolean writeObject(List<T> list,File file)
    {
        T[] array = (T[]) list.toArray();
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) 
        {
            out.writeObject(array);
            out.flush();
            return true;
        }
        catch (IOException e) 
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 序列化,List
     */
    public static   boolean writeStr(String content,File file)
    {
         try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file)))
        {
            out.writeObject(content);
            out.flush();
            return true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }



    /**
     * 反序列化,List
     */
    public static String readObjectForStr(File file)
    {

        try(ObjectInputStream out = new ObjectInputStream(new FileInputStream(file)))
        {

            return  out.readObject()+"";
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 反序列化,List
     */
    public static <E> List<E> readObjectForList(File file)
    {
        E[] object;
        try(ObjectInputStream out = new ObjectInputStream(new FileInputStream(file)))
        {
            object = (E[]) out.readObject();
            return Arrays.asList(object);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
//    	//序列化
//    	StreamUtils.<TestObject>writeObject(list, new File("object.adt"));
//    	//反序列化
//    	List<TestObject> re = StreamOfByte.<TestObject>readObjectForList(new File("object.txt"));
	}
}