import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;

import org.junit.Test;
import org.leolo.album.Utils;

public class Test1 {

	@Test
	public void passwordHash() throws Exception {
		String password = "qwertyuiop";
		String hash = Utils.hashPassword(password);
		if(!Utils.verifyPassword(password, hash))
			fail("Match PWD");
		if(Utils.verifyPassword("QWERTYUIOP", hash))
			fail("Misatch PWD");
		if(hash.equals(Utils.hashPassword(password)))
			fail("Same hash upon rehash");
	}

}
