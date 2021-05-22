package Projeto.Projeto.src;


import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Stream;

public class App {
    
  public static void main(String[] args) {

    Scanner leitura = new Scanner(System.in);
    LinkedList<String[]> dataset = new LinkedList();
    String[] target = {};

    try {
        
        int contador = 0;
      File arquivo = new File("C:\\Users\\joaoa\\Desktop\\ProjetoKNN\\Projeto\\Projeto\\dados.txt");
      Scanner leituraArquivo = new Scanner(arquivo);
      while (leituraArquivo.hasNextLine()) {
        String data = leituraArquivo.nextLine();

        // Este if pula a primeira leitura (Height,Weight,Age,Class)
        if(contador == 0){
            contador++;
            continue;
        }else{
            System.out.println(data);

        // Separa os dados por "," do vetor
        dataset.add(data.split(","));
        }

        
      }
      leituraArquivo.close();
    } catch (FileNotFoundException e) {
      System.out.println("Erro ao ler o arquivo txt!");
      e.printStackTrace();
    }


    /* teste da leitura separada 
    for( int i = 0; i< dataset.size() ; i++){
        for( int j=0; j < dataset.get(i).length; j++){
            System.out.println(dataset.get(i)[j]);
        }
    }*/

    
    while (true){
        boolean valido = false;
        System.out.print("\nDigite a altura, peso e idade (separados com ','):  ");
        target = leitura.nextLine().split(",");

        if(target.length != 3){
            System.out.println("Formato incorreto!!");
        }else{
            for(int i = 0; i < target.length; i++){
                try{
                    Float.parseFloat(target[i]);
                    if(i == target.length - 1){
                        valido = true;
                    }
                }catch(NumberFormatException e){
                    System.out.println("Digite apenas numeros!");
                }
                
            }
        }
        if(valido)
            break;
    }

  
    

    System.out.println("\nDigite quantos vizinhos deseja analisar: ");
    classify(dataset, Integer.parseInt(leitura.nextLine()), target);
    
    
    /*
    teste para saber se os dados digitados pelo usuario foram separados com virgula
    for( int i = 0; i< target.length ; i++){
            System.out.println(target[i]);
    }*/


  }

  private static SortedMap<Float,String[]> euclidianDistance(String[] target, LinkedList<String[]> dataset){
      float distancia = -1; 
      float calculo = 0;

      SortedMap<Float,String[]> mapa = new TreeMap<>();
      for(int i =0; i < dataset.size() ; i++){
        calculo = (float) Math.sqrt((Math.pow((Float.parseFloat(target[0]) - Float.parseFloat(dataset.get(i)[0])),2))
        + (Math.pow((Float.parseFloat(target[1]) - Float.parseFloat(dataset.get(i)[1])),2))
        + (Math.pow((Float.parseFloat(target[2]) - Float.parseFloat(dataset.get(i)[2])),2)));

        System.out.println("calculo: "+ calculo);
        if( calculo < distancia || distancia == -1){
            distancia = calculo;
        }
        mapa.put(calculo, dataset.get(i));
      }
      return mapa;
  }

  private static void classify(LinkedList<String[]> dataset, int k, String[] target ){
    if(k<=0){
        k = 1;
    }else if(k>dataset.size()){
        k = dataset.size();
        System.out.println("O numero de vizinhos digitados ultrapassa o limite, o valor utilizado será: " + dataset.size());
    }
    SortedMap<String,Integer> mapaClasse = new TreeMap<>();
    int count = 0;
    for(Map.Entry<Float,String[]> entry : euclidianDistance(target, dataset).entrySet()){
        System.out.println(entry.getKey() + " " + entry.getValue()[3]);

        if(mapaClasse.get(entry.getValue()[3]) == null){
            mapaClasse.put(entry.getValue()[3],1);
        }else{
            mapaClasse.put(entry.getValue()[3], mapaClasse.get(entry.getValue()[3]) + 1);
        }
        if(++count == k) 
            break;   
        }

        String classe = "";
        int maiorQtd = 0;

        for(Map.Entry<String,Integer> entry: mapaClasse.entrySet()){
            if(entry.getValue() > maiorQtd){
                maiorQtd = entry.getValue();
                classe = entry.getKey();
            }
        }
        System.out.println("\n\nClasse que mais aparece: " + classe);
        System.out.println(mapaClasse);
    }
}