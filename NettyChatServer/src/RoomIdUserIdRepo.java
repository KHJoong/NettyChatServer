import org.apache.commons.collections.map.MultiValueMap;

// room�� ����ִ� user�� id�� �����صα� ���� Ŭ�����Դϴ�.
// �� room�� ���� ���� user�� �� ���� �� �����Ƿ� multivalumap�� ����մϴ�.
public class RoomIdUserIdRepo {

	private final MultiValueMap roomIdUserIdMap = new MultiValueMap();
	
	public MultiValueMap getRoomIdUserIdMap() {
		return roomIdUserIdMap;
	}
	
}

