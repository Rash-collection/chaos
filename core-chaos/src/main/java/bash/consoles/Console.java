/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package bash.consoles;

import bash.comds.Commanding;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Simple interactive Swing console.
 *
 * <p>The console supports text output, command input, command history,
 * clearing the output, and moving the console content into another panel.</p>
 *
 * @author rash4
 */
public class Console {
    protected static final String DEFAULT_WELCOME = """
            ×××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××
            ×//===========================================================\\\\×
            ×|  -------------------  Hello %9s  -------------------  |×
            ×\\\\===========================================================//×
            ×××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××
            """;
//    private boolean successful;
    private boolean alive;
    private boolean moved;
    private Commanding commando;
    private int historyIndex = -1;
    private final ArrayList<String> commandHistory = new ArrayList<>();
    private JFrame console;
    private JPanel contentPanel;
    private JTextArea strings;
    private JScrollPane scroll;
    private JTextField command;
    private JButton clearButton;
    private String inputs = "";
    /**
     * Initializes the console if it has not already been initialized.
     */
    public void initConsole() {
        if (contentPanel != null)return;
        createComponents();
        if (!moved)openWindow();
        println(DEFAULT_WELCOME.formatted("World"));
        command.requestFocusInWindow();
    }
    private void createComponents() {
        strings = new JTextArea();
        strings.setEditable(false);
        strings.setLineWrap(true);
        strings.setWrapStyleWord(true);
        strings.setBackground(Color.BLACK);
        strings.setForeground(Color.GREEN);
        strings.setFont(new Font("Monospaced", Font.PLAIN, 14));
        strings.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scroll = new JScrollPane(strings);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        command = new JTextField();
        command.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        command.setFont(new Font("Monospaced", Font.PLAIN, 14));
        command.setAlignmentX(Component.LEFT_ALIGNMENT);
        command.addActionListener(e -> submitCommand());
        command.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ESCAPE -> System.exit(0);
                    case KeyEvent.VK_UP -> previousCommand();
                    case KeyEvent.VK_DOWN -> nextCommand();
                }
            }
        });
        clearButton = new JButton("Clear Console");
        clearButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        clearButton.setFocusable(false);
        clearButton.addActionListener(e -> clearConsole());
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );
        contentPanel.setBackground(new Color(0xFDF6E3));
        contentPanel.add(scroll);
        contentPanel.add(Box.createVerticalStrut(12));
        contentPanel.add(command);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(clearButton);
    }
    public void setCommando(Commanding commando){
        if(commando == null)return;
        this.commando = commando;
    }
    private void submitCommand() {
        String input = command.getText().trim();
        if (input.isEmpty())return;
        inputs = input;
        println("> " + input);
        saveCommand(input);
        command.setText("");
    }
    private void previousCommand() {
        if (historyIndex > 0) {
            historyIndex--;
            command.setText(commandHistory.get(historyIndex));
        }
    }
    private void nextCommand() {
        if (historyIndex < commandHistory.size() - 1) {
            historyIndex++;
            command.setText(commandHistory.get(historyIndex));
        } else {
            command.setText("");
            historyIndex = commandHistory.size();
        }
    }
    private void saveCommand(String input) {
        if (!this.commando.execute(input))return;
        if(commandHistory.contains(input))return;
        commandHistory.add(input);
        historyIndex = commandHistory.size();
        println(">> Command (" + historyIndex + ") is saved.");
    }
    /**
     * Marks the last submitted command as successful.
     *
     * <p>The command is added to history when it has been marked successful.</p>
     */
//    public void success() {
//        successful = true;
//    }
    /**
     * Prints a message followed by a newline.
     *
     * @param text message to print
     */
    public void println(String text) {
        ensureInitialized();
        strings.append(text + "\n");
        strings.setCaretPosition(strings.getDocument().getLength());
    }
    /**
     * Prints a message without adding a newline.
     *
     * @param text message to print
     */
    public void print(String text) {
        ensureInitialized();
        strings.append(text);
        strings.setCaretPosition(strings.getDocument().getLength());
    }
    private void ensureInitialized() {
        if (contentPanel == null)initConsole();
    }
    /**
     * Clears all console output.
     */
    public void clearConsole() {
        ensureInitialized();
        strings.setText("");
    }
    /**
     * Displays a formatted welcome message.
     *
     * @param name name to display
     */
    public void welcoming(String name) {
        if (name == null || name.isBlank())name = "World";
        if (name.length() <= 13) {
            String message = """
                    ×××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××
                    ×//===========================================================\\\\×
                    ×|  ------------------- Hello %11s -------------------  |×
                    ×\\\\===========================================================//×
                    ×××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××
                    """.formatted(name);
            println(message);
            return;
        }
        final int maxNameLength = 49;
        String trimmed = name.substring(0, Math.min(name.length(), maxNameLength));
        int remaining = maxNameLength - trimmed.length();
        int leftDashes = remaining / 2;
        int rightDashes = remaining - leftDashes;
        String line = "×|  "
                + "-".repeat(leftDashes)
                + " Hello "
                + trimmed
                + " "
                + "-".repeat(rightDashes)
                + "  |×";
        String message = """
                ×××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××
                ×//===========================================================\\\\×
                %s
                ×\\\\===========================================================//×
                ×××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××
                """.formatted(line);
        println(message);
    }
    /**
     * Returns the currently entered command.
     *
     * @return current command text
     */
    public String getCommand() {
        return command == null ? "" : command.getText();
    }
    /**
     * Returns the last submitted input.
     *
     * @return submitted input
     */
    public String getInputs() {
        return inputs;
    }
    /**
     * Sets the last submitted input.
     *
     * @param input input value
     */
    public void setInputs(String input) {
        inputs = input;
    }
    /**
     * Clears the last submitted input.
     */
    public void clearInput() {
        inputs = "";
    }
    /**
     * Returns whether the console window/content is currently alive.
     *
     * @return {@code true} if active
     */
    public boolean isAlive() {
        return alive;
    }
    /**
     * Returns whether the Swing window does not currently exist.
     *
     * @return {@code true} if no window exists
     */
    public boolean isNull() {
        return console == null;
    }
    /**
     * Returns whether the console has been moved into another panel.
     *
     * @return {@code true} if moved
     */
    public boolean isMoved() {
        return moved;
    }
    /**
     * Moves the console content into another panel.
     *
     * @param otherPanel destination panel
     * @param bounds bounds for the console content
     */
    public void moveTo(JPanel otherPanel, Rectangle bounds) {
        closeWindow();
        moved = true;
        ensureInitialized();
        contentPanel.setBounds(bounds);
        otherPanel.add(contentPanel);
        otherPanel.revalidate();
        otherPanel.repaint();
        alive = true;
    }
    /**
     * Toggles between embedded and window mode.
     */
    public void toggleMovedAlive() {
        moved = !moved;
        alive = !alive;
        if (alive && !moved) {
            openWindow();
        } else if (!alive && console != null) {
            closeWindow();
        }
    }
    /**
     * Requests focus for the command field.
     */
    public void requestCommand() {
        ensureInitialized();
        if (console != null)console.toFront();
        contentPanel.revalidate();
        contentPanel.repaint();
        command.requestFocusInWindow();
    }

    /**
     * Displays a non-exception error message.
     *
     * @param message error message
     */
    public void errorSkip(String message) {
        println("X >> Error:\n\t>> " + message);
    }
    /**
     * Prints an exception message.
     *
     * @param e exception
     */
    public void error(Exception e) {
        Toolkit.getDefaultToolkit().beep();
        println(
                "Exception X -> " + e.getClass().getSimpleName()
                + " : " + e.getMessage()
        );
    }
    /**
     * Prints an exception and its stack trace.
     *
     * @param e exception
     */
    public void printStack(Exception e) {
        Toolkit.getDefaultToolkit().beep();
        println(
                "Exception: " + e.getClass().getSimpleName()
                + " - " + e.getMessage()
        );
        for (StackTraceElement element : e.getStackTrace()) {
            println("    at " + element);
        }
    }
    /**
     * Throws an {@link UnsupportedOperationException}.
     *
     * @param message exception message
     */
    public void throwException(String message) {
        if (message == null || message.isBlank()) {
            throwException();
            return;
        }
        println("X >> " + message);
        throw new UnsupportedOperationException("X >> " + message);
    }
    /**
     * Throws an {@link UnsupportedOperationException}.
     */
    public void throwException() {
        println("X >> Unknown Exception has been thrown.");
        throw new UnsupportedOperationException();
    }
    /**
     * Throws an {@link UnsupportedOperationException}.
     *
     * @param message exception message
     */
    public void unsupportedOperation(String message) {
        if (message == null || message.isBlank()) {
            println("X >> Unsupported Operation Exception -> has been thrown.");
            throw new UnsupportedOperationException();
        }
        println("X >> " + message);
        throw new UnsupportedOperationException("X >> " + message);
    }
    /**
     * Throws an {@link IllegalArgumentException}.
     *
     * @param message exception message
     */
    public void illegalArgument(String message) {
        if (message == null || message.isBlank()) {
            println("X >> Illegal Argument Exception -> has been thrown.");
            throw new IllegalArgumentException();
        }
        println("X >> " + message);
        throw new IllegalArgumentException("X >> " + message);
    }
    /**
     * Returns the Swing console window.
     *
     * @return JFrame, or {@code null} when embedded
     */
    public JFrame getFrame() {
        return console;
    }
    /**
     * Returns the console content panel.
     *
     * @return content panel
     */
    public JPanel getContentPanel() {
        ensureInitialized();
        return contentPanel;
    }
    /**
     * Opens the console in its own window.
     */
    private void openWindow() {
        if (console != null || contentPanel == null)return;
        console = new JFrame("Console");
        console.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        console.setSize(640, 480);
        console.setLocationRelativeTo(null);
        console.setResizable(true);
        console.setContentPane(contentPanel);
        console.setVisible(true);
        alive = true;
    }
    /**
     * Closes and disposes the Swing window.
     *
     * <p>The console content itself is kept so it can be embedded again.</p>
     */
    private void closeWindow() {
        if (console == null)return;
        console.dispose();
        console = null;
        alive = false;
    }
    /**
     * Completely disposes the console and its components.
     */
    public void dissConsole() {
        closeWindow();
        if (contentPanel != null)contentPanel.removeAll();
        contentPanel = null;
        strings = null;
        scroll = null;
        command = null;
        clearButton = null;
        commandHistory.clear();
        historyIndex = -1;
//        successful = false;
        inputs = "";
        alive = false;
        moved = false;
    }
}
