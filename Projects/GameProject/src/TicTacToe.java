import java.awt.*;
import javax.swing.*;
import java.util.Random;

/*
 * Chris Elim V Nodel
 * CS 1181 
 * Project 2: GUI (game chosen: TicTacToe)
 * 10/9/2022
 */
public class TicTacToe extends JFrame {

    //initialpress is for player(s) button symbol
    //aiinitial press is solely for the AI player
    private String initialPress= "O";
    private String AIInitialPress="X";

    //scores for the players
    private int playerOneScore=0;
    private int playerTwoScore=0;

    //turn counters counts the total turns
    //symbol checker counts the number of player symbols currently on the board
    private int turnCounter=0;
    private int symbolChecker=0;

    //randomizer for the AIs selection
    private Random randomSquare = new Random();
    public TicTacToe()
    {
        //Initializes the JPanel for the vs. player game
        JPanel buttonPanel=new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 3));
        buttonPanel.setMinimumSize(new Dimension(300,300));
        buttonPanel.setPreferredSize(new Dimension(300,300));

        //message variable that will be used in both game types
        JLabel message= new JLabel();

        for(int x=0;x<9;x++)
        {
            //instantiataes JButtons for the panel
            JButton tacSquares= new JButton();
            tacSquares.setPreferredSize(new Dimension(100,100));
            tacSquares.setFont(new Font("Serif", Font.PLAIN, 30 ));

            //ActionListener will be defined by component
            tacSquares.addActionListener(e ->
            {
                //ticks up turn count
                turnCounter++;

                //if the button does have a prior symbol inside do the following:
                if(!(tacSquares.getText().equals("O"))&&!(tacSquares.getText().equals("X")))
                {

                //sets the buttons symbol to initialPress, neutralizes error message if it exists
                tacSquares.setText(initialPress);
                message.setText("");

                //checks if the board is filled or not
                for(int j=0;j<9;j++)
                {
                    if(((JButton)buttonPanel.getComponent(j)).getText().equals("O")||((JButton)buttonPanel.getComponent(j)).getText().equals("X"))
                    symbolChecker++;
                }

                //if the turn count and the number of symbols both equal 9 the game defaults to a tie and disables the board
                if(turnCounter==9&&symbolChecker==9)
                {
                    message.setText("Oh no it is a tie");
                    for(int j=0;j<9;j++)
                    {
                        ((JButton)buttonPanel.getComponent(j)).setEnabled(false);
                    }
                }

                //if the symbolnumber is not 9, it resets to 0
                else
                {
                symbolChecker=0;

                    //if the initialPress has a value of O, recolors the button text color to blue
                    if(initialPress.equals("O"))
                    {
                        tacSquares.setForeground(Color.BLUE);
                        tacSquares.repaint();

                        //checks if the player has successfully won
                        //if yes the board will be disabled
                        //win conditions are, there have been five or more turns that have passed
                        //the winchecker method returns true (see below)
                        //the player one score is three
                        if(turnCounter>=5&&winchecker(buttonPanel)&&playerOneScore==3)
                        {
                            message.setText("Congratulations Player One. You have won the game");
                            for(int j=0;j<9;j++)
                            {
                                ((JButton)buttonPanel.getComponent(j)).setEnabled(false);
                            }
                            
                        }
                        //otherwise the turn is set to player two's, the initialPress is
                        //set to X
                        else
                        {
                        message.setText("It is now player Twos Turn");
                        initialPress="X";
                        }
                    }
                    //the following cases relate to when the turn is player two's or the 
                    //initial press equals x
                    //the button foreground is set to red
                    else if(initialPress.equals("X"))
                    {
                        tacSquares.setForeground(Color.RED);
                        tacSquares.repaint();

                        //this checks if the second player has won, with similar conditions
                        //to player one
                        //if yes disables the buttons
                        //otherwise sets it to player one's turn
                        if(turnCounter>=5&&winchecker(buttonPanel)&&playerTwoScore==3)
                        {
                            message.setText("Congratulations Player Two. You have won the game");
                            for(int j=0;j<9;j++)
                            {
                                ((JButton)buttonPanel.getComponent(j)).setEnabled(false);    
                            }
                        }else
                        {
                        message.setText("It is now player Ones Turn");
                        initialPress="O";
                        }
                    }
                }
            }
            //this considers cases where the player attempts to press a symbol occupied button
            //it informs the player that the current is not possible and that it is still their turn
            else 
                {
                if(initialPress.equals("O"))
                {
                    message.setText("Uh Oh you cant do that. It is still Player Ones turn");
                    initialPress="O";
                }else if(initialPress.equals("X"))
                {
                    message.setText("Uh Oh you cant do that, it is still player twos turn");
                    initialPress="X";
                }
                }

                //the message is repainted at the end of the case set 
                message.revalidate();
                message.repaint();
                }     
            );
            buttonPanel.add(tacSquares);
        }

        //instantiates the panel for the AI opponent game
        JPanel AIButtonPanel=new JPanel();
        AIButtonPanel.setMinimumSize(new Dimension(300,300));
        AIButtonPanel.setPreferredSize(new Dimension(300,300));
        AIButtonPanel.setLayout(new GridLayout(3, 3));

        for(int x=0;x<9;x++)
        {
            //similar parameters for the buttons are initialized
            JButton tacSquares= new JButton();
            tacSquares.setPreferredSize(new Dimension(100,100));
            tacSquares.setFont(new Font("Serif", Font.PLAIN, 30 ));

            //minor modifications for the player have been made
            tacSquares.addActionListener(e ->
            {

                turnCounter++;

                //sets button text color to blue and inlays the initial press
                //(note: initial press is only o for consideration of this set)
                //(the ai has its own press variable)
                if(!(tacSquares.getText().equals("O"))&&!(tacSquares.getText().equals("X")))
                {
                    
                    tacSquares.setText(initialPress);
                    tacSquares.setForeground(Color.BLUE);
                    tacSquares.repaint();
                
                //checks if board is filled or not
                //only checks at the end of player one's turn due to them being the odd turn
                //and thus the finalizing turn
                for(int j=0;j<9;j++)
                {
                    if(((JButton)buttonPanel.getComponent(j)).getText().equals("O")||((JButton)buttonPanel.getComponent(j)).getText().equals("X"))
                    symbolChecker++;
                }

                //similar tie check 
                if(turnCounter==9&&symbolChecker==9)
                {
                    message.setText("Oh no it is a tie");
                    for(int j=0;j<9;j++)
                    {
                        ((JButton)buttonPanel.getComponent(j)).setEnabled(false);
                    }
                }

                //then checks if player one has accomplished the win condition
                //if yes disables the board
                //if not prepares the board for the AI's turn 
                //the board is also disabled in this instance to prevent off phonomenon 
                //originating from the player
                else
                {
                    message.setText("");
                    if(turnCounter>=5&&winchecker(AIButtonPanel)&&playerOneScore==3)
                    {
                        message.setText("Congratulations Player One. You have won the game");
                        for(int j=0;j<9;j++)
                        {
                            ((JButton)AIButtonPanel.getComponent(j)).setEnabled(false);
                        }            
                    }else
                    {
                    message.setText("It is now player Twos Turn");
                    for(int j=0;j<9;j++)
                    {
                        ((JButton)AIButtonPanel.getComponent(j)).setEnabled(false);    
                    }

                    //All AI actions are done in the timer event
                    //Admittedly the algorithm for it is still rather rudimentary 
                    //and takes no consideration of the player's actions
                    Timer t= new Timer(5000, ex ->
                    {
                        turnCounter++;

                        //randomizer that picks a number such that it will be used to inlay 
                        //the AI's symbol
                        //will refresh while the randomizer does pick a blank space
                        int randomSquares= randomSquare.nextInt(9);
                        while(!((JButton)(AIButtonPanel.getComponent(randomSquares))).getText().equals(""))
                        {
                            randomSquares= randomSquare.nextInt(9);
                        }

                        //sets given space to red text color and sets an X in place
                        ((JButton)AIButtonPanel.getComponent(randomSquares)).setForeground(Color.RED);
                        ((JButton)AIButtonPanel.getComponent(randomSquares)).setText(AIInitialPress);

                        //checks the win condition of the AI, due to the board being disabled 
                        //during the bot's turn it will simply leave the board deactivated
                        if(turnCounter>=5&&winchecker(AIButtonPanel)&&playerTwoScore==3)
                        {
                            message.setText("Congratulations Player Two. You have won the game");
                        }

                        //otherwise enables the board for player one's turn
                        else
                        {
                        message.setText("It is now player Ones Turn");
                        for(int j=0;j<9;j++)
                        {
                            ((JButton)AIButtonPanel.getComponent(j)).setEnabled(true);    
                        }
                    }
                    }
                    );
                    //the timer only repeats once per action instance
                    t.setRepeats(false);
                    t.start();
                    }
                }
            }
                //error message only for the player
                else
                {
                    message.setText("Uh Oh You cant do that. It is still your turn");
                }
                message.revalidate();
                message.repaint();
        });
            AIButtonPanel.add(tacSquares);
        }
        //root is the panel that will contain all elements
        JPanel root= new JPanel();

        //subpanel contains the minor elements
        JPanel subPanel= new JPanel();
        subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));

        //buttons for the selection of player mode
        JButton playerSelect= new JButton("Select to Play Against a Player");
        JButton AISelect= new JButton("Select to Play Against an AI");
        subPanel.add(playerSelect);
        subPanel.add(AISelect);

        //formatting
        subPanel.setMinimumSize(new Dimension(200,200));
        root.setLayout(new BorderLayout());
        root.add(subPanel, BorderLayout.CENTER);
        
        //instructions for playing the game
        JLabel instructions = new JLabel("<html>Hello and Welcome to the TicTacToe game.<br>Select if you would like to fight a player or AI opponent.<br>Your goal is to fill Three Squares with Os</br></br></html>");

        //the select buttons when pressed will perform similar functions
        //removes the buttons themselves
        //replaces them wth either the player or AI board
        //then adds the instructions
        playerSelect.addActionListener((e) -> 
        {
            root.remove(subPanel);
            root.revalidate();
            root.repaint();
            root.setLayout(new BoxLayout(root, BoxLayout.X_AXIS));
            subPanel.remove(playerSelect);
            subPanel.remove(AISelect);
            subPanel.add(buttonPanel);
            subPanel.add(message);
            root.add(subPanel);
            root.add(instructions);
            root.revalidate();
            root.repaint();
        });
        AISelect.addActionListener((e) -> 
        {
            root.remove(subPanel);
            root.revalidate();
            root.repaint();
            root.setLayout(new BoxLayout(root, BoxLayout.X_AXIS));
            subPanel.remove(playerSelect);
            subPanel.remove(AISelect);
            subPanel.add(AIButtonPanel);
            subPanel.add(message);
            root.add(subPanel);
            root.add(instructions);
            root.revalidate();
            root.repaint();
        });

        //standard process to set up the GUI
        this.getContentPane().add(root);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600,500);
        this.setVisible(true);
    }
    public static void main(String[] args)
    {
        new TicTacToe();
    }

    //winchecker method
    //checks for all possible cases
    //will set either 
    //player one ot two's score to 3
    //and returns true if one of the conditions is met
    //otherwise returns false
    public boolean winchecker (JPanel buttons)
    {
        if(((JButton)buttons.getComponent(0)).getText().equals("O")&&((JButton)buttons.getComponent(1)).getText().equals("O")&&((JButton)buttons.getComponent(2)).getText().equals("O"))
        {
            playerOneScore=3;
            return true;
        }else if(((JButton)buttons.getComponent(3)).getText().equals("O")&&((JButton)buttons.getComponent(4)).getText().equals("O")&&((JButton)buttons.getComponent(5)).getText().equals("O"))
        {
            playerOneScore=3;
            return true;
        }
        else if(((JButton)buttons.getComponent(6)).getText().equals("O")&&((JButton)buttons.getComponent(7)).getText().equals("O")&&((JButton)buttons.getComponent(8)).getText().equals("O"))
        {
            playerOneScore=3;
            return true;
        }
        else if(((JButton)buttons.getComponent(0)).getText().equals("O")&&((JButton)buttons.getComponent(3)).getText().equals("O")&&((JButton)buttons.getComponent(6)).getText().equals("O"))
        {
            playerOneScore=3;
            return true;
        }else if(((JButton)buttons.getComponent(1)).getText().equals("O")&&((JButton)buttons.getComponent(4)).getText().equals("O")&&((JButton)buttons.getComponent(7)).getText().equals("O"))
        {
            playerOneScore=3;
            return true;
        }
        else if(((JButton)buttons.getComponent(2)).getText().equals("O")&&((JButton)buttons.getComponent(5)).getText().equals("O")&&((JButton)buttons.getComponent(8)).getText().equals("O"))
        {
            playerOneScore=3;
            return true;
        }else if(((JButton)buttons.getComponent(0)).getText().equals("O")&&((JButton)buttons.getComponent(4)).getText().equals("O")&&((JButton)buttons.getComponent(8)).getText().equals("O"))
        {
            playerOneScore=3;
            return true;
        }
        if(((JButton)buttons.getComponent(2)).getText().equals("O")&&((JButton)buttons.getComponent(4)).getText().equals("O")&&((JButton)buttons.getComponent(6)).getText().equals("O"))
        {
            playerOneScore=3;
            return true;
        }else if(((JButton)buttons.getComponent(0)).getText().equals("X")&&((JButton)buttons.getComponent(1)).getText().equals("X")&&((JButton)buttons.getComponent(2)).getText().equals("X"))
        {
            playerTwoScore=3;
            return true;
        }else if(((JButton)buttons.getComponent(3)).getText().equals("X")&&((JButton)buttons.getComponent(4)).getText().equals("X")&&((JButton)buttons.getComponent(5)).getText().equals("X"))
        {
            playerTwoScore=3;
            return true;
        }
        else if(((JButton)buttons.getComponent(6)).getText().equals("X")&&((JButton)buttons.getComponent(7)).getText().equals("X")&&((JButton)buttons.getComponent(8)).getText().equals("X"))
        {
            playerTwoScore=3;
            return true;
        }
        else if(((JButton)buttons.getComponent(0)).getText().equals("X")&&((JButton)buttons.getComponent(3)).getText().equals("X")&&((JButton)buttons.getComponent(6)).getText().equals("X"))
        {
            playerTwoScore=3;
            return true;
        }else if(((JButton)buttons.getComponent(1)).getText().equals("X")&&((JButton)buttons.getComponent(4)).getText().equals("X")&&((JButton)buttons.getComponent(7)).getText().equals("X"))
        {
            playerTwoScore=3;
            return true;
        }
        else if(((JButton)buttons.getComponent(2)).getText().equals("X")&&((JButton)buttons.getComponent(5)).getText().equals("X")&&((JButton)buttons.getComponent(8)).getText().equals("X"))
        {
            playerTwoScore=3;
            return true;
        }else if(((JButton)buttons.getComponent(0)).getText().equals("X")&&((JButton)buttons.getComponent(4)).getText().equals("X")&&((JButton)buttons.getComponent(8)).getText().equals("X"))
        {
            playerTwoScore=3;
            return true;
        }
        if(((JButton)buttons.getComponent(2)).getText().equals("X")&&((JButton)buttons.getComponent(4)).getText().equals("X")&&((JButton)buttons.getComponent(6)).getText().equals("X"))
        {
            playerTwoScore=3;
            return true;
        }
        else
        return false;
    }
    
}
