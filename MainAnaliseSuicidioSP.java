package apsFinal;

/* ----------------- AGORA SÓ FAZER O METODO DE LIMPEZA DO CODIGO ----------------*/

import java.io.IOException;

import algoritmos.ExcelMetodo;
import algoritmos.FormulasMetodo;
import algoritmos.GraficoXYMetodo;
import algoritmos.HistogramaMetodo;

public class MainAnaliseSuicidioSP {
	public static void main(String[] args) {
			
		LeituraExcel teste = new LeituraExcel();
			
			try {
				teste.LerExcel();
				System.out.println("------------- DENTRO DO MAIN ----------------");
				
				// Pegando os dados lidos no vetor
				String[][] dadoRetirado = teste.getDadosDF();
				
							
				// Pegando mortos 
				int[] mortos = teste.getMortes();
				
				
				/* ---------------- CRIAÇÃO DO GRAFICO -------------------- */
				GraficoSuicidiosSP grafico = new GraficoSuicidiosSP(mortos); //chamar com o numero de mortos o construtor para fazer o grafico
				
				grafico.setMortesSp(mortos);
				
				
				/* ---------------------------------- Criação Histograma ------------------------*/
				HistogramaSuicidiosSP hist = new HistogramaSuicidiosSP(mortos); // Passa-se como parametro os dados a serem analisados
				
				/* ------------------------------------ CONTAS E FORMULAS --------------------------------- */
				FormulasEstatistica FE = new FormulasEstatistica();
				FE.setVetorUser(mortos); //Alimentado com o vetor de mortes
				FE.setTamanhoVetor(mortos.length); //Não sei se é mesmo necessário mas armazenando o tamanho do vetor do usuario
				
							
				FE.calcularAmp();
				System.out.println("Amplitude = " + FE.getAmplitude()); 
				
				FE.calcularMedia();
				System.out.println("Media = " + FE.getMedia()); // Media sem parametros funcionando
							
				FE.calcularNClasses(); /*se a ordem nao for obedecida calculos podem dar errado*/
				System.out.println("Numero de classes = " + FE.getNumCla() + " + / - 1");
				
				FE.calcularModa(); //erro ao calcular Moda - NullPointerException
				System.out.println("Moda = " + FE.getModa());
				
				FE.calcularIC();
				System.out.println("Intervalo de classe = " + FE.getInterClasse());
				
				
				FE.calcularMediana();
				System.out.println("Mediana = " + FE.getMediana());
									
				// Calculo da Variância e Desvio Medio
				FE.calcularVariancia(); // Variancia e Desvio padrão estao com valores que nao batem -- Erro era nos dados do vetor MenosMedia que não tava recebendo dos DadosGlobais
				FE.calcularDesvioPadrao();
				
				System.out.println("Variancia = " + FE.getVariancia());
				System.out.println("Desvio Padrão = " + FE.getDesvioPadrao());
				System.out.println();
				
				// -------------------------------------------- Montagem de tabela -----------------------------------------
				
				//pegando dados necessarias para a tabela
				int NumClasses = FE.getNumCla();
				float IntervaloClass = FE.getInterClasse();
				int[] dados = FE.getVetorUser(); // ainda temos que organizar no rol
				
				
				int[] rol = FE.fazerRol(dados);	// Colocando vetor no rol e armazenando em nova variavel
				
				
				
				System.out.print("i  | \t I.C.\t  | Fi  | Fiac |   Fr%   | Frac% |\t Xi \t| \n"); // Espaços adicionados para se adequar as colunas
				
				int quantDados = FE.getTamanhoVetor(); /*pego da classe criada no main o tamanho do vetor*/
				int firstIc = (int)(rol[0]); /*armazena a nossa base para criação do Intervalo de Classe da tabela*/
				int[] fi = new int[NumClasses]; /*Armazena minha Frequencia simples*/
				int[]fiac = new int[NumClasses]; /*Frequencia simples acumulada*/
				double[] fr = new double [NumClasses]; /*Frequencia relativa percentual*/
				double[] frac = new double[NumClasses]; /*Frequencia relativa percentual acumulada*/
				double[] xi = new double[NumClasses]; /*Ponto medio do meu Intervalo de classe*/
				int acumulaFi = 0; /*criada para acumulo e construção do Fiac*/
				double acumulaFr = 0; /*criada para acumulo e construção do Fr*/
				double percentPraUm = 100 / (double)quantDados; /*obtenho quanto cada dado vale em porcentagem*/
				
				for(int i = 0; i < fi.length; i++) { // Loop para contagem de acumulações (Fi, Fiac, Fr%, Frac%) -- contador para armazenar só as repetições
					int repeticoes = 0;
					for(int j = 0; j < rol.length; j++) { // percorre todos os dados 
							if(rol[j] >= firstIc && rol[j] < (firstIc + IntervaloClass)) { /*CORRIGIR ESTE CODIGO PARA FAZER Fi*/
								repeticoes++;
							}
					}
					fi[i] = repeticoes; // armazena as repetições de cada linha no vetor
					
					
					acumulaFi = acumulaFi + fi[i]; // somando Fi
					fiac[i] = acumulaFi; // armazenando Fiac
					
					xi[i] = (firstIc  + (firstIc + IntervaloClass)) / 2;
					
					firstIc = (int) (firstIc + IntervaloClass); //  Acrescenta o Intervalo de classe à variavel a cada loop
									
					fr[i] = (percentPraUm * fi[i]); // Somatoria == 99,97 // carregando vetor de Fr
					
					acumulaFr = acumulaFr + fr[i]; // Processo de somatoria do Frac
					frac[i] = acumulaFr;				
				}
				
				firstIc = (int)(rol[0]); // retornado ao valor original para o loop
				for(int i = 0; i < NumClasses; i++) {
					
					// Ordem de formatação = i - IC - Fi - Fiac - Fr% - Frac% - Xi
					System.out.printf("%d   %d |-- %.0f   %d \t  %d \t %.2f%%    %.2f%% \t%.2f   \n", (i+1),firstIc,(firstIc+IntervaloClass), fi[i], fiac[i], fr[i],frac[i],xi[i]);
								
					firstIc = (int) (firstIc + IntervaloClass);
					
				}
				
				// Imprimindo ultima linha
				int somatoriaFi = 0;
				float somatoriaFr = 0;
				for(int i = 0; i < NumClasses; i++) { // Fazendo as somatorias
					somatoriaFi += fi[i];
					somatoriaFr += fr[i];
				}
				System.out.printf("--      ------      %d \t ----\t %.2f%%    ------\t-------", somatoriaFi, somatoriaFr);
				
				//Tratamento de Exceções
			} catch (IOException e) {
				
				System.err.printf("Erro na abertura do arquivo: %s \n", e.getMessage());
				e.printStackTrace();
			}
			catch (ArithmeticException erroAritmetico) {
				
				System.err.println("Erro aritmetico, verifique a ordem de execução das contas ou contate um admnistrador: ");
				System.err.println("Mensagem de erro = " + erroAritmetico.getMessage());
			}
			catch (NullPointerException nullpoint) {
				System.err.println("Erro de acesso a dado com valor null por favor verifique o diretorio ou contate o admnistrador ");
				System.err.println("Mensagem de erro = " + nullpoint.getMessage());
			}
			catch(ArrayIndexOutOfBoundsException erroArray) {
				System.err.println("Erro no vetor, por favor contate um dos admnistradores: ");
				System.err.println("Mensagem de erro = " + erroArray.getMessage());
			}
			
			
		}
}
