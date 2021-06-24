package Copy.tw.com.bean;

import java.io.Serializable;
import java.util.List;

public class CopyList implements Serializable {
	private static final long serialVersionUID = -5449101793914271740L;
	private List<Copy> copyList;

	public CopyList() {
		super();
	}

	public List<Copy> getCopyList() {
		return copyList;
	}

	public void setCopyList(List<Copy> copyList) {
		this.copyList = copyList;
	}

	@Override
	public String toString() {
		return "CopyList [copyList=" + copyList + "]";
	}

}
