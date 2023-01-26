package apsFinal;

// ----------------------------------- TRABALHAMOS COM 38 ANOS e  39 dados ------------ ACRESCENTAR THROWS ARITMETICOS AO FINAL DA LIMPA
public class FormulasEstatistica implements InterfaceFormulas{
		
			private float media; 
			private float mediana;
			private float moda;
			private float modas[];
			private float interClasse;//intervalo de classes
			private int amplitude;
			private int numClasses;//numero de classe
			private int[] vetorUser;
			private int tamanhoVetor;
			private float variancia;
			private double desvioPadrao;
			
			//METODOS
			
			public float calcularMedia(float qnt, float soma) { 
				float x = soma / qnt;
				return x;
			}
			
			@Override
			public void calcularMedia() throws ArithmeticException, ArrayIndexOutOfBoundsException{ //Metodo de calculo da media sem parametros -> Funcionando, será usada
				int somatoria = 0;
				for(int i = 0; i < vetorUser.length; i++) {
					somatoria = somatoria + vetorUser[i];
				}
				System.out.println("vetor tamanho = " + vetorUser.length);
				this.media = somatoria / (vetorUser.length) ; // 
			}
			public void calcularMedia(int[] numeros) { //Metodo de calculo da media se passar com um vetor
				int somatoria = 0;
				for(int i = 0; i < numeros.length; i++) {
					somatoria = somatoria + numeros[i];
				}
				System.out.println("vetor tamanho = " + numeros.length);
				this.media = somatoria / (numeros.length) ; // 
			}
			
			@Override
			public void calcularModa() throws ArithmeticException, ArrayIndexOutOfBoundsException{
				//Inicializando vetor moda internamente
				float[] modas2 = new float[50]; // criada novo vetor para intermediar o vetor daqui pro vetor da classe
				int numVezes = 1; // Ao menos se repete uma vez
				int comparaVz = 0; // Compara o numero de vezes maximas
				int qntModas = 0;
				
				for(int i =0; i < this.vetorUser.length; i++) {
					numVezes = 1; // contador de repetições, resetado toda vez que volta para este loop principal
					for(int j = i + 1; j < this.vetorUser.length; j++) { /*percorre o vetor procurando igualdades*/
						if(this.vetorUser[i] == this.vetorUser[j]) { /*caso encontre acrescenta 1 na repetição*/
							++numVezes;
						}
					}
					if(numVezes > comparaVz) { /*caso seja maior que o valor max de repetição atual, armazena o novo valor como moda*/
						this.moda = (float) this.vetorUser[i];
						modas2[qntModas] = (float) this.vetorUser[i];
						comparaVz = numVezes;
					}
					else if(numVezes == comparaVz) { /*se forem iguais, os 2 são modas, então armazenamos em um vetor*/
						qntModas++;
						modas2[qntModas] = (float) this.vetorUser[i];
						this.moda = (float) this.vetorUser[i];
					}
				}
				
				this.setModas(modas2); /*armazena no vetor modas sempre por precaução para caso n seja modal simples*/
			}
			
			@Override
			public void calcularMediana() throws ArithmeticException, ArrayIndexOutOfBoundsException{ /*Calcula a mediana dos dados utilizando os atributos já inseridos, sem parametros*/
				if(vetorUser.length % 2 == 0) {/*checagem caso o numero de dados seja par*/
					int meio_vetor = vetorUser.length / 2;
					double valor1 = vetorUser[meio_vetor];
					double valor2 = vetorUser[(meio_vetor - 1 )];/*é pego os 2 valores centrais para calculo da mediana*/
					this.mediana = (float)(valor1 + valor2) /2;
					//pode-se colocar um return pra n precisar usar o get
				}else { // caso impar
					int pos = (vetorUser.length / 2);
					this.mediana = (float) vetorUser[pos]; //-1 pra descontar o 0
					//tambem adicionar return caso queira
				}
			}
			
			public void calcularAmp(double li, double ls) { // Calculo de amplitude recebendo como parametro, o maior e o menor dado
				this.amplitude = (int) (ls - li);
			}
			
			@Override
			public void calcularAmp() throws ArithmeticException, ArrayIndexOutOfBoundsException{ // Calcula a amplitude utilizando atributos já inseridos da classe
				//Pegando o maior e menor
				int menor = this.vetorUser[0], maior = 0; //menor recebe qlqr do vetor pq se for 0 nunca vai ter menor
				for(int i = 0; i < this.vetorUser.length; i++) {
					if(this.vetorUser[i] <= menor) {
						menor = this.vetorUser[i];
					}
					else if(this.vetorUser[i] >= maior) {
						maior = this.vetorUser[i];
					}
				}
				
				this.amplitude = maior - menor;
				//this.amplitude = vetorUser[vetorUser.length - 1] - vetorUser[0]; // length -1 pra pegar o ultimo vetor, ou seja, maior
			}
			
			public void calcularNClasses(int n) { // funcionando
				int qnt = (int) Math.round((1 + 3.33 * (Math.log10(n))));
				this.numClasses = qnt;
			}
			
			@Override
			public void calcularNClasses() throws ArithmeticException, ArrayIndexOutOfBoundsException{	/* Faz o calculo do Num de Classes(linhas) utilizando os atributos da classe*/		
				int quantidadeClasses = (int) Math.round((1 + 3.33 * (Math.log10(vetorUser.length)))); // log 10 pq é numeros comuns, de base 10.
				/*Formula de Sturges se arredonda para o numero inteiro mais proximo então o Math.round basta*/
				this.numClasses = quantidadeClasses;
			}
			
			public void calcularIC(int delta, int num_cla) { /* A implementar, pois usa 2 resultados ja dados
				* assim possivelmente precisaremos de retorno para obter os valores, alem de ter que
				* tratar o numero de casas decimais de acordo com os dados brutos digitados pelo usuario*/
				float intervalo = (float) (delta / num_cla);
				this.setInterClasse(intervalo);
			}
			
			@Override
			public void calcularIC() throws ArithmeticException, ArrayIndexOutOfBoundsException{ /*calcula o Intervalo de classe usado para montagem da tabela*/
				int intervalo = (this.amplitude / this.numClasses) + 1; //acrescimo de 1 para arredondamento correto
				this.setInterClasse(intervalo);
			}
			
			@Override
			public float calcularVariancia() { /* calcula a variancia populacional dos dados*/
				/*A formula é a média aritmética dos quadrados dos desvios tomados em relação à
				média. Somatoria(Xi - media)² / n*/
				
				int[] dadosGlobal = this.vetorUser;
				float[] dadosMenosMedia = new float[39]; // variavel para armazenar os numeros como float para ser mais preciso
				float mediaAtual = this.media; // Criando variavel interna para ser trabalhada
				int somatoria = 0;
				
				//Carregando vetor novo com valores em float
				/*for(int i = 0; i < dadosGlobal.length; i++) {
					dadosMenosMedia[i] = (float) dadosGlobal[i]; //------------- ERRO LINHA 136 CONVERSÃO PARA FLOAT - CORRIGIR - CASO NÃO MANTER TUDO EM INT MESMO
				}*/
				
				//dadosMenosMedia = (float) dadosGlobal;
				
				for(int i = 0; i < dadosGlobal.length; i++) {
					dadosMenosMedia[i] = dadosGlobal[i] - (float) mediaAtual; // subtraindo a media de cada um dos dados
				}
				
				for(int i = 0; i < dadosGlobal.length; i++) {
					dadosMenosMedia[i] = (int) Math.pow(dadosMenosMedia[i], 2); // elevando cada um dos numeros ao quadrado
					somatoria += dadosMenosMedia[i]; // somatoria de todos os valores ao quadrado
				}
				
				
				float varianciametodo = somatoria / (dadosMenosMedia.length - 1); // Dvidindo a soma dos quadrados de cada elemento pela quantidade pra gerar a nova media que é a variancia
				this.variancia = varianciametodo;
				return varianciametodo;
			}
			
			@Override
			public double calcularDesvioPadrao() throws ArithmeticException, ArrayIndexOutOfBoundsException{
				//Desvio padrão é a raiz quadrada da variancia;
				if(this.variancia != 0) { // caso já tenhamos o valor da variância
					double desvioPadraoMetodo = Math.sqrt(this.variancia);
					this.desvioPadrao = desvioPadraoMetodo;
					return this.desvioPadrao;
				}else { // Caso não tenhamos a variancia o metodo já o faz
					int[] dadosGlobal = this.vetorUser;
					float[] dadosMenosMedia = null; // variavel para armazenar os numeros como float para ser mais preciso
					float mediaAtual = this.media; // Criando variavel interna para ser trabalhada
					int somatoria = 0;
					
					for(int i = 0; i < dadosGlobal.length; i++) {
						dadosMenosMedia[i] = dadosMenosMedia[i] - (float) mediaAtual; // subtraindo a media de cada um dos dados
					}
					
					for(int i = 0; i < dadosGlobal.length; i++) {
						dadosMenosMedia[i] = (int) Math.pow(dadosMenosMedia[i], 2); // elevando cada um dos numeros ao quadrado
						somatoria += dadosMenosMedia[i]; // somatoria de todos os valores ao quadrado
					}
					
					float varianciametodo = somatoria / dadosMenosMedia.length; // Dvidindo a soma dos quadrados de cada elemento pela quantidade pra gerar a nova media que é a variancia
					this.variancia = varianciametodo;
					
					double desvioPadraoMetodo = Math.sqrt(this.variancia);
					this.desvioPadrao = desvioPadraoMetodo;
					return this.desvioPadrao;
				}
				
			}
			
			@Override
			public int[] fazerRol(int[] vetorOriginal) throws ArithmeticException, ArrayIndexOutOfBoundsException{ 		//Organizando o vetor em ordem crescente
				int aux;
				for(int i = 0; i < vetorOriginal.length; i++) {
					for(int j = 0; j < vetorOriginal.length; j++) {
						if(vetorOriginal[i] < vetorOriginal[j]) {
							aux = vetorOriginal[i];
							vetorOriginal[i] = vetorOriginal[j];
							vetorOriginal[j] = aux;
						}
					}
				}
				int[] vetorOrganizado = vetorOriginal;
				
				return vetorOrganizado; /*Função retorna todo o vetor já organizado*/
			}
			
			//Getters and Setters
			public float getMedia() {
				return this.media;
			}
			public int getAmplitude() {
				return this.amplitude;
			}
			public int getNumCla() {
				return this.numClasses;
			}
			public float getInterClasse() {
				return interClasse;
			}
			public void setInterClasse(float interClasse) {
				this.interClasse = interClasse;
			}
			public float getMediana() {
				return mediana;
			}
			public void setMediana(float mediana) {
				this.mediana = mediana;
			}
			public float getModa() {
				return moda;
			}
			public void setModa(float moda) {
				this.moda = moda;
			}
			public int[] getVetorUser() {
				return this.vetorUser;
			}
			public void setVetorUser(int[] dados_brutos) {
				this.vetorUser = dados_brutos;
				
			}
			public int getTamanhoVetor() {
				return tamanhoVetor;
			}
			public void setTamanhoVetor(int tamanhoVetor) {
				this.tamanhoVetor = tamanhoVetor;
			}
			public float[] getModas() {
				return modas;
			}
			public void setModas(float modas[]) {
				this.modas = modas;
			}
			public float getVariancia() {
				return variancia;
			}
			public void setVariancia(float variancia) {
				this.variancia = variancia;
			}
			
			
			public double getDesvioPadrao() {
				return desvioPadrao;
			}
			public void setDesvioPadrao(double desvioPadrao) {
				this.desvioPadrao = desvioPadrao;
			}
}
