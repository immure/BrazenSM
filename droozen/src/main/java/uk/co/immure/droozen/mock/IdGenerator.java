package uk.co.immure.droozen.mock;

public class IdGenerator {
	
	private static long id = 1;
	private final static byte MIN_LEN=8;
	
	public String getId() {
		String id = Long.toString(_getId());
		StringBuilder sb = new StringBuilder();
		for (int x = id.length(); x < MIN_LEN; x++) {
			sb.append("0");
		}
		sb.append(id);
		return sb.toString();
	}
	
	private synchronized long _getId() {
		return id++;
	}

}
