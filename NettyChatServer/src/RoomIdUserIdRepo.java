import org.apache.commons.collections.map.MultiValueMap;

public class RoomIdUserIdRepo {

	private final MultiValueMap roomIdUserIdMap = new MultiValueMap();
	
	public MultiValueMap getRoomIdUserIdMap() {
		return roomIdUserIdMap;
	}
	
}

