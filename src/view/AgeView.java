package view;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class AgeView extends JFrame {

    private final JButton btnEnter = new JButton("Enter data");
    private final JTextArea resultArea = new JTextArea(12, 40);

    private final JDialog inputDialog = new JDialog(this, "Enter birth date", true);
    private final JTextField tfDay = new JTextField(4);
    private final JTextField tfMonth = new JTextField(4);
    private final JTextField tfYear = new JTextField(6);
    private final JButton btnOk = new JButton("OK");
    private final JButton btnCancel = new JButton("Cancel");

    public AgeView() {
        super("Age in unexpected values");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initMainView();
        initInputDialog();
        pack();
        setLocationRelativeTo(null);
    }

    private void initMainView() {
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        resultArea.setText("Data not enter yet.");

        JPanel center = new JPanel(new BorderLayout());
        center.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        center.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.add(btnEnter);

        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }

    private void initInputDialog() {
        inputDialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0;
        inputDialog.add(new JLabel("Day:"), gbc);
        gbc.gridx = 1;
        inputDialog.add(tfDay, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        inputDialog.add(new JLabel("Month:"), gbc);
        gbc.gridx = 1;
        inputDialog.add(tfMonth, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        inputDialog.add(new JLabel("Year:"), gbc);
        gbc.gridx = 1;
        inputDialog.add(tfYear, gbc);

        JPanel buttons = new JPanel();
        buttons.add(btnOk);
        buttons.add(btnCancel);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        inputDialog.add(buttons, gbc);

        inputDialog.pack();
        inputDialog.setLocationRelativeTo(this);
    }

    public JButton getBtnEnter() { return btnEnter; }
    public JButton getBtnOk() { return btnOk; }
    public JButton getBtnCancel() { return btnCancel; }
    public JDialog getInputDialog() { return inputDialog; }
    public JTextField getTfDay() { return tfDay; }
    public JTextField getTfMonth() { return tfMonth; }
    public JTextField getTfYear() { return tfYear; }

    public void showResult(String text) {
        resultArea.setText(text);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Enter error",
                JOptionPane.ERROR_MESSAGE);
    }

    public void fillDialog(String day, String month, String year) {
        tfDay.setText(day);
        tfMonth.setText(month);
        tfYear.setText(year);
    }

    public void clearDialog() {
        tfDay.setText("");
        tfMonth.setText("");
        tfYear.setText("");
    }
}