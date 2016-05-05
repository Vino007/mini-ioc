package com.vino.miniioc.utils;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.JarFile;

public class PackageUtil {

	public static List<String>getClassNameByPackageName(String packageName,boolean isRecursion){
		List<String> classNames=null;
		String packagePath=packageName.replace(".", "/");//ת�����ļ�·��
		URL url=ClassLoader.getSystemResource(packagePath);
		if(url!=null){
			String protocol=url.getProtocol();
			if("file".equals(protocol)){
				classNames=getClassNameFromFileSystem(url.getPath(),packageName,isRecursion);
			}else if("jar".equals(protocol)){
				JarFile jarFile=null;
				try {
					jarFile=((JarURLConnection)url.openConnection()).getJarFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(jarFile!=null){
					//getClassNameFromJar();
				}
			}
		}else{
			throw new RuntimeException("basePackage���ô���");
		}
		return classNames;
	}
	public static List<String> getClassNameFromFileSystem(String filePath,String packageName,boolean isRecursion){
		filePath=filePath.replace("%20", " ");//Ҫ��·���е�%20ת���" "
		List<String> classNames=new ArrayList<>();
		File file=new File(filePath);
		File[] files=file.listFiles();
		for(File childFile:files){
			if(childFile.isDirectory()){
				//�ݹ����
				if(isRecursion){
					classNames.addAll(getClassNameFromFileSystem(childFile.getPath(), packageName+"."
							+childFile.getName(), isRecursion));
				}
			}else{
				String fileName=childFile.getName();
				if(fileName.endsWith(".class")&&!fileName.contains("$")){//�޳��ڲ���
					classNames.add(packageName+"."+fileName.replace(".class", ""));
				}
			}
		}
		return classNames;
	}
}
