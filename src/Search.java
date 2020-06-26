import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;



public class Search {
	
	
	String POT = "";
	
	private List<String> extensions = new ArrayList<>();
	
	/**
	 * Metoda ponastavi List<String> extensions na new ArrayList<>()
	 */
	public void resetExtensions() {
		extensions = new ArrayList<>();
	}
	
	/**
	 * Metoda doda string v seznam List<String> extensions
	 * @param item
	 */
	public void appendExtensions(String item) {
		extensions.add(item);
	}
	
	/**
	 * funkcija pove ce se neka pot konca s koncnico, ki se nahaja v List<String> extensions
	 * in ime te datoteke se ni v mnozici znanih
	 * @param original_pot pot
	 * @return Boolean
	 */
	public Boolean vsebuje(Path original_pot, Set<String> znani) {
		
		String pot = original_pot.toString();
		String[] seznam = pot.split("\\.");
		if (extensions.contains(seznam[seznam.length -1])) {
			String[] seznam1 = pot.split("\\\\");
			Path file = Paths.get(pot);
			BasicFileAttributes attr;
			try {
				attr = Files.readAttributes(file, BasicFileAttributes.class);
				if(znani.contains(seznam1[seznam1.length -1].split("\\.")[0] + "," +attr.lastModifiedTime().toString())) {
					return false;
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * Metoda vrne mapo elementa
	 * @param pot
	 * @return
	 */
	public String potElementa(String pot) {
		
		String[] tmp = pot.split("\\\\");
		tmp = Arrays.copyOf(tmp, tmp.length-1);
		return String.join("//", tmp);
		
	}
	
	/**
	 * Metoda zacne iskanje v spremenljivki pot in nato preisce vse mape in podmape
	 * @param pot
	 * @param znani Mnozica ze znanih slik
	 * @return
	 */
	public Set<String> isci(String pot, Set<String> znani) {
		Set<String> paths = null;
		try {
			paths = Files.walk(Paths.get(pot))
					.filter(el -> vsebuje(el,znani))
					.map(el -> potElementa(el.toString()))
					.collect(Collectors.toSet());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return paths;
	}
	
	/**
	 * Metoda zacne iskanje v spremenljivki pot in nato preisce vse mape in podmape od metode 
	 * iskanje se razlikuje v tem, da namesto map, kjer se nahajajo mankajoce slike vrne posamezne slike
	 * ki jih potem uporabimo z gumbom update v nasem gui
	 * @param pot
	 * @param znani Mnozica ze znanih slik
	 * @return
	 */
	
	public Set<String> preglej(String pot, Set<String> znani) {
		Set<String> podatki = null;
		try {
			podatki = Files.walk(Paths.get(pot))
					.filter(el -> vsebuje(el,znani))
					.map(el -> podatkiSlike(el.toString()))
					.collect(Collectors.toSet());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return podatki;
	}
	
	/**
	 * Metoda iz poti doloci ime slike in datum zadnje modifikacije
	 * @param pot slike
	 * @return String ime,modifikacija
	 */
	
	public String podatkiSlike(String pot) {
		
		String[] tmp = pot.split("\\\\");
		String[] tmp1 = tmp[tmp.length-1].split("\\.");
		
		Path file = Paths.get(pot);
		BasicFileAttributes attr;
		try {
			attr = Files.readAttributes(file, BasicFileAttributes.class);
			return tmp1[0]+","+attr.lastModifiedTime().toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
}