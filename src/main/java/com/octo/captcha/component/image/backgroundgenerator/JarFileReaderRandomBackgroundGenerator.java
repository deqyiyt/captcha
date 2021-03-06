package com.octo.captcha.component.image.backgroundgenerator;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.imageio.ImageIO;

import com.octo.captcha.CaptchaException;

/**
 *********************************************** 
 * @Create Date: 2015年10月30日 下午9:04:22
 * @Create Author: hujiuzhou
 * @Function: 重写后读取jar包中的图片背景
 ************************************************* 
 */
public class JarFileReaderRandomBackgroundGenerator extends
        AbstractBackgroundGenerator {

	private List<BufferedImage> images = new ArrayList<BufferedImage>();
    private String rootPath = ".";

    public JarFileReaderRandomBackgroundGenerator(Integer width, Integer height, String rootPath) {
        super(width, height);
        //this.images=images;
        if (rootPath != null)
            this.rootPath = rootPath;
        findDirectory(this.rootPath);
    }

    /**
     *
     */
    protected static Map<String, File> cachedDirectories = new HashMap<String, File>();

    protected void findDirectory(String rootPath) {
        if (cachedDirectories.containsKey(rootPath)) {
            cachedDirectories.get(rootPath);
        }

        //try direct path
        File dir = new File(rootPath);
        StringBuffer triedPath = new StringBuffer();
        appendFilePath(triedPath, dir);
        if (!dir.canRead() || !dir.isDirectory()) {
            //try with . parent
            dir = new File(".", rootPath);
            appendFilePath(triedPath, dir);
            if (!dir.canRead() || !dir.isDirectory()) {
                //try with / parent
                dir = new File("/", rootPath);
                appendFilePath(triedPath, dir);

                if (!dir.canRead() || !dir.isDirectory()) {
                    //trying with ressource
                    URL url = JarFileReaderRandomBackgroundGenerator.class.getClassLoader().getResource(rootPath);
                    if (url != null) {
                        dir = new File(url.getFile());
                        appendFilePath(triedPath, dir);

                    } else {
                        //trying the class path
                        url = ClassLoader.getSystemClassLoader().getResource(rootPath);

                        if (url != null) {
                            dir = new File(url.getFile());
                            appendFilePath(triedPath, dir);

                        }
                    }
                }
            }
        }
        
        String filePath = dir.getPath();
        
        if (!dir.canRead() || !dir.isDirectory()) {
        	// dir is still no good -- let's try directories in the system classpath
            StringTokenizer token = getClasspathFromSystemProperty();
            while (token.hasMoreElements()) {
                String path = token.nextToken();
                if (!path.endsWith(".jar")) {
                    dir = new File(path, rootPath);
                    appendFilePath(triedPath, dir);
                    if (dir.canRead() && dir.isDirectory()) {
                        break;
                    }
                }
            }
        }
        
    	if(filePath.startsWith("file:") && filePath.indexOf(".jar!") > -1) {
    		
    		String jarPath = "";
    		String os = System.getProperty("os.name");  
    		if(os.toLowerCase().startsWith("win")){  
    			jarPath = filePath.substring(6, filePath.indexOf("!"));
    		} else {
    			jarPath = filePath.substring(5, filePath.indexOf("!"));
    		}
    		// 创建JAR文件对象 
			try (JarFile jarFile = new JarFile(jarPath)){
				// 枚举获得JAR文件内的实体,即相对路径
				Enumeration<JarEntry> en = jarFile.entries(); 
				while (en.hasMoreElements()) { // 遍历显示JAR文件中的内容信息  
		        	JarEntry jfn = en.nextElement();
		        	if(jfn.getName().startsWith(filePath.substring(filePath.indexOf("!")+2)) && !jfn.getName().endsWith("/")) {
			        	
			            try (InputStream is = jarFile.getInputStream(jfn)){
				        	BufferedImage bufferedImage = ImageIO.read(is);
				        	images.add(tile(bufferedImage));
			                // Return the format name
			            } catch (IOException e) {
			                throw new CaptchaException("Unknown error during file reading ", e);
			            }
		        	}
		        }
			} catch (IOException e) {
				e.printStackTrace();
			}  
            
    	} else if (!dir.canRead() || !dir.isDirectory()) {
            throw new CaptchaException("All tried paths :'" + triedPath.toString() + "' is not" +
                    " a directory or cannot be read");
        } else {
        	File[] files = dir.listFiles();

            //get all jpeg
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    File file = files[i];
                    BufferedImage out = null;
                    if (file.isFile()) {
                        out = getImage(file);
                    }
                    if (out != null) {
                        images.add(images.size(), out);
                    }
                }


                if (images.size() != 0) {
                    for (int i = 0; i < images.size(); i++) {
                        BufferedImage bufferedImage = (BufferedImage) images.get(i);
                        images.set(i, tile(bufferedImage));
                    }
                } else {
                    throw new CaptchaException("Root path directory is valid but " +
                            "does not contains any image (jpg) files");
                }
            }
        }
        
        // cache answer for later
        cachedDirectories.put(rootPath, dir);
    }

    private StringTokenizer getClasspathFromSystemProperty() {
        String classpath;

        classpath = System.getProperty("java.class.path");
        StringTokenizer token = new StringTokenizer(classpath, File.pathSeparator);
        return token;
    }


    private void appendFilePath(StringBuffer triedPath, File dir) {
        triedPath.append(dir.getAbsolutePath());
        triedPath.append("\n");
    }

    private BufferedImage tile(BufferedImage tileImage) {
        BufferedImage image = new BufferedImage(getImageWidth(),
                getImageHeight(), tileImage.getType());
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        int NumberX = (getImageWidth() / tileImage.getWidth());
        int NumberY = (getImageHeight() / tileImage.getHeight());
        for (int k = 0; k <= NumberY; k++) {
            for (int l = 0; l <= NumberX; l++) {
                g2.drawImage(tileImage, l * tileImage.getWidth(), k *
                        tileImage.getHeight(),
                        Math.min(tileImage.getWidth(), getImageWidth()),
                        Math.min(tileImage.getHeight(), getImageHeight()),
                        null);
            }
        }
        g2.dispose();
        return image;
    }

    private static BufferedImage getImage(File o) {
        BufferedImage out = null;
        try {

            //            ImageInfo info = new ImageInfo();
            //            Image image = ToolkitFactory.getToolkit().createImage(o.toString());
            //            info.setInput(new FileInputStream(o));
            //            out = new BufferedImage(info.getWidth(), info.getHeight(),BufferedImage.TYPE_INT_RGB );
            //            out.getGraphics().drawImage(image,out.getWidth(),out.getHeight(),null);
            //            out.getGraphics().dispose();
            //
            FileInputStream fis = new FileInputStream(o);
            out = ImageIO.read(fis);
            fis.close();

            // Return the format name
            return out;
        } catch (IOException e) {
            throw new CaptchaException("Unknown error during file reading ", e);
        }
    }

    /**
     * Generates a backround image on wich text will be paste. Implementations must take into account the imageHeigt and
     * imageWidth.
     *
     * @return the background image
     */
    public BufferedImage getBackground() {
        return (BufferedImage) images.get(myRandom.nextInt(images.size()));
    }

}

