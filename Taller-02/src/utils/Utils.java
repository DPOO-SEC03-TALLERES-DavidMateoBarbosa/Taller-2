package utils;

import java.io.*;

public class Utils {
    public static String input(String prompt)
    {
        try
        {
            printNoJump(prompt);
            return (new BufferedReader(new InputStreamReader(System.in))).readLine();
        }
        catch (IOException ignored){print("Error with the console read");}
        return "";
    }

    public static File[] multiOpen(String start_path ,String ... paths){
        File[] files = new File[paths.length];
        for (int index = 0; index < paths.length; index ++){
            files[index] = new File(start_path + paths[index]);
        }
        return files;
    };
    public static void print(String ... values){
        String out_message = "";
        for (String message : values){
            out_message = out_message.concat(" "+message);
        }
        System.out.println(out_message);
    }
    public static void printNoJump(String ... values){
        String out_message = "";
        for (String message : values){
            out_message = out_message.concat(" "+message);
        }
        System.out.print(out_message);
    }

    public static int floorDiv(double num_1, double num_2){
        return toInt(num_1/num_2);
    }
    public static int toInt(Object __parse){
        if (__parse.getClass() == String.class) return Integer.parseInt((String) __parse);
        return (int) __parse;
    }
    public static double toDouble(Object __parse){
        if (__parse.getClass() == String.class) return Double.parseDouble((String) __parse);
        return (double) __parse;
    }
}
