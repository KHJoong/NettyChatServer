import org.apache.commons.collections.map.MultiValueMap;

public class RoomIdUserIdRepo {

	private final MultiValueMap roomIdUserIdMap = new MultiValueMap();
	
	public MultiValueMap getRoomIdUserIdMap() {
		return roomIdUserIdMap;
	}
	
}


//import java.util.HashMap;
//import java.util.Map;
//
//public class RoomIdUserIdRepo {
//	
//	private final Map<String, String> roomIdUserIdMap = new HashMap<>();
//	
//	public Map<String, String> getRoomIdUserIdMap() {
//		return roomIdUserIdMap;
//	}
//	
//}
