using System;

public class Palindrome
{
    public static bool IsPalindrome(string word)
    {
        int length = word.Length;
        int middle = length / 2;
        if (word[0].ToString().ToLower() != word[length - 1].ToString()) return false;
        for (int i = 1; i <= length - middle - 1;i++)
        {
            if (word[i].ToString() != word[length - 1 - i].ToString()) return false;
        }
        return true;
        throw new NotImplementedException("Waiting to be implemented.");

    }

    public static void Main(string[] args)
    {
        Console.WriteLine(Palindrome.IsPalindrome("Deleveled"));
    }
}