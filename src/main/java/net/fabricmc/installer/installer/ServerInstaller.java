package net.fabricmc.installer.installer;

import com.openmodloader.util.Version;
import com.openmodloader.util.VersionSpec;
import net.fabricmc.installer.Main;
import net.fabricmc.installer.util.IInstallerProgress;
import net.fabricmc.installer.util.Translator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ServerInstaller {


	public static void install(File mcDir, String version, IInstallerProgress progress) throws IOException {
		File fabricJar = new File(mcDir, "OpenModLoader-" + version + ".jar");
		if (fabricJar.exists()) {
			fabricJar.delete();
		}

		progress.updateProgress(Translator.getString("install.server.downloadOML"), 5);
		FileUtils.copyURLToFile(new URL("http://maven.modmuss50.me/com/openmodloader/OpenModLoader/" + version + "/OpenModLoader-" + version + ".jar"), fabricJar);
		install(mcDir, version, progress, fabricJar);
	}

	public static void install(File mcDir, String version, IInstallerProgress progress, File fabricJar) throws IOException {
		progress.updateProgress(Translator.getString("gui.installing") + ": " + version, 0);
		String[] split = version.split("-");
		String mcVer = split[0];

		File mcJar = new File(mcDir, "minecraft_server." + mcVer + ".jar");

		if(!mcJar.exists()){
			progress.updateProgress(Translator.getString("install.server.downloadServer"), 10);

			VersionSpec spec = null;

			for(Version v : Main.LAUNCH_META.getVersions()) {
				if(v.getId().equals(mcVer)) {
					String url = v.getUrl();
					String json = Main.readUrl(url);
					spec=Main.GSON.fromJson(json, VersionSpec.class);
					break;
				}
			}
			if(spec==null) {
				progress.error(Translator.getString("install.server.failedVSpec"));
				return;
			}

			FileUtils.copyURLToFile(new URL(spec.getDownloads().getServer().getUrl()), mcJar);
		}

		File libs = new File(mcDir, "libs");

		ZipFile fabricZip = new ZipFile(fabricJar);
		ZipEntry dependenciesEntry = fabricZip.getEntry("dependencies_server.txt");
		List<String> fabricDeps = IOUtils.readLines(fabricZip.getInputStream(dependenciesEntry), Charset.defaultCharset());
		for(String dep : fabricDeps){
			String[] depSplit = dep.split("/");
			File depFile = new File(libs, depSplit[depSplit.length -1]);
			if(depFile.exists()){
				depFile.delete();
			}
			progress.updateProgress("Downloading " + depFile.getName(), 20);
			FileUtils.copyURLToFile(new URL(dep), depFile);
		}
		
		progress.updateProgress(Translator.getString("install.success"), 100);
	}

}
