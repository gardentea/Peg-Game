package pegtest;

import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PegLogic extends JFrame
{
    JButton[][] buttons = new JButton[7][7];
    int[][] pegBoardSquares = new int[7][7];
    ButtonHandler handler = new ButtonHandler();

    int storeY;
    int storeX;
    int tempY;
    int tempX;

    static Icon holePic;
    static Icon peggedHolePic;
    static Icon resetButton;

    public PegLogic()
    {
        setLayout(new GridLayout(7,7));
        holePic = new ImageIcon(getClass().getResource("PegHoleBit1.jpg"));
        peggedHolePic = new ImageIcon(getClass().getResource("FilledPegHole1.jpg"));
        resetButton = new ImageIcon(getClass().getResource("ResetButton.png"));

        for (int y = 0; y < buttons.length; y++)
        {
            for (int x = 0; x < buttons[y].length; x++)
            {
                buttons[y][x] = new JButton();
                buttons[y][x].addActionListener(handler);
                add(buttons[y][x]);
            }
        }
        SetBoard();
    }

    public void SetBoard()
    {
        for (int y = 0; y < buttons.length; y++)
        {
            for (int x = 0; x < buttons[y].length; x++)
            {
                // 1 = Filled. 0 = Empty. -1 = Invalid space.

                // Skipping check for rows that have no invalid squares.
                if ((y == 2) || (y == 3) || (y == 4))
                {
                    buttons[y][x].setIcon(peggedHolePic);
                    pegBoardSquares[y][x] = 1;
                }
                else
                {
                    // Checking for invalid squares.
                    if ((x == 0) || (x == 1) || (x == 5) || (x == 6))
                    {
                        // No icon necessary for these squares.
                        pegBoardSquares[y][x] = -1;
                    }
                    // Applying icon and proper integer to valid squares.
                    else
                    {
                        buttons[y][x].setIcon(peggedHolePic);
                        pegBoardSquares[y][x] = 1;
                    }
                }

                // Fills in the middle square with the empty peg picture.
                // This is set at the end to very clumsily override any previous icon's set.
                if ((y == 3) && (x == 3))
                {
                    buttons[y][x].setIcon(holePic);
                    pegBoardSquares[y][x] = 0;
                }

                if ((y == 6) && (x == 6))
                {
                    buttons[y][x].setIcon(resetButton);
                    pegBoardSquares[y][x] = -1;
                }
            }
        }
    }

    private class ButtonHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            for (int y = 0; y < buttons.length; y++)
            {
                for (int x = 0; x < buttons[y].length; x++)
                {
                    tempY = y;
                    tempX = x;

                    if (event.getSource() == buttons[y][x])
                    {
                        if (buttons[y][x] == buttons[6][6] )
                        {
                            SetBoard();
                        }
                        // Checks value of pegBoardSquares[y][x]. -1 indicates invalid, so if the square is invalid, then do nothing.
                        else if (pegBoardSquares[y][x] == -1)
                        {
                            continue;
                        }
                        else if (pegBoardSquares[y][x] == 1)
                        {
                            storeY = y;
                            storeX = x;
                        }
                        else
                        {
                            // These can probably be refactored into calling a class that takes in the +1/-1 value and the Y or X to change
                           if (storeY + 2 == tempY)
                           {
                               buttons[storeY + 1][storeX].setIcon(holePic);
                               pegBoardSquares[storeY + 1][storeX] = 0;

                               buttons[storeY][storeX].setIcon(holePic);
                               pegBoardSquares[storeY][storeX] = 0;

                               buttons[y][x].setIcon(peggedHolePic);
                               pegBoardSquares[y][x] = 1;
                           }
                           else if (storeY - 2 == tempY)
                           {
                               buttons[storeY - 1][storeX].setIcon(holePic);
                               pegBoardSquares[storeY - 1][storeX] = 0;

                               buttons[storeY][storeX].setIcon(holePic);
                               pegBoardSquares[storeY][storeX] = 0;

                               buttons[y][x].setIcon(peggedHolePic);
                               pegBoardSquares[y][x] = 1;
                           }
                           else if (storeX + 2 == tempX)
                           {
                               buttons[storeY][storeX + 1].setIcon(holePic);
                               pegBoardSquares[storeY][storeX + 1] = 0;

                               buttons[storeY][storeX].setIcon(holePic);
                               pegBoardSquares[storeY][storeX] = 0;

                               buttons[y][x].setIcon(peggedHolePic);
                               pegBoardSquares[y][x] = 1;
                           }
                           else if (storeX - 2 == tempX)
                           {
                               buttons[storeY][storeX - 1].setIcon(holePic);
                               pegBoardSquares[storeY][storeX - 1] = 0;

                               buttons[storeY][storeX].setIcon(holePic);
                               pegBoardSquares[storeY][storeX] = 0;

                               buttons[y][x].setIcon(peggedHolePic);
                               pegBoardSquares[y][x] = 1;
                           }
                        }
                    }
                }
            }
        }
    }
}