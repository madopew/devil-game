package kz.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
//класс с картинкой и преобразованием в текстуру
public class ImageResource {
	//изначально и текстура и картинка равны нулю
	private Texture texture = null;
	private BufferedImage image = null;
	public ImageResource(String path) {
		//при инициализции достаем картину по ссылке и сохраняем ее
		URL url = ImageResource.class.getResource(path);
		try {
			image = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(image != null)
			image.flush();
	}
	//достаем текстуру из картинки
	public Texture getTexture() {
		//если картинки нет возвращаем ноль
		if(image == null)
			return null;
		//если есть создаем текстуру для оболочки
		if(texture == null)
			texture = AWTTextureIO.newTexture(Renderer.getProfile(), image, true);
		return texture;
	}
}
