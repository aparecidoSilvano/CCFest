import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import controllers.Login;

public class LoginTest {
	private static Login login;
	
	@Before
	private void inicia(){
		login = new Login();
	}
	
	@Test
	public static void naoDeveLogar() {
		/*
		 * não deve logar por uma serie de motivos:
		 * 
		 * -não haver usuario cadastrados;
		 * -email incorreto;
		 * -senha incorreta;
		 * -email e senha incorretos.
		 */
		
		
	}

	@Test
	public static void deveLogar() {

	}
}
