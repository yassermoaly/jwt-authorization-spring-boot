package demo.project.utility.interfaces;

public interface Serializer {
	public String serialize(Object obj);	
	public <T> T deserialize(String data);
}
