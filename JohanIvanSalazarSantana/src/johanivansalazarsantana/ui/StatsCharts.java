/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package johanivansalazarsantana.ui;

import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Map;
import johanivansalazarsantana.dao.StatsDAO;

/**
 *
 * @author JohanIvánSalazarSant
 */
public class StatsCharts     extends JFrame {

    private StatsDAO dao;

    public StatsCharts() throws SQLException {
        this.dao = new StatsDAO();
        setTitle("Estadísticas de Ventas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel con pestañas
        JTabbedPane tabs = new JTabbedPane();

        tabs.add("Ventas por Producto", new ChartPanel(graficoVentasPorProducto()));
        tabs.add("Ingresos Totales", new JLabel("Ingresos: $" + dao.ingresos()));
        tabs.add("Promedio", new JLabel("Promedio de ventas: $" + dao.promedio()));
        tabs.add("Más vendido", new JLabel("Producto más vendido: " + dao.masVendido()));
        tabs.add("Menos vendido", new JLabel("Producto menos vendido: " + dao.menosVendido()));

        add(tabs);
        setLocationRelativeTo(null);
    }

    // 📊 Gráfico de barras
    private JFreeChart graficoVentasPorProducto() throws SQLException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        Map<String, Integer> ventas = dao.ventasPorProducto();
        for (Map.Entry<String, Integer> entry : ventas.entrySet()) {
            dataset.addValue(entry.getValue(), "Ventas", entry.getKey());
        }

        return ChartFactory.createBarChart(
                "Ventas por Producto", 
                "Producto", 
                "Cantidad Vendida", 
                dataset
        );
    }

    // 🍕 Gráfico de pastel (opcional)
    private JFreeChart graficoPastel() throws SQLException {
        DefaultPieDataset dataset = new DefaultPieDataset();

        Map<String, Integer> ventas = dao.ventasPorProducto();
        for (Map.Entry<String, Integer> entry : ventas.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

        return ChartFactory.createPieChart(
                "Distribución de Ventas", 
                dataset, 
                true, true, false
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new StatsCharts().setVisible(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
    
