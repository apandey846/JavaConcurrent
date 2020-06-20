package my.java.Others;

public class Solution {

    public static void main(String[] arg){
        System.out.println("Result : "+checkSequence("Abhi","Abhdfdfd"));
    }

    public static boolean checkSequence(String s1, String s2)
    {
        String smallerString = s1.length()<=s2.length() ? s1 : s2;
        String largerString = smallerString.equals(s2) ? s1 : s2;
        int smallerStringPointer=0;
        for(int i=0;i<largerString.length();i++){
            if(smallerString.charAt(smallerStringPointer) == largerString.charAt(i)){
                smallerStringPointer++;
            }
            if(smallerStringPointer == smallerString.length()){
                return true;
            }
        }
        return false;
    }
}
