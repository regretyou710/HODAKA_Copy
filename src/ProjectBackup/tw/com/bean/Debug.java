package ProjectBackup.tw.com.bean;

import java.io.Serializable;
import java.util.List;

public class Debug implements Serializable {
	private static final long serialVersionUID = 2144402072264945926L;
	private String srcFile;// 資料來源
	private String destFile;// 資料目地
	private List<String> filterData;// 過濾資料

	public Debug() {
		super();
	}

	public List<String> getFilterData() {
		return filterData;
	}

	public void setFilterData(List<String> filterData) {
		this.filterData = filterData;
	}

	public String getSrcFile() {
		return srcFile;
	}

	public void setSrcFile(String srcFile) {
		this.srcFile = srcFile;
	}

	public String getDestFile() {
		return destFile;
	}

	public void setDestFile(String destFile) {
		this.destFile = destFile;
	}

	@Override
	public String toString() {
		return "Debug [srcFile=" + srcFile + ", destFile=" + destFile + ", filterData=" + filterData + "]";
	}

}
