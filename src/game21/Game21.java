/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game21;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author zhuan
 */
public class Game21 {
    private static Scanner sc = new Scanner(System.in);
    private static Random rand = new Random();
    private static int[] scores = new int[2];
    private static int userIndex, computerIndex;
    private static String userThrowHint="Please hit return key to throw.";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        startBowling();
    }

    private static void startBowling() {
        do {
            startOneGame();
        } while (confirmContinue());
    }

    private static boolean confirmContinue() {
        System.out.print("Do you want to continue a new game(Y/N)?");
        String s = sc.next();
        return s.equalsIgnoreCase("y") || s.equalsIgnoreCase("yes");
    }

    private static void startOneGame() {
        System.out.println("New game start.");
        scores[0]=0; // clear scores
        scores[1]=0;
        determineWhoThrowFirst();
        for (int i=0;i<10;i++) {
            playOneRound();
        }
        printScores();
    }

    private static void determineWhoThrowFirst() {
        System.out.println("Press return key to determine who throws first:");
        sc.nextLine();
        userIndex = rand.nextInt(2);
        computerIndex = 1 - userIndex;
        System.out.printf("%s throws first.\r\n", userIndex == 0 ? "User" : "Computer");
    }

    private static void playOneRound() {
        if (userIndex==0) {
            playOneFrame(0,"Your",userThrowHint);
            playOneFrame(1,"Computer",null);
        }
        else {
            playOneFrame(0,"Computer",null);
            playOneFrame(1,"Your",userThrowHint);
        }
    }

    private static void playOneFrame(int index, String player, String hint) {
        System.out.println("\r\n"+player+" turn:");
        if (hint!=null) { // should interact with user
            System.out.print(hint);
            sc.nextLine();
        }
        int bins1=rand.nextInt(10)+1;
        System.out.printf("First throw: %d bins.\r\n",bins1);
        if (bins1==10) { // hit 10 bins at first throw
            scores[index]+=20;
        } 
        else { // need the second throw
            System.out.print("Now second throw.");
            if (hint!=null) {
                System.out.print(hint);
                sc.nextLine();
            }
            int bins2=rand.nextInt(10-bins1)+1;
            System.out.printf("Second throw: %d bins.\r\n",bins2);
            if (bins1+bins2==10) // hit 10 bins with 2 throws
                scores[index]+=15;
            else // not all bins are knocked.
                scores[index]+=bins1+bins2;
        }
        System.out.printf("%s current score is : %d. \r\n",player,scores[index]);
    }

    private static void printScores() {
        System.out.println("Your score:"+scores[userIndex]);
        System.out.println("Computer score:"+scores[computerIndex]);
        if (scores[userIndex]>scores[computerIndex])
            System.out.println("You win.");
        else if (scores[userIndex]<scores[computerIndex])
            System.out.println("You loose.");
        else
            System.out.println("It's incredible! You have got same score with computer.");
    }
}
