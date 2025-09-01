/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package johanivansalazarsantana.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import johanivansalazarsantana.dao.OrderDAO;
import johanivansalazarsantana.dao.ProductDAO;
import johanivansalazarsantana.dao.SaleDAO;
import johanivansalazarsantana.dao.StatsDAO;
import johanivansalazarsantana.model.Product;
import johanivansalazarsantana.model.Sale;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author JohanIvánSalazarSant
 */
public class MainFrame extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainFrame.class.getName());

    private JTable table;
    private JLabel lblIngresos, lblMas, lblMenos, lblPromedio;
    private final ProductDAO productDAO = new ProductDAO();
    private final SaleDAO saleDAO = new SaleDAO();
    private final StatsDAO statsDAO = new StatsDAO();
    private final OrderDAO orderDAO = new OrderDAO();
    private final NumberFormat currency = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        //initComponents();
        super("Tienda");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(780, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        add(buildProductsPanel(), BorderLayout.CENTER);
        add(buildStatsPanel(), BorderLayout.SOUTH);
        refreshTable();
        refreshStats();

    }

    private JPanel buildProductsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Productos"));
        table = new JTable();
        JScrollPane scroll = new JScrollPane(table);
        panel.add(scroll, BorderLayout.CENTER);
        JPanel ops = new JPanel();
        ops.setBorder(BorderFactory.createTitledBorder("Operaciones"));
        JButton vender = new JButton("Vender Producto");
        vender.addActionListener(e -> onVender());
        JButton pedir = new JButton("Pedir Producto");
        pedir.addActionListener(e -> onPedir());
        ops.add(vender);
        ops.add(pedir);
        panel.add(ops, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel buildStatsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 8, 4)); // Aumentamos a 5 filas
        panel.setBorder(BorderFactory.createTitledBorder("Cálculos"));

        lblIngresos = new JLabel("$ 0");
        lblMas = new JLabel("");
        lblMenos = new JLabel("");
        lblPromedio = new JLabel("$ 0");

        panel.add(new JLabel("Ingresos"));
        panel.add(lblIngresos);

        panel.add(new JLabel("Producto más vendido"));
        panel.add(lblMas);

        panel.add(new JLabel("Producto menos vendido"));
        panel.add(lblMenos);

        panel.add(new JLabel("Promedio"));
        panel.add(lblPromedio);
        
        panel.add(new JLabel("Gráficas"));
       

        // 🔹 Nueva fila de botones para gráficas
        JButton btnGraficoBarras = new JButton("Gráfico de Ingresos");
        btnGraficoBarras.addActionListener(e -> mostrarGraficoBarras());

        JButton btnGraficoPastel = new JButton("Gráfico de Ventas");
        btnGraficoPastel.addActionListener(e -> mostrarGraficoPastel());

        JButton btnMasVendido = new JButton("Producto Más Vendido");
        btnMasVendido.addActionListener(e -> mostrarProductoMasVendido());

        JButton btnMenosVendido = new JButton("Producto Menos Vendido");
        btnMenosVendido.addActionListener(e -> mostrarProductoMenosVendido());

        JButton btnTotalDinero = new JButton("Total Dinero Obtenido");
        btnTotalDinero.addActionListener(e -> mostrarTotalDinero());

        JButton btnPromedio = new JButton("Promedio de Ventas");
        btnPromedio.addActionListener(e -> mostrarPromedioVentas());

        panel.add(btnMasVendido);
        panel.add(btnMenosVendido);
         panel.add(btnTotalDinero);
        panel.add(btnPromedio);
         panel.add(btnGraficoBarras);
        panel.add(btnGraficoPastel);

        return panel;
    }

    private void refreshTable() {
        try {
            List<Product> products = productDAO.findAll();
            String[] cols = {"ID", "Producto", "Cantidad", "IVA", "Precio", "Pedido"};
            DefaultTableModel model = new DefaultTableModel(cols, 0) {
                @Override
                public boolean isCellEditable(int r, int c) {
                    return false;
                }
            };
            for (Product p : products) {
                String iva = (int) Math.round(p.getTaxRate() * 100) + ".0%";
                String precio = currency.format(p.finalPrice());
                String pedido = p.needsReorder() ? "SI" : "NO";
                model.addRow(new Object[]{p.getId(), p.getName(), p.getStock(), iva, precio, pedido});
            }
            table.setModel(model);
            table.removeColumn(table.getColumnModel().getColumn(0)); // hide ID
        } catch (SQLException ex) {
            showError(ex);
        }
    }

    private void refreshStats() {
        try {
            lblIngresos.setText(currency.format(statsDAO.ingresos()));
            lblMas.setText(statsDAO.masVendido());
            lblMenos.setText(statsDAO.menosVendido());
            lblPromedio.setText(currency.format(statsDAO.promedio()));
        } catch (SQLException ex) {
            showError(ex);
        }
    }

    private void onVender() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) ((DefaultTableModel) table.getModel()).getValueAt(row, 0);
        String nombre = (String) ((DefaultTableModel) table.getModel()).getValueAt(row, 1);
        String cantidadStr = JOptionPane.showInputDialog(this, "¿Cantidad a vender de " + nombre + "?", "1");
        if (cantidadStr == null) {
            return;
        }
        try {
            int qty = Integer.parseInt(cantidadStr.trim());
            Product p = productDAO.findById(id);
            if (p == null) {
                return;
            }
            if (qty <= 0 || qty > p.getStock()) {
                JOptionPane.showMessageDialog(this, "Cantidad inválida. Stock disponible: " + p.getStock(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Sale s = new Sale(id, qty, p.finalPrice());
            saleDAO.create(s);
            productDAO.updateStock(id, p.getStock() - qty);
            refreshTable();
            refreshStats();
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void onPedir() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) ((DefaultTableModel) table.getModel()).getValueAt(row, 0);
        try {
            Product p = productDAO.findById(id);
            if (p == null) {
                return;
            }
            if (!p.needsReorder()) {
                JOptionPane.showMessageDialog(this, "El producto no ha alcanzado el mínimo de pedido", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            int qty = p.getReorderQty();
            int confirm = JOptionPane.showConfirmDialog(this, "¿Generar pedido por " + qty + " unidades?", "Confirmar Pedido", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                orderDAO.create(id, qty);
                productDAO.updateStock(id, p.getStock() + qty);
                refreshTable();
            }
        } catch (SQLException ex) {
            showError(ex);
        }
    }

    private void mostrarGraficoBarras() {
        try {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            var stats = statsDAO.ventasPorProducto();
            for (var entry : stats.entrySet()) {
                dataset.addValue(entry.getValue(), "Ventas", entry.getKey());
            }

            JFreeChart chart = ChartFactory.createBarChart(
                    "Ventas por Producto", // Título
                    "Producto", // Eje X
                    "Cantidad", // Eje Y
                    dataset
            );

            mostrarGraficoEnDialogo(chart, "Gráfico de Ingresos");
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void mostrarGraficoPastel() {
        try {
            DefaultPieDataset dataset = new DefaultPieDataset();
            var stats = statsDAO.ventasPorProducto();
            for (var entry : stats.entrySet()) {
                dataset.setValue(entry.getKey(), entry.getValue());
            }

            JFreeChart chart = ChartFactory.createPieChart(
                    "Distribución de Ventas", // Título
                    dataset,
                    true, // leyenda
                    true, // tooltips
                    false // urls
            );

            mostrarGraficoEnDialogo(chart, "Gráfico de Ventas");
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void mostrarProductoMasVendido() {
        try {
            var stats = statsDAO.ventasPorProducto();
            if (stats.isEmpty()) {
                showError(new Exception("No hay ventas registradas."));
                return;
            }

            var maxEntry = stats.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .get();

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            dataset.addValue(maxEntry.getValue(), "Unidades", maxEntry.getKey());

            JFreeChart chart = ChartFactory.createBarChart(
                    "Producto Más Vendido",
                    "Producto",
                    "Cantidad",
                    dataset
            );

            mostrarGraficoEnDialogo(chart, "Producto Más Vendido");
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void mostrarProductoMenosVendido() {
        try {
            var stats = statsDAO.ventasPorProducto();
            if (stats.isEmpty()) {
                showError(new Exception("No hay ventas registradas."));
                return;
            }

            var minEntry = stats.entrySet().stream()
                    .min(Map.Entry.comparingByValue())
                    .get();

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            dataset.addValue(minEntry.getValue(), "Unidades", minEntry.getKey());

            JFreeChart chart = ChartFactory.createBarChart(
                    "Producto Menos Vendido",
                    "Producto",
                    "Cantidad",
                    dataset
            );

            mostrarGraficoEnDialogo(chart, "Producto Menos Vendido");
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void mostrarTotalDinero() {
        try {
            double total = statsDAO.ingresos();

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            dataset.addValue(total, "Dinero", "Total Ventas");

            JFreeChart chart = ChartFactory.createBarChart(
                    "Total Dinero Obtenido",
                    "Concepto",
                    "Monto ($)",
                    dataset
            );

            mostrarGraficoEnDialogo(chart, "Total de Ventas en Dinero");
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void mostrarPromedioVentas() {
        try {
            double promedio = statsDAO.promedio();

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            dataset.addValue(promedio, "Dinero", "Promedio por Unidad");

            JFreeChart chart = ChartFactory.createBarChart(
                    "Promedio de Ventas",
                    "Concepto",
                    "Monto ($)",
                    dataset
            );

            mostrarGraficoEnDialogo(chart, "Promedio de Ventas");
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void mostrarGraficoEnDialogo(JFreeChart chart, String titulo) {
        JDialog dialog = new JDialog(this, titulo, true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);

        ChartPanel chartPanel = new ChartPanel(chart);
        dialog.setContentPane(chartPanel);

        dialog.setVisible(true);
    }

    private void showError(Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new MainFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
