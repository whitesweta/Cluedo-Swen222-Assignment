package cluedo.other;

public class Character implements Item {

	public enum CharacterType implements Type {
		MISS_SCARLET, COLONEL_MUSTARD, MRS_WHITE, REVEREND_GREEN, MRS_PEACOCK, PROFESSOR_PLUM
	};

	private CharacterType type;

	public Character(CharacterType n) {
		type = n;
	}

	public Type getType() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Character other = (Character) obj;
		if (type != other.type)
			return false;
		return true;
	}
}
