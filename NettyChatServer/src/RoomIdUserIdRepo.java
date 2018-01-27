import org.apache.commons.collections.map.MultiValueMap;

// room에 들어있는 user의 id를 저장해두기 위한 클래스입니다.
// 한 room에 여러 명의 user가 들어가 있을 수 있으므로 multivalumap을 사용합니다.
public class RoomIdUserIdRepo {

	private final MultiValueMap roomIdUserIdMap = new MultiValueMap();
	
	public MultiValueMap getRoomIdUserIdMap() {
		return roomIdUserIdMap;
	}
	
}

