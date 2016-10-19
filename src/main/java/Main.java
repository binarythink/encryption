import io.kindler.encryption.AES256;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;

/**
 * Created by kyj on 2016-10-19.
 */
public class Main {
	public static void main(String[] args) {
		printParameters(args);

		String key = args[0];
		String value = args[1];

		AES256 encryption = null;
		String encrypted = "";
		String decrypted = "";

		try {
			// 암호화 객체 생성
			encryption = new AES256.Builder(key)
					.keyEncoding(StandardCharsets.UTF_8)
					.build();

			// encrypt & decrypt
			encrypted = encryption.encrypt(value);
			decrypted = encryption.decrypt(encrypted);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NullPointerException ne) {
			ne.printStackTrace();
		}

		// print result
		System.out.println("\n:::::::::::::: Outputs ::::::::::::::");
		System.out.printf("encrypt : %s\n", encrypted);
		System.out.printf("decrypt : %s\n", decrypted);
	}

	private static void printParameters(String... args) {
		if (args.length < 2) {
			printUsage();
			return;
		}

		System.out.println("\n:::::::::::::: Inputs ::::::::::::::");
		System.out.printf("encrypt key[%d] : %s\n", args[0].length(), args[0]);
		System.out.printf("plan text : %s\n", args[1]);
	}

	public static void printUsage() {
		System.out.println("\n:::::::::::::: Usage ::::::::::::::");
		System.out.println("$ java -jar encrytion.jar encryptKey value");
		System.out.println("* encrypt key has more than 16 digits.");
		System.out.println();
	}
}
