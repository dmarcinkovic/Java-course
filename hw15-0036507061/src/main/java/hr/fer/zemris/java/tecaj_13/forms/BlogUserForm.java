package hr.fer.zemris.java.tecaj_13.forms;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * <p>Model formulara koji odgovara web-reprezentaciji domenskog objekta {@link Record}.
 * U domenskom modelu, različita svojstva su različitih tipova; primjerice, {@link Record#getId()}
 * je tipa {@link Long}. U formularu, sva su svojstva stringovi jer se tako prenose preko HTTP-a
 * i čuvaju u web-stranici u formularima.</p>
 * 
 * <p>Za svako svojstvo, mapa {@link #greske} omogućava da se pri validaciji (metoda {@link #validiraj()}) upiše 
 * je li došlo do pogreške u podatcima. Formular nudi sljedeće funkcionalnosti.</p>
 * 
 * <ol>
 * <li>Punjenje iz trenutnog zahtjeva metodom {@link #popuniIzHttpRequesta(HttpServletRequest)}. Čita parametre
 *     i upisuje odgovarajuća svojstva u formular.</li>
 * <li>Punjenje iz domenskog objekta metodom {@link #popuniIzRecorda(Record)}. Prima {@link Record} kao argument
 *     i temeljem toga što je u njemu upisano popunjava ovaj formular.</li>
 * <li>Punjenje domenskog objekta temeljem upisanog sadržaja u formularu metodom {@link #popuniURecord(Record)}.
 *     Ideja je da se ovo radi tek ako su podatci u formularu prošli validaciju. Pogledajte pojedine servlete koji
 *     su pripremljeni uz ovaj primjer za demonstraciju kako se to radi.</li>
 * </ol>
 * 
 * @author marcupic
 */
public class BlogUserForm {
	private String id;
	private String firstName;
	private String lastName;
	private String nick;
	private String email;
	private String passwordHash;

	/**
	 * Mapa s pogreškama. Očekuje se da su ključevi nazivi svojstava a vrijednosti
	 * tekstovi pogrešaka.
	 */
	Map<String, String> greske = new HashMap<>();
	
	/**
	 * Konstruktor.
	 */
	public BlogUserForm() {
	}
	
	public void setGreske(String errorName, String error) {
		greske.put(errorName, error);
	}
	
	/**
	 * Dohvaća poruku pogreške za traženo svojstvo.
	 * 
	 * @param ime naziv svojstva za koje se traži poruka pogreške
	 * @return poruku pogreške ili <code>null</code> ako svojstvo nema pridruženu pogrešku
	 */
	public String dohvatiPogresku(String ime) {
		return greske.get(ime);
	}
	
	/**
	 * Provjera ima li barem jedno od svojstava pridruženu pogrešku.
	 * 
	 * @return <code>true</code> ako ima, <code>false</code> inače.
	 */
	public boolean imaPogresaka() {
		return !greske.isEmpty();
	}
	
	/**
	 * Provjerava ima li traženo svojstvo pridruženu pogrešku. 
	 * 
	 * @param ime naziv svojstva za koje se ispituje postojanje pogreške
	 * @return <code>true</code> ako ima, <code>false</code> inače.
	 */
	public boolean imaPogresku(String firstName) {
		return greske.containsKey(firstName);
	}
	
	@Override
	public String toString() {
		return "BlogUserForm [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", nick=" + nick
				+ ", email=" + email + ", passwordHash=" + passwordHash + "]";
	}

	/**
	 * Na temelju parametara primljenih kroz {@link HttpServletRequest} popunjava
	 * svojstva ovog formulara.
	 * 
	 * @param req objekt s parametrima
	 */
	public void popuniIzHttpRequesta(HttpServletRequest req) {
		this.firstName = pripremi(req.getParameter("firstName"));
		this.lastName = pripremi(req.getParameter("lastName"));
		this.email = pripremi(req.getParameter("email"));
		
		this.passwordHash = getHashedPassword(req.getParameter("passwordHash"));
		this.nick = pripremi(req.getParameter("nick"));
	}

	private String getHashedPassword(String password) {
		if (password == null || password.isEmpty()) {
			return "";
		}
		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			byte[] array = password.getBytes();
			byte[] hash = sha.digest(array);
			
			return bytetohex(hash).trim();
		} catch (NoSuchAlgorithmException e) {
		}
		return null; 
	}
	

	/**
	 * Method that converts hexadecimal byte array to String.
	 * 
	 * @param byteArray byte array to be converted.
	 * @return Hexadecimal representation of given byte array.
	 * @throws NullPointerException if byteArray is null.
	 */
	private  String bytetohex(byte[] byteArray) {
		if (byteArray == null) {
			throw new NullPointerException();
		}

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < byteArray.length; i++) {
			String hex = Integer.toHexString(0xff & byteArray[i]);

			if (hex.length() == 1) {
				sb.append("0");
			}
			sb.append(hex);
		}

		return sb.toString();
	}
	
	/**
	 * Na temelju predanog {@link Record}-a popunjava ovaj formular.
	 * 
	 * @param r objekt koji čuva originalne podatke
	 */
	public void popuniIzRecorda(BlogUser blogUser) {
		this.firstName = blogUser.getFirstName();
		this.lastName = blogUser.getLastName();
		this.email = blogUser.getEmail();
		this.nick = blogUser.getNick();
		this.passwordHash = blogUser.getPasswordHash();
	}

	/**
	 * Temeljem sadržaja ovog formulara puni svojstva predanog domenskog
	 * objekta. Metodu ne bi trebalo pozivati ako formular prethodno nije
	 * validiran i ako nije utvrđeno da nema pogrešaka.
	 * 
	 * @param r domenski objekt koji treba napuniti
	 */
	public void popuniURecord(BlogUser blogUser) {
		blogUser.setFirstName(this.firstName);
		blogUser.setLastName(this.lastName);
		blogUser.setEmail(this.email);
		blogUser.setPasswordHash(this.passwordHash);
		blogUser.setNick(this.nick);
	}
	
	public void validateLogin() { 
		greske.clear();
		if (this.passwordHash.isEmpty()) {
			greske.put("passwordHash", "Password is required!");
		}
		
		if (this.nick.isEmpty()) {
			greske.put("nick", "Nick is required");
		}
	}
	
	/**
	 * Metoda obavlja validaciju formulara. Formular je prethodno na neki način potrebno
	 * napuniti. Metoda provjerava semantičku korektnost svih podataka te po potrebi
	 * registrira pogreške u mapu pogrešaka.
	 */
	public void validateRegistration() {
		validateLogin();
		
		if(this.firstName.isEmpty()) {
			greske.put("firstName", "First name is required!");
		}
		
		if(this.lastName.isEmpty()) {
			greske.put("lastName", "Last name is required!");
		}

		if(this.email.isEmpty()) {
			greske.put("email", "EMail is required!");
		} else {
			int l = email.length();
			int p = email.indexOf('@');
			if(l<3 || p==-1 || p==0 || p==l-1) {
				greske.put("email", "EMail is not in the correct format.");
			}
		}
	}
	
	/**
	 * Pomoćna metoda koja <code>null</code> stringove konvertira u prazne stringove, što je
	 * puno pogodnije za uporabu na webu.
	 * 
	 * @param s string
	 * @return primljeni string ako je različit od <code>null</code>, prazan string inače.
	 */
	private String pripremi(String s) {
		if(s==null) return "";
		return s.trim();
	}

	/**
	 * Dohvat id-a.
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Setter za id. 
	 * @param id vrijednost na koju ga treba postaviti.
	 */
	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
}
