package media.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import media.model.FileMediaMessageSocketModel;

public class FileToolkit {

	private static ArrayList<String> compatibleExtensionMedia = new ArrayList<>(
			Arrays.asList(".aif", ".aiff", ".m3u8", ".mp3", ".mp4", ".m4a", ".m4v", ".wav"));

	public static FileMediaMessageSocketModel fileToMediaFileSocket(File file) {
		FileMediaMessageSocketModel fileMedia = new FileMediaMessageSocketModel();		
		fileMedia.setPath(file.toURI().toASCIIString());
		fileMedia.setFileName(file.getName());	

		return fileMedia;
	}

	public static boolean checkIsCompatibleMedia(File file) {
		String name = file.getName();
		int lastIndexOf = name.lastIndexOf(".");
		if (lastIndexOf == -1) {
			return false;
		}
		String extension = name.substring(lastIndexOf);
		System.out.println("Extension:" + extension);
		if (compatibleExtensionMedia.contains(extension)) {
			return true;
		}
		return false;
	}

	public static File[] listMediaFiles() {
		File mediaFilePath = new File(System.getProperty("user.dir") + "/Media Files/");

		if (mediaFilePath.isDirectory()) {
			File[] mediaFiles = mediaFilePath.listFiles();

			return mediaFiles;
		}

		return new File[0];
	}

}
