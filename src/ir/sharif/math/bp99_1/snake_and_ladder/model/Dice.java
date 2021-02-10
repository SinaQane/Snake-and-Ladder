package ir.sharif.math.bp99_1.snake_and_ladder.model;

import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;

import java.util.Random;

public class Dice
{

    /**
     * add some fields to store :
     * 1) chance of each dice number ( primary chance of each number, should be 1 )
     * currently our dice has 1 to 6.
     * 2) generate a random number
     * <p>
     * initialize these fields in constructor.
     */

    int sides = 6;
    int[] chance = new int[sides];
    int lastDice;

    public Dice()
    {
        this.lastDice = 0;
        for (int i=0; i<sides; i++)
            this.chance[i] = 1;
    }

    /**
     * create an algorithm generate a random number(between 1 to 6) according to the
     * chance of each dice number( you store them somewhere)
     * return the generated number
     */
    public int roll()
    {
        int chanceSum = 0, sum = 0;
        for (int i=0; i<sides; i++) chanceSum += this.chance[i];
        int[] diceNumbers = new int [chanceSum];
        for (int i=0; i<sides; i++)
        {
            for (int j=0; j<this.chance[i]; j++)
            {
                diceNumbers[sum]=i+1;
                sum++;
            }
        }
        Random random = new Random();
        int diceResult = diceNumbers[random.nextInt(chanceSum)];
        if (diceResult==2)
            resetDice();
        if (lastDice==diceResult)
        {
            lastDice = 0;
            addChance(diceResult, 1);
            System.out.println("Dice " + diceResult + " chance increased by multiple occurrence.\n");
        }
        else
            lastDice=diceResult;
        return diceResult;
    }

    /**
     * give a dice number and a chance, you should UPDATE chance
     * of that number.
     * pay attention chance of none of the numbers must not be negative (it can be zero)
     */
    public void addChance(int number, int chance)
    {
        if (number!=0)
        {
            if (this.chance[number - 1] + chance < 0)
                this.chance[number - 1] = 0;
            else if (this.chance[number - 1] + chance > 8)
                this.chance[number - 1] = 8;
            else
                this.chance[number - 1] += chance;
        }
    }

    public void setChance(int side, int chance)
    {
        this.chance[side-1] = chance;
    }

    public void resetDice()
    {
        for (int i=0; i<6; i++)
        {
            this.chance[i] = 1;
        }
        System.out.println("This player's dice was reset.\n");
    }

    public int getChance(int side)
    {
        return this.chance[side-1];
    }

    /**
     * you should return the details of the dice number.
     * sth like:
     * "1 with #1 chance.
     * 2 with #2 chance.
     * 3 with #3 chance
     * .
     * .
     * . "
     * where #i is the chance of number i.
     */
    public String getDetails()
    {
        StringBuilder details = new StringBuilder();
        for (int i=0; i<sides; i++)
            details.append(String.format("%d with %d chance.\n", i+1, this.chance[i]));
        return details.toString();
    }

    public int getLastDice()
    {
        return this.lastDice;
    }

    public void setLastDice(int lastDice)
    {
        this.lastDice = lastDice;
    }
}