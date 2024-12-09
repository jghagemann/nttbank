package com.hagemann.nttbank.naousar.helper;

import com.hagemann.nttbank.naousar.domain.transacao.Categoria;
import com.hagemann.nttbank.naousar.domain.transacao.TipoTransacao;
import com.hagemann.nttbank.naousar.domain.transacao.Transacao;
import com.hagemann.nttbank.naousar.exceptions.ArquivoException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GraficoHelper {

    public static ByteArrayOutputStream gerarGraficoDespesas(List<Transacao> transacoes) {
        Map<Categoria, BigDecimal> gastosPorCategoria = transacoes.stream()
                .filter(t -> t.getTipoTransacao() != TipoTransacao.DEPOSITO)
                .collect(Collectors.groupingBy(
                        Transacao::getCategoria,
                        Collectors.mapping(
                                Transacao::getValor,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                        )
                ));

        BigDecimal totalGasto = gastosPorCategoria.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        gastosPorCategoria.forEach((categoria, valor) -> {

            BigDecimal percentage = valor.divide(totalGasto, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
            String label = categoria.name() + " (" +
                    "R$ " + valor.setScale(2, RoundingMode.HALF_UP) + " - " +
                    percentage.setScale(2, RoundingMode.HALF_UP) + "%)";
            dataset.setValue(label, valor);
        });

        // Create the pie chart
        JFreeChart pieChart = ChartFactory.createPieChart(
                "Distribuição de Gastos por Categoria",
                dataset,
                true,
                true,
                false
        );

        PiePlot plot = (PiePlot) pieChart.getPlot();
        plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        plot.setInsets(new RectangleInsets(10, 10, 10, 10));
        plot.setCircular(true);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ChartUtils.writeChartAsPNG(baos, pieChart, 800, 600);
            return baos;
        } catch (IOException e) {
            throw new ArquivoException("Erro ao gerar gráfico de despesas");
        }
    }
}
