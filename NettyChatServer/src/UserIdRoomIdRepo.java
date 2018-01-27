import java.util.HashMap;
import java.util.Map;

// 어떤 user가 들어가 있는 room이 어디인지 담아두기 위한 클래스입니다.
// 코드 정리 시 삭제될 가능성이 높은 부분입니다.
// 없어도 지장 없을 것 같습니다.
public class UserIdRoomIdRepo {
	
	private final Map<String, String> userIdRoomIdMap = new HashMap<>();
	
	public Map<String, String> getUserIdRoomIdMap() {
		return userIdRoomIdMap;
	}
	
}
