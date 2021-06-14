package ProjectBackup.tw.com.bean;

import java.io.Serializable;
import java.util.List;

public class BackupList implements Serializable {
	private static final long serialVersionUID = -5449101793914271740L;
	private List<Backup> backupList;

	public BackupList() {
		super();
	}

	public List<Backup> getBackupList() {
		return backupList;
	}

	public void setBackupList(List<Backup> backupList) {
		this.backupList = backupList;
	}

	@Override
	public String toString() {
		return "BackupList [backupList=" + backupList + "]";
	}

}
