package ProjectSCD;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.util.stream.Collectors;
import java.awt.Color;
import java.awt.Font;

public class scd {

    private JFrame frmProject;
    private JTextField amountField, sourceField;
    private DefaultListModel<Income> incomeListModel;
    private JList<Income> incomeList;
    private ArrayList<Income> incomeData = new ArrayList<>();

    private JTextField expenseAmountField, expenseDateField;
    private JComboBox<String> categoryBox;
    private DefaultListModel<Expense> expenseListModel;
    private JList<Expense> expenseList;
    private ArrayList<Expense> expenseData = new ArrayList<>();
    private HashMap<String, Double> categoryLimits = new HashMap<>();

    private double totalIncome = 0;
    private double totalExpense = 0;

    class Income {
        double amount;
        String source;

        Income(double amount, String source) {
            this.amount = amount;
            this.source = source;
        }

        public String toString() {
            return "Amount: $" + amount + ", Source: " + source;
        }
    }

    class Expense {
        double amount;
        String date, category;

        Expense(double amount, String date, String category) {
            this.amount = amount;
            this.date = date;
            this.category = category;
        }

        public String toString() {
            return "Amount: $" + amount + ", Date: " + date + ", Category: " + category;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new scd().frmProject.setVisible(true));
    }

    public scd() {
        initialize();
    }

    private void initialize() {
        frmProject = new JFrame("BudgetTrackingApp - All Features");
        frmProject.getContentPane().setBackground(new Color(245, 255, 250));
        frmProject.setBounds(100, 100, 900, 600);
        frmProject.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmProject.getContentPane().setLayout(null);

        JLabel lblIncome = new JLabel(" Income");
        lblIncome.setFont(new Font("Sitka Subheading", Font.BOLD, 28));
        lblIncome.setForeground(new Color(0, 206, 209));
        lblIncome.setBounds(20, 29, 169, 36);
        frmProject.getContentPane().add(lblIncome);

        amountField = new JTextField();
        amountField.setText("amount");
        amountField.setForeground(new Color(128, 0, 128));
        amountField.setBackground(new Color(255, 255, 255));
        amountField.setBounds(20, 241, 80, 25);
        frmProject.getContentPane().add(amountField);
        
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 10, 10);
        frmProject.getContentPane().add(panel);

        sourceField = new JTextField();
        sourceField.setForeground(new Color(128, 0, 128));
        sourceField.setBounds(120, 241, 80, 25);
        frmProject.getContentPane().add(sourceField);

        JButton btnAddIncome = new JButton("Add Income");
        btnAddIncome.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
        btnAddIncome.setBackground(new Color(176, 224, 230));
        btnAddIncome.setForeground(Color.BLUE);
        btnAddIncome.setBounds(220, 240, 120, 25);
        frmProject.getContentPane().add(btnAddIncome);

        incomeListModel = new DefaultListModel<>();
        incomeList = new JList<>(incomeListModel);
        incomeList.setForeground(new Color(128, 0, 128));
        incomeList.setBackground(new Color(240, 255, 240));
        JScrollPane incomeScroll = new JScrollPane(incomeList);
        incomeScroll.setBounds(20, 80, 320, 150);
        frmProject.getContentPane().add(incomeScroll);

        JLabel lblExpense = new JLabel("Expense");
        lblExpense.setForeground(new Color(0, 206, 209));
        lblExpense.setFont(new Font("Sitka Subheading", Font.BOLD, 28));
        lblExpense.setBounds(400, 24, 204, 36);
        frmProject.getContentPane().add(lblExpense);

        expenseAmountField = new JTextField();
        expenseAmountField.setForeground(new Color(128, 0, 128));
        expenseAmountField.setBounds(400, 241, 80, 25);
        frmProject.getContentPane().add(expenseAmountField);

        expenseDateField = new JTextField("YYYY-MM-DD");
        expenseDateField.setFont(new Font("Urdu Typesetting", Font.BOLD, 13));
        expenseDateField.setForeground(new Color(128, 0, 128));
        expenseDateField.setBounds(492, 241, 100, 25);
        frmProject.getContentPane().add(expenseDateField);

        String[] categories = {"Food", "Travel", "Utilities", "Health", "Entertainment", "Other"};
        categoryBox = new JComboBox<>(categories);
        categoryBox.setEditable(true);
        categoryBox.setModel(new DefaultComboBoxModel(new String[] {" Food", " Travel", " Utilities", " Health", " Entertainment", " Other"}));
        categoryBox.setForeground(new Color(0, 128, 128));
        categoryBox.setBackground(new Color(240, 255, 240));
        categoryBox.setBounds(602, 241, 100, 25);
        frmProject.getContentPane().add(categoryBox);

        JButton btnAddExpense = new JButton("Add Expense");
        btnAddExpense.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
        btnAddExpense.setBackground(new Color(176, 224, 230));
        btnAddExpense.setForeground(Color.BLUE);
        btnAddExpense.setBounds(720, 240, 110, 25);
        frmProject.getContentPane().add(btnAddExpense);

        expenseListModel = new DefaultListModel<>();
        expenseList = new JList<>(expenseListModel);
        expenseList.setLayoutOrientation(JList.VERTICAL_WRAP);
        expenseList.setForeground(new Color(128, 0, 128));
        expenseList.setBackground(new Color(240, 255, 240));
        expenseList.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        JScrollPane expenseScroll = new JScrollPane(expenseList);
        expenseScroll.setBounds(400, 80, 430, 150);
        frmProject.getContentPane().add(expenseScroll);

        JButton btnBalance = new JButton("View Balance");
        btnBalance.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
        btnBalance.setBackground(new Color(176, 224, 230));
        btnBalance.setForeground(Color.BLUE);
        btnBalance.setBounds(39, 331, 150, 30);
        frmProject.getContentPane().add(btnBalance);

        JButton btnReport = new JButton("Category Report");
        btnReport.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
        btnReport.setBackground(new Color(176, 224, 230));
        btnReport.setForeground(Color.BLUE);
        btnReport.setBounds(690, 473, 150, 30);
        frmProject.getContentPane().add(btnReport);

        JButton btnSetLimit = new JButton("Set Category Limit");
        btnSetLimit.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
        btnSetLimit.setBackground(new Color(176, 224, 230));
        btnSetLimit.setForeground(Color.BLUE);
        btnSetLimit.setBounds(251, 331, 164, 30);
        frmProject.getContentPane().add(btnSetLimit);

        JButton btnEditDelete = new JButton("Edit/Delete");
        btnEditDelete.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
        btnEditDelete.setBackground(new Color(176, 224, 230));
        btnEditDelete.setForeground(Color.BLUE);
        btnEditDelete.setBounds(475, 331, 140, 30);
        frmProject.getContentPane().add(btnEditDelete);

        JButton btnSortAmount = new JButton("Sort by Amount");
        btnSortAmount.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
        btnSortAmount.setBackground(new Color(176, 224, 230));
        btnSortAmount.setForeground(Color.BLUE);
        btnSortAmount.setBounds(690, 331, 150, 30);
        frmProject.getContentPane().add(btnSortAmount);

        JButton btnSortDate = new JButton("Sort by Date");
        btnSortDate.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
        btnSortDate.setBackground(new Color(176, 224, 230));
        btnSortDate.setForeground(Color.BLUE);
        btnSortDate.setBounds(475, 473, 150, 30);
        frmProject.getContentPane().add(btnSortDate);

        JButton btnReset = new JButton("Reset Data");
        btnReset.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
        btnReset.setBackground(new Color(176, 224, 230));
        btnReset.setForeground(Color.BLUE);
        btnReset.setBounds(39, 473, 150, 30);
        frmProject.getContentPane().add(btnReset);

        JButton btnSummary = new JButton("Session Summary");
        btnSummary.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
        btnSummary.setBackground(new Color(176, 224, 230));
        btnSummary.setForeground(Color.BLUE);
        btnSummary.setBounds(251, 473, 150, 30);
        frmProject.getContentPane().add(btnSummary);

        // Button Actions
        btnAddIncome.addActionListener(e -> {
            try {
                double amt = Double.parseDouble(amountField.getText());
                if (amt < 0) throw new NumberFormatException("Negative amount");
                String src = sourceField.getText();
                if (src.trim().isEmpty()) throw new IllegalArgumentException("Empty source");
                Income income = new Income(amt, src);
                incomeData.add(income);
                incomeListModel.addElement(income);
                totalIncome += amt;
                sourceField.setText("source");
                show("Income added successfully.");
            } catch (NumberFormatException ex) {
                show("Please enter a valid Amount");
            } catch (IllegalArgumentException ex) {
                show("Source cannot be empty.");
            } finally {
                replayOption();
            }
        });

        btnAddExpense.addActionListener(e -> {
            try {
                double amt = Double.parseDouble(expenseAmountField.getText());
                if (amt < 0) throw new NumberFormatException("Negative amount");
                String date = expenseDateField.getText();
                if (!isValidDate(date)) throw new IllegalArgumentException("Invalid date");
                String cat = (String) categoryBox.getSelectedItem();
                Expense exp = new Expense(amt, date, cat);
                expenseData.add(exp);
                expenseListModel.addElement(exp);
                totalExpense += amt;

                double totalCat = expenseData.stream()
                        .filter(ex -> ex.category.equals(cat))
                        .mapToDouble(ex -> ex.amount)
                        .sum();
                if (categoryLimits.containsKey(cat) && totalCat > categoryLimits.get(cat)) {
                    show("Warning: Expense in " + cat + " exceeds limit!");
                }

                expenseAmountField.setText("  amount");
                expenseDateField.setText("YYYY-MM-DD");
                
                JLayeredPane layeredPane = new JLayeredPane();
                layeredPane.setBounds(395, 360, 1, 1);
                frmProject.getContentPane().add(layeredPane);
                show("Expense added successfully.");
            } catch (NumberFormatException ex) {
                show("Blank space not allowed. Please enter a valid Amount.");
            } catch (IllegalArgumentException ex) {
                show("Invalid date format. Use YYYY-MM-DD.");
            } finally {
                replayOption();
            }
        });

        btnBalance.addActionListener(e -> {
            show("Balance: $" + (totalIncome - totalExpense));
            replayOption();
        });

        btnReport.addActionListener(e -> {
            Map<String, Double> catMap = new HashMap<>();
            for (Expense exp : expenseData) {
                catMap.put(exp.category, catMap.getOrDefault(exp.category, 0.0) + exp.amount);
            }
            StringBuilder sb = new StringBuilder("Category-wise Expense Report:\n");
            catMap.forEach((k, v) -> sb.append(k).append(": $").append(String.format("%.2f", v)).append("\n"));
            show(sb.toString());
            replayOption();
        });

        btnSetLimit.addActionListener(e -> {
            String cat = (String) JOptionPane.showInputDialog(frmProject, "Choose category", "Set Limit",
                    JOptionPane.PLAIN_MESSAGE, null, categories, categories[0]);
            if (cat != null) {
                String val = JOptionPane.showInputDialog(frmProject, "Set monthly limit for " + cat + ":");
                if (val != null) {
                    try {
                        double limit = Double.parseDouble(val);
                        if (limit < 0) throw new NumberFormatException("Negative limit");
                        categoryLimits.put(cat, limit);
                        show("Limit set for " + cat + ": $" + String.format("%.2f", limit));
                    } catch (NumberFormatException ex) {
                        show("Invalid limit. Please enter a valid positive number.");
                    } finally {
                        replayOption();
                    }
                } else {
                    replayOption();
                }
            } else {
                replayOption();
            }
        });

        btnEditDelete.addActionListener(e -> {
            Object[] options = {"Edit Income", "Delete Income", "Edit Expense", "Delete Expense"};
            int opt = JOptionPane.showOptionDialog(frmProject, "Choose Action", "Edit/Delete",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            if (opt == 0) { // Edit Income
                if (incomeList.isSelectionEmpty()) {
                    show("Please select an income entry to edit.");
                    replayOption();
                    return;
                }
                Income selected = incomeList.getSelectedValue();
                String amtStr = JOptionPane.showInputDialog(frmProject, "New Amount:", selected.amount);
                if (amtStr == null) {
                    replayOption();
                    return;
                }
                String srcStr = JOptionPane.showInputDialog(frmProject, "New Source:", selected.source);
                if (srcStr == null) {
                    replayOption();
                    return;
                }
                try {
                    double newAmount = Double.parseDouble(amtStr);
                    if (newAmount < 0) throw new NumberFormatException("Negative amount");
                    if (srcStr.trim().isEmpty()) throw new IllegalArgumentException("Empty source");
                    totalIncome -= selected.amount;
                    selected.amount = newAmount;
                    selected.source = srcStr;
                    totalIncome += newAmount;
                    int index = incomeList.getSelectedIndex();
                    incomeListModel.set(index, selected);
                    show("Income updated successfully.");
                } catch (NumberFormatException ex) {
                    show("Invalid amount. Please enter a valid positive number.");
                } catch (IllegalArgumentException ex) {
                    show("Source cannot be empty.");
                } finally {
                    replayOption();
                }
            } else if (opt == 1) { // Delete Income
                if (incomeList.isSelectionEmpty()) {
                    show("Please select an income entry to delete.");
                    replayOption();
                    return;
                }
                Income selected = incomeList.getSelectedValue();
                if (JOptionPane.showConfirmDialog(frmProject, "Delete this income entry?") == JOptionPane.YES_OPTION) {
                    incomeData.remove(selected);
                    incomeListModel.removeElement(selected);
                    totalIncome -= selected.amount;
                    show("Income deleted successfully.");
                }
                replayOption();
            } else if (opt == 2) { // Edit Expense
                if (expenseList.isSelectionEmpty()) {
                    show("Please select an expense entry to edit.");
                    replayOption();
                    return;
                }
                Expense selected = expenseList.getSelectedValue();
                String amtStr = JOptionPane.showInputDialog(frmProject, "New Amount:", selected.amount);
                if (amtStr == null) {
                    replayOption();
                    return;
                }
                String dateStr = JOptionPane.showInputDialog(frmProject, "New Date (YYYY-MM-DD):", selected.date);
                if (dateStr == null) {
                    replayOption();
                    return;
                }
                String catStr = (String) JOptionPane.showInputDialog(frmProject, "New Category:", "Select Category",
                        JOptionPane.PLAIN_MESSAGE, null, categories, selected.category);
                if (catStr == null) {
                    replayOption();
                    return;
                }
                try {
                    double newAmount = Double.parseDouble(amtStr);
                    if (newAmount < 0) throw new NumberFormatException("Negative amount");
                    if (!isValidDate(dateStr)) throw new IllegalArgumentException("Invalid date");
                    totalExpense -= selected.amount;
                    selected.amount = newAmount;
                    selected.date = dateStr;
                    selected.category = catStr;
                    totalExpense += newAmount;
                    int index = expenseList.getSelectedIndex();
                    expenseListModel.set(index, selected);
                    double totalCat = expenseData.stream()
                            .filter(ex -> ex.category.equals(catStr))
                            .mapToDouble(ex -> ex.amount)
                            .sum();
                    if (categoryLimits.containsKey(catStr) && totalCat > categoryLimits.get(catStr)) {
                        show("Warning: Expense in " + catStr + " exceeds limit!");
                    }
                    show("Expense updated successfully.");
                } catch (NumberFormatException ex) {
                    show("Invalid amount. Please enter a valid positive number.");
                } catch (IllegalArgumentException ex) {
                    show("Invalid date format. Use YYYY-MM-DD.");
                } finally {
                    replayOption();
                }
            } else if (opt == 3) { // Delete Expense
                if (expenseList.isSelectionEmpty()) {
                    show("Please select an expense to delete.");
                    replayOption();
                    return;
                }
                Expense selected = expenseList.getSelectedValue();
                if (JOptionPane.showConfirmDialog(frmProject, "Delete this expense entry?") == JOptionPane.YES_OPTION) {
                    expenseData.remove(selected);
                    expenseListModel.removeElement(selected);
                    totalExpense -= selected.amount;
                    show("Expense deleted successfully.");
                }
                replayOption();
            }
        });

        btnSortAmount.addActionListener(e -> {
            expenseData.sort(Comparator.comparingDouble(e1 -> e1.amount));
            expenseListModel.clear();
            for (Expense exp : expenseData)
                expenseListModel.addElement(exp);
            show("Expenses sorted by amount.");
            replayOption();
        });

        btnSortDate.addActionListener(e -> {
            expenseData.sort(Comparator.comparing(e1 -> e1.date));
            expenseListModel.clear();
            for (Expense expense : expenseData) {
                expenseListModel.addElement(expense);
            }
            show("Expenses sorted by date.");
            replayOption();
        });

        btnReset.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(frmProject, "Clear all data?") == JOptionPane.YES_OPTION) {
                incomeData.clear();
                expenseData.clear();
                incomeListModel.clear();
                expenseListModel.clear();
                totalIncome = 0;
                totalExpense = 0;
                categoryLimits.clear();
                show("All data reset.");
            }
            replayOption();
        });

        btnSummary.addActionListener(e -> {
            Map<String, Double> catMap = new TreeMap<>();
            for (Expense exp : expenseData)
                catMap.put(exp.category, catMap.getOrDefault(exp.category, 0.0) + exp.amount);

            String topCategory = catMap.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse("N/A");

            StringBuilder summary = new StringBuilder("=== Session Summary ===\n");
            summary.append("Total Income: $").append(String.format("%.2f", totalIncome)).append("\n");
            summary.append("Total Expense: $").append(String.format("%.2f", totalExpense)).append("\n");
            summary.append("Balance: $").append(String.format("%.2f", totalIncome - totalExpense)).append("\n");
            summary.append("Highest Expense Category: ").append(topCategory).append("\n");
            show(summary.toString());
            replayOption();
        });
    }

    private boolean isValidDate(String date) {
        return date.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    private void show(String msg) {
        JOptionPane.showMessageDialog(frmProject, msg, "Budget Tracker", JOptionPane.INFORMATION_MESSAGE);
    }

    private void replayOption() {
        Object[] options = {"Return to Main Menu", "Exit"};
        int choice = JOptionPane.showOptionDialog(frmProject, "What would you like to do?", "Next Action",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (choice == 1) {
            frmProject.dispose();
            System.exit(0);
        }
    }
}