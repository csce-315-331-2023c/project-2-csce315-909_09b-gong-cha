import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PosSystem {
    private JPanel itemListPanel; // Panel to hold item labels
    private double subtotal;
    private double tip;
    private double total;
    private JLabel subtotalLabel;
    private JLabel tipLabel;
    private JLabel totalLabel;

    public PosSystem() {
        // Initialize the receipt panel and other components
        initializeUI();

        // Initialize subtotal, tip, and total
        subtotal = 0;
        tip = 0;
        total = 0;
    }

    private void initializeUI() {
        JFrame frame = new JFrame("POS System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Create the item list panel and make it scrollable
        itemListPanel = new JPanel();
        itemListPanel.setLayout(new BoxLayout(itemListPanel, BoxLayout.Y_AXIS));
        JScrollPane itemScrollPane = new JScrollPane(itemListPanel);

        // Add the item list scroll pane to the frame
        frame.add(itemScrollPane, BorderLayout.CENTER);

        // Create the bottom panel for Subtotal, Tip, Total, and Checkout button
        JPanel receiptPanel2_bottom = new JPanel();
        receiptPanel2_bottom.setLayout(new BoxLayout(receiptPanel2_bottom, BoxLayout.Y_AXIS));

        // Create and add labels for Subtotal, Tip, Total
        subtotalLabel = new JLabel("Subtotal: $" + subtotal);
        tipLabel = new JLabel("Tip: $" + tip);
        totalLabel = new JLabel("Total: $" + total);
        receiptPanel2_bottom.add(subtotalLabel);
        receiptPanel2_bottom.add(tipLabel);
        receiptPanel2_bottom.add(totalLabel);

        // Create the Checkout button
        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //ask for tip
                String tipString = JOptionPane.showInputDialog("Enter tip amount: ");
                tip = Double.parseDouble(tipString);
                //update tip and total
                total = subtotal + tip;
                //update tip and total labels
                tipLabel.setText("Tip: $" + tip);
                totalLabel.setText("Total: $" + total);
                //display total in jdialog box and clear if yes is clicked
                int result = JOptionPane.showConfirmDialog(null, "Total: $" + total + "\n" + "Clear receipt?", "Checkout", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) { 
                    //clear receipt
                    itemListPanel.removeAll();
                    //reset subtotal, tip, and total
                    subtotal = 0;
                    tip = 0;
                    total = 0;
                    //update subtotal, tip, and total labels
                    subtotalLabel.setText("Subtotal: $" + subtotal);
                    tipLabel.setText("Tip: $" + tip);
                    totalLabel.setText("Total: $" + total);
                    
                    //repaint receipt panel
                    itemListPanel.revalidate();
                    itemListPanel.repaint();
                }
            }
        });
        receiptPanel2_bottom.add(checkoutButton);

        // Add the bottom panel to the frame
        frame.add(receiptPanel2_bottom, BorderLayout.SOUTH);

        // Add name of server and an exit button that clears the receipt panel
        JPanel serverPanel = new JPanel();
        serverPanel.add(new JLabel("Server:" + " " + "John Doe"));
        serverPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        frame.add(serverPanel, BorderLayout.NORTH);

        frame.pack();
        frame.setVisible(true);

        // Create buttons with associated prices and names
        ItemButton addItemButton1 = new ItemButton("Item 1", 10.99);
        ItemButton addItemButton2 = new ItemButton("Item 2", 7.49);

        // Add action listeners to the buttons
        addItemButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call the function to update the receipt panel with item details
                addItemToReceipt(addItemButton1);
            }
        });

        addItemButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call the function to update the receipt panel with item details
                addItemToReceipt(addItemButton2);
            }
        });

        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1)); // Adjust the layout as needed

        // Add the buttons to the button panel
        buttonPanel.add(addItemButton1);
        buttonPanel.add(addItemButton2);

        // Add the button panel to the frame (you can choose the appropriate location)
        frame.add(buttonPanel, BorderLayout.WEST);
    }

    private void addItemToReceipt(ItemButton itemButton) {
        // Get item details from the button
        String itemName = itemButton.getItemName();
        double itemPrice = itemButton.getItemPrice();

        // Create components for the new item
        JLabel itemLabel = new JLabel(itemName);
        JLabel priceLabel = new JLabel("$" + itemPrice);

        // Add the item to the item list panel
        itemListPanel.add(itemLabel);
        itemListPanel.add(priceLabel);

        // Update the running total
        subtotal += itemPrice;
        total = subtotal + tip;

        // Update the labels in receiptPanel2
        subtotalLabel.setText("Subtotal: $" + subtotal);
        tipLabel.setText("Tip: $" + tip);
        totalLabel.setText("Total: $" + total);

        // Repaint the item list panel
        itemListPanel.revalidate();
        itemListPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PosSystem();
            }
        });
    }
}

class ItemButton extends JButton {
    private String itemName;
    private double itemPrice;

    public ItemButton(String itemName, double itemPrice) {
        super(itemName);
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }
}
