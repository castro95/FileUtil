package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
* Uma classe abstrata para ajudar a trabalhar com manipulacao de conteudo de arquivos
* <br/><br/>
* Sim, <strong>eu estou ciente que ja existem bibliotecas para o que foi feito aqui</strong>.
* Mas eu optei por fazer mesmo assim. ^^
* <br/>
* 
* @author  Matheus Castro
* @version 1.0
* @since   13/06/2021
*/

public abstract class FileUtil {

	/**
	 * Junta o conteudo dos dois arquivos no <i>arquivo1</i>(caminhoArquivo1)
	 * <strong>O <i>arquivo2</i>(caminhoArquivo2) eh deletado.</strong>
	 * <br/><br/>
	 * 
	 * @param caminhoArquivo1
	 * @param caminhoArquivo2
	 * @throws IOException 
	 */
	public static void unirArquivos(String caminhoArquivo1, String caminhoArquivo2)
			throws IOException {
		unirArquivos(new File(caminhoArquivo1), new File(caminhoArquivo1), new File(caminhoArquivo2));
	}

	/**
	 * Junta o conteudo dos dois arquivos no <i>arquivo1</i>.<br/>
	 * <strong>O <i>arquivo2</i> eh deletado.</strong>
	 * <br/><br/>
	 * 
	 * @param arquivo1
	 * @param arquivo2
	 * @throws IOException 
	 */
	public static void unirArquivos(File arquivo1, File arquivo2)
			throws IOException {
		unirArquivos(arquivo1, arquivo1, arquivo2);
	}
	
	/**
	 * Junta o conteudo dos dois arquivos no <i>novoArquivo(caminhoNovoArquivo)</i>.<br/>
	 * <strong>Tanto o <i>arquivo1"(caminhoArquivo1)</i> quanto o <i>arquivo2"(caminhoArquivo2)</i> sao 
	 * deletados.</strong>
	 * <br/><br/>
	 * 
	 * @param caminhoNovoArquivo
	 * @param caminhoArquivo1
	 * @param caminhoArquivo2
	 * @throws IOException 
	 */
	public static void unirArquivos(String caminhoNovoArquivo, String caminhoArquivo1, String caminhoArquivo2)
			throws IOException {
		unirArquivos(new File(caminhoNovoArquivo), new File(caminhoArquivo1), new File(caminhoArquivo2));
	}

	/**
	 * Junta o conteudo dos dois arquivos no <i>novoArquivo(caminhoNovoArquivo)</i>.<br/>
	 * <strong>Tanto o <i>arquivo1</i> quanto o <i>arquivo2</i> sao deletados.</strong>
	 * <br/><br/>
	 * 
	 * @param caminhoNovoArquivo
	 * @param arquivo1
	 * @param arquivo2
	 * @throws IOException 
	 */
	public static void unirArquivos(String caminhoNovoArquivo, File arquivo1, File arquivo2)
			throws IOException {
		unirArquivos(new File(caminhoNovoArquivo), arquivo1, arquivo2);
	}

	/**
	 * Junta o conteudo dos dois arquivos no <i>novoArquivo</i>.<br/>
	 * <strong>Tanto o <i>arquivo1"(caminhoArquivo1)</i> quanto o <i>arquivo2"(caminhoArquivo2)</i>
	 * sao deletados.</strong>
	 * <br/><br/>
	 * 
	 * @param novoArquivo
	 * @param caminhoArquivo1
	 * @param caminhoArquivo2
	 * @throws IOException 
	 */
	public static void unirArquivos(File novoArquivo, String caminhoArquivo1, String caminhoArquivo2)
			throws IOException {
		unirArquivos(novoArquivo, new File(caminhoArquivo1), new File(caminhoArquivo2));
	}

	/**
	 * Junta o conteudo dos dois arquivos no <i>novoArquivo</i>.<br/>
	 * <strong>Tanto o <i>arquivo1</i> quanto o <i>arquivo2</i> sao deletados.</strong>
	 * <br/><br/>
	 * 
	 * @param novoArquivo
	 * @param arquivo1
	 * @param arquivo2
	 * @throws IOException 
	 */
	public static void unirArquivos(File novoArquivo, File arquivo1, File arquivo2) throws IOException {
		List<String> linhas = lerArquivo(arquivo1);
		linhas.addAll(lerArquivo(arquivo2));

		arquivo1.delete();
		arquivo2.delete();

		if (!novoArquivo.exists())
			novoArquivo.createNewFile();

		sobreescreverConteudo(novoArquivo, linhas);
	}

	/**
	 * Remove linhas que possuem o conteudo de uma linha "anterior".<br/>
	 * Ex: Caso duas ou mais linhas contenham o conteudo "X", apenas a primeira linha sera mantida.<br/>
	 * <strong>As linhas sao reordenadas.</strong>
	 * 
	 * <br/><br/>
	 * @param caminho
	 * @throws IOException 
	 */
	public static void limparLinhasDuplicadas(String caminho) throws IOException {
		limparLinhasDuplicadas(new File(caminho));
	}

	/**
	 * Remove linhas que possuem o conteudo de uma linha "anterior".<br/>
	 * Ex: Caso duas ou mais linhas contenham o conteudo "X", apenas a primeira linha sera mantida.<br/>
	 * <strong>As linhas sao reordenadas.</strong>
	 * 
	 * <br/><br/>
	 * @param arquivo
	 * @throws IOException 
	 */
	public static void limparLinhasDuplicadas(File arquivo) throws IOException {
		List<String> linhas = lerArquivo(arquivo);
		for (int i = 0; i < linhas.size(); i++) {
			if (linhas.get(i).trim().equalsIgnoreCase("")) {
				linhas.remove(i);
				i--;
			}
		}
		Set<String> set = new HashSet<>(linhas);
		linhas.clear();
		linhas.addAll(set);

		sobreescreverConteudo(arquivo, linhas);
	}

	/**
	 * Ordena as linhas do arquivo.<br/>
	 * Utiliza a classe String como "Comparator".<br/>
	 * <strong>As linhas sao comparadas considerando todos os caracteres "alfabeticos" como caixa-baixa</strong>
	 * 
	 * <br/><br/>
	 * @param caminho
	 * @throws IOException 
	 */
	public static void ordenarLinhas(String caminho) throws IOException {
		ordenarLinhas(new File(caminho));
	}

	/**
	 * Ordena as linhas do arquivo.<br/>
	 * Utiliza a classe String como "Comparator".<br/>
	 * <strong>As linhas sao comparadas considerando todos os caracteres "alfabeticos" como caixa-baixa</strong>
	 * 
	 * <br/><br/>
	 * @param arquivo
	 * @throws IOException 
	 */
	public static void ordenarLinhas(File arquivo) throws IOException {
		ordenarLinhas(arquivo, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.toLowerCase().compareTo(o2.toLowerCase());
			}
		});
	}
	
	/**
	 * Ordena as linhas do arquivo de acordo com o Comparator passado.
	 * 
	 * <br/><br/>
	 * @param caminho
	 * @param comparator
	 * @throws IOException 
	 */
	public static void ordenarLinhas(String caminho, Comparator<String> comparator) throws IOException {
		ordenarLinhas(new File(caminho), comparator);
	}
	
	/**
	 * Ordena as linhas do arquivo de acordo com o Comparator passado.
	 * 
	 * <br/><br/>
	 * @param arquivo
	 * @param comparator
	 * @throws IOException 
	 */
	public static void ordenarLinhas(File arquivo, Comparator<String> comparator) throws IOException {
		List<String> linhas = lerArquivo(arquivo);
		linhas.sort(comparator);
		sobreescreverConteudo(arquivo, linhas);
	}
	
	/**
	 * Le o conteudo do arquivo e retorna uma Lista, onde cada elemento corresponde a uma linha.
	 * 
	 * <br/><br/>
	 * @param caminho
	 * @return List<String>
	 * @throws FileNotFoundException
	 */
	public static List<String> lerArquivo(String caminho) throws FileNotFoundException {
		return lerArquivo(new File(caminho));
	}

	/**
	 * Le o conteudo do arquivo e retorna uma Lista, onde cada elemento corresponde a uma linha.
	 * 
	 * <br/><br/>
	 * @param arquivo
	 * @return List<String>
	 * @throws FileNotFoundException
	 */
	public static List<String> lerArquivo(File arquivo) throws FileNotFoundException {
		List<String> linhas = new ArrayList<>();
		try (Scanner s = new Scanner(arquivo)) {
			while (s.hasNext()) {
				linhas.add(s.nextLine().trim());
			}
		}
		return linhas;
	}

	/**
	 * Adiciona uma linha no final do arquivo.
	 * 
	 * <br/><br/>
	 * @param caminho
	 * @param linha
	 * @throws IOException
	 */
	public static void adicionarLinha(String caminho, String linha) throws IOException {
		adicionarLinha(new File(caminho), linha);
	}

	/**
	 * Adiciona uma linha no final do arquivo.
	 * 
	 * <br/><br/>
	 * @param arquivo
	 * @param linha
	 * @throws IOException
	 */
	public static void adicionarLinha(File arquivo, String linha) throws IOException  {
		adicionarLinhas(arquivo, Collections.singletonList(linha));
	}

	/**
	 * Adiciona cada elemento do Array <i>linhas</i> no final do arquivo.<br/>
	 * Cada elemento eh inserido em uma linha diferente, seguindo a ordem do Array.<br/>
	 * 
	 * <br/><br/>
	 * @param caminho
	 * @param linhas
	 * @throws IOException
	 */
	public static void adicionarLinhas(String caminho, String[] linhas) throws IOException {
		adicionarLinhas(new File(caminho), Arrays.asList(linhas));
	}

	/**
	 * Adiciona cada elemento do Array <i>linhas</i> no final do arquivo.<br/>
	 * Cada elemento eh inserido em uma linha diferente, seguindo a ordem do Array.<br/>
	 * 
	 * <br/><br/>
	 * @param arquivo
	 * @param linhas
	 * @throws IOException
	 */
	public static void adicionarLinhas(File arquivo, String[] linhas) throws IOException {
		adicionarLinhas(arquivo, Arrays.asList(linhas));
	}

	/**
	 * Adiciona cada elemento da Lista <i>linhas</i> no final do arquivo.<br/>
	 * Cada elemento eh inserido em uma linha diferente, seguindo a ordem da Lista.<br/>
	 * 
	 * <br/><br/>
	 * @param caminho
	 * @param linhas
	 * @throws IOException
	 */
	public static void adicionarLinhas(String caminho, List<String> linhas) throws IOException {
		adicionarLinhas(new File(caminho), linhas);
	}

	/**
	 * Adiciona cada elemento da Lista <i>linhas</i> no final do arquivo.<br/>
	 * Cada elemento eh inserido em uma linha diferente, seguindo a ordem da Lista.<br/>
	 * 
	 * <br/><br/>
	 * @param arquivo
	 * @param linhas
	 * @throws IOException
	 */
	public static void adicionarLinhas(File arquivo, List<String> linhas) throws IOException {
		boolean pularLinha = false;
		try(Scanner s = new Scanner(arquivo)) {
			int i = 0;
			String temp = "";
			while(s.hasNext()) {
				temp = s.nextLine();
				i++;
			}
			
			if(i != 0 && temp != "")
				pularLinha = true;
		}
		
		try (PrintWriter pw = new PrintWriter(new FileWriter(arquivo, true))) {
			if(pularLinha)
				pw.println();
			for (int i = 0; i < (linhas.size() - 1); i++)
				pw.println(linhas.get(i));
			pw.print(linhas.get(linhas.size() - 1));
		}
	}

	/**
	 * Sobreescreve o conteudo do arquivo com o conteudo da String linha.<br/>
	 * Cada elemento eh inserido em uma linha diferente, seguindo a ordem do Array.<br/>
	 * 
	 * <br/><br/>
	 * @param caminho
	 * @param linhas
	 * @throws IOException
	 */
	public static void sobreescreverConteudo(String caminho, String linha) throws IOException {
		sobreescreverConteudo(new File(caminho), Collections.singletonList(linha));
	}

	
	/**
	 * Sobreescreve o conteudo do arquivo com o conteudo do Array <i>linhas</i>.<br/>
	 * Cada elemento eh inserido em uma linha diferente, seguindo a ordem do Array.<br/>
	 * 
	 * <br/><br/>
	 * @param caminho
	 * @param linhas
	 * @throws IOException
	 */
	public static void sobreescreverConteudo(String caminho, String[] linhas) throws IOException {
		sobreescreverConteudo(new File(caminho), Arrays.asList(linhas));
	}

	/**
	 * Sobreescreve o conteudo do arquivo com o conteudo do Array <i>linhas</i>.<br/>
	 * Cada elemento eh inserido em uma linha diferente, seguindo a ordem do Array.<br/>
	 * 
	 * <br/><br/>
	 * @param arquivo
	 * @param linhas
	 * @throws IOException
	 */
	public static void sobreescreverConteudo(File arquivo, String[] linhas) throws IOException {
		sobreescreverConteudo(arquivo, Arrays.asList(linhas));
	}

	/**
	 * Sobreescreve o conteudo do arquivo com o conteudo da Lista <i>linhas</i>.<br/>
	 * Cada elemento eh inserido em uma linha diferente, seguindo a ordem da Lista.<br/>
	 * 
	 * <br/><br/>
	 * @param caminho
	 * @param linhas
	 * @throws IOException
	 */
	public static void sobreescreverConteudo(String caminho, List<String> linhas) throws IOException {
		sobreescreverConteudo(new File(caminho), linhas);
	}

	/**
	 * Sobreescreve o conteudo do arquivo com o conteudo da Lista <i>linhas</i>.<br/>
	 * Cada elemento eh inserido em uma linha diferente, seguindo a ordem da Lista.<br/>
	 * 
	 * <br/><br/>
	 * @param arquivo
	 * @param linhas
	 * @throws IOException
	 */
	public static void sobreescreverConteudo(File arquivo, List<String> linhas) throws IOException {
		limpar(arquivo);
		adicionarLinhas(arquivo, linhas);
	}

	/**
	 * Apaga todas as linhas do arquivo.
	 * 
	 * <br/><br/>
	 * @param caminho
	 * @throws FileNotFoundException
	 */
	public static void limpar(String caminho) throws FileNotFoundException {
		limpar(new File(caminho));
	}

	/**
	 * Apaga todas as linhas do arquivo.
	 * 
	 * <br/><br/>
	 * @param arquivo
	 * @throws FileNotFoundException
	 */
	public static void limpar(File arquivo) throws FileNotFoundException {
		try (PrintWriter s = new PrintWriter(arquivo)) {
			s.print("");
		}
	}
}
