package ProjectBackup.tw.com.bean;

import java.io.Serializable;
import java.util.List;

public class BackupList implements Serializable {
	private static final long serialVersionUID = -5449101793914271740L;
	private List<Backup> backup;

	public BackupList() {
		super();
	}

	public List<Backup> getBackup() {
		return backup;
	}

	public void setBackup(List<Backup> backup) {
		this.backup = backup;
	}

	@Override
	public String toString() {
		return "BackupList [backup=" + backup + "]";
	}

}
