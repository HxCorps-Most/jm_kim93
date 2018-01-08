using System;
using System.Diagnostics;

public class TextInput // accepts all characters
{
    protected string value;
    public TextInput() {}
    public virtual void Add(char c) // Adds the given character to the current value
    {
        this.value = this.value + c;
    }
    public string GetValue() // Returns the current value
    {
        return value;
    }
} 

public class NumericInput : TextInput  // accepts only digits
{
    public NumericInput() {}
    override public void Add(char c)
    {
        if (c >= 48 && c < 58) base.Add(c);
    }
}

public class UserInput
{
    public static void Main(string[] args)
    {
        Stopwatch swh = new Stopwatch();
        swh.Start();

        TextInput input = new NumericInput();
        input.Add('1');
        input.Add('a');
        input.Add('0');
        Console.WriteLine(input.GetValue());

        swh.Stop();
        Console.WriteLine(swh.ElapsedMilliseconds.ToString());
    }
}
