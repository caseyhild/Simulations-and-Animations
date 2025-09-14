import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TextBox implements MouseListener, MouseMotionListener, KeyListener
{
    private final int xPos;             //x-coordinate of center of text box
    private final int yPos;             //y-coordinate of center of text box
    private final int textSize;         //size of text (font size)
    private final int fieldWidth;       //maximum number of characters that can be typed
    private final int blink;            //number of frames between each blink of the cursor
    private final int delay1;           //time after key is pressed until more of the same key action is used
    private final int delay2;           //time between successive key actions
    private String prompt;              //message to prompt the user with
    private String entry;               //text that is entered
    private int cursorPos;              //position of the cursor (number of characters to the left of cursor)
    private int time;                   //time when key started being pressed
    private int remainder;              //time since last key action (if key is being pressed, -1 otherwise)
    private boolean boxSelected;        //whether the text box is selected and can be typed in
    private boolean highlightBox;       //whether the text box should be outlined in yellow
    private int mouseX; //x coordinate of mouse
    private int mouseY; //y coordinate of mouse
    private boolean mousePressed; //whether mouse is being pressed (still or moving)
    private boolean keyPressed; //whether any key is being pressed
    private boolean keyReleased; //true immediately after key is released
    private boolean keyTyped; //true if key is pressed and a valid unicode character is generated
    private KeyEvent key; //key currently pressed (or last one pressed if none are pressed)
    private KeyEvent prevKey; //key previously pressed

    public TextBox()
    {
        //default values
        xPos = 320;
        yPos = 240;
        textSize = 12;
        fieldWidth = 20;
        prompt = "Enter text here:";

        entry = "";
        cursorPos = 0;
        time = 0;
        blink = 500;
        delay1 = 600;
        delay2 = 60;
        remainder = -1;
        boxSelected = false;
        highlightBox = false;

        //keyboard input
        keyPressed = false;
        keyReleased = false;
        keyTyped = false;
        key = new KeyEvent(new JFrame(), 0, 0, 0, 0, KeyEvent.CHAR_UNDEFINED);
        prevKey = new KeyEvent(new JFrame(), 0, 0, 0, 0, KeyEvent.CHAR_UNDEFINED);

        //mouse input
        mouseX = 0;
        mouseY = 0;
        mousePressed = false;
    }

    public TextBox(int x, int y, int ts, int fw, String p)
    {
        xPos = x;
        yPos = y;
        textSize = ts;
        fieldWidth = fw;
        prompt = p;

        entry = "";
        cursorPos = 0;
        time = 0;
        blink = 500;
        delay1 = 600;
        delay2 = 60;
        remainder = -1;
        boxSelected = false;
        highlightBox = false;

        //keyboard input
        keyPressed = false;
        keyReleased = false;
        keyTyped = false;
        key = new KeyEvent(new JFrame(), 0, 0, 0, 0, KeyEvent.CHAR_UNDEFINED);
        prevKey = new KeyEvent(new JFrame(), 0, 0, 0, 0, KeyEvent.CHAR_UNDEFINED);

        //mouse input
        mouseX = 0;
        mouseY = 0;
        mousePressed = false;
    }

    public void render(int frame, Graphics g)
    {
        //set font for prompt message
        g.setFont(new Font("Verdana", Font.BOLD, textSize + 3));
        FontMetrics fm = g.getFontMetrics();

        //change prompt message if box gets highlighted
        if(highlightBox && !prompt.startsWith("Invalid Entry, "))
            prompt = "Invalid Entry, " + prompt;

        //print prompt message
        g.setColor(new Color(0));
        g.drawString(prompt, xPos - fm.stringWidth(prompt)/2, yPos + fm.getAscent()/2 - 2 * textSize - 2);

        //make a string with fieldWidth number of characters
        StringBuilder fieldWidthString = new StringBuilder();
        fieldWidthString.append(" ".repeat(Math.max(0, fieldWidth)));

        if(boxSelected)
        {
            //time since last key action if key is still held down, -1 otherwise
            if(keyPressed && remainder == -1)
            {
                remainder = frame % delay2;
            }
            else if(keyReleased)
            {
                remainder = -1;
            }

            if(keyReleased && key.getKeyCode() == KeyEvent.VK_BACK_SPACE)
            {
                //backspace key released
                time = 0;
            }
            else if(keyPressed && frame >= time + delay1 && (frame - remainder) % delay2 == 0 && key.getKeyCode() == KeyEvent.VK_BACK_SPACE)
            {
                //backspace key pressed
                if(time == 0)
                    time = frame;
                entry = entry.substring(0, Math.max(0, cursorPos - 1)) + entry.substring(cursorPos);
                cursorPos--;
            }
            else if(keyReleased && key.getKeyCode() == KeyEvent.VK_DELETE)
            {
                //delete key released
                time = 0;
            }
            else if(keyPressed && frame >= time + delay1 && (frame - remainder) % delay2 == 0 && key.getKeyCode() == KeyEvent.VK_DELETE)
            {
                //delete key pressed
                if(time == 0)
                    time = frame;
                entry = entry.substring(0, cursorPos) + entry.substring(Math.min(cursorPos + 1, entry.length()));
            }
            else if(keyReleased && key.getKeyChar() != KeyEvent.CHAR_UNDEFINED && key.getKeyChar() != KeyEvent.VK_ESCAPE && key.getKeyChar() != KeyEvent.VK_ENTER && key.getKeyCode() != KeyEvent.VK_BACK_SPACE && key.getKeyCode() != KeyEvent.VK_DELETE && !key.isControlDown())
            {
                //any displayable key is released
                //not displayable: CHAR_UNDEFINED, ESCAPE, ENTER, BACK_SPACE, DELETE
                time = 0;
            }
            else if(keyPressed && entry.length() < fieldWidth && key.getKeyChar() != KeyEvent.CHAR_UNDEFINED && key.getKeyChar() != KeyEvent.VK_ESCAPE && key.getKeyChar() != KeyEvent.VK_ENTER && key.getKeyCode() != KeyEvent.VK_BACK_SPACE && key.getKeyCode() != KeyEvent.VK_DELETE && !key.isControlDown())
            {
                //any displayable key is pressed
                //not displayable: CHAR_UNDEFINED, ESCAPE, ENTER, BACK_SPACE, DELETE
                if((frame >= time + delay1 && (frame - remainder) % delay2 == 0) || key.getKeyChar() != prevKey.getKeyChar())
                {
                    if(time == 0)
                        time = frame;
                    entry = entry.substring(0, cursorPos) + key.getKeyChar() + entry.substring(Math.min(cursorPos, entry.length()));
                    cursorPos++;
                    prevKey = key;
                }
            }
            else if(keyReleased && key.getKeyCode() == KeyEvent.VK_LEFT)
            {
                //left arrow key released
                time = 0;
            }
            else if(keyPressed && frame >= time + delay1 && (frame - remainder) % delay2 == 0 && key.getKeyCode() == KeyEvent.VK_LEFT)
            {
                //left arrow key pressed
                if(time == 0)
                    time = frame;
                cursorPos--;
            }
            else if(keyReleased && key.getKeyCode() == KeyEvent.VK_RIGHT)
            {
                //right arrow key released
                time = 0;
            }
            else if(keyPressed && frame >= time + delay1 && (frame - remainder) % delay2 == 0 && key.getKeyCode() == KeyEvent.VK_RIGHT)
            {
                //right arrow key pressed
                if(time == 0)
                    time = frame;
                cursorPos++;
            }
        }

        //set font for text entered
        g.setFont(new Font("monospaced", Font.PLAIN, textSize));
        fm = g.getFontMetrics();

        //get cursor position
        int x1 = xPos - fm.stringWidth(fieldWidthString.toString())/2 - fm.stringWidth("0")/4;
        int x2 = xPos + fm.stringWidth(fieldWidthString.toString())/2 + fm.stringWidth("0")/2;
        int y1 = yPos - 9 * fm.getAscent()/16;
        int y2 = yPos + 9 * fm.getAscent()/16 - 1;
        if(mousePressed && mouseX >= x1 && mouseX <= x2 && mouseY >= y1 && mouseY <= y2)
        {
            cursorPos = (int) Math.round((mouseX - x1 + 1 - (double) fm.stringWidth("0") / 2) /fm.stringWidth("0"));
            boxSelected = true;
        }
        else if(mousePressed)
            boxSelected = false;
        cursorPos = Math.max(0, Math.min(cursorPos, entry.length()));

        //draw a yellow box around text box if enter is pressed with no text in box
        if(highlightBox)
        {
            g.setColor(new Color(255, 255, 0));
            g.fillRect(xPos - fm.stringWidth(fieldWidthString.toString())/2 - fm.stringWidth("0")/4 - 2, yPos - 9 * fm.getAscent()/16 - 2, fm.stringWidth(fieldWidthString.toString()) + 3 * fm.stringWidth("0")/4 + 4, 9 * fm.getAscent()/8 + 4);
        }

        //draw text box
        g.setColor(new Color(255, 255, 255));
        g.fillRect(xPos - fm.stringWidth(fieldWidthString.toString())/2 - fm.stringWidth("0")/4, yPos - 9 * fm.getAscent()/16, fm.stringWidth(fieldWidthString.toString()) + 3 * fm.stringWidth("0")/4, 9 * fm.getAscent()/8);

        //draw text
        g.setColor(new Color(0, 0, 0));
        g.drawString(entry, xPos - fm.stringWidth(fieldWidthString.toString())/2 + 1, yPos + fm.getAscent()/2 - 2);

        //draw cursor
        if(boxSelected && (frame / blink % 2 == 0 || keyPressed))
            g.drawLine(xPos - fm.stringWidth(fieldWidthString.toString())/2 + fm.stringWidth("0") * cursorPos + 1, yPos - textSize/2 + 1, xPos - fm.stringWidth(fieldWidthString.toString())/2 + fm.stringWidth("0") * cursorPos + 1, yPos + textSize/2 - 1);

        //ENTER key released
        if(boxSelected && keyReleased && key.getKeyCode() == KeyEvent.VK_ENTER)
        {
            if(!entry.isEmpty())
            {
                //reset values
                entry = "";
                remainder = -1;
                time = 0;
                cursorPos = 0;
                highlightBox = false;
                if(prompt.startsWith("Invalid Entry, "))
                    prompt = prompt.substring(15);
            }
            else
                highlightBox = true;
        }

        //reset key states
        if(keyReleased)
            keyReleased = false;
        if(keyTyped)
            keyTyped = false;
    }

    public void mouseClicked(MouseEvent me)
    {

    }

    public void mouseEntered(MouseEvent me)
    {

    }

    public void mouseExited(MouseEvent me)
    {

    }

    public void mousePressed(MouseEvent me)
    {
        mousePressed = true;
    }

    public void mouseReleased(MouseEvent me)
    {
        mousePressed = false;
    }

    public void mouseDragged(MouseEvent me)
    {
        mousePressed = true;
        mouseX = me.getX() - 1;
        mouseY = me.getY() - 31;
    }

    public void mouseMoved(MouseEvent me)
    {
        mousePressed = false;
        mouseX = me.getX() - 1;
        mouseY = me.getY() - 31;
    }

    public void keyPressed(KeyEvent key)
    {
        keyPressed = !keyTyped;
        this.key = key;
    }

    public void keyReleased(KeyEvent key)
    {
        keyPressed = false;
        keyReleased = true;
        this.key = key;
        prevKey = new KeyEvent(new JFrame(), 0, 0, 0, 0, KeyEvent.CHAR_UNDEFINED);
    }

    public void keyTyped(KeyEvent key)
    {
        keyTyped = true;
    }
}