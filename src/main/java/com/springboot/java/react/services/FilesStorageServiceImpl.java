package com.springboot.java.react.services;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {

//  RESIZE IMAGE SIZE
  private static final int IMG_WIDTH = 100;
  private static final int IMG_HEIGHT = 100;
		
  private final Path root = Paths.get("src/main/resources/static");
  

  @Override
  public void init() {
    try {
      Files.createDirectories(root);
    } catch (IOException e) {
    	System.out.println("Could not initialize folder for upload!");
      throw new RuntimeException("Could not initialize folder for upload!");
    }
  }

  @Override
  public void save(MultipartFile file, String newfile) {
    try {
    	
    	BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
    	
    	// RESIZE NEW IMAGE
        Image newResizedImage = src.getScaledInstance(IMG_WIDTH, IMG_HEIGHT, Image.SCALE_SMOOTH);
		File destination = new File("src/main/resources/static/users/" + newfile);

		// WRITE NEW RESIZE IMAGE TO static/users FOLDER
		ImageIO.write(convertToBufferedImage(newResizedImage), "png", destination);    	
    	
    } catch (Exception e) {
      if (e instanceof FileAlreadyExistsException) {
        throw new RuntimeException("A file of that name already exists.");
      }
      throw new RuntimeException(e.getMessage());
    }
  }

  @Override
  public Resource load(String filename) {
    try {
      Path file = root.resolve(filename);
      Resource resource = new UrlResource(file.toUri());

      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new RuntimeException("Could not read the file!");
      }
    } catch (MalformedURLException e) {
      throw new RuntimeException("Error: " + e.getMessage());
    }
  }

  @Override
  public void deleteAll() {
    FileSystemUtils.deleteRecursively(root.toFile());
  }

  @Override
  public Stream<Path> loadAll() {
    try {
      return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
    } catch (IOException e) {
      throw new RuntimeException("Could not load the files!");
    }
  }

//  CONVERSION OF IMAGE TO BUFFERIMAGE
  public static BufferedImage convertToBufferedImage(Image img) {

      if (img instanceof BufferedImage) {
          return (BufferedImage) img;
      }

      // Create a buffered image with transparency
      BufferedImage bi = new BufferedImage(
              img.getWidth(null), img.getHeight(null),
              BufferedImage.TYPE_INT_ARGB);

      Graphics2D graphics2D = bi.createGraphics();
      graphics2D.drawImage(img, 0, 0, null);
      graphics2D.dispose();

      return bi;
  }
  
}
