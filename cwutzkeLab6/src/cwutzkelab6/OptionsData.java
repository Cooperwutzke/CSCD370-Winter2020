/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cwutzkeLab6;

/**
 *
 * @author cooper.wutzke
 */
public class OptionsData
{
    public static boolean showString;
    public static boolean dateTime;
    public static boolean italics;
    public static boolean bold;
    public static String userString;
    public static int size;
    
    public OptionsData()
    {
        showString = true;
        dateTime = false;
        italics = false;
        bold = false;
        userString = "Do File -> Options to change this";
        size = 8;
    }
}
