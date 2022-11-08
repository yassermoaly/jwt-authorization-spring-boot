package demo.project.utility.imp;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import demo.project.utility.interfaces.Base64EncodeDecoder;
import demo.project.utility.interfaces.Hashing;

@Service
@PropertySource("classpath:hashing.properties")
public class HashingImp implements Hashing {
	
	@Autowired
	Base64EncodeDecoder base64EncodeDecoder;
	
	@Value("${hasing.algorithm}")
	private String _algorithm;
	
	@Value("${hasing.salt}")
	private String _salt;
	
	@Value("${hasing.splitter}")
	private String _splitter;


	private static String algorithm;
	private static String salt;
	private static String splitter;

	@PostConstruct
	private void initProperties()  {
		algorithm = _algorithm;
		salt = _salt;
		splitter=_splitter;
	}
	
	private static String create(String text) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance(algorithm);
			String fullText  = salt + text ;
			byte[] hash = digest.digest(fullText.getBytes(StandardCharsets.UTF_8));
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < hash.length; i++) {
	            sb.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
	        }
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {			
			throw new RuntimeException("Hashing algorithm "+algorithm+" is not supported");
		}		
	}
	private static Boolean verify(String text,String hash) {
		return create(text).equals(hash);
	}	
	
	public String Sign(String text) {
		String Hash = create(text);		
		return base64EncodeDecoder.encode(Hash+splitter+text);
	}
	
	public String verify(String text) {
		String value = base64EncodeDecoder.decode(text);
		int splitterIndex = value.lastIndexOf(splitter);
		String hash = value.substring(0, splitterIndex);
		String message = value.substring(splitterIndex+splitter.length());
		if(verify(message,hash))
			return message;
		
		throw new RuntimeException("Invalid Signature");
	}
}
