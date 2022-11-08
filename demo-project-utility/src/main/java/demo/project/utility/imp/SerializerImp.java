package demo.project.utility.imp;




import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;


import org.springframework.stereotype.Service;

import demo.project.utility.interfaces.Serializer;
@Service
public class SerializerImp implements Serializer {

	public String serialize(Object data) {
		try (ByteArrayOutputStream b = new ByteArrayOutputStream()) {
			try (ObjectOutputStream o = new ObjectOutputStream(b)) {
				o.writeObject(data);
			}
			return Base64.getEncoder().encodeToString(b.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> T deserialize(String data) {
		byte[] bytes = Base64.getDecoder().decode(data.getBytes());
		try (ByteArrayInputStream b = new ByteArrayInputStream(bytes)) {
			try (ObjectInputStream o = new ObjectInputStream(b)) {
				return (T) o.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e.getMessage());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
		

}
