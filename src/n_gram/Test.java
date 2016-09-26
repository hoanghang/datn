package n_gram;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Test {

/**
 * ngram algorithm: split string to word and remove regex 
 * @param n 	number for split string
 * @param str 	String input 
 * @return list word
 */
	public List<String> ngrams(int n, String str) {
		str = str.replaceAll("[-+.^:,!?\"0-9]","");
		List<String> ngrams = new ArrayList<String>();
		String[] words = str.split(" ");
		for (int i = 0; i < words.length - n + 1; i++) {
			ngrams.add(concat(words, i, i + n));
		}
		return ngrams;
	}

	public String concat(String[] words, int start, int end) {
		StringBuilder sb = new StringBuilder();
		for (int i = start; i < end; i++)
			sb.append((i > start ? " " : "") + words[i]);
		return sb.toString();
	}
/**
* remove stop words in comments
* @param ngram word basic
* @return list word removed stop words
*/	
	public List<String> removedStopword(List<String> ngram) {
		List<String> stopWords = new ArrayList<String>();
		List<String> strs = new ArrayList<String>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(
					"C:\\Users\\HoangHangPC\\Desktop\\stopword.txt"));
			String s;
			while ((s = in.readLine()) != null) {
				stopWords.add(s.trim());
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		for(String s : ngram){	
			String temp = s.toLowerCase().trim();
			if(!stopWords.contains(temp)){
				strs.add(temp);
			}
		}
		return strs;
	}
	
	public Map<String, Integer> countW(List<String> words){
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (String temp : words) {
			Integer count = map.get(temp);
			map.put(temp, (count == null) ? 1 : count + 1);
		}
		Map<String, Integer> treeMap = new TreeMap<String, Integer>(map);
		return treeMap;
	}

	public static void main(String[] args) {
		Test t =new Test();
		File file = new File("C:\\Users\\HoangHangPC\\Desktop\\searchterms.txt");
		try {
			String contents = new Scanner(file).useDelimiter("\\Z").next();
			List<String> strs = t.ngrams(1, contents);
			List<String> stopW = t.removedStopword(strs);
			
			for(int i = 0; i<stopW.size(); i++){
				stopW.get(i).trim();
				if(stopW.get(i).equals("")){
					stopW.remove(i);
				}
			}
			System.out.println(stopW);
			Map<String, Integer> map = t.countW(stopW);
			printMap(map);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static void printMap(Map<String, Integer> map) {
		int value = 0;
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			value += entry.getValue();
		}
		System.out.println("value: " + value);
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			System.out.println(entry.getKey() + " ------------ "+ (double)entry.getValue()/value);
		}
	}
}
