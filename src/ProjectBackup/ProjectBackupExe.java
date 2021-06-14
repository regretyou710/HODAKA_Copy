package ProjectBackup;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.google.gson.Gson;

import ProjectBackup.tw.com.bean.Backup;
import ProjectBackup.tw.com.bean.Debug;

public class ProjectBackupExe {
	private static List<String> debugList;
	private static Logger logger = LogManager.getLogger("RollingRandomAccessFileLogger");

	public static void main(String[] args) {

		String BackupJson = "ProjectBackup/Backup.json";
		InputStreamReader isr = null;
		BufferedReader br = null;

		try {
			InputStream is = ProjectBackupExe.class.getClassLoader().getResourceAsStream(BackupJson);
			isr = new InputStreamReader(is, "utf-8");
			br = new BufferedReader(isr);

			// 透過gson對json文件讀取
			Gson gson = new Gson();
			Backup backup = gson.fromJson(br, Backup.class);
			Debug debug = backup.getDebug();
			debugList = debug.getFilterData();

			File srcFile = new File(debug.getSrcFile());
			if (!srcFile.exists())
				throw new IOException("來源路徑不存在!");

			File destFile = new File(debug.getDestFile());
			if (!destFile.exists())
				destFile.mkdir();

			File[] srcFileList = srcFile.listFiles();

			for (File f : srcFileList)
				copy(f, destFile);

			logger.info("複製檔案成功!");

		} catch (Exception e) {
			logger.error("出現異常:", e);
			// e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					logger.error("關閉IO流出現異常:", e);
				}
			}
		}
	}

	/**
	 * 複製
	 * 
	 * @param srcFile
	 * @param destFile
	 * @param datas
	 * @throws IOException
	 */
	private static void copy(File srcFile, File destFile) throws Exception {
		// 判斷是否為目錄，是則在目標中創建同名目錄然後調用自己來判斷下一層中是否有目錄
		if (srcFile.isDirectory()) {
			if (!accept(debugList, srcFile)) {
				File newFolder = new File(destFile, srcFile.getName());
				newFolder.mkdirs();
				File[] Array = srcFile.listFiles();

				for (File file : Array)
					copy(file, newFolder);
			}
		} else {
			// 判斷是否為要過濾的類型，是則不做任何事，不是就執行複製
			if (!accept(debugList, srcFile)) {
				File newFile = new File(destFile, srcFile.getName());
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcFile));
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(newFile));

				byte[] bbuff = new byte[1024];
				int len = 0;
				while ((len = bis.read(bbuff)) != -1) {
					bos.write(bbuff, 0, len);
				}

				bos.close();
				bis.close();
			}
		}

	}

	/**
	 * 過濾
	 * 
	 * @param pathname
	 * @param srcFile
	 * @return
	 */
	private static boolean accept(List<String> pathNameList, File srcFile) {
		boolean rs = false;

		for (int i = 0; i < pathNameList.size(); i++) {
			logger.debug(srcFile.getAbsolutePath());
			rs |= srcFile.getAbsolutePath().contains(pathNameList.get(i));
		}

		return rs;
	}
}
