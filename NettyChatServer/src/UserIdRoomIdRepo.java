import java.util.HashMap;
import java.util.Map;

// � user�� �� �ִ� room�� ������� ��Ƶα� ���� Ŭ�����Դϴ�.
// �ڵ� ���� �� ������ ���ɼ��� ���� �κ��Դϴ�.
// ��� ���� ���� �� �����ϴ�.
public class UserIdRoomIdRepo {
	
	private final Map<String, String> userIdRoomIdMap = new HashMap<>();
	
	public Map<String, String> getUserIdRoomIdMap() {
		return userIdRoomIdMap;
	}
	
}
