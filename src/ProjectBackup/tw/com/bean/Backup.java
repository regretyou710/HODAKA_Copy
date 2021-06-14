package ProjectBackup.tw.com.bean;

import java.io.Serializable;

public class Backup implements Serializable {
	private static final long serialVersionUID = -5449101793914271740L;
	private Debug debug;

	public Backup() {
		super();
	}

	public Debug getDebug() {
		return debug;
	}

	public void setDebug(Debug debug) {
		this.debug = debug;
	}

}
