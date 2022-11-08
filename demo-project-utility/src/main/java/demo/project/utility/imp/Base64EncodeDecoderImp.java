package demo.project.utility.imp;
import demo.project.utility.interfaces.Base64EncodeDecoder;
import java.util.Base64;

import org.springframework.stereotype.Service;

@Service
public class Base64EncodeDecoderImp implements Base64EncodeDecoder {
	public String encode(String textToEncode) {
		return Base64.getEncoder().encodeToString(textToEncode.getBytes());
	}

	public String decode(String textToDecode) {
		return new String(Base64.getDecoder().decode(textToDecode));
	}	
}