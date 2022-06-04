package controllers;

public class MainRunner {
    public static void main(String[] args)
    {
        JsonRead.deserializeMoves();
        for(int i = 0; i < 10; i++)
            System.out.println();
        JsonRead.deserializeAbilities();
    }

}
