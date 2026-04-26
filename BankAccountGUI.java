import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BankAccountGUI extends JFrame implements ActionListener {

    private double balance;
    private JLabel balanceLabel = new JLabel("Balance: $0.00");
    private JTextField amountField = new JTextField(10);
    private JButton depositBtn  = new JButton("Deposit");
    private JButton withdrawBtn = new JButton("Withdraw");

    public BankAccountGUI(double initialBalance) {
        this.balance = initialBalance;
        updateLabel();

        
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(balanceLabel);
        panel.add(new JLabel("Amount:"));
        panel.add(amountField);

        
        depositBtn.addActionListener(this);
        withdrawBtn.addActionListener(this);
        panel.add(depositBtn);
        panel.add(withdrawBtn);

        add(panel);                                  // add JPanel to JFrame
        setTitle("Bank Account");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == depositBtn)  handleTransaction(true);
        if (e.getSource() == withdrawBtn) handleTransaction(false);
    }

    private void handleTransaction(boolean isDeposit) {
        try {
            double amt = Double.parseDouble(amountField.getText());
            if (isDeposit) {
                balance += amt;
            } else if (amt <= balance) {
                balance -= amt;
            } else {
                JOptionPane.showMessageDialog(this, "Insufficient funds");
                return;
            }
            updateLabel();
            amountField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter a valid number");
        }
    }

    private void updateLabel() {
        balanceLabel.setText(String.format("Balance: $%.2f", balance));
    }

    public static void main(String[] args) {
        // Display remaining balance on exit
        BankAccountGUI app = new BankAccountGUI(1000.00);
        app.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JOptionPane.showMessageDialog(app,
                    String.format("Final Balance: $%.2f%nThank you for banking with us!",
                        app.balance));
            }
        });
    }
}
