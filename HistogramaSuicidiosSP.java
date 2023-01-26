package apsFinal;

import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;

public class HistogramaSuicidiosSP extends JFrame{
private double[] mortes;
	
	
	public HistogramaSuicidiosSP() { // deixado por garantia, caso não sejam colocados parametros, exibindo algo fora do normal
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Histograma");
		setSize(950, 700);
		
		criarHistograma();// chamada do metodo que constroi o grafico
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public HistogramaSuicidiosSP(int[] numMortes) { /*Construtor para criação de janela, recebe os dados de morte como parametro*/
		double[] mortesDouble = new double[39];
		
		for(int i = 0; i < numMortes.length; i++) {/*armazena cada uma das mortes em um novo vetor convertido para double*/
				mortesDouble[i] = (double) numMortes[i];
				//System.out.println(mortesDouble[i]);
			}
		
		this.mortes = mortesDouble; //Acima criado para armazenar valores 1 a 1 convertidos para double
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Histograma");
		setSize(950, 700);
		
		criarHistograma();// chamada do metodo que constroi o grafico
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void criarHistograma() {
	       
	        HistogramDataset dataset = new HistogramDataset();
	        
	        dataset.setType(HistogramType.RELATIVE_FREQUENCY);
	        
	        dataset.addSeries("Mortes", this.mortes, 7, 1048, 2399); // o numero de colunas pode ser definido ao instanciar com o numero de classes calculado automaticamente
	        
	        String plotTitle = "Histograma - Suicidios (1979 a 2017)"; // Titulo do grafico
	        String xaxis = "mortes"; // Eixo X - atribuido o numero de mortes
	        String yaxis = "Porcentagem dos dados ao decorrer de 39 anos"; // Aparentemente é a porcentagem dos dados -- antigo = Decada (em percentual ex.: 0,18 decada de 80)
	        PlotOrientation orientation = PlotOrientation.VERTICAL; // Orientação das colunas
	       
	        boolean show = false; 
	        boolean toolTips = false;
	        boolean urls = false; 
	       
	        JFreeChart chart = ChartFactory.createHistogram( plotTitle, xaxis, yaxis, 
	                dataset, orientation, show, toolTips, urls); /*Criação da tabela usando classe importada do JFreeChart*/
	       
	        int width = 500; /*Largura e altura da janela*/
	        int height = 300; 
	        
	        try { /* Adicionar o tratamento em todo o processo para tratar possiveis exceções*/
	    	   
	    	   ChartUtilities.saveChartAsPNG(new File("histogram.PNG"), chart, width, height);// Salvando em um PNG
	    	   
	        	ChartPanel tela = new ChartPanel(chart);
	        	add(tela);
	        	
	        } catch (IOException e) {
	        	System.err.printf("Erro na criação de histograma, contate o admnistrador, ERRO = " + e.getMessage());
	        }
	    
	}
}
