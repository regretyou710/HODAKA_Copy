package ProjectBackup;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ProjectBackup.tw.com.util.ProjectBackupUtil;
import ProjectBackup.tw.com.bean.Backup;

public class ProjectBackupExe {
	private static Logger logger = LogManager.getLogger("RollingRandomAccessFileLogger");

	public static void main(String[] args) {

		try {
			List<Backup> backupList = ProjectBackupUtil.getBackupList();

			for (Backup item : backupList) {
				double startTime = System.currentTimeMillis();
				ProjectBackupUtil.setFilterData(item.getFilterData());

				File srcFile = new File(item.getSrcFile());
				if (!srcFile.exists())
					throw new IOException("來源路徑不存在!");

				File destFile = new File(item.getDestFile());
				if (!destFile.exists())
					destFile.mkdir();

				File[] srcFileList = srcFile.listFiles();

				for (File f : srcFileList)
					ProjectBackupUtil.copy(f, destFile);

				double endTime = System.currentTimeMillis();
				logger.info("{}複製檔案成功!花費時間{}秒", item.getSrcFile(), (endTime - startTime) / 1000);
			}
		} catch (Exception e) {
			logger.error("出現異常:", e);
			// e.printStackTrace();
		}
	}

}
