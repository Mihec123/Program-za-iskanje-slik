import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


public class ReadWrite {
	
	/**
	 * Metoda odpre csv datoteko in iz nje vrne v mnozici imena slik
	 * @param filepath
	 * @return mnozica ki vsebuje nize ime_slike,datum_modifikacije
	 */
	public Set<String> readCSV(String filepath) {
		
		Set<String> vnosi = new HashSet<String>();
		
		BufferedReader csvReader;
		try {
			csvReader = new BufferedReader(new FileReader(filepath));
		String row;
		int first_row = 0;
		while ((row = csvReader.readLine()) != null) {
			if (first_row > 0){
				vnosi.add(row);
				
			}
			else {
				first_row = 1;
			}
			
		}
		csvReader.close();
		
		
		return vnosi;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * Metoda naredi csv datoteko ce ta se ne obstaja, ali pa ji samo pripne nove
	 * elemente
	 * @param path pot csv datoteke(ce ne obstaja jo na tej poti naredi)
	 * @param data podatki, ki jih zapisemo v csv datoteko
	 * @throws IOException
	 */
	public void writeCSV(String path, Set<String> data) throws IOException {
		
		File f = new File(path);
		FileWriter csvWriter;
		if(f.exists() && !f.isDirectory()) { 
//			System.out.println("pisemo v znan");
			csvWriter = new FileWriter(path,true);
			
			for (String el:data) {
				csvWriter.write(el);
			    csvWriter.write("\n");
			}
			csvWriter.flush();
			csvWriter.close();
			
			
			

		}
		else {
//			System.out.println("pisemo v neznan");
			csvWriter = new FileWriter(path);
			csvWriter.write("Name,Date");
			csvWriter.write("\n");
			
			for (String el:data) {
				csvWriter.write(el);
			    csvWriter.write("\n");
			}
			
			csvWriter.flush();
			csvWriter.close();


		}
	
	}


}
