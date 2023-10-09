import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyTableApp extends JFrame {
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField recipeNameField;
    private JCheckBox isSlushCheckBox;
    private JTextField medPriceField;
    private JTextField largePriceField;
    private JTextField recipePriceField;
    private JTextField toppingsField;
    private JTextField ingredientsField;

    public MyTableApp() {
        setTitle("Editable Table Example");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the table model
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Recipe_ID");
        tableModel.addColumn("Recipe_Name");
        tableModel.addColumn("Is_Slush");
        tableModel.addColumn("Med_Price");
        tableModel.addColumn("Large_Price");
        tableModel.addColumn("Recipe_Price");

        // Create the JTable
        table = new JTable(tableModel);

        // Add the JTable to a JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Create panel for input fields
        JPanel inputPanel = new JPanel(new GridLayout(7, 2));

        // Add input fields and labels
        inputPanel.add(new JLabel("Recipe Name:"));
        recipeNameField = new JTextField();
        inputPanel.add(recipeNameField);

        inputPanel.add(new JLabel("Is Slush:"));
        isSlushCheckBox = new JCheckBox();
        inputPanel.add(isSlushCheckBox);

        inputPanel.add(new JLabel("Medium Price:"));
        medPriceField = new JTextField();
        inputPanel.add(medPriceField);

        inputPanel.add(new JLabel("Large Price:"));
        largePriceField = new JTextField();
        inputPanel.add(largePriceField);

        inputPanel.add(new JLabel("Recipe Price:"));
        recipePriceField = new JTextField();
        inputPanel.add(recipePriceField);

        inputPanel.add(new JLabel("Toppings (comma-separated):"));
        toppingsField = new JTextField();
        inputPanel.add(toppingsField);

        inputPanel.add(new JLabel("Ingredients (comma-separated):"));
        ingredientsField = new JTextField();
        inputPanel.add(ingredientsField);

        // Add input panel to the frame
        getContentPane().add(inputPanel, BorderLayout.NORTH);

        // Create buttons for adding, updating, and deleting rows
        JButton addButton = new JButton("Add Row");
        // Create the "Update Row" button
        JButton updateButton = new JButton("Update Row");

        // Add action listener to the "Update Row" button
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    // Read input values
                    String recipeName = recipeNameField.getText();
                    boolean isSlush = isSlushCheckBox.isSelected();
                    double medPrice = Double.parseDouble(medPriceField.getText());
                    double largePrice = Double.parseDouble(largePriceField.getText());
                    double recipePrice = Double.parseDouble(recipePriceField.getText());
                    String toppings = toppingsField.getText();
                    String ingredients = ingredientsField.getText();

                    // Update the selected row with the input values
                    //only update the fields that are not empty
                    if (!recipeName.isEmpty()) {
                        tableModel.setValueAt(recipeName, selectedRow, 1);
                    }
                    if (!toppings.isEmpty()) { //toppings is a comma-separated list
                        //TODO: backend will have to delete all toppings associated with this recipe and then re-add them
                    }
                    if (!ingredients.isEmpty()) {
                        //TODO: backend will have to delete all ingredients associated with this recipe and then re-add them
                    }
                    if (!medPriceField.getText().isEmpty()) {
                        tableModel.setValueAt(medPrice, selectedRow, 3);
                    }
                    if (!largePriceField.getText().isEmpty()) {
                        tableModel.setValueAt(largePrice, selectedRow, 4);
                    }
                    if (!recipePriceField.getText().isEmpty()) {
                        tableModel.setValueAt(recipePrice, selectedRow, 5);
                    }
                    //if slush value is different than the current value, update it
                    if (isSlush != (boolean) tableModel.getValueAt(selectedRow, 2)){
                        tableModel.setValueAt(isSlush, selectedRow, 2);
                    }
                }
            }
        });

        JButton deleteButton = new JButton("Delete Row");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected row and delete it
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    tableModel.removeRow(selectedRow);
                }
            }
        });
        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton); //requires a row to be selected and uses input fields
        buttonPanel.add(deleteButton); //requires a row to be selected

        // Add the button panel to the frame
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Add action listener to the "Add Row" button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Read input values
                //TODO: recipeID is generated by the backend, here is a call to backend to generate a new recipeID
                //TODO: backend should have a function called generateRecipeID() that returns a unique recipeID
                String recipeName = recipeNameField.getText();
                boolean isSlush = isSlushCheckBox.isSelected();
                double medPrice = Double.parseDouble(medPriceField.getText());
                double largePrice = Double.parseDouble(largePriceField.getText());
                double recipePrice = Double.parseDouble(recipePriceField.getText());
                String toppings = toppingsField.getText();
                String ingredients = ingredientsField.getText();
                //toppings and ingredients are comma-separated lists that will be handled by backend

                // Add a new row to the table
                tableModel.addRow(new Object[]{generateRecipeID(), recipeName, isSlush, medPrice, largePrice, recipePrice});
            }
        });
    }

    // Generate a unique Recipe_ID (you can implement this logic as needed)
    private int generateRecipeID() {
        // Implement your logic to generate a unique Recipe_ID
        // For simplicity, we're just using a random number here
        return (int) (Math.random() * 1000);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MyTableApp app = new MyTableApp();
                app.setVisible(true);
            }
        });
    }
}
