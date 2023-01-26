package apsFinal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LeituraExcel {
	private String[][] matrizExcel;
	private String[][] spDados;
	
	String[][] excelMatriz = new String[1054][4];
	String[][] dadosDeSp = new String[1054][4];
	String dadosSP[][] = new String[50][4]; // Só de SP ---- TRABALHAREMOS COM ESTE
	int mortes[] = new int[39]; // TRABALHAREMOS COM ESTE - 39 Pq são todos os anos que nós temos os dados
	
	public String[][] LerExcel() throws IOException{
		
		
		try {
			/**/
	        String excelFilePath = "testes.xlsx";
	        /**/
	        FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\eike_\\Desktop\\Facul\\3° Semestre\\APS\\suicidios.xlsx")); /*Alterar diretorio para o arquivo
	        excel desejado para leitura, todo o codigo é baseado em um padrão já estabelecido no trabalhar*/
	        
	        
	         
	        Workbook workbook = new XSSFWorkbook(inputStream);
	        Sheet firstSheet = workbook.getSheetAt(0);
	        Iterator<Row> iterator = firstSheet.iterator();
	        
	        /*variaveis para leitura*/
	        int numeroLinhas = 0, numeroColunas = 0, contadorLinha = 0, contadorColuna = 0;
	        numeroLinhas = firstSheet.getLastRowNum() + 1; // + 1 pq conta com 0 de inicio
	        	         
	        while (iterator.hasNext()) {
	            Row nextRow = iterator.next();
	            Iterator<Cell> cellIterator = nextRow.cellIterator();
	            if(numeroColunas == 0) { // só entra aqui na primeira volta 
	            	numeroColunas = nextRow.getLastCellNum();
	            }
	            contadorColuna = 0;
	            contadorLinha ++;
	            while (cellIterator.hasNext()) {
	                Cell cell = cellIterator.next(); 
	                 
	                switch (cell.getCellType()) { 
	                    case Cell.CELL_TYPE_STRING:
	                        excelMatriz[contadorLinha - 1][contadorColuna] = cell.getStringCellValue();
	                        break;
	                    case Cell.CELL_TYPE_BOOLEAN:
	                        break;
	                    case Cell.CELL_TYPE_NUMERIC:
	                        excelMatriz[contadorLinha - 1][contadorColuna] = String.valueOf((int) cell.getNumericCellValue());
	                        break;
	                }
	                contadorColuna ++;
	            }
	            
	        }
	        
	        //Pegando dados somente de SP - codigo 35
	        int j = 0;
	        for(int i = 0; i < numeroLinhas; i++) { 
	        	if(excelMatriz[i][j].contentEquals("35")) {
		        	for(j = 0; j < numeroColunas; j++) {
		        		dadosDeSp[i][j] = excelMatriz[i][j]; // recebe dados só de SP
		        		
		        		}
	        	}
	        	j = 0; // para voltar pra primeira coluna qnd for analisar as proximas linhas
	        }
	        
	        /* --------------------------------------  LENDO SOMENTE DADOS DE MORTE E ARMAZENANDO EM VETOR ----------------------------------*/
	        
	        /* Este vetor criado é para fazer os calculos*/
	        //vetor adicionado no escopo principal da matriz -> dadosDeSp
	        int posMortes = 0;
	        for(int x = 1; x < 1054; x++) {// x = 1 pra ignorar a primeira
	        	for(int y = 0; y < 4; y++) {
	        		if(y == 3){//dados de SP ta pegando todas as linhas uma a uma, tenho q colocar outro if com not null
	        			if(dadosDeSp[x][y] != null) {
		        			String numeroMortos = dadosDeSp[x][y];
		        			mortes[posMortes] = Integer.parseInt(numeroMortos);
		        			posMortes++;
	        			}
	        		}
	        	}
	        }
	       
	        /*------------------ DADOS DE SP SEM ESPAÇOS NULLS -----------------*/
	        
	        //variavel adicionada no escopo principal da classe -> dadosSp
	        
	        int posLSP = 0, posCSP = 0;    // L = linha C = coluna SP = Estado -> usados para definir a linha e col. q vai ser armazenada no vetor
	        int auxCol = 0;    // Auxiliar pra linha do vetor sem valores null pular só qnd coluna pular de lugar, oq só acontece quando for ! null
	        
	        for(int x = 0; x < 1054; x++) {    // varredura de todos os dados
	        	for(int y = 0; y < 4;  y++) {    // varredura de todas as colunas
	        		if(dadosDeSp[x][y] != null) {
	        			dadosSP[posLSP][posCSP] = dadosDeSp[x][y];    // quando o dado for diferente de null armazena
	        			posCSP++;    // acrescenta 1 no numero da coluna para armazenar a proxima coluna
	        		}
	        	}
	        	if(auxCol != posCSP) { // Se nao for igual quer dizer que armazenou então acrescento +1 na pos de linha assim, contando linha só qnd foi encontrado valores ! null
	        		posLSP++;
	        	}
	        	posCSP = 0; // reseto o valor da coluna para que o processo se repita para outras linhas 
	        	auxCol = posCSP;//recebe 0 novamente, para que mude somente quando encontrar valor != null e este seja armazenado
	        	
	        }
	        
	        this.matrizExcel = excelMatriz;
	        this.spDados = dadosSP;
	        inputStream.close();
	        
		}finally {
			System.out.println("LEITURA DE DADOS DO EXCEL FINALIZADA COM SUCESSO!");
		}
		return dadosDeSp;
	}
	
	public String[][] getDadosDF(){
		return this.dadosSP;
	}
	
	public int[] getMortes(){
		return this.mortes;
	}
}
