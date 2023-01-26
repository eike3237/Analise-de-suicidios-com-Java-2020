package apsFinal;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class GraficoSuicidiosSP extends JFrame{
	private int[] mortesSp; // armazena as mortes do estado
	
	public GraficoSuicidiosSP() { /*CONSTRUTOR */
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Grafico Suicidios");
		setSize(1000, 750); // Original 950x700
		
		//criarGrafico();
		criarGrafico2();
		setLocationRelativeTo(null); // centraliza na tela o ojeto
		setVisible(true);
	}
	
	public GraficoSuicidiosSP(int[] numMortes) { /*CONSTRUTOR 2 usado - invoca o metodo de criação do grafico*/
		this.mortesSp = numMortes;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Grafico suicidios");
		setSize(1000, 750); // Original 950x700
		
		//criarGrafico();
		criarGrafico2();
		setLocationRelativeTo(null); // centraliza na tela o ojeto
		setVisible(true);
	}
	
		
	public void criarGrafico2() {
		
		
		final XYSeries estado = new XYSeries("São Paulo");
		
		int contador = 1979; /*Contador para adicionar os dados dos anos e os de morte*/
		for(int i = 0; i < mortesSp.length; i++) {
			estado.add(contador, mortesSp[i]); /*metodo de adição no grafico 1par. = ano; 2par. = dado*/
			contador = contador + 1;
			
		}
		
	
		final XYSeriesCollection dataset = new XYSeriesCollection( );
	      dataset.addSeries( estado );
	      
		
		JFreeChart xylineChart = ChartFactory.createXYLineChart(
		         "Suicidios estado de São Paulo",  /*titulo do grafico*/
		         "Anos", /*Eixo X*/
		         "Mortes por suicidio", /*Eixo y*/
		         dataset,
		         PlotOrientation.VERTICAL, 
		         true, true, false);
		      
		      ChartPanel painel = new ChartPanel(xylineChart); /*criação de um Painel para o grafico, como parametro  ografico ja criado*/
		      add(painel);
	      
	      
	}

	public int[] getMortesSp() {
		return mortesSp;
	}

	public void setMortesSp(int[] mortesSp) {
		this.mortesSp = mortesSp;
	}


	
}
